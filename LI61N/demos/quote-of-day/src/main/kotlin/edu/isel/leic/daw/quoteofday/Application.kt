package edu.isel.leic.daw.quoteofday

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuoteOfDayApplication

fun main(args: Array<String>) {
	runApplication<QuoteOfDayApplication>(*args)
}
