package isel.leic.daw.hvac.state

import isel.leic.daw.hvac.common.HVAC_PATH
import isel.leic.daw.hvac.common.POWER_STATE_PART
import isel.leic.daw.hvac.common.ProblemJson
import isel.leic.daw.hvac.common.authorization.ProtectedResource
import isel.leic.daw.hvac.common.authorization.RestrictedAccess
import isel.leic.daw.hvac.common.model.Hvac
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*

/**
 * Controller for the Hvac's state resource
 */
@RestController
@RequestMapping(HVAC_PATH)
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
    fun getPowerState() =
            PowerStateOutputModel(hvac.power.name)

    @RestrictedAccess
    @PutMapping(POWER_STATE_PART)
    fun putPowerState(@RequestBody state: PowerStateInputModel): PowerStateOutputModel {
        hvac.power = state.toPower()
        return hvac.power.toOutputModel()
    }
}