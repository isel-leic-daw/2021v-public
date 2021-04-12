package isel.leic.daw.hvac.temperature

import com.fasterxml.jackson.annotation.JsonCreator
import isel.leic.daw.hvac.common.*
import isel.leic.daw.hvac.common.model.Temperature
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI

val SET_TARGET_TEMPERATURE_ACTION = SirenAction(
    name = "set-target-temperature",
    title = "Set Target Temperature",
    href = URI(TARGET_TEMPERATURE_PATH),
    method = HttpMethod.PUT,
    type = MediaType.APPLICATION_JSON,
    fields = listOf(SirenAction.Field("value", "number"))
)

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
class TemperatureInfoOutputModel(val current: Float, val target: Float) {
    fun toSirenObject(actions: List<SirenAction>? = null) = SirenEntity(
        properties = this,
        clazz = listOf("TemperatureInfo"),
        links = listOf(
            SirenLink(rel = listOf("target-temperature"), href = URI(TARGET_TEMPERATURE_PATH)),
            SirenLink(rel = listOf("current-temperature"), href = URI(CURRENT_TEMPERATURE_PATH))
        ),
        actions = actions
    )
}

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
class TemperatureOutputModel(val value: Float) {
    fun toSirenObject(selfUri: URI, actions: List<SirenAction>? = null) = SirenEntity(
        properties = this,
        clazz = listOf("Temperature"),
        links = listOf(SirenLink(rel = listOf("self"), href = selfUri)),
        actions = actions
    )
}


