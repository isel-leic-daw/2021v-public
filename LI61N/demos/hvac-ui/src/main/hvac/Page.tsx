import { useContext, useEffect, useState } from 'react'

import './Page.css'

import { PowerButton, PowerButtonProps } from './components/PowerButton'
import { TemperatureCard } from './components/temperature/TemperatureCard'
import { PowerState, toggle, Temperature, ControlledTemperature } from './Model'

import * as UserSession from '../login/UserSession'

/**
 * Type that specifies the props object for the Hvac control page.
 * @property viewModel  - the associated view model instance
 */
type PageProps = {
  service: Service
}

/**
 * The HVAC control page.
 * @argument props - the page's props object.
 * @returns The React Element used to render the page.
 */
export function Page(props: PageProps) {
  
  // TODO: Revisit the representation for data being fetched (currently we use undefined)
  const [powerState, setPowerState] = useState<PowerState | undefined>()
  const [temperatureState, setTemperatureState] = useState<ControlledTemperature | undefined>()
  const userSession = useContext(UserSession.Context)

  // TODO: Revisit useEffect to handle cancellattion of the async operation
  useEffect(() => { 
    async function loadPowerState() {
      console.log("Loading power state ...")
      const currPowerState = await props.service.getPowerState()
      setPowerState(currPowerState)
    }
    console.log("Running power state effect ...")
    if (!powerState) loadPowerState()
  }, [props.service, powerState])


  useEffect(() => {
    async function loadTemperature() {
      console.log("Loading temperature ...")
      const currTemperature = await props.service.getTemperature()
      setTemperatureState(currTemperature)
    }
    console.log("Running temperature state effect ...")
    if (!temperatureState) loadTemperature()
  }, [props.service, temperatureState])

  console.log(`ControlPage.render(): 
    powerstate = ${JSON.stringify(powerState)} 
    temperatureState = ${JSON.stringify(temperatureState)}`)

  async function handlePowerToggle(): Promise<void> {
    if (powerState) {
      setPowerState(undefined)
      const nextPowerState = await props.service.setPowerState(toggle(powerState))
      setPowerState(nextPowerState)
    }
  }

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
        onClick={() => {userSession?.logout() }}>
          <i className="sign-out icon" />
      </button>
      <PageHeader state={powerState} onClick={handlePowerToggle} />
      <PageBody 
        temperature={temperatureState} 
        handleTemperatureChange={handleTargetTemperatureChange} />
    </>
  )
}

/**
 * Contract to be supported by the service used by the ControlPage.
 */
export interface Service {
  setPowerState: (state: PowerState) => Promise<PowerState>
  getPowerState: () => Promise<PowerState>
  getTemperature: () => Promise<ControlledTemperature>
  setTargetTemperature: (value: Temperature) => Promise<Temperature>
}

/**
 * Creates the page view model.
 * @argument mocked - A boolean value indicating whether the service should be mocked or not.
 * @returns The service instance.
 */
export function createService(mocked: Boolean): Service {
  return getMockedService(PowerState.OFF, { current: new Temperature(21), target: new Temperature(21) })
}

/**
 * The header of the HVAC control page.
 * @param {PowerButtonProps} props - The props object.
 * @returns The React Element used to render the page's header (i.e. ON/OFF button).
 */
function PageHeader(props: PowerButtonProps) {
  return (
      <div className="Control-header">
        <div className="ui massive floating message">
          <p>HVAC is </p>
          <PowerButton state={props.state} onClick={props.onClick} />
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
 * A mocked implementation of the HVAC control service.
 * @param initialState - the initial HVAC power state.
 * @param initialTemperature - the initial temperature information.
 * @returns the newly instantiated service mock.
 */
function getMockedService(initialState: PowerState, initialTemperature: ControlledTemperature): Service {
  let mockedPowerState = initialState
  let mockedTemperature = initialTemperature
  return {
    setPowerState: async (state: PowerState): Promise<PowerState> => {
      return new Promise<PowerState>((resolve, _) => {
        setTimeout(() => { mockedPowerState = state; resolve(mockedPowerState) }, 5000)
      })
    },

    getPowerState: async (): Promise<PowerState> => {
      return new Promise<PowerState>((resolve, _) => {
        setTimeout(() => { resolve(mockedPowerState) }, 5000)
      })
    },

    getTemperature: async (): Promise<ControlledTemperature> => {
      return new Promise<ControlledTemperature>((resolve, _) => {
        setTimeout(() => { resolve(mockedTemperature) }, 5000)
      })
    },

    setTargetTemperature: async (value): Promise<Temperature> => {
      return new Promise<Temperature>((resolve, _) => {
        setTimeout(() => { resolve(value) }, 5000)
      })
    }
  }
}


