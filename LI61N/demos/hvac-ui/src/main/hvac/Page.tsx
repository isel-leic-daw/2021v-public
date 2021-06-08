import { useContext, useEffect, useState, MouseEventHandler } from 'react'

import './Page.css'

import { PowerButton } from './components/PowerButton'
import { TemperatureCard } from './components/temperature/TemperatureCard'
import { PowerState, toggle, Temperature, ControlledTemperature, PowerStateDto } from './Model'
import { Credentials } from '../login/UserSession'
import { Service, getMockedService, getService } from './Service'

import * as UserSession from '../login/UserSession'
import * as API from '../common/FetchUtils'
import * as Siren from '../common/Siren'

/**
 * Type that specifies the props object for the Hvac control page.
 * @property viewModel  - the associated view model instance
 */
type PageProps = {
  service: Service
}

/**
 * Types used to represent the page's power state and power update state. 
 */
type PowerStateUpdate = API.Request<Siren.Entity<PowerStateDto>> 
type PowerStateInfo = API.FetchInfo<Siren.Entity<PowerStateDto>> 

/**
 * Gets the power state value from the given information produced by the API.
 * @param powerState - The API response information.
 * @returns the corresponding power state value, or undefined if no value is found.
 */
function getPowerStateValue(powerState?: PowerStateInfo): PowerState | undefined {
  return powerState && powerState.result && powerState.result?.body?.properties?.value
}

/**
 * Toggles the power state value found in the given information produced by the API.
 * @param powerState - The API response information.
 * @returns the toggled value, or 
 */
function togglePowerStateValue(powerState: PowerStateInfo): PowerState | undefined {
  if (powerState.status !== API.FetchState.SUCCESS)
    return undefined
  const currentValue = powerState.result?.body?.properties?.value
  return currentValue && toggle(currentValue)
} 

/**
 * The HVAC control page.
 * @argument props - the page's props object.
 * @returns The React Element used to render the page.
 */
export function Page(props: PageProps) {
  
  const [powerState, setPowerState] = useState<PowerStateInfo | undefined>()
  const [powerStateUpdate, setPowerStateUpdate] = useState<PowerStateUpdate>(props.service.getPowerState())

  const [temperatureState, setTemperatureState] = useState<ControlledTemperature | undefined>()
  const userSession = useContext(UserSession.Context)

  useEffect(() => { 
    async function sendPowerRequest(request: API.Request<Siren.Entity<PowerStateDto>>) {
      try {
        setPowerState({ status: API.FetchState.NOT_READY })
        const result: API.Result<Siren.Entity<PowerStateDto>> = await request.send()
        
        setPowerState({ 
          status: result.header.ok && result.body ? API.FetchState.SUCCESS : API.FetchState.ERROR,
          result
        })
      }
      catch(reason) {
        if(reason.name !== 'AbortError')
          setPowerState({ status: API.FetchState.ERROR })
      }
    }

    sendPowerRequest(powerStateUpdate)
    // TODO: Support cancelation

  }, [props.service, powerStateUpdate])

  function handlePowerToggle() {
    if (powerState?.status === API.FetchState.SUCCESS) {
      const newValue = togglePowerStateValue(powerState)
      if (newValue) setPowerStateUpdate(props.service.setPowerState( { value: newValue }))
    }
  }

  /*
  useEffect(() => {
    async function loadTemperature() {
      console.log("Loading temperature ...")
      const currTemperature = await props.service.getTemperature()
      setTemperatureState(currTemperature)
    }
    console.log("Running temperature state effect ...")
    if (!temperatureState) loadTemperature()
  }, [props.service, temperatureState])
  */
  /*
  console.log(`ControlPage.render(): 
    powerstate = ${JSON.stringify(powerState)} 
    temperatureState = ${JSON.stringify(temperatureState)}`)
    */

  async function handleTargetTemperatureChange(newTemperature: Temperature): Promise<void> {
    if (temperatureState) {
      setTemperatureState({ current: temperatureState.current, target: undefined})
      const newTarget = await props.service.setTargetTemperature(newTemperature)
      setTemperatureState( { current: temperatureState.current, target: newTarget } )
    }
  }
  
  return (
    !userSession?.credentials ? <> </> : 
    <>
      <button className="ui mini basic icon button" 
        style={{ float: 'right'}} 
        onClick={() => { userSession?.logout() }}>
          <i className="sign-out icon" />
      </button>
      <PageHeader powerState={powerState} onClick={handlePowerToggle} />
      <PageBody 
        temperature={temperatureState} 
        handleTemperatureChange={handleTargetTemperatureChange} />
    </>
  )
}

type PageHeaderProps = {
  powerState?: PowerStateInfo,
  onClick?: MouseEventHandler<HTMLButtonElement>
}

/**
 * The header of the HVAC control page.
 * @param {PowerButtonProps} props - The props object.
 * @returns The React Element used to render the page's header (i.e. ON/OFF button).
 */
function PageHeader(props: PageHeaderProps) {

  console.log(JSON.stringify(props.powerState))

  const power = getPowerStateValue(props.powerState)
  const hasSetAction: boolean = !!props.powerState?.result?.body?.actions?.find(
    (action: Siren.Action) => action.name === Siren.SET_POWER_STATE_ACTION
  )

  return (
      <div className="Control-header">
        <div className="ui massive floating message">
            <p>HVAC is </p>
            <PowerButton state={power} onClick={props.onClick} disabled={!hasSetAction} />
        </div>
      </div>
  )
}

/**
 * Type that specifies the prop object for the PageBody component that displays the 
 * temperature information, both current and desired temperature.
 */
type TemperatureProps = {
  temperature?: ControlledTemperature
  handleTemperatureChange?: (newTemperature: Temperature) => void
}

/**
 * The body of the HVAC control page.
 * @param {TemperatureProps} props - The props object.
 * @returns The React Element used to render the page's body (i.e. temperature controls).
 */
function PageBody(props: TemperatureProps) {
  return (
    <div className="ui text container Control-body">
      <div className="ui centered cards">
        <TemperatureCard value={props.temperature?.current} label="Current" />
        <TemperatureCard value={props.temperature?.target} label="Target" 
          editable={true} disabled={false} 
          handleSetTemperature={props.handleTemperatureChange} />
      </div>
    </div>
  )
}

/**
 * Creates an implementation of the HVAC control service. If any of the resource's URL is not provided, a mocked 
 * implementation is produced.
 * @param temperature - the temperature resource URL.
 * @param powerState  - the power state resource URL.
 * @param credentials - the user's credentials.
 * @returns The service instance.
 */
 export function createService(temperature?: URL, powerState?: URL, credentials?: Credentials): Service {
   if (!temperature || !powerState || !credentials)
    return getMockedService({ value: PowerState.OFF }, { current: new Temperature(21), target: new Temperature(21) })
  else return getService(temperature, powerState, credentials)
}
