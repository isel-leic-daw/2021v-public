package isel.leic.daw.hvac.simulation

import isel.leic.daw.hvac.Cooler
import isel.leic.daw.hvac.Heater
import isel.leic.daw.hvac.Power
import isel.leic.daw.hvac.Temperature
import java.util.*
import kotlin.concurrent.fixedRateTimer

/**
 * Class used to capture similarities between the [CoolerSimulator] and the [HeaterSimulator] implementations.
 */
private class TemperatureActuator(
    private val sensor: SensorSimulator,
    private val message: String,
    private val actuator: (Temperature) -> Temperature) {

    private var timer: Timer? = null

    /**
     * Starts the actuator operation, if not already started.
     */
    fun start() {
        if (timer == null) {
            timer = fixedRateTimer(message, initialDelay = 5 * 1000, period = 5 * 1000) {
                    sensor.temperature = actuator(sensor.temperature)
            }
        }
    }

    /**
     * Stops the actuator operation, if not already stopped.
     */
    fun stop() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }
}

/**
 * Implementation of a cooling system simulator
 */
class CoolerSimulator(sensor: SensorSimulator) : Cooler {

    private val actuator = TemperatureActuator(sensor, "Cooler running") { (it - 1f) ?: it }

    override var state: Power = Power.OFF
        set(value) {
            field = value
            if (value == Power.ON) {
                actuator.start()
            }
            else {
                actuator.stop()
            }
        }
}

/**
 * Implementation of a heating system simulator
 */
class HeaterSimulator(sensor: SensorSimulator) : Heater {

    private val actuator = TemperatureActuator(sensor, "Heater running") { (it + 1f) ?: it }

    override var state: Power = Power.OFF
        set(value) {
            field = value
            if (value == Power.ON) {
                actuator.start()
            }
            else {
                actuator.stop()
            }
        }
}
