import React, { ChangeEvent, useEffect, useState } from 'react'
import ReactDOM from 'react-dom'

type Validator = (input: string) => Promise<true | string>

type InputProps = {
  validator: Validator
}
function Input({ validator }: InputProps) {
  const [value, setValue] = useState('')
  const [error, setError] = useState('')

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
        if (typeof res === 'string') {
          setError(res)
        }
      } catch (error) {
        // we ignore errors on the validation process
      }
    }, 500)
    return () => {
      console.log('cancelling effect')
      clearTimeout(tid)
      cancelled = true
    }
  }, [value, validator])

  function onChangeHandler(event: ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
    setError(undefined)
  }

  return (
    <>
      <div>
        <input type='text' value={value} onChange={onChangeHandler} />
      </div>
      <div>{error}</div>
    </>
  )
}

function delay<T>(ms: number, value: T): Promise<T> {
  return new Promise((resolve, reject) => {
    setTimeout(() => resolve(value), ms)
  })
}

async function evenValidator(input: String): Promise<true | string> {
  await delay(1000, undefined)
  return input.length % 2 == 0 ? true : 'must have even size'
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

