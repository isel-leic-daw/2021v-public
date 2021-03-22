package isel.leic.daw.hvac

import isel.leic.daw.hvac.common.MySampleInterceptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class ApiConfig : WebMvcConfigurer {
	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(MySampleInterceptor())
	}
}

@SpringBootApplication
class HvacSpringMvcApplication

fun main(args: Array<String>) {
	runApplication<HvacSpringMvcApplication>(*args)
}

