package isel.leic.daw.hvac.temperature

import isel.leic.daw.hvac.common.CURRENT_TEMPERATURE_PART
import isel.leic.daw.hvac.common.ProblemJson
import isel.leic.daw.hvac.common.TARGET_TEMPERATURE_PART
import isel.leic.daw.hvac.common.TEMPERATURE_PATH
import isel.leic.daw.hvac.common.authorization.ProtectedResource
import isel.leic.daw.hvac.common.authorization.RestrictedAccess
import isel.leic.daw.hvac.common.model.Hvac
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*

/**
 * Controller for the Temperature resource
 */
@RestController
@RequestMapping(TEMPERATURE_PATH, headers = ["Accept=application/json"])
@ProtectedResource
class TemperatureController(private val hvac: Hvac) {

    @ExceptionHandler(value = [InvalidTemperatureException::class, HttpMessageNotReadableException::class])
    fun handleInvalidTemperature() = ResponseEntity
        .badRequest()
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(
            ProblemJson(
                type = "/hvac/problems/invalid-temperature",
                title = "Invalid temperature",
                detail = "The specified temperature is not in the acceptable interval",
                status = 400
            )
        )

    @GetMapping(TARGET_TEMPERATURE_PART)
    fun getTargetTemperature() = TemperatureOutputModel(hvac.target.value)

    @RestrictedAccess
    @PutMapping(TARGET_TEMPERATURE_PART)
    fun putTargetTemperature(@RequestBody newTemperature: TemperatureInputModel): TemperatureInfoOutputModel {
        hvac.target = newTemperature.toTemperature()
        return TemperatureInfoOutputModel(hvac.current.value, hvac.target.value)
    }

    @GetMapping(CURRENT_TEMPERATURE_PART)
    fun getCurrentTemperature() = TemperatureOutputModel(hvac.current.value)

    @GetMapping
    fun getTemperature() = TemperatureInfoOutputModel(hvac.current.value, hvac.target.value)
}