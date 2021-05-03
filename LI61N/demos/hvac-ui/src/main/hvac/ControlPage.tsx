import React, { MouseEvent } from 'react'
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
  targetTemperature: number
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
        <TemperatureCard value={props.targetTemperature} label="Target" editable={true} disabled={false} />
      </div>
    </div>
  )
}

/**
 * The HVAC control page.
 * @returns The React Element used to render the page.
 */
export default function ControlPage() {
  
  function handlePowerToggle(evt: MouseEvent<HTMLButtonElement>): void {
    console.log(evt.currentTarget)
  }
  
  return (
    <>
      <PageHeader state={PowerState.OFF} onClick={handlePowerToggle} />
      <PageBody currentTemperature={21} targetTemperature={21} />
    </>
  )
}