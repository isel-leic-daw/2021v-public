import logo from './logo.svg'
import './App.css'
import ControlPage from './hvac/ControlPage'
import LoginPage from './login/LoginPage'

function SplashPage() {
  return (
    <header className="App-header">
      <img src={logo} className="App-logo" alt="logo" />
      <h1>Loading ...</h1>
    </header>
  )
}

function App() {

  return (
    <div className="App">
      <ControlPage />
    </div>
  )
}

export default App
