function addMessage(msg: string) {
  const elem = document.createElement('div')
  elem.innerText = msg
  document.body.appendChild(elem)
}

addMessage("Hello World using TypeScript")
addMessage("Another message")
addMessage("And yet anothe message")
