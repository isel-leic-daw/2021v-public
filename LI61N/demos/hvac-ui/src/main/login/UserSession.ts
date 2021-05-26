import { createContext } from 'react'
import { Base64Encoded } from '../common/StringUtils'

/**
 * The user's credentials.
 */
export type Credentials = {
  type: 'Basic' | 'Bearer',
  content: Base64Encoded
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
      const credentials: Credentials = { type: 'Basic', content: new Base64Encoded(`${username}:${password}`) }
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
  readonly credentials?: Credentials,
  login: (username: string, password: string) => void
  logout: () => void
}

/**
 * The user session context. Initially, it is undefined.
 */
export const Context = createContext<ContextType | undefined>(undefined)

