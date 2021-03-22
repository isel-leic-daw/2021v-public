package isel.leic.daw.hvac.temperature

import isel.leic.daw.hvac.common.CURRENT_TEMPERATURE_PART
import isel.leic.daw.hvac.common.TARGET_TEMPERATURE_PART
import isel.leic.daw.hvac.common.TEMPERATURE_PATH
import isel.leic.daw.hvac.common.model.Hvac
import isel.leic.daw.hvac.common.model.InvalidTemperature
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller for the Temperature resource
 */
@RestController
@RequestMapping(TEMPERATURE_PATH, headers = [ "Accept=application/json"])
class TemperatureController(private val hvac: Hvac) {

    @ExceptionHandler
    fun handleInvalidTemperature(ex: InvalidTemperature) = ResponseEntity.badRequest()

    @GetMapping(TARGET_TEMPERATURE_PART)
    fun getTargetTemperature() = TemperatureOutputModel(hvac.target.value)

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