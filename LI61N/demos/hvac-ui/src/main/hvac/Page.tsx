import { useContext, useEffect, useState, MouseEventHandler } from 'react'

import './Page.css'

import { PowerButton } from './components/PowerButton'
import { TemperatureCard } from './components/temperature/TemperatureCard'
import { PowerState, toggle, Temperature, ControlledTemperature, TemperatureDto, PowerStateValue } from './Model'
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
  service: Service,
  apiBaseUrl: string
}

/**
 * Types used to represent the page's power state and power update state. 
 */
type PowerStateUpdate = API.Request<Siren.Entity<PowerStateValue>> 
type PowerStateInfo = API.FetchInfo<Siren.Entity<PowerStateValue>> 

/**
 * Types used to represent the page's temperature state and temperature update state. 
 */
type TemperatureUpdate = API.Request<Siren.Entity<TemperatureDto>>
type TemperatureInfo = API.FetchInfo<Siren.Entity<TemperatureDto>>

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

  const [temperatureState, setTemperatureState] = useState<TemperatureInfo | undefined>()
  const [temperatureUpdate, setTemperatureUpdate] = useState<TemperatureUpdate>(props.service.getTemperature())
  const userSession = useContext(UserSession.Context)

  useEffect(() => { 
    async function sendPowerRequest(request: PowerStateUpdate) {
      try {
        setPowerState({ status: API.FetchState.NOT_READY })
        const result: API.Result<Siren.Entity<PowerStateValue>> = await request.send()
        if (powerStateUpdate.isCanceled) return
        
        setPowerState({ 
          status: result.header.ok && result.body ? API.FetchState.SUCCESS : API.FetchState.ERROR,
          result
        })
      }
      catch(reason) {
        if (!powerStateUpdate.isCanceled) return
        setPowerState({ status: API.FetchState.ERROR })
      }
    }

    sendPowerRequest(powerStateUpdate)
    return powerStateUpdate.cancel

  }, [props.service, powerStateUpdate])

  function handlePowerToggle() {
    if (powerState?.status === API.FetchState.SUCCESS) {
      const newValue = togglePowerStateValue(powerState)
      if (newValue) setPowerStateUpdate(props.service.setPowerState( { value: newValue }))
    }
  }

  useEffect(() => {
    async function sendTemperatureRequest(request: TemperatureUpdate) {
      try {
        setTemperatureState({ status: API.FetchState.NOT_READY })
        const result: API.Result<Siren.Entity<TemperatureDto>> = await request.send()
        if (request.isCanceled) return

        setTemperatureState({ 
          status: result.header.ok && result.body ? API.FetchState.SUCCESS : API.FetchState.ERROR,
          result
        })
      }
      catch(reason) {
        if (request.isCanceled) return
        setPowerState({ status: API.FetchState.ERROR })
      }
    }

    sendTemperatureRequest(temperatureUpdate)
    return temperatureUpdate.cancel

  }, [props.service, temperatureUpdate])

  function handleTargetTemperatureChange(newTemperature: Temperature) {

    if (temperatureState?.result && temperatureState?.status === API.FetchState.SUCCESS) {
      const updateUrl = temperatureState?.result?.body && Siren.getAction(temperatureState?.result?.body, Siren.SET_TARGET_ACTION)
      if (updateUrl)
        setTemperatureUpdate(
          props.service.setTargetTemperature(
            new URL(`${props.apiBaseUrl}${updateUrl.href}`), 
            newTemperature
          )
        )
    }
  }
  
  const temperature = temperatureState?.result?.body?.properties ? {
    current: new Temperature(temperatureState?.result?.body?.properties.current),
    target: new Temperature(temperatureState?.result?.body?.properties.target)
  } : undefined

  const targetEditable = temperatureState?.result?.body && Siren.getAction(temperatureState.result.body, Siren.SET_TARGET_ACTION)
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
        targetEditable={!!targetEditable}
        temperature={temperature} 
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

  const power = getPowerStateValue(props.powerState)
  const hasSetAction = props.powerState?.result?.body && 
    Siren.getAction(props.powerState?.result?.body, Siren.SET_POWER_STATE_ACTION)

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
type PageBodyProps = {
  targetEditable: boolean,
  temperature?: ControlledTemperature,
  handleTemperatureChange?: (newTemperature: Temperature) => void
}

/**
 * The body of the HVAC control page.
 * @param {TemperatureProps} props - The props object.
 * @returns The React Element used to render the page's body (i.e. temperature controls).
 */
function PageBody(props: PageBodyProps) {
  return (
    <div className="ui text container Control-body">
      <div className="ui centered cards">
        <TemperatureCard value={props.temperature?.current} label="Current" />
        <TemperatureCard value={props.temperature?.target} label="Target" 
          editable={props.targetEditable} disabled={false} 
          handleSetTemperature={props.handleTemperatureChange} />
      </div>
    </div>
  )
}

/**
 * Creates an implementation of the HVAC control service. If any of the resource's URL is not provided, a mocked 
 * implementation is produced.
 * @param temperature       - the temperature resource URL.
 * @param powerState        - the power state resource URL.
 * @param credentials       - the user's credentials.
 * @returns The service instance.
 */
 export function createService(temperature?: URL, powerState?: URL, credentials?: Credentials): Service {
   if (!temperature || !powerState || !credentials)
    return getMockedService({ value: PowerState.OFF }, { current: 21, target: 21 })
  else return getService(temperature, powerState, credentials)
}
