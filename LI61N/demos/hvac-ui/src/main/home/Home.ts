import { Request, cancelableRequest } from '../common/FetchUtils'
import * as UserSession from '../login/UserSession'

// TODO: Refine these types
export type ApiInfo = {
  title: string,
  links: {
    author: string,
    describedBy: string
  }
}

export type NavigationLink = {
  href: string
}

export type ResourcesInfo = {
  power_state?: NavigationLink,
  temperature?: NavigationLink
}

export type Info = {
  api: ApiInfo,
  resources: ResourcesInfo
}

/**
 * Gets the Home resource from the API.
 * @param url         - The Home resource URL.
 * @param credentials - The user's credentials.
 * @returns The request to be sent.
 */
export function fetchInfo(url: URL, credentials?: UserSession.Credentials): Request<Info> {
  return cancelableRequest(url, {
    headers: credentials ? { 'Authorization': `${credentials.type} ${credentials.content.value}` } : { },
  })
}

