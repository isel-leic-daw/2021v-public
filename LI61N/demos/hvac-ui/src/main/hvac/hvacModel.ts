/**
 * Type used for representing the HVAC power state.
 */
export enum PowerState { ON = "ON", OFF = "OFF" }

/**
 * Toggles the given power state value, that is, OFF becomes ON e vice-versa.
 * @param powerState - the input power state value.
 * @returns the toggled value.
 */
export function toggle(powerState: PowerState): PowerState {
  return powerState === PowerState.ON ? PowerState.OFF : PowerState.ON
}

/**
 * Type used for representing temperature values in the HVAC domain context. 
 * Temperatures are expressed in ºC and must be in the range [10, 40]. 
 */
export class Temperature {
  static MAX: number = 40
  static MIN: number = 10
  readonly value: number
  constructor(value: number) { this.value = coerceToLimits(value, Temperature.MIN, Temperature.MAX)}
}

/**
 * Type used to represent a controlled temperature in the HVAC domain context.
 * @property current - the current temperature.
 * @property target - the desired temperature.
 */
export interface ControlledTemperature {
  current: Temperature
  target?: Temperature
}

function coerceToLimits(value: number, min: number, max: number): number {
  return Math.max(Math.min(max, value), min)
}