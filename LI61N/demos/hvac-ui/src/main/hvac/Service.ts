import { PowerStateDto, Temperature, ControlledTemperature } from './Model'
import * as Fetch from '../common/FetchUtils'
import * as Siren from '../common/Siren'
import { Credentials } from '../login/UserSession'

/**
 * Contract to be supported by the service used by the ControlPage.
 */
 export interface Service {
  setPowerState: (state: PowerStateDto) => Fetch.Request<Siren.Entity<PowerStateDto>>
  getPowerState: () => Fetch.Request<Siren.Entity<PowerStateDto>>
  getTemperature: () => Promise<ControlledTemperature>
  setTargetTemperature: (value: Temperature) => Promise<Temperature>
}

/**
 * A mocked implementation of the HVAC control service.
 * @param initialState        - the initial HVAC power state.
 * @param initialTemperature  - the initial temperature information.
 * @returns the newly instantiated service mock.
 */
export function getMockedService(initialState: PowerStateDto, initialTemperature: ControlledTemperature): Service {
  let mockedPowerState = initialState
  let mockedTemperature = initialTemperature
  return {
    setPowerState: (state: PowerStateDto): Fetch.Request<Siren.Entity<PowerStateDto>> => {
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<PowerStateDto>>>((resolve, _) => {
          setTimeout(() => {
            mockedPowerState = state
            resolve({ 
              header: { headers: new Headers(), status: 200, statusText: 'ok', type: 'default', url: '.', ok: true }, 
              body: { class: ['PowerState'], properties: mockedPowerState, actions: [] }
            })
          }, 5000)
        })
      }
    },

    getPowerState: (): Fetch.Request<Siren.Entity<PowerStateDto>> => {
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<PowerStateDto>>>((resolve, _) => {
          setTimeout(() => {
            resolve({ 
              header: { headers: new Headers(), status: 200, statusText: 'ok', type: 'default', url: '.', ok: true }, 
              body: { class: ['PowerState'], properties: mockedPowerState, actions: [] }
            })
          }, 5000)
        })
      }
    },

    getTemperature: async (): Promise<ControlledTemperature> => {
      return new Promise<ControlledTemperature>((resolve, _) => {
        setTimeout(() => { resolve(mockedTemperature) }, 5000)
      })
    },

    setTargetTemperature: async (value): Promise<Temperature> => {
      return new Promise<Temperature>((resolve, _) => {
        setTimeout(() => { resolve(value) }, 5000)
      })
    }
  }
}

/**
 * An implementation of the HVAC control service.
 * @param temperature - the temperature resource URL.
 * @param powerState  - the power state resource URL.
 * @returns the newly instantiated service.
 */
export function getService(temperature: URL, powerState: URL, credentials: Credentials): Service {
  return {
    setPowerState: (value: PowerStateDto): Fetch.Request<Siren.Entity<PowerStateDto>> => {
      const headers = new Headers({ 'Content-type' : 'application/json' })
      if (credentials) headers.append('Authorization', `${credentials.type} ${credentials.content.value}`)
      return Fetch.cancelableRequest<Siren.Entity<PowerStateDto>>(powerState, {
        method: 'PUT',
        headers,
        body: JSON.stringify(value)
      })
    },

    getPowerState: (): Fetch.Request<Siren.Entity<PowerStateDto>> => {
      return Fetch.cancelableRequest<Siren.Entity<PowerStateDto>>(powerState, {
        headers: credentials ? { 'Authorization': `${credentials.type} ${credentials.content.value}` } : { },
      })
    },

    getTemperature: async (): Promise<ControlledTemperature> => {
      return new Promise<ControlledTemperature>((resolve, _) => {
        setTimeout(() => { }, 5000)
      })
    },

    setTargetTemperature: async (value): Promise<Temperature> => {
      return new Promise<Temperature>((resolve, _) => {
        setTimeout(() => { }, 5000)
      })
    }
  }
}