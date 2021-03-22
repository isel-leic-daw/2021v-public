package isel.leic.daw.hvac

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.*

/**
 * Comment this class if you wish to try the manual context instantiation
 */
@SpringBootApplication
class HvacSpringApplication(private val router: Router): CommandLineRunner {
	override fun run(vararg args: String?) {
		do {
			val result = router(readLine()).invoke()
		} while (result != CommandResult.EXIT)
	}
}

/**
 * If we are using @SpringBootApplication, the @ComponentScan annotation is not required
 */
@Configuration
@ComponentScan
class MyConfig {
	@Bean("Router")
	@Scope("prototype")
	fun createRouter(hvac: Hvac): Router {
		println("createRouter() called")
		return initRouter(hvac)
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

	/*
	// Manual context instantiation
	val appContext = AnnotationConfigApplicationContext(MyConfig::class.java)
	println("\n===========================================================================")
	appContext.beanDefinitionNames.forEach {
		val bean = appContext.getBeanDefinition(it)
		println("Bean name = $it, class = ${bean.beanClassName}, scope = ${bean.scope}")
	}
	println("===========================================================================")

	val hvac = appContext.getBean(Hvac::class.java)
	val otherHvac = appContext.getBean(Hvac::class.java)

	println("hvac = ${hvac.hashCode()}; otherHvac = ${otherHvac.hashCode()}")
	println("hvac === otherHvac = ${hvac === otherHvac}")

	val router = appContext.getBean("Router") as Router
	val anotherRouter = appContext.getBean("Router") as Router
	do {
		val result = router(readLine()).invoke()
	} while (result != CommandResult.EXIT)
	*/
}

