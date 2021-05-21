import { createContext } from 'react'
import { Base64Encoded } from '../common'

export namespace UserSession {
  
  /**
   * The user's credentials.
   * @property username - the user's identifier
   * @property password - the user's password in a Base64 encoding. IMPORTANT: This is still clear text
   * and therefore it cannot be stored outside of the process' address space.
   */
  export type Credentials = {
    username: string,
    password: Base64Encoded
  }
  
  /**
   * The contract to be supported by user session repositories.
   */
  export type Repository = {
    isLoggedIn: () => Credentials | undefined,
    login: (username: string, password: string) => Credentials
    logout: () => void
  }

  /**
   * Creates a user session repository.
   * @returns The newly created user session repository.
   */
  export function createRepository(): Repository {
    const KEY = 'CredentialsKey'
    return {
      isLoggedIn: (): Credentials => { 
        const credentialsJSON = sessionStorage.getItem(KEY)
        return credentialsJSON ? JSON.parse(credentialsJSON) : undefined
      },
      login: (username: string, password: string) => { 
        const credentials = { username, password: new Base64Encoded(password) }
        sessionStorage.setItem(KEY, JSON.stringify(credentials))
        return credentials
      },
      logout: (): Repository => { 
        sessionStorage.removeItem(KEY) 
        return createRepository()
      }
    }
  }

  export type ContextType = {
    readonly credentials?: UserSession.Credentials,
    login: (username: string, password: string) => void
    logout: () => void
  }

  /**
   * The user session context. Initially, it is undefined.
   */
  export const Context = createContext<ContextType | undefined>(undefined)
}  

