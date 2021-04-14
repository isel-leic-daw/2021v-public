package isel.leic.daw.hvac.state

import com.fasterxml.jackson.annotation.JsonCreator
import isel.leic.daw.hvac.common.*
import isel.leic.daw.hvac.common.model.Power
import isel.leic.daw.hvac.temperature.TEMPERATURE_LINK
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI

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


// Below are Siren specific definitions. They could be placed in another file.

val SET_POWER_STATE_ACTION = SirenAction(
    name = "set-power-state",
    title = "Set Power State",
    href = URI(POWER_STATE_PATH),
    method = HttpMethod.PUT,
    type = MediaType.APPLICATION_JSON,
    fields = listOf(SirenAction.Field("value", "radio"))
)

val POWER_STATE_LINK = SirenLink(rel = listOf("/hvac/rel/power-state"), href = URI(POWER_STATE_PATH))

/**
 * Extension function that produces the Siren version of the resource's external representation
 *
 * @param [includeActions]  a boolean value indicating if associated actions should be included or not. Notice that
 * actions are operations that modify the resource's state, and therefore have restricted access.
 * @return The corresponding siren representation
 */
fun PowerStateOutputModel.toSirenObject(includeActions: Boolean) = SirenEntity(
    properties = this,
    clazz = listOf("PowerState"),
    links = listOf(selfLink(HVAC_PATH), TEMPERATURE_LINK),
    actions = if (includeActions) listOf(SET_POWER_STATE_ACTION) else emptyList()
)