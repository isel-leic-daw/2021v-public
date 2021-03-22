package isel.leic.daw.hvac.state

import com.fasterxml.jackson.annotation.JsonCreator
import isel.leic.daw.hvac.common.model.Power


/**
 * Exception used to report the presence of an invalid power state value. Notice that we are not using exceptions
 * in the information model. We are merely using them in the presentation layer, in the spirit of what is expected
 * in traditional Spring MVC approaches.
 */
class InvalidPowerStateException : Exception()

/**
 * Represents power-state values as received by the HVAC system API
 *
 * @property    value     The temperature value
 */
class PowerStateInputModel @JsonCreator constructor(val value: String) {
    /**
     * Converts this instance to a [Power] instance.
     * @return  The [Power] instance
     * @throws  [InvalidPowerStateException] if [value] is not within the acceptable values
     */
    fun toPower() =
        Power.values().firstOrNull { it.name.compareTo(value, ignoreCase = true) == 0 } ?:
            throw InvalidPowerStateException()
}

/**
 * Represents power-state values as reported by the HVAC system API
 *
 * @property    value     The power state value
 */
class PowerStateOutputModel(val value: String)

/**
 * Extension method that creates an instance of [PowerStateOutputModel] from this [Power] instance
 */
fun Power.toOutputModel() = PowerStateOutputModel(this.name)

