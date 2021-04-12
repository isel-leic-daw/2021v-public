package isel.leic.daw.hvac.common.model

import kotlin.math.abs

/**
 * Data type for representing temperatures in the context of the HVAC system. All values are expressed in
 * degrees Celsius.
 */
class Temperature private constructor(val value: Float) {

    companion object {

        val HUMAN_COMFORT = Temperature(21f)
        val MAX = Temperature(60f)
        val MIN = Temperature(-20f)

        /**
         * Factory method that returns a [Temperature] instance with the specified [value], which must be in the
         * interval [-50..50] degrees Celsius.
         *
         * @param   value   The temperature value in the interval [-50..50]
         * @return  The corresponding [Temperature] instance, or null if [value] is within the admissible interval
         */
        fun of(value: Float): Temperature? =
            if (value <= MAX.value && value >= MIN.value) Temperature(value)
            else null

        /**
         * Overload of the function call operator to have the same behavior as the [of] function
         */
        operator fun invoke(value: Float): Temperature? = of(value)
    }

    /**
     * Checks if this [Temperature] is approximately equal to [another].
     *
     * @return  a boolean value indicating if the two temperature values are approximately equal to each other. Two
     *          values are said to be 'approximately equal' if their difference is within a predetermined interval.
     */
    infix fun isApproximateTo(another: Temperature) = abs(value - another.value) <= 0.5f

    /**
     * Subtracts the given value from the current temperature value.
     *
     * @param   value   The value to subtract from the current instance.
     * @return  the resulting [Temperature] instance or null if the result is not within the admissible values.
     */
    operator fun minus(value: Float): Temperature? = of(this.value - value)

    /**
     * Adds the given value to the current temperature value.
     *
     * @param   value   The value to add to the current instance.
     * @return  the resulting [Temperature] instance or null if the result is not within the admissible values.
     */
    operator fun plus(value: Float): Temperature? = of(this.value + value)

    /**
     * Compares two temperatures in accordance with Kotlin's
     */
    operator fun compareTo(other: Temperature):Int = (value - other.value).toInt()

    /**
     * Returns a string representation of the current instance.
     */
    override fun toString() = "Temperature(value=$value)"
}