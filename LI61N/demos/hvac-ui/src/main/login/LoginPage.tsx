/**
 * The application's login page.
 * @returns The React Element used to render the page.
 */
export default function LoginPage() {
    
  return (
    <div className='ui middle aligned center aligned grid' style={{ marginTop: 125 }}>
      <div className='column' style={{maxWidth: 380}}>
        <h2 className='ui header centered'>
          <div className='content'>HVAC UI</div>
        </h2>
        <form className='ui large form'>
          <div className='ui segment'>
            <div className='field'>
              <div className='ui left icon required input'>
                <i className='user icon'></i>
                <input type='text' name='username' placeholder='Your username' />
              </div>
            </div>
            <div className='field'>
              <div className='ui left icon required input'>
                <i className='key icon'></i>
                <input type='password' name='password' placeholder='Your password' />
              </div>
            </div>
            <button className='ui fluid large submit button' type='submit'>
              <i className='sign in icon'></i>Sign in
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
