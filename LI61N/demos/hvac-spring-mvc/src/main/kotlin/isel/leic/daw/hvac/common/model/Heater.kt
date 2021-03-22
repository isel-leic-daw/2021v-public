package isel.leic.daw.hvac.common.model

/**
 * Contract to be supported by heaters
 */
interface Heater {
    /**
     * The heater's current enabled state
     */
    var state: Power
}
