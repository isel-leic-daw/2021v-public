package isel.leic.daw.hvac.temperature

import isel.leic.daw.hvac.common.*
import isel.leic.daw.hvac.common.authorization.ProtectedResource
import isel.leic.daw.hvac.common.authorization.RestrictedAccess
import isel.leic.daw.hvac.common.authorization.isFromOwner
import isel.leic.daw.hvac.common.model.Hvac
import isel.leic.daw.hvac.state.POWER_STATE_LINK
import isel.leic.daw.hvac.state.SET_POWER_STATE_ACTION
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Controller for the Temperature resource
 */
@RestController
@RequestMapping(TEMPERATURE_PATH, produces = [SIREN_MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE])
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
    fun getTargetTemperature(req: HttpServletRequest) = ResponseEntity
        .ok()
        .allow(HttpMethod.GET, HttpMethod.PUT)
        .body(
            TemperatureOutputModel(hvac.target.value).toSirenObject(
                links = listOf(selfLink(TARGET_TEMPERATURE_PATH)),
                actions = if (req.isFromOwner()) listOf(SET_TARGET_TEMPERATURE_ACTION) else emptyList()
            )
        )

    @RestrictedAccess
    @PutMapping(TARGET_TEMPERATURE_PART)
    fun putTargetTemperature(
        req: HttpServletRequest,
        @RequestBody newTemperature: TemperatureInputModel
    ): SirenEntity<TemperatureInfoOutputModel> {

        hvac.target = newTemperature.toTemperature()
        return TemperatureInfoOutputModel(hvac.current.value, hvac.target.value)
            .toSirenObject(
                links = listOf(TARGET_TEMPERATURE_LINK, CURRENT_TEMPERATURE_LINK),
                actions = if (req.isFromOwner()) listOf(SET_POWER_STATE_ACTION) else emptyList()
            )
    }

    @GetMapping(CURRENT_TEMPERATURE_PART)
    fun getCurrentTemperature(req: HttpServletRequest) =
        TemperatureOutputModel(hvac.current.value).toSirenObject(
            links = listOf(selfLink(CURRENT_TEMPERATURE_PATH), TARGET_TEMPERATURE_LINK)
        )

    @GetMapping
    fun getTemperature(req: HttpServletRequest): ResponseEntity<SirenEntity<TemperatureInfoOutputModel>> =
        // We can explicitly override the produced Content-Type header, as shown here. In this case, the header will
        // refer to the SIREN_MEDIA_TYPE, regardless of the client asking for it or, more generally, for JSON
        ResponseEntity.ok()
            .header("Content-Type", SIREN_MEDIA_TYPE)
            .body(
                TemperatureInfoOutputModel(hvac.current.value, hvac.target.value)
                    .toSirenObject(
                        links = listOf(selfLink(TEMPERATURE_PATH), CURRENT_TEMPERATURE_LINK, TARGET_TEMPERATURE_LINK, POWER_STATE_LINK),
                        actions = if (req.isFromOwner()) listOf(SET_TARGET_TEMPERATURE_ACTION) else emptyList()
                    )
            )
}