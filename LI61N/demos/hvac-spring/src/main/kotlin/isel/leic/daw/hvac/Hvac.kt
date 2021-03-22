package isel.leic.daw.hvac

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * The HVAC implementation.
 *
 * @constructor Initiates an instance with the specified dependencies.
 */
@Component("HVAC")
@Scope("prototype")
class Hvac(private val sensor: Sensor, private val cooler: Cooler, private val heater: Heater) {

    /**
     * Implementation of the HVAC control algorithm
     */
    private fun doControl() {
        if (current isApproximateTo target) {
            power = Power.OFF
            return
        }

        sensor.onChange = {
            println("Temperature has changed to $it")
            if (current isApproximateTo target) {
                power = Power.OFF
            }
        }

        if (target > current) {
            heater.state = Power.ON
            cooler.state = Power.OFF
        } else {
            heater.state = Power.OFF
            cooler.state = Power.ON
        }
    }

    /**
     * The current temperature.
     */
    val current: Temperature
        get() = sensor.temperature

    /**
     * The HVAC's power state. It can be either turned on ([Power.ON]), in which case it will be regulating
     * the environment's temperature, or turned off ([Power.OFF]), doing nothing and therefore being
     * environmentally neutral ;)
     */
    @Volatile
    var power: Power = Power.OFF
        set(value) {
            when (value) {
                Power.OFF -> {
                    heater.state = Power.OFF
                    cooler.state = Power.OFF
                    sensor.onChange = null
                }
                Power.ON -> doControl()
            }
            field = value
            println("The HVAC is now $value")
        }

    /**
     * The target temperature, that is, the desired environment temperature.
     */
    @Volatile
    var target: Temperature = Temperature.HUMAN_COMFORT
        set(value) {
            field = value
            if (power == Power.ON)
                doControl()
        }
}