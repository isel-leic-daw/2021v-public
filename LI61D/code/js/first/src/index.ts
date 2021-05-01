/** @jsx createElement */
import { createElement } from './view'
import { app } from './main'

const body = document.body

// Using `createElement` directly
body.appendChild(
  createElement('div', {},
    createElement('ul', {},
      createElement('li', {}, 'item 1'),
      createElement('li', {}, 'item 2'),
      createElement('li', {}, 'item 3')
    )
  )
)

body.appendChild(
  app()
)
