import * as React from 'react'
import { ToDoModel } from './ToDoModel'
import { ToDo } from './ToDo'

type ToDoListProps = {
  caption: string,
  todos: Array<ToDoModel>
}

export function ToDoList({ todos, caption }: ToDoListProps) {
  return (
    <div>
      <p>{caption}</p>
      {todos.map(item => <ToDo key={item.id} todo={item} />)}
    </div>
  )
}
