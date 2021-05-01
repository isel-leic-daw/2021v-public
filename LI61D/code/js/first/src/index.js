import _ from 'lodash'
const msg = _.join(['Hello', 'World', 'using webpack dev server'], " ")
const elem = document.createElement('div')
elem.innerText = msg
document.body.appendChild(elem)
