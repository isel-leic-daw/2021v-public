
/**
 * Contract to be supported by objects passed as props to the TemperatureEditor component.
 * @property  value     - the initial temperature value.
 * @property  disabled  - indicates whether the component is disabled or not. Default is false.
 * @property  editable  - indicates whether the component is editable or not. Default is true.
 */
export interface TemperatureEditorProps {
  value: number,
  disabled?: boolean,
  editable?: boolean
}

/**
 * Component used to edit the target temperature value.
 * @param {TemperatureEditorProps} props - The props object.
 * @returns the React Element used to render the component.
 */
export function TemperatureEditor(props: TemperatureEditorProps) {

  const buttonClass = (!props.disabled) ? 'ui basic blue button' : 'ui disabled loading basic blue button'
  return (
    <div style={{ visibility: props.editable ? "visible" : "hidden", height: "30pt" }}>
      <div className={buttonClass} >
          <i className="edit icon" />Edit
      </div>
    </div>
  )
}