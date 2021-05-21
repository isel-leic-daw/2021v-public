import logo from './logo.svg'
import './App.css'
import { useState } from 'react'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom'
import { HvacControl } from './hvac/ControlPage'
import { Login } from './login/LoginPage'
import { UserSession } from './login/UserSession'

/**
 * The application's splash page, displayed during startup.
 * @returns The React Element used to render the page.
 */
 // eslint-disable-next-line
function SplashPage() {
  return (
    <header className="App-header">
      <img src={logo} className="App-logo" alt="logo" />
      <h1>Loading ...</h1>
    </header>
  )
}

/**
 * Renders the page that corresponds to the current route.
 */
function PageRouter() {
  const loginPageRoute = '/login'
  const hvacPageRoute = '/hvac'
  return (
    <Router>
      <Switch>
        <Route exact path={loginPageRoute}>
          <Login.Page redirectPath={hvacPageRoute} />
        </Route>
        <Route exact path={hvacPageRoute}>
          <Login.EnsureCredentials loginPageRoute={loginPageRoute}>
            <HvacControl.Page service={HvacControl.createService(true)} />
          </Login.EnsureCredentials>
        </Route>
        <Route path="/">
          <Redirect to={hvacPageRoute} />
        </Route>
      </Switch>
    </Router>
  )
}

/**
 * The application entry point.
 */
function App() {
  const [userCredentials, setUserCredentials] = useState<UserSession.Credentials | undefined>(undefined)
  const userSessionRepo = UserSession.createRepository()

  const currentSessionContext = {
    credentials: userCredentials,
    login: (username: string, password: string) => {
      setUserCredentials(userSessionRepo.login(username, password))
    },
    logout: () => { userSessionRepo.logout(); setUserCredentials(undefined) }
  }

  return (
    <div className="App">
      <UserSession.Context.Provider value={currentSessionContext}>
        <PageRouter />
      </UserSession.Context.Provider> 
    </div>
  )
}

export default App
