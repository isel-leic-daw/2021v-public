package pt.isel.daw.spring.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

// The output representation classes
data class MappingItemRepr(
    val description: String,
)

data class MappingRepr(
    val list: List<MappingItemRepr>,
)

@RestController
@RequestMapping("/handler-mappings")
class IntrospectionController(
    // Note how we are depending on an infra-structure "bean"
    private val mapping: RequestMappingHandlerMapping
) {

    @GetMapping()
    fun get() = MappingRepr(
        list = mapping.handlerMethods.keys.map {
            MappingItemRepr(
                description = it.toString()
            )
        }
    )
}