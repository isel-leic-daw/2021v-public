package edu.isel.leic.daw.quoteofday

import org.springframework.web.bind.annotation.*

const val BASE_PATH = "/quotes"
const val QUOTE_OF_DAY = "/quote-of-day"

/**
 * Controller for the Hvac's state resource
 */
@RestController
@RequestMapping(BASE_PATH)
class Controller(val service: Service) {

    @GetMapping(QUOTE_OF_DAY)
    fun getQuoteOfDay() = service.getQuote()
}