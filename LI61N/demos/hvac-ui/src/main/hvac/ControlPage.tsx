import React, { MouseEvent, useEffect, useState } from 'react'
import { PowerButton, PowerButtonProps } from './PowerButton'
import { TemperatureCard } from './temperature/TemperatureCard'
import { PowerState, toggle, Temperature, ControlledTemperature } from './hvacModel'
import './ControlPage.css'
import { ControlPageViewModel } from './ControlPageViewModel'

/**
 * Contract to be supported by objects passed as props to the LoginPage component.
 * @property viewModel  - the associated view model instance
 */
interface ControlPageProps {
  viewModel: ControlPageViewModel
}

/**
 * The HVAC control page.
 * @argument props - the page's props object.
 * @returns The React Element used to render the page.
 */
export default function ControlPage(props: ControlPageProps) {
  
  // TODO: Revisit the representation for data being fetched (currently we use undefined)
  const [powerState, setPowerState] = useState<PowerState | undefined>()
  const [temperatureState, setTemperatureState] = useState<ControlledTemperature | undefined>()

  useEffect(() => { 
    async function loadPowerState() {
      console.log("Loading power state ...")
      const currPowerState = await props.viewModel.getPowerState()
      setPowerState(currPowerState)
    }
    console.log("Running power state effect ...")
    if (!powerState) loadPowerState()
  }, [props.viewModel, powerState])


  useEffect(() => {
    async function loadTemperature() {
      console.log("Loading temperature ...")
      const currTemperature = await props.viewModel.getTemperature()
      setTemperatureState(currTemperature)
    }
    console.log("Running temperature state effect ...")
    if (!temperatureState) loadTemperature()
  }, [props.viewModel, temperatureState])

  console.log(`ControlPage.render(): 
    powerstate = ${JSON.stringify(powerState)} 
    temperatureState = ${JSON.stringify(temperatureState)}`)

  async function handlePowerToggle(evt: MouseEvent<HTMLButtonElement>): Promise<void> {
    if (powerState) {
      setPowerState(undefined)
      const nextPowerState = await props.viewModel.setPowerState(toggle(powerState))
      setPowerState(nextPowerState)
    }
  }

  async function handleTargetTemperatureChange(newTemperature: Temperature): Promise<void> {
    if (temperatureState) {
      setTemperatureState({ current: temperatureState.current, target: undefined})
      const newTarget = await props.viewModel.setTargetTemperature(newTemperature)
      setTemperatureState( { current: temperatureState.current, target: newTarget } )
    }
  }
  
  return (
    <>
      <PageHeader state={powerState} onClick={handlePowerToggle} />
      <PageBody 
        temperature={temperatureState} 
        handleTemperatureChange={handleTargetTemperatureChange} />
    </>
  )
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

interface TemperatureProps {
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

