import * as React from 'react'
import * as ReactDOM from 'react-dom'

export function demo() {

  let counter = 0

  function handler() {
    counter += 1
    // Notice how each render call defines the *complete* UI
    console.log('Calling ReactDOM.render')
    ReactDOM.render(
      // This is the virtual DOM
      <div>
        <input type="text" />
        <input type="text" />
        <p>{counter}</p>
      </div>
      , document.getElementById('container'))
  }

  setInterval(handler, 1000)
}

