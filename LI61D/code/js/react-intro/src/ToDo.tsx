import { ToDoModel } from './ToDoModel'
import * as React from 'react'

type ToDoProps = {
  todo: ToDoModel
}

export function ToDo({ todo }: ToDoProps) {
  return (
    <>
      <p>{todo.id}: {todo.name}</p>
    </>
  )
}
