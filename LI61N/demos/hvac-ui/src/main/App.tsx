import logo from './logo.svg'
import './App.css'
import { useContext, useEffect, useState } from 'react'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom'

import * as Hvac from './hvac/Page'
import * as Login from './login/Page'
import * as UserSession from './login/UserSession'

import { EnsureCredentials } from './login/EnsureCredentials'
import { Home } from './home/Home'


// TODO: this should be an environment variable
const API_BASE_URL = 'http://localhost:3000/api'
const HOME_URL = new URL(`${API_BASE_URL}`)

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

type RouterProps = {
  fetchHomeInfo: (homeUrl: URL, credentials?: UserSession.Credentials) => Promise<Home.Info>
}

/**
 * Renders the page that corresponds to the current route.
 */
function PageRouter({fetchHomeInfo}: RouterProps) {
  const loginPageRoute = '/login'
  const hvacPageRoute = '/hvac'

  const [homeInfo, setHomeInfo] = useState<Home.Info>()
  const userSession = useContext(UserSession.Context)
  
  useEffect(() => {
    if (userSession && userSession.credentials) {
      // TODO: Show error page if we cannot get the home resource
      fetchHomeInfo(HOME_URL, userSession.credentials)
        .then((info) => setHomeInfo(info))
        .catch((error) => console.log(error))
    }
  }, [userSession, userSession?.credentials, fetchHomeInfo, setHomeInfo])

  return (
    <Router>
      <Switch>
        <Route exact path={loginPageRoute}>
          <Login.Page redirectPath={hvacPageRoute} />
        </Route>
        <Route exact path={hvacPageRoute}>
          <EnsureCredentials loginPageRoute={loginPageRoute}>
            { 
              !homeInfo ? <SplashPage /> : 
                <Hvac.Page service={Hvac.createService(true)} />
            }
          </EnsureCredentials>
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
        <PageRouter fetchHomeInfo={Home.fetchInfo} />
      </UserSession.Context.Provider> 
    </div>
  )
}

export default App
