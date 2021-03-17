package isel.leic.daw.hvac

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private enum class CommandResult { EXIT, CONTINUE }
private typealias Command = (String?) -> CommandResult
private typealias ParameterizedCommand = () -> CommandResult
private typealias Router = (String?) -> ParameterizedCommand

/**
 * Creates the application's command router, used to determine which code is to be executed for
 * each command entered by the user.
 *
 * @param   hvac    The HVAC instance on which the supported commands will operate.
 * @return  A new command router instance.
 */
private fun initRouter(hvac: Hvac): Router {
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

@SpringBootApplication
class HvacSpringApplication(private val hvac: Hvac): CommandLineRunner {
	override fun run(vararg args: String?) {
		val router = initRouter(hvac)
		do {
			val result = router(readLine()).invoke()
		} while (result != CommandResult.EXIT)
	}
}

/**
 * The application's entry point.
 *
 * The application operates in interactive mode and supports the following set of commands:
 *
 * GET /hvac/power-state
 * PUT /hvac/power-state (on|off)
 * GET /temperature/current
 * GET /temperature/target
 * PUT /temperature/target <temperature_value>
 * EXIT
 */
fun main(args: Array<String>) {
	runApplication<HvacSpringApplication>(*args)
}

