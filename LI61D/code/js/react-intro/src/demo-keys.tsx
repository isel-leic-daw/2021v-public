import React from 'react'
import ReactDOM from 'react-dom'
import { ToDoModel } from './ToDoModel'

export function demo() {

  let nextId = 2
  const model: Array<ToDoModel> = [
    {
      id: 0,
      name: 'Learn javascript'
    },
    {
      id: 1,
      name: 'Leart react'
    }
  ]

  function handler() {
    // adding a new item
    nextId += 1
    model.push({ id: nextId, name: `item no ${nextId}` })
    // Notice how each render call defines the *complete* UI
    console.log('Calling ReactDOM.render')
    ReactDOM.render(
      // This is the virtual DOM
      <div>
        <ul>
          {model.map(item => <li key={item.id}>{item.id}:{item.name}<input type="text" /></li>)}
        </ul>
      </div>
      , document.getElementById('container'))
  }

  setInterval(handler, 1000)
}
