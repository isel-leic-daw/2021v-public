package isel.leic.daw.hvac

/**
 * Contract to be supported by sensors
 */
interface Sensor {
    /**
     * The temperature reported by the sensor
     */
    val temperature: Temperature

    /**
     * The function called whenever the temperature changes, if one is registered
     */
    var onChange: ((Temperature) -> Unit)?
}
