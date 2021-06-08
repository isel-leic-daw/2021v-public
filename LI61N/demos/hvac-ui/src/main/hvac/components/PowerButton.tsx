import { MouseEventHandler } from 'react'
import { PowerState } from '../Model'

/**
 * Contract to be supported by objects passed as props to the PowerButton component.
 * @property state    - The power state to be displayed
 * @property onClick  - The callback function to handle button click events
 */
export interface PowerButtonProps {
  state?: PowerState
  disabled?: boolean
  onClick?: MouseEventHandler<HTMLButtonElement>
}

/**
 * A customized button used to represent and control the power state of equipments.
 * @param {PowerButtonProps} props - The props object.
 * @returns the React Element used to render the component.
 */
export function PowerButton(props: PowerButtonProps) {
  const color = props.state === PowerState.ON ? " green" : ""
  const buttonContent = <><i className={"power off icon" + color}/> {props.state}</>

  return props.state ? (
    props.disabled ? <button className={"ui basic button" + color} disabled>{buttonContent}</button> : 
    <button className={"ui basic button" + color} onClick={props.onClick}>
      {buttonContent}
    </button>

  ) : (
    <button className="ui basic loading button">Loading</button>
  )
}
