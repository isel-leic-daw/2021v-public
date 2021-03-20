package pt.isel.daw.spring.demo.controllers

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy::class)
data class OutputModel (
    val studentName: String,
    val studentNumber: Int
)

@RestController
@RequestMapping("mc-examples")
class MessageConversionExamplesController {

    // Using simple strings
    @GetMapping("0")
    fun handler0() = "Hello Web"

    // Using output models
    @GetMapping("1")
    fun handler1() = OutputModel("Alice", 12345)

    // using `ResponseEntity` that allows controlling other parts of the response message
    @GetMapping("2")
    fun handler2() = ResponseEntity.status(200)
        .contentType(MediaType.parseMediaType("application/vnd.isel.student+json"))
        .body(OutputModel("Alice", 12345))

    // using custom message converters
    @GetMapping("3")
    fun handler3() = URI("https://www.example.com")
}