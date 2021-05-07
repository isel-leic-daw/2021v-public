import React, { MouseEvent, useState } from 'react'
import { PowerButton, PowerState, PowerButtonProps } from './PowerButton'
import { TemperatureCard } from './temperature/TemperatureCard'
import './ControlPage.css'

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
  currentTemperature: number,
  targetTemperature: number,
  handleTemperatureChange?: (newTemperature: number) => void
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
        <TemperatureCard value={props.currentTemperature} label="Current" />
        <TemperatureCard value={props.targetTemperature} label="Target" 
          editable={true} disabled={false} 
          handleSetTemperature={props.handleTemperatureChange} />
      </div>
    </div>
  )
}

/**
 * The HVAC control page.
 * @returns The React Element used to render the page.
 */
export default function ControlPage() {
  
  const [powerState, setPowerState] = useState(PowerState.OFF)
  const [temperatureState, setTemperatureState] = useState({ current: 21, target: 21 })

  console.log(`ControlPage.render(): 
    powerstate = ${JSON.stringify(powerState)} 
    temperatureState = ${JSON.stringify(temperatureState)}`)

  function handlePowerToggle(evt: MouseEvent<HTMLButtonElement>): void {
    // TODO: Actually interact with the Web API
    const nextPowerState = powerState === PowerState.ON ? PowerState.OFF : PowerState.ON
    setPowerState(nextPowerState)
    console.log(`Powerstate is = ${powerState}. Changed it to ${nextPowerState}`)
  }

  function handleTargetTemperatureChange(newTemperature: number): void {
    // TODO: Actually interact with the Web API
    setTemperatureState( { current: temperatureState.current, target: newTemperature } )
    console.log(`Target temperature is ${temperatureState.current}. Changed it to ${newTemperature}`)
  }
  
  return (
    <>
      <PageHeader state={powerState} onClick={handlePowerToggle} />
      <PageBody 
        currentTemperature={temperatureState.current} 
        targetTemperature={temperatureState.target} 
        handleTemperatureChange={handleTargetTemperatureChange} />
    </>
  )
}