package isel.leic.daw.hvac

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import isel.leic.daw.hvac.common.APPLICATION_TYPE
import isel.leic.daw.hvac.common.authorization.AccessControlInterceptor
import isel.leic.daw.hvac.common.authorization.UserInfo
import isel.leic.daw.hvac.common.authorization.getBasicCredentialsVerifier
import isel.leic.daw.hvac.home.JSON_HOME_SUBTYPE
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Function that returns a customized message converter for generating json-home representations
 */
fun createJsonHomeMessageConverter(): MappingJackson2HttpMessageConverter {
	with(MappingJackson2HttpMessageConverter()) {
		supportedMediaTypes = listOf(MediaType(APPLICATION_TYPE, JSON_HOME_SUBTYPE))
		objectMapper
			.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
		return this
	}
}

@Configuration
@EnableWebMvc
class ApiConfig : WebMvcConfigurer {

	private val logger = LoggerFactory.getLogger(HvacSpringMvcApplication::class.java)

	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(AccessControlInterceptor())
	}

	/**
	 * Configure the chain of message converters, and log its composition
	 */
	override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {

		val converter = converters.find {
			it is MappingJackson2HttpMessageConverter
		} as MappingJackson2HttpMessageConverter

		// Customizing the default json converter
		converter.objectMapper
			.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

		// Adding a new converter dedicated to json-home conversion
		converters.add(createJsonHomeMessageConverter())

		logger.info("Installed message converters are:")
		converters.forEach {
			logger.info("${it.javaClass.name} supporting ${it.supportedMediaTypes}")
		}
	}
}

@SpringBootApplication
class HvacSpringMvcApplication {

	/**
	 * Provides the implementation of the user credentials verification procedure
	 */
	@Bean
	fun authenticationProvider(): Function1<String, UserInfo?> = getBasicCredentialsVerifier()
}

fun main(args: Array<String>) {
	runApplication<HvacSpringMvcApplication>(*args)
}

