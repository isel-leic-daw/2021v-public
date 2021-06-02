import '../App.css'

/**
 * Component used to display a message stating that the HVAC API is offline.
 */
export function OfflinePage() {
  return (
    <header className="App-header">
      <div className="ui very padded text container ">
        <div className="ui icon massive error message">
          <i className="wrench icon"></i>
          <div className="content">
            <div className="header">HVAC API is offline</div>
          </div>
        </div>
      </div>
    </header>
  )
}

/**
 * Component used to display a message stating that the entered credentials are invalid.
 */
 export function InvalidCredentialsPage() {
  return (
    <header className="App-header">
      <div className="ui very padded text container ">
        <div className="ui icon massive error message">
          <i className="ban icon"></i>
          <div className="content">
            <div className="header">Invalid credentials</div>
          </div>
        </div>
      </div>
    </header>
  )
}

