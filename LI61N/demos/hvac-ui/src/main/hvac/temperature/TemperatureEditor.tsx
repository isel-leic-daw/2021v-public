import React, { useRef, useState } from 'react'
import { Temperature } from '../hvacModel'

/**
 * Contract to be supported by objects passed as props to the TemperatureEditor component.
 * @property  value         - the initial temperature value.
 * @property  disabled      - indicates whether the component is disabled or not. Default is false.
 * @property  editable      - indicates whether the component is editable or not. Default is true.
 * @property  submitChange  - callback function used to signal that a new temperature value has been submitted by the user.
 */
export interface TemperatureEditorProps {
  value?: Temperature,
  disabled?: boolean,
  editable?: boolean,
  submitChange?: (newValue: Temperature) => void
}

/**
 * Component used to edit the target temperature value. 
 * @param {TemperatureEditorProps} props - The props object.
 * @returns the React Element used to render the component.
 */
export function TemperatureEditor(props: TemperatureEditorProps) {

  const [editing, setEditing] = useState(false)
  const temperatureInputRef = useRef<HTMLInputElement>(null)
  
  const handleEditClick = () => { setEditing(!editing) }
  const handleCancel = () => { setEditing(!editing) }
  const handleSubmit = () => { 
    setEditing(!editing); 
    if (props.submitChange && props.value)
      props.submitChange(new Temperature(+(temperatureInputRef.current?.value || props.value.toString()))) 
  }
  
  function renderEditingMode()  {
    const temperatureValue = props.value?.value
    return (
      <div className="ui mini input">
        <input type="number" placeholder="Enter new value..." defaultValue={temperatureValue} ref={temperatureInputRef} /> &nbsp;
        <div className="ui small basic icon buttons">
          <div className="ui red basic button" onClick={handleCancel}>
            <i className="close icon" />
          </div>
          <div className="ui green basic button" onClick={handleSubmit}>
            <i className="check icon" />
          </div>
        </div>
      </div>
    )
  }
  

  function renderNonEditingMode() {
    const buttonClass = (!props.disabled) ? 'ui basic blue button' : 'ui disabled loading basic blue button'
  
    return (
      <div style={{ visibility: props.editable ? "visible" : "hidden", height: "30pt" }}>
        <button className={buttonClass} onClick={handleEditClick}>
            <i className="edit icon" />Edit
        </button>
      </div>
    )
  }

  return !editing ? renderNonEditingMode() : renderEditingMode()
}
