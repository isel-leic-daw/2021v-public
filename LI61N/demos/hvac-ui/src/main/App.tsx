import logo from './logo.svg'
import './App.css'
import { ReactNode, useContext, useEffect, useState } from 'react'
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom'

import * as Hvac from './hvac/Page'
import * as Login from './login/Page'
import * as UserSession from './login/UserSession'
import * as Home from './home/Home'
import { OfflinePage, InvalidCredentialsPage } from './error/ErrorPages'

import { EnsureCredentials } from './login/EnsureCredentials'
import * as API from './common/FetchUtils'

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
  fetchHomeInfo: (homeUrl: URL, credentials?: UserSession.Credentials) => API.Request<Home.Info>
}

/**
 * Renders the page that corresponds to the current route.
 */
function PageRouter({fetchHomeInfo}: RouterProps) {
  const loginPageRoute = '/login'
  const hvacPageRoute = '/hvac'

  const [homeInfo, setHomeInfo] = useState<API.FetchInfo<Home.Info>>({
    status: API.FetchState.NOT_READY
  })
  const [invalidCredentials, setInvalidCredentials] = useState<boolean>(false)
  const userSession = useContext(UserSession.Context)
  
  useEffect(() => {
    async function sendRequest(request: API.Request<Home.Info>) {
      try {
        const result: API.Result<Home.Info> = await request.send()
        if (result.header.ok && result.body) {
          setHomeInfo({ status: API.FetchState.SUCCESS, result })
        }
        else {
          if (API.isServerError(result)) {
            setHomeInfo({ status: API.FetchState.ERROR, result })
          }
          if (API.isAuthorizationError(result)) {
            setHomeInfo({ status: API.FetchState.SUCCESS, result })
            setInvalidCredentials(true)
          }
        }
      }
      catch(reason) {
        setHomeInfo({ status: API.FetchState.ERROR })
      }
    }

    if (userSession && userSession.credentials) {
      const homeRequest = fetchHomeInfo(HOME_URL, userSession.credentials)
      sendRequest(homeRequest)
      return homeRequest.cancel
    }
  }, [userSession, userSession?.credentials, fetchHomeInfo, setHomeInfo])

  function renderContent(): ReactNode {
    let content: ReactNode | undefined = undefined
    switch (homeInfo.status) {
      case API.FetchState.NOT_READY: content = <SplashPage />; break
      case API.FetchState.ERROR: content = <OfflinePage />; break
      default: content = invalidCredentials ? <InvalidCredentialsPage /> : 
        <Hvac.Page service={Hvac.createService(
          new URL(`${API_BASE_URL}${homeInfo.result?.body?.resources?.temperature?.href ?? ''}`),
          new URL(`${API_BASE_URL}${homeInfo.result?.body?.resources?.power_state?.href ?? '/'}`),
          userSession?.credentials
        )} />
    }
    return content
  }

  return (
    <Router>
      <Switch>
        <Route exact path={loginPageRoute}>
          <Login.Page redirectPath={hvacPageRoute} />
        </Route>
        <Route exact path={hvacPageRoute}>
          <EnsureCredentials loginPageRoute={loginPageRoute}>
            { renderContent() }
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
