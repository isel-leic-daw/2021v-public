package isel.leic.daw.hvac.temperature

import com.fasterxml.jackson.annotation.JsonCreator
import isel.leic.daw.hvac.common.model.Temperature

/**
 * Exception used to report the presence of an invalid temperature value. Notice that we are not using exceptions
 * in the information model. We are merely using them in the presentation layer, in the spirit of what is expected
 * in traditional Spring MVC approaches.
 */
class InvalidTemperatureException : Exception()

/**
 * Represents aggregate temperature values as reported by the HVAC system API
 *
 * @property    current     The current temperature
 * @property    target      The desired temperature
 */
class TemperatureInfoOutputModel(val current: Float, val target: Float)

/**
 * Represents temperature values as received by the HVAC system API
 *
 * @property    value     The temperature value
 */
class TemperatureInputModel @JsonCreator constructor(val value: Float) {

    /**
     * Converts this instance to a [Temperature] instance.
     * @return  The [Temperature] instance
     * @throws  [InvalidTemperatureException] if [value] is not within the acceptable values
     */
    fun toTemperature() = Temperature(value) ?: throw InvalidTemperatureException()
}

/**
 * Represents temperature values as reported by the HVAC system API
 *
 * @property    value     The temperature value
 */
class TemperatureOutputModel(val value: Float)


