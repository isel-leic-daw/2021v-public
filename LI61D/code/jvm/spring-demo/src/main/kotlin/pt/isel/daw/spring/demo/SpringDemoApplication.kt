package pt.isel.daw.spring.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.context.annotation.Bean

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@SpringBootApplication
class SpringDemoApplication {
    @Bean
    fun jackson2ObjectMapperBuilder() = Jackson2ObjectMapperBuilder()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
}

private val log = LoggerFactory.getLogger(SpringDemoApplication::class.java)

fun main(args: Array<String>) {
    val context = runApplication<SpringDemoApplication>(*args)
    context.getBeansWithAnnotation(RestController::class.java).forEach { (key, value) ->
        log.info("Found controller bean '{}' with type '{}'", key, value.javaClass.name)
    }
}
