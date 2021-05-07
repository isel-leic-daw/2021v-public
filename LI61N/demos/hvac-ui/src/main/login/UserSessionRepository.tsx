
/**
 * Type used to represent base64 encoded strings.
 * Implementation note: For simplicity, non-ascii characters are not supported.
 * If you really whish to support Unicode strings, check this out:
 * https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/btoa#unicode_strings
 */
class Base64Encoded {
  readonly value: string
  constructor(value: string) { this.value = btoa(value) }
}

/**
 * The user's credentials.
 * @property username - the user's identifier
 * @property password - the user's password in a Base64 encoding. IMPORTANT: This is still clear text
 * and therefore it cannot be stored outside of the process' address space.
 */
export interface Credentials {
  username: string,
  password: Base64Encoded
}

/**
 * The contract to be supported by user session repositories.
 */
export interface UserSessionRepository {
  isLoggedIn: () => Credentials | null,
  login: (username: string, password: string) => void
  logout: () => void
}

/**
 * Creates a user session repository.
 * @returns The newly created user session repository.
 */
export function createUserSessionRepository(): UserSessionRepository {
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
