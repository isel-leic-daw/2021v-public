package isel.leic.daw.hvac.state

import isel.leic.daw.hvac.common.*
import isel.leic.daw.hvac.common.authorization.ProtectedResource
import isel.leic.daw.hvac.common.authorization.RestrictedAccess
import isel.leic.daw.hvac.common.authorization.isFromOwner
import isel.leic.daw.hvac.common.model.Hvac
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Controller for the Hvac's state resource
 */
@RestController
@RequestMapping(HVAC_PATH, produces = [SIREN_MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE])
@ProtectedResource
class HvacStateController(private val hvac: Hvac) {

    @ExceptionHandler(value = [InvalidPowerStateException::class, HttpMessageNotReadableException::class])
    fun handleInvalidPowerState() = ResponseEntity
        .badRequest()
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(
            ProblemJson(
                type = "/hvac/problems/invalid-power-state",
                title = "Invalid power state",
                detail = "The specified power state is not acceptable",
                status = 400
            )
        )

    @GetMapping(POWER_STATE_PART)
    fun getPowerState(req: HttpServletRequest) = ResponseEntity
        .ok()
        .allow(HttpMethod.GET, HttpMethod.PUT)
        .body(PowerStateOutputModel(hvac.power.name).toSirenObject(req.isFromOwner()))

    @RestrictedAccess
    @PutMapping(POWER_STATE_PART)
    fun putPowerState(req: HttpServletRequest, @RequestBody state: PowerStateInputModel): SirenEntity<PowerStateOutputModel> {
        hvac.power = state.toPower()
        return hvac.power.toOutputModel().toSirenObject(req.isFromOwner())
    }
}