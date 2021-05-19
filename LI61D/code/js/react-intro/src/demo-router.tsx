import React, { ChangeEvent, useEffect, useState, useCallback } from 'react'
import ReactDOM from 'react-dom'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from 'react-router-dom'

const Component1 = ({ }) => <p>Hello, I'm Component1</p>
const Component2 = ({ }) => <p>Hello, I'm Component2</p>
const Component3 = ({ }) => <p>Hello, I'm Component3</p>

type Params = {
  pid: string
}

function Project({}) {
  const { pid } = useParams<Params>()
  return (
    <p>
      Hello, I'm project {pid}
    </p>
  )
}

function App({ }) {
  return (
    <Router>
      <ul>
        <li><Link to="/path1" >Go to component 1</Link></li>
        <li><Link to="/path2" >Go to component 2</Link></li>
        <li><Link to="/some/path" >Go to component 3</Link></li>
        <li><Link to="/projects/1" >Go to project 1</Link></li>
        <li><Link to="/projects/2" >Go to project 2</Link></li>
        <li><Link to="/projects/3" >Go to project 3</Link></li>
        <li><Link to="/some/path" >Go to component 3</Link></li>
        <li><a href="/another/path">Anchor to another path</a></li>
      </ul>
      <Switch>
        <Route path="/path1">
          <Component1 />
        </Route>
        <Route path="/path2">
          <Component2 />
        </Route>
        <Route path="/some/path">
          <Component3 />
        </Route>
        <Route path="/projects/:pid">
          <Project />
        </Route>
        <Route path="/">
          <p>Default route</p>
        </Route>
      </Switch>
    </Router>
  )
}


export function demo() {
  ReactDOM.render(
    <App />,
    document.getElementById('container')
  )
}
