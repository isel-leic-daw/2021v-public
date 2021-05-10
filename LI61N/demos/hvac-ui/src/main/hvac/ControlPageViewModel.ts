import { ControlledTemperature, PowerState, Temperature } from './hvacModel'

/**
 * Contract to be supported by the view model for the ControlPage. The page's view model is responsible for 
 * providing the information to be displayed in the page. 
 */
export interface ControlPageViewModel {
  setPowerState: (state: PowerState) => Promise<PowerState>
  getPowerState: () => Promise<PowerState>
  getTemperature: () => Promise<ControlledTemperature>
  setTargetTemperature: (value: Temperature) => Promise<Temperature>
}

/**
 * Gets the ControlPage view model.
 * @returns The view model instance.
 */
export function getControlPageViewModel(): ControlPageViewModel {
  return getMockedViewModel(PowerState.OFF, { current: new Temperature(21), target: new Temperature(21) })
}

function getMockedViewModel(initialState: PowerState, initialTemperature: ControlledTemperature): ControlPageViewModel {
  let mockedPowerState = initialState
  let mockedTemperature = initialTemperature
  return {
    setPowerState: async (state: PowerState): Promise<PowerState> => {
      return new Promise<PowerState>((resolve, _) => {
        setTimeout(() => { mockedPowerState = state; resolve(mockedPowerState) }, 5000)
      })
    },

    getPowerState: async (): Promise<PowerState> => {
      return new Promise<PowerState>((resolve, _) => {
        setTimeout(() => { resolve(mockedPowerState) }, 5000)
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
