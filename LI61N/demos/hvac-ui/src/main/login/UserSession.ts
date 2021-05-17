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
  export interface Repository {
    isLoggedIn: () => Credentials | null,
    login: (username: string, password: string) => void
    logout: () => void
  }

  /**
   * Creates a user session repository.
   * @returns The newly created user session repository.
   */
  export function createRepository(): Repository {
    const KEY = 'CredentialsKey'
    return {
      isLoggedIn: () => { 
        const credentials = sessionStorage.getItem(KEY)
        return !credentials ? null : JSON.parse(credentials) 
      },
      login: (username: string, password: string) => { 
        const credentials = { username, password: new Base64Encoded(password) }
        sessionStorage.setItem(KEY, JSON.stringify(credentials))
      },
      logout: () => { sessionStorage.removeItem(KEY) }
    }
  }
}  

