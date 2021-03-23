package pt.isel.daw.spring.demo.controllers

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class StudentOutputModel (
    val studentName: String,
    val studentNumber: Int,
    @JsonProperty("class")
    val clazz: String,
)

@RestController
@RequestMapping("mc-examples")
class MessageConversionExamplesController {

    // Using simple strings
    @GetMapping("0")
    fun handler0() = "Hello Web"

    // Using output models
    @GetMapping("1")
    fun handler1() = StudentOutputModel("Alice", 12345, "DAW")

    @PostMapping("1.1")
    fun handler11(
        @RequestBody student: StudentOutputModel
    ) = "Received $student"

    // using `ResponseEntity` that allows controlling other parts of the response message
    @GetMapping("2")
    fun handler2() = ResponseEntity
        .status(201)
        .contentType(MediaType.parseMediaType("application/vnd.isel.student+json"))
        .body(StudentOutputModel("Alice", 12345, "DAW"))

    // using custom message converters
    @GetMapping("3")
    fun handler3(): URI {
        Thread.sleep(3_000)
        return URI("https://www.example.com")
    }
}