/** @jsx createElement */
import { createElement } from './view'

export function Items({ items }: { items: Array<string> }): HTMLElement {

  return (
    <div>
      <ul>
        {items.map(item => <li>{item}</li>)}
      </ul>
    </div>
  )
}
