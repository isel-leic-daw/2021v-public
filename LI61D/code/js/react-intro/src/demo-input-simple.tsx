import React, { ChangeEvent, useEffect, useState } from 'react'
import ReactDOM from 'react-dom'

function Input({ }) {
  const [value, setValue] = useState('')
  const [error, setError] = useState('')
  function onChangeHandler(event: ChangeEvent<HTMLInputElement>) {
    const inputValue = event.target.value
    if (inputValue.length < 10) {
      setValue(inputValue)
      setError('')
    } else {
      setError('Must not exceeed 10 chars')
    }
  }
  return (
    <>
      <div>
        <input type='text' value={value} onChange={onChangeHandler} />
        <p>{error}</p>
      </div>
    </>
  )
}

export function demo() {

  ReactDOM.render(
    <div>
      <h1>Input example</h1>
      <Input />
    </div>,
    document.getElementById('container')
  )
}

