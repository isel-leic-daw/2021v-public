import { Temperature } from '../../Model'

/**
 * Contract to be supported by objects passed as props to the TemperatureDisplay component.
 * @property label  - The label to be displayed
 * @property value  - The temperature value
 */
export interface TemperatureDisplayProps {
  label: string,
  value?: Temperature
}

/**
 * Component used to display a temperature value, along with its label.
 * @param {TemperatureDisplayProps} props - The props object.
 * @returns the React Element used to render the component.
 */
export function TemperatureDisplay(props: TemperatureDisplayProps) {
  const { value, label } = props
  return (
    <div className="ui huge statistic">
      <div className="value"> {value ? value.value : '--'}&deg;</div>
      <div className="label"> {label} </div>
    </div>  
  )
}

