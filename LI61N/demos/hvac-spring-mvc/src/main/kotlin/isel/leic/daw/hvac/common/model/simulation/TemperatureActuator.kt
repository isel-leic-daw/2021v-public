package isel.leic.daw.hvac.common.model.simulation

import isel.leic.daw.hvac.common.model.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
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
    private val logger = LoggerFactory.getLogger(TemperatureActuator::class.java)

    /**
     * Starts the actuator operation, if not already started.
     */
    fun start() {
        if (timer == null) {
            logger.info(message)
            timer = fixedRateTimer(message, initialDelay = 10 * 1000, period = 10 * 1000) {
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
@Component
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
@Component
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
