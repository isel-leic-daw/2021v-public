package isel.leic.daw.hvac.state

import isel.leic.daw.hvac.common.HVAC_PATH
import isel.leic.daw.hvac.common.POWER_STATE_PART
import isel.leic.daw.hvac.common.model.Hvac
import org.springframework.web.bind.annotation.*

/**
 * Controller for the Hvac's state resource
 */
@RestController
@RequestMapping(HVAC_PATH)
class HvacStateController(private val hvac: Hvac) {

    @GetMapping(POWER_STATE_PART)
    fun getPowerState() =
            PowerStateOutputModel(hvac.power.name)

    @PutMapping(POWER_STATE_PART)
    fun putPowerState(@RequestBody state: PowerStateInputModel): PowerStateOutputModel {
        hvac.power = state.toPower()
        return hvac.power.toOutputModel()
    }
}