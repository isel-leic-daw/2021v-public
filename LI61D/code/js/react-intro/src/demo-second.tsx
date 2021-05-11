import React, { ReactChildren } from 'react'
import ReactDOM from 'react-dom'

type ToDoModel = {
  id: number
  name: string,
  desc: string
}

const todos = [
  {
    id: 1,
    name: 'learn javascript',
    desc: 'Something something'
  },
  {
    id: 2,
    name: 'learn react',
    desc: 'Something something'
  },
]

// - function that receives a *single* parameter
// - this single parameter is an object with a model field
// - this single parameter is typically called "props" (from the word properties)
type ToDoProps = { model: ToDoModel, children: React.ReactNode }
function ToDo({ model, children }: ToDoProps) {
  console.log(`rendering todo ${model.id}`)
  return (
    <div key={model.id}>
      <h2>{model.name}</h2>
      <p>{model.desc}</p>
      <p><input type='text' /></p>
      <div style={{ color: 'red' }}>
        {children}
      </div>
    </div>
  )
}

function render(todos: Array<ToDoModel>) {
  return (
    <div>
      <h1>My todos</h1>
      {todos.map(todo => (
        <ToDo model={todo} key={todo.id}>
          <p>HEEEELOOOOOOOOO</p>
          <p>HEEEELOOOOOOOOO</p>
        </ToDo>
      ))}
    </div>
  )
}

let nextId = 2

export function demo() {

  setInterval(() => {
    nextId += 1
    todos.unshift({
      id: nextId,
      name: `todo ${nextId}`,
      desc: 'Something something'
    })
    ReactDOM.render(
      render(todos),
      document.getElementById('container')
    )
  }, 2000)

}