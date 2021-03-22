package isel.leic.daw.hvac.common.model.simulation

import isel.leic.daw.hvac.common.model.Sensor
import isel.leic.daw.hvac.common.model.Temperature
import isel.leic.daw.threadSafe
import org.springframework.stereotype.Component
import kotlin.properties.Delegates.observable

/**
 * Implementation of a sensor simulator that always reports the last written temperature.
 */
@Component
class SensorSimulator(initialTemperature: Temperature = Temperature.HUMAN_COMFORT) : Sensor {

    override var temperature: Temperature by threadSafe(observable(initialTemperature) { _, _, newValue: Temperature ->
        onChange?.invoke(newValue)
    })

    @Volatile
    override var onChange: ((Temperature) -> Unit)? = null
}
