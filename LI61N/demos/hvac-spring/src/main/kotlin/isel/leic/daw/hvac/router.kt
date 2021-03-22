package isel.leic.daw.hvac

enum class CommandResult { EXIT, CONTINUE }
typealias Command = (String?) -> CommandResult
typealias ParameterizedCommand = () -> CommandResult
typealias Router = (String?) -> ParameterizedCommand

/**
 * Creates the application's command router, used to determine which code is to be executed for
 * each command entered by the user.
 *
 * @param   hvac    The HVAC instance on which the supported commands will operate.
 * @return  A new command router instance.
 */
fun initRouter(hvac: Hvac): Router {
    val mappings: Map<Pair<String, String?>, Command> = mapOf(
        Pair("EXIT", null) to { _: String? -> CommandResult.EXIT },
        Pair("GET", "/hvac/power-state") to { _: String? ->
            println("HVAC is ${hvac.power}")
            CommandResult.CONTINUE
        },
        Pair("PUT", "/hvac/power-state") to { params: String? ->
            val newPowerState = if (params == null) Power.OFF else enumValueOf(params.toUpperCase())
            println("HVAC is being turned $newPowerState")
            hvac.power = newPowerState
            CommandResult.CONTINUE
        },
        Pair("GET", "/temperature/current") to { _: String? ->
            println("Current temperature is ${hvac.current}")
            CommandResult.CONTINUE
        },
        Pair("GET", "/temperature/target") to { _: String? ->
            println("Target temperature is ${hvac.target}")
            CommandResult.CONTINUE
        },
        Pair("PUT", "/temperature/target") to { params: String? ->
            val newTarget = params?.toTemperature() ?: Temperature.HUMAN_COMFORT
            println("Target temperature changed to $newTarget")
            hvac.target = newTarget
            CommandResult.CONTINUE
        }
    )

    fun parameterizeCommand(params: String?, cmd: Command): ParameterizedCommand {
        return { cmd(params) }
    }

    val defaultCommand: Command = {
        println("Please enter a valid command");
        CommandResult.CONTINUE
    }

    return {
        if (it != null) {
            val input = it.split(' ')
            val cmd = mappings.getOrDefault(input[0] to input.getOrNull(1), defaultCommand)
            parameterizeCommand(input.getOrNull(2), cmd)
        } else {
            parameterizeCommand(null, defaultCommand)
        }
    }
}

