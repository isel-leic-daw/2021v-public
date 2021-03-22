package isel.leic.daw.hvac.common.model

/**
 * Contract to be supported by coolers.
 */
interface Cooler {
    /**
     * The cooler's current enabled state.
     */
    var state: Power
}
