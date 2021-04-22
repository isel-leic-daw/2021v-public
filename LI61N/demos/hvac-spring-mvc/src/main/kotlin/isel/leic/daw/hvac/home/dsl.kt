package isel.leic.daw.hvac.home

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI

// Work in progress: A first draft of a minimalistic DSL for specifying the Home resource
// Curious? Check this out: https://kotlinlang.org/docs/type-safe-builders.html

@DslMarker
annotation class HomeDslMarker

// Root element of the DSL
fun navigation(init: NavigationBuilder.() -> Unit): Navigation {
    return with(NavigationBuilder()) {
        init()
        build()
    }
}

@HomeDslMarker
class NavigationBuilder {

    private val apiInfoBuilder: ApiInfoBuilder = ApiInfoBuilder()
    private val resourcesBuilder: ResourcesBuilder = ResourcesBuilder()

    fun api(init: ApiInfoBuilder.() -> Unit) = apiInfoBuilder.init()

    fun resources(init: ResourcesBuilder.() -> Unit) = resourcesBuilder.init()

    fun build(): Navigation {
        return Navigation(
            api = apiInfoBuilder.build(),
            resources = resourcesBuilder.build()
        )
    }
}

// The api element
@HomeDslMarker
class ApiInfoBuilder {

    var title: String? = null
    private var links: Map<String, URI>? = null

    fun links(vararg pairs: Pair<String, String>) {
        links = mapOf(*pairs).mapValues { URI(it.value) }
    }

    fun build() = ApiInfo(title ?: "", links)
}

// The resources element
@HomeDslMarker
class ResourcesBuilder {

    var power_state: NavigationLink? = null
    var temperature: NavigationLink? = null

    fun link(href: String) = NavigationLinkBuilder(URI(href)).build()

    fun link(href: String, init: NavigationLinkBuilder.() -> Unit) =
        with(NavigationLinkBuilder(URI(href))) {
            init()
            build()
        }

    fun build() = Resources(power_state, temperature)
}

@HomeDslMarker
class NavigationLinkBuilder(private val href: URI) {

    var hrefTemplate: String? = null
    var hrefVars: Map<String, String>? = null
    private var hintsBuilder: HintsBuilder? = null

    fun hints(init: HintsBuilder.() -> Unit) {
        hintsBuilder = HintsBuilder().also(init)
    }

    fun build() = NavigationLink(href, hrefTemplate, hrefVars, hintsBuilder?.build())
}

// The hints element
@HomeDslMarker
class HintsBuilder {

    private var allow: List<HttpMethod>? = null
    private var acceptPut: List<MediaType>? = null
    private var acceptPost: List<MediaType>? = null

    fun allow(vararg methods: HttpMethod) {
        allow = methods.asList()
    }

    fun acceptPut(vararg mediaTypes: MediaType) {
        acceptPut = mediaTypes.asList()
    }

    fun acceptPost(vararg mediaTypes: MediaType) {
        acceptPost = mediaTypes.asList()
    }

    fun build() = Hints(allow, acceptPut, acceptPost)
}