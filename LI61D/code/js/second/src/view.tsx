/** @jsx createElement */
import { createElement } from './createElement'

export const elem2 = (
  <div className='some'>
    <p>Defined in the view module using JSX</p>
    <p>First list</p>
    <ul>
      <li>first item</li>
      <li>second item</li>
      <li>third item</li>
    </ul>
    <p>Second list</p>
    <ol>
      <li>first item</li>
      <li>second item</li>
      <li>third item</li>
    </ol>
  </div>
)

export const elem =
  createElement('div', { className: 'some' },
    createElement('p', {}, 'Defined in the view module'),
    createElement('p', {}, 'First list'),
    createElement('ul', {},
      createElement('li', {}, 'first item'),
      createElement('li', {}, 'second item'),
      createElement('li', {}, 'second item'),
    ),
    createElement('p', {}, 'Second list'),
    createElement('ol', {},
      createElement('li', {}, 'first item'),
      createElement('li', {}, 'second item'),
      createElement('li', {}, 'second item'),
    )
  )
