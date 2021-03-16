package pt.isel.daw.spring.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringDemoApplication

private val log = LoggerFactory.getLogger(SpringDemoApplication::class.java)

fun main(args: Array<String>) {
    val context = runApplication<SpringDemoApplication>(*args)
    context.getBeansWithAnnotation(RestController::class.java).forEach { (key, value) ->
        log.info("Found controller bean '{}' with type '{}'", key, value.javaClass.name)
    }
}
