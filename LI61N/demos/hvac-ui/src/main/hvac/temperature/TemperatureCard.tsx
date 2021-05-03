
import { TemperatureDisplay } from './TemperatureDisplay'
import { TemperatureEditor } from './TemperatureEditor'

/**
 * Contract to be supported by objects passed as props to the TemperatureCard component.
 * @property label      - The label to be displayed.
 * @property  value     - the temperature value.
 * @property  disabled  - indicates whether the component is disabled or not. Default is false.
 * @property  editable  - indicates whether the temperature value is editable or not. Default is false.
 */
export interface TemperatureCardProps {
  label: string,
  value: number,
  disabled?: boolean,
  editable?: boolean
}

/**
 * A Component used to display and eventually edit a temperature.
 */
export function TemperatureCard(props: TemperatureCardProps) {
  return (
    <div className="ui raised centered card">
      <div className="content">
        <TemperatureDisplay { ... props } />
      </div>
      <div className="extra content">
        <TemperatureEditor { ... props } />
      </div>
    </div>
  )
}
