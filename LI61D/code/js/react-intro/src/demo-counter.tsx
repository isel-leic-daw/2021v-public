import React, { useState } from 'react'
import ReactDOM from 'react-dom'

function Counter({ title }: { title: string }) {
  const [counter, setCounter] = useState(0)
  const [clicks, setClicks] = useState(0)
  function inc() {
    setCounter(counter + 1)
    setClicks(clicks + 1)
  }
  function dec() {
    setCounter(counter - 1)
    setClicks(clicks + 1)
  }
  return (
    <div>
      <h2>{title}</h2>
      <p>Number of clicks {clicks}</p>
      <p><button onClick={inc}>+</button></p>
      {counter}
      <p><button onClick={dec}>-</button></p>
    </div>
  )
}

let iteration = 1

export function demo() {
  setInterval(() => {
    iteration += 1
    ReactDOM.render(
      <div>
        <p>{iteration}</p>
        <Counter title={`Counter 1 - ${iteration}`} />
        <Counter title={`Counter 2 - ${iteration}`} />
      </div>
      ,
      document.getElementById('container')
    )
  }, 2000)
}
