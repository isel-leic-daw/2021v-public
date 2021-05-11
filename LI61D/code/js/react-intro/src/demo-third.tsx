import React from 'react'
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

function render(todos: Array<ToDoModel>) {
  return (
    <div>
      <h1>My todos</h1>
      {todos.map(todo => (
        <div key={todo.id}>
          <h2>{todo.name}</h2>
          <p>{todo.desc}</p>
          <p><input type='text' /></p>
        </div>
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