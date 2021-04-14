package isel.leic.daw.hvac.common

const val TEMPERATURE_PATH = "/temperature"
const val TARGET_TEMPERATURE_PART = "/target"
const val CURRENT_TEMPERATURE_PART = "/current"

const val TARGET_TEMPERATURE_PATH = "${TEMPERATURE_PATH}${TARGET_TEMPERATURE_PART}"
const val CURRENT_TEMPERATURE_PATH = "${TEMPERATURE_PATH}${CURRENT_TEMPERATURE_PART}"

const val HVAC_PATH = "/hvac"
const val POWER_STATE_PART = "/power-state"
const val POWER_STATE_PATH = "${HVAC_PATH}${POWER_STATE_PART}"

