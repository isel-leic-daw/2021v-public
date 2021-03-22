package isel.leic.daw.hvac

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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

