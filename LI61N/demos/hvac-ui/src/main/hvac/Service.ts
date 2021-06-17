import { PowerStateValue, Temperature, TemperatureDto } from './Model'
import * as Fetch from '../common/FetchUtils'
import * as Siren from '../common/Siren'
import { Credentials } from '../login/UserSession'

/**
 * Contract to be supported by the service used by the ControlPage.
 */
 export interface Service {
  setPowerState: (state: PowerStateValue) => Fetch.Request<Siren.Entity<PowerStateValue>>
  getPowerState: () => Fetch.Request<Siren.Entity<PowerStateValue>>
  getTemperature: () => Fetch.Request<Siren.Entity<TemperatureDto>>
  setTargetTemperature: (url: URL, value: Temperature) => Fetch.Request<Siren.Entity<TemperatureDto>>
}

/**
 * A mocked implementation of the HVAC control service.
 * @param initialState        - the initial HVAC power state.
 * @param initialTemperature  - the initial temperature information.
 * @returns the newly instantiated service mock.
 */
export function getMockedService(initialState: PowerStateValue, initialTemperature: TemperatureDto): Service {
  let mockedPowerState = initialState
  let mockedTemperature = initialTemperature
  return {
    setPowerState: (state: PowerStateValue): Fetch.Request<Siren.Entity<PowerStateValue>> => {
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<PowerStateValue>>>((resolve, _) => {
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

    getPowerState: (): Fetch.Request<Siren.Entity<PowerStateValue>> => {
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<PowerStateValue>>>((resolve, _) => {
          setTimeout(() => {
            resolve({ 
              header: { headers: new Headers(), status: 200, statusText: 'ok', type: 'default', url: '.', ok: true }, 
              body: { class: ['PowerState'], properties: mockedPowerState, actions: [] }
            })
          }, 5000)
        })
      }
    },

    getTemperature: (): Fetch.Request<Siren.Entity<TemperatureDto>> => {
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<TemperatureDto>>>((resolve, _) => {
          setTimeout(() => {
            resolve({ 
              header: { headers: new Headers(), status: 200, statusText: 'ok', type: 'default', url: '.', ok: true }, 
              body: { class: ['TemperatureInfo'], properties: mockedTemperature, actions: [] }
            })
          }, 5000)
        })
      }
    },

    setTargetTemperature: (url: URL, target: Temperature): Fetch.Request<Siren.Entity<TemperatureDto>> => {
      mockedTemperature.target = target.value
      return {
        isCanceled: false, 
        cancel: () => {},
        send: () => new Promise<Fetch.Result<Siren.Entity<TemperatureDto>>>((resolve, _) => {
          setTimeout(() => {
            resolve({ 
              header: { headers: new Headers(), status: 200, statusText: 'ok', type: 'default', url: '.', ok: true }, 
              body: { class: ['TemperatureInfo'], properties: mockedTemperature, actions: [] }
            })
          }, 5000)
        })
      }
    }
  }
}

/**
 * An implementation of the HVAC control service.
 * @param temperature       - the temperature resource URL.
 * @param powerState        - the power state resource URL.
 * @returns the newly instantiated service.
 */
export function getService(temperature: URL, powerState: URL, credentials: Credentials): Service {

  return {
    setPowerState: (value: PowerStateValue): Fetch.Request<Siren.Entity<PowerStateValue>> => {
      const headers = new Headers({ 'Content-type' : 'application/json' })
      if (credentials) headers.append('Authorization', `${credentials.type} ${credentials.content.value}`)
      return Fetch.cancelableRequest<Siren.Entity<PowerStateValue>>(powerState, {
        method: 'PUT',
        headers,
        body: JSON.stringify(value)
      })
    },

    getPowerState: (): Fetch.Request<Siren.Entity<PowerStateValue>> => {
      return Fetch.cancelableRequest<Siren.Entity<PowerStateValue>>(powerState, {
        headers: credentials ? { 'Authorization': `${credentials.type} ${credentials.content.value}` } : { },
      })
    },

    getTemperature: (): Fetch.Request<Siren.Entity<TemperatureDto>> => {
      return Fetch.cancelableRequest<Siren.Entity<TemperatureDto>>(temperature, {
        headers: credentials ? { 'Authorization': `${credentials.type} ${credentials.content.value}` } : { },
      })
    },

    setTargetTemperature: (url: URL, target: Temperature): Fetch.Request<Siren.Entity<TemperatureDto>> => {
      const headers = new Headers({ 'Content-type' : 'application/json' })
      if (credentials) headers.append('Authorization', `${credentials.type} ${credentials.content.value}`)
      return Fetch.cancelableRequest<Siren.Entity<TemperatureDto>>(url, {
        method: 'PUT',
        headers,
        body: JSON.stringify(target)
      })
    }
  }
}