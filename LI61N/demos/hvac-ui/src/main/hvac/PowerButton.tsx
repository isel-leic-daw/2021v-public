import React, { MouseEventHandler } from 'react'
import { PowerState } from './hvacModel'

/**
 * Contract to be supported by objects passed as props to the PowerButton component.
 * @property state    - The power state to be displayed
 * @property onClick  - The callback function to handle button click events
 */
export interface PowerButtonProps {
  state?: PowerState
  onClick?: MouseEventHandler<HTMLButtonElement>
}

/**
 * A customized button used to represent and control the power state of equipments.
 * @param {PowerButtonProps} props - The props object.
 * @returns the React Element used to render the component.
 */
export function PowerButton(props: PowerButtonProps) {
  const color = props.state === PowerState.ON ? " green" : ""
  return props.state ? (
    <button className={"ui basic button" + color} onClick={props.onClick}>
      <i className={"power off icon" + color}/> {props.state}
    </button>
  ) : (
    <button className="ui basic loading button">Loading</button>
  )
}
