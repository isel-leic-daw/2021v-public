package pt.isel.daw.spring.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    private val greetingsMessageService: GreetingsMessageService,
) {

    @GetMapping("/examples/1")
    fun get() = "Hello Web"

    @GetMapping("/examples/2", produces = ["text/plain"])
    fun get2() = "Hello Web, from ${System.getenv("HOSTNAME") }"

    @GetMapping("/examples/3")
    fun get3() = greetingsMessageService.greet()

    @GetMapping("/examples/4")
    fun get4(): Unit = throw Exception("As an example")
}