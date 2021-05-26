import { ReactNode } from 'react'
import { Redirect } from 'react-router-dom'

import * as UserSession from './UserSession'

/**
 * Type that specifies the props object for the EnsureCredentials component.
 */
type EnsureCredentialsProps = {
  loginPageRoute: string,
  children?: ReactNode
}

/**
 * Component responsible for verifying if the user has already entered his credentials.
 */
export function EnsureCredentials({loginPageRoute, children} : EnsureCredentialsProps) {
  return <UserSession.Context.Consumer>
    { user => user && user.credentials ? <> {children} </> : <Redirect to={loginPageRoute} /> }
  </UserSession.Context.Consumer>
}

