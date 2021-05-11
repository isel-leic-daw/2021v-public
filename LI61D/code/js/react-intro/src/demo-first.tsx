import React from 'react'
import ReactDOM from 'react-dom'

const virtualTree =
  React.createElement('div', {},
    React.createElement('ul', {},
      React.createElement('li', {}, 'first item'),
      React.createElement('li', {}, 'second item')))

const virtualTreeUsingJsx = (
  <div>
    <ul>
      <li>first item</li>
      <li>second item</li>
      <li>third item</li>
    </ul>
  </div>
)

let counter = 0

function render() {
  counter += 1
  return (
    <div>
      <p><input type='text' /></p>
      <p><input type='text' /></p>
      <p>{counter}</p>
      {counter % 2 == 0 ? <p>even</p> : null}
    </div>
  )
}

export function demo() {
  setInterval(
    () => ReactDOM.render(
      render(),
      document.getElementById('container'))
    , 2000)
}
