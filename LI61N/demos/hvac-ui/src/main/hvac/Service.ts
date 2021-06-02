import { PowerState, PowerStateDto, Temperature, ControlledTemperature } from './Model'
import * as Siren from '../common/Siren'

/**
 * Contract to be supported by the service used by the ControlPage.
 */
 export interface Service {
  setPowerState: (state: PowerState) => Promise<PowerState>
  getPowerState: () => Promise<Siren.Entity<PowerStateDto>>
  getTemperature: () => Promise<ControlledTemperature>
  setTargetTemperature: (value: Temperature) => Promise<Temperature>
}

/**
 * A mocked implementation of the HVAC control service.
 * @param initialState - the initial HVAC power state.
 * @param initialTemperature - the initial temperature information.
 * @returns the newly instantiated service mock.
 */
export function getMockedService(initialState: PowerState, initialTemperature: ControlledTemperature): Service {
  let mockedPowerState = initialState
  let mockedTemperature = initialTemperature
  return {
    setPowerState: async (state: PowerState): Promise<PowerState> => {
      return new Promise<PowerState>((resolve, _) => {
        setTimeout(() => { mockedPowerState = state; resolve(mockedPowerState) }, 5000)
      })
    },

    getPowerState: async (): Promise<Siren.Entity<PowerStateDto>> => {
      return new Promise<Siren.Entity<PowerStateDto>>((resolve, _) => {
        setTimeout(() => { 
          resolve({ class: ['PowerState'], properties: { value: mockedPowerState } }) 
        }, 5000)
      })
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
 export function getService(temperature: URL, powerState: URL): Service {
  // TODO: Implement
  throw new Error()
}
