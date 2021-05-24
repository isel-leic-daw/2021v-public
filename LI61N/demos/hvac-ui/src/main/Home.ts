import { UserSession } from './login/UserSession'

// TODO: Refine these types
export namespace Home {
  
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

  export async function fetchInfo(url: URL, credentials?: UserSession.Credentials): Promise<Info> {
    console.log(`Home.fetchInfo()`)
    const response = await fetch(url.toString(), {
      headers: credentials ? { 'Authorization': `${credentials.type} ${credentials.content.value}` } : { }
    })

    if (response.ok)
      return await response.json()

    throw new Error(response.status === 401 ? "Invalid credentials" : "Could not get home resource")
  }
}

