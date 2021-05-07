import logo from './logo.svg'
import './App.css'
import ControlPage from './hvac/ControlPage'

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
 * The application entry point.
 */
function App() {
  return (
    <div className="App">
      <ControlPage />
    </div>
  )
}

export default App
