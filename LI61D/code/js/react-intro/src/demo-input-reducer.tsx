import React, { ChangeEvent, useEffect, useReducer } from 'react'
import ReactDOM from 'react-dom'

type Validator = (input: string) => Promise<true | string>

type State = {
  value: string
  valid: undefined | true | string
}

type Action =
  { type: 'set-value', value: string }
  | { type: 'set-validation-result', value: string, result: true | string }

function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'set-value': return { value: action.value, valid: undefined }
    case 'set-validation-result':
      if (state.value === action.value) {
        return { value: action.value, valid: action.result }
      } else {
        return state
      }
  }
}

type InputProps = {
  validator: Validator
}
function Input({ validator }: InputProps) {

  const [{ value, valid }, dispatch] = useReducer(reducer, { value: '', valid: true })

  useEffect(() => {
    let cancelled = false
    const tid = setTimeout(async () => {
      try {
        console.log(`validating '${value}'...`)
        const res = await validator(value)
        if (cancelled) {
          console.log(`... '${value}' validation cancelled`)
          return
        }
        console.log(`... '${value}' validation completed`)
        dispatch({ type: 'set-validation-result', value: value, result: res })
      } catch (error) {
        // we ignore errors on the validation process
      }
    }, 500)
    return () => {
      clearTimeout(tid)
      cancelled = true
    }
  }, [value, dispatch])

  function onChange(event: ChangeEvent<HTMLInputElement>) {
    dispatch({ type: 'set-value', value: event.target.value })
  }

  return (
    <>
      <div>
        <input type='text' value={value} onChange={onChange} />
      </div>
      <div>{typeof valid === 'string' ? valid : ''}</div>
    </>
  )
}

function delay<T>(ms: number, value: T): Promise<T> {
  return new Promise((resolve, reject) => {
    setTimeout(() => resolve(value), ms)
  })
}

async function evenValidator(input: String): Promise<true | string> {
  return input.length % 2 == 0 ? await delay(1000, true) : await delay(1000, 'must have even size')
}

export function demo() {

  ReactDOM.render(
    <div>
      <h1>Input example</h1>
      <Input validator={evenValidator} />
    </div>,
    document.getElementById('container')
  )
}

