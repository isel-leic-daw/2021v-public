import logo from './logo.svg'
import './App.css'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom'
import ControlPage from './hvac/ControlPage'
import { getControlPageViewModel } from './hvac/ControlPageViewModel'
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
  const sessionRepository = UserSession.createRepository()
  const loginPageRoute = "/login"
  const hvacPageRoute = "/hvac"
  return (
    <Router>
      <Switch>
        <Route exact path={loginPageRoute}>
          <Login.Page sessionRepo={sessionRepository} redirectPath={hvacPageRoute} />
        </Route>
        <Route exact path={hvacPageRoute}>
          <Login.EnsureCredentials sessionRepo={sessionRepository} loginPageRoute={loginPageRoute}>
            <ControlPage viewModel={getControlPageViewModel()} sessionRepo={sessionRepository} signOutRedirectRoute="/" />
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
  return (
    <div className="App">
      <PageRouter />
    </div>
  )
}

export default App
