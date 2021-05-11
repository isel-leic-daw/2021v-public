import * as React from 'react'
import * as ReactDOM from 'react-dom'
import { ToDoModel } from './ToDoModel'
import { ToDoList } from './ToDoList'

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

export function demo() {
  ReactDOM.render(
    <ToDoList caption="Here are your todos" todos={model} />,
    document.getElementById('container')
  )
}
