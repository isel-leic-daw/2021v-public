
// an object...
type Attributes = {
  // ...where the field keys are string and the field values are also strings or booleans
  [name: string]: string | boolean
}

type ElementChild = HTMLElement | string

export function createElement(
  name: string,
  attrs: Attributes,
  ...children: Array<ElementChild>
): HTMLElement {
  // 1. Create element
  const elem = document.createElement(name)
  // 2. Set the attributes
  for (const attrName in attrs) {
    const value = attrs[attrName]
    // value can be string or boolean
    if (typeof value === 'string') {
      // type narrowing!!!!
      // value can only be a string
      elem.setAttribute(attrName, value)
    } else if (value) {
      elem.setAttribute(attrName, "")
    }
  }
  // 3. Add the children
  children.forEach(child => {
    if (typeof child === 'string') {
      elem.appendChild(document.createTextNode(child))
    } else {
      elem.appendChild(child)
    }
  })

  return elem;
}

declare global {
  namespace JSX {
    interface IntrinsicElements {
      [elemName: string]: any;
    }
  }
}
