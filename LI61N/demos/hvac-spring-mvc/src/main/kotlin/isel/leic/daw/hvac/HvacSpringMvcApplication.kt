package isel.leic.daw.hvac

import com.fasterxml.jackson.annotation.JsonInclude
import isel.leic.daw.hvac.common.authorization.AccessControlInterceptor
import isel.leic.daw.hvac.common.authorization.UserInfo
import isel.leic.daw.hvac.common.authorization.verifyBasicSchemeCredentials
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class ApiConfig : WebMvcConfigurer {

	private val logger = LoggerFactory.getLogger(HvacSpringMvcApplication::class.java)

	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(AccessControlInterceptor())
	}

	/**
	 * We could be using this override to extend the chain of message converters by adding to the received list.
	 * But in this case we are using it simply for logging the list of installed message converters.
	 */
	override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
		logger.info("Installed message converters are:")
		converters.forEach {
			logger.info("${it.javaClass.name} supporting ${it.supportedMediaTypes}")
		}
	}
}

@SpringBootApplication
class HvacSpringMvcApplication {

	/**
	 * Provides a builder of customized message converters from the Jackson library. These converters handle JSON
	 * serialization. This is how we can provide a message converter that does not use the default settings.
	 */
	@Bean
	fun jackson2ObjectMapperBuilder() = Jackson2ObjectMapperBuilder()
		.serializationInclusion(JsonInclude.Include.NON_NULL)
		.failOnUnknownProperties(true)

	/**
	 * Provides the implementation of the user credentials verification procedure
	 */
	@Bean
	fun authenticationProvider(): Function1<String, UserInfo?> = ::verifyBasicSchemeCredentials
}

fun main(args: Array<String>) {
	runApplication<HvacSpringMvcApplication>(*args)
}

