package pt.isel.daw.spring.demo.controllers

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import pt.isel.daw.spring.demo.data.ClientIp
import javax.servlet.http.HttpServletRequest

/*
 * Parameter binding examples
 */

data class StudentInputModel(
    val name: String,
    val number: Int,
)

@RestController
@RequestMapping("ab-examples")
class ArgumentBindingExamplesController {

    // Binding path variables to arguments
    // /ab-examples/0/{id}
    @GetMapping("0/{id}")
    fun handler0(
        @PathVariable id: Int,
    ) = "handler0 with $id"

    // Binding query string values to arguments
    @GetMapping("1")
    fun handler1(
        @RequestParam id: Int,
    ) = "handler1 with $id"

    // Support for optional query string value
    @GetMapping("2")
    fun handler2(
        @RequestParam id: Int?,
    ) = "handler2 with ${id ?: "absent"}"

    // Binding all query string pairs to an arguments
    @GetMapping("3")
    fun handler3(
        @RequestParam prms: MultiValueMap<String, String>,
    ) = prms
        .map { "${it.key}: ${it.value.joinToString(", ", "[", "]")}\n" }
        .joinToString()

    // Using a custom ArgumentResolver to bind a custom type to an arguments
    @GetMapping("4")
    fun handler4(
        clientIp: ClientIp,
    ) = "Hello ${clientIp.ipString}"

    // Using generic argument resolution, supporting JSON
    @PostMapping("5")
    fun handler5(
        @RequestBody input: StudentInputModel,
    ) = "Received student with name=${input.name} and number=${input.number}"

    // Binding the raw HttpServletRequest to the request
    @PostMapping("6")
    fun handler6(
        request: HttpServletRequest,
        @RequestBody input: StudentInputModel,
    ) = "Accept: ${request.getHeader("Accept")}, Body: ${input.toString()} }"

    @GetMapping("7/{aid}/b/{bid}")
    fun handler7(
        @PathVariable aid: Int,
        @PathVariable bid: String,
    ) = "handler7 with aid=$aid and bid=$bid"
}