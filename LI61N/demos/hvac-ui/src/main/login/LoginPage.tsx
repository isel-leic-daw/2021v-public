import { useRef } from "react";
import { UserSessionRepository } from "./UserSessionRepository";

/**
 * Contract to be supported by objects passed as props to the LoginPage component.
 */
interface LoginPageProps {
  sessionRepo: UserSessionRepository
}

/**
 * The application's login page.
 * @returns The React Element used to render the page.
 */
export default function LoginPage(props: LoginPageProps) {
  
  const userNameInputRef = useRef<HTMLInputElement>(null)
  const pwdInputRef = useRef<HTMLInputElement>(null)

  function handleSubmit() {
    props.sessionRepo.login(userNameInputRef.current?.value || "", pwdInputRef.current?.value || "")
  }

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
                <input type='text' name='username' placeholder='Your username' ref={userNameInputRef} />
              </div>
            </div>
            <div className='field'>
              <div className='ui left icon required input'>
                <i className='key icon'></i>
                <input type='password' name='password' placeholder='Your password' ref={pwdInputRef} />
              </div>
            </div>
            <button className='ui fluid large submit button' type='button' onClick={handleSubmit} >
              <i className='sign in icon'></i>Sign in
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
