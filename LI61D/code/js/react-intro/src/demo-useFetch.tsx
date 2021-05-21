import React, { ChangeEvent, useEffect, useState, useCallback } from 'react'
import ReactDOM from 'react-dom'
import { useFetch } from './useFetch'

function Fetch({ }) {
  const [inputValue, setInputValue] = useState('')
  const [uri, setUri] = useState<string>(undefined)
  const fetchState = useFetch(uri)

  const onChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value)
  }

  const onClickHandler = (event: React.MouseEvent<HTMLButtonElement>) => {
    setUri(inputValue)
  }

  const status = fetchState.type == 'response' ? fetchState.status : '?'
  const body = fetchState.type == 'response' ? fetchState.body : ''
  const state = fetchState.type

  return (
    <div>
      <p><input type="text" value={inputValue} onChange={onChangeHandler} /></p>

      <p><button onClick={onClickHandler} >Fetch</button></p>

      <p>URI: {uri}</p>

      <p>{status}</p>

      <p>{state}</p>

      <textarea value={body} rows={25} cols={120} readOnly={true} />
    </div>
  )
}

export function demo() {
  ReactDOM.render(
    <Fetch />,
    document.getElementById('container')
  )
}
