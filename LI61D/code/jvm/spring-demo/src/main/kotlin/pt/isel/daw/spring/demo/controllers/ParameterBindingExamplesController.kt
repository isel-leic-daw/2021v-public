package pt.isel.daw.spring.demo.controllers

import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import pt.isel.daw.spring.demo.data.ClientIp
import javax.servlet.http.HttpServletRequest

/*
 * Parameter binding examples
 */

data class InputModel(
    val name: String,
    val number: Int,
)

@RestController
@RequestMapping("pb-examples")
class ParameterBindingExamplesController {

    // Binding path variables to parameters
    @GetMapping("0/{id}")
    fun handler0(
        @PathVariable id: Int,
    ) = "handler0 with $id"

    // Binding query string values to parameters
    @GetMapping("1")
    fun handler1(
        @RequestParam id: Int,
    ) = "handler1 with $id"

    // Support for optional query string value
    @GetMapping("2")
    fun handler2(
        @RequestParam() id: Int?,
    ) = "handler1 with ${id ?: "absent"}"

    // Binding all query string pairs to a parameter
    @GetMapping("3")
    fun handler3(
        @RequestParam prms: MultiValueMap<String, String>,
    ) = prms
        .map { "${it.key}: ${it.value.joinToString(", ", "[", "]")}\n" }
        .joinToString()

    // Using a custom ArgumentResolver to bind a custom type to a parameter
    @GetMapping("4")
    fun handler4(
        clientIp: ClientIp,
    ) = "Hello ${clientIp.ipString}"

    // Using generic argument resolution, supporting JSON
    @PostMapping("5")
    fun handler5(
        @RequestBody input: InputModel,
    ) = input.toString()

    // Binding the raw HttpServletRequest to the request
    @PostMapping("6")
    fun handler6(
        request: HttpServletRequest,
        @RequestBody input: InputModel,
    ) = "Accept: ${request.getHeader("Accept")}, Body: ${input.toString()} }"
}