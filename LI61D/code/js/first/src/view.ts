interface Attrs {
  [name: string]: string | boolean
}

type Child = HTMLElement | string | Array<Child>

type Component = string | ((props: any) => HTMLElement)

function append(parent: HTMLElement, child: Child) {
  if (typeof child === 'string') {
    parent.appendChild(document.createTextNode(child))
  } else if (Array.isArray(child)) {
    child.forEach(item => append(parent, item))
  } else {
    parent.appendChild(child)
  }
}

export function createElement(component: Component, attrs: Attrs, ...children: Array<Child>): HTMLElement {
  const elem = typeof component === 'string' ? document.createElement(component) : component(attrs)
  for (const property in attrs) {
    const value = attrs[property]
    if (typeof value === 'string') {
      elem.setAttribute(property, value)
    } else {
      if (value) {
        elem.setAttribute(property, "")
      }
    }
  }
  children.forEach(child => append(elem, child))
  return elem
}

declare global {
  namespace JSX {
    interface IntrinsicElements {
      [elemName: string]: any;
    }
  }
}
