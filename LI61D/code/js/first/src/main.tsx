/** @jsx createElement */
import { createElement } from './view'
import { Items } from './items'

const items = ['item 1', 'item 2', 'item 3']

export function app() {
  return (
    <div>
      <h1>Some List</h1>
      <Items items={items} />
      <h1> Another List</h1>
      <Items items={['A', 'B', 'C']} />
    </div>
  )
}