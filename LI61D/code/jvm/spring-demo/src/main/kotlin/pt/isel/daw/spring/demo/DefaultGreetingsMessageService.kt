package pt.isel.daw.spring.demo

import org.springframework.stereotype.Component

private val greetings = arrayOf(
    "Hello Web",
    "Hi there, Web"
)

@Component
class DefaultGreetingsMessageService : GreetingsMessageService {
    var ix = 0

    override fun greet() = greetings[incrementAndGetIndex()]

    private fun incrementAndGetIndex(): Int {
        ix = (ix + 1) % greetings.size
        return ix
    }
}