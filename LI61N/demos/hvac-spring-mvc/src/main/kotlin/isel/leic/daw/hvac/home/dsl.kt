package isel.leic.daw.hvac.home

import java.net.URI

// Work in progress: A first draft of a minimalistic DSL for specifying the Home resource
// Curious? Check this out: https://kotlinlang.org/docs/type-safe-builders.html

@DslMarker
annotation class HomeDslMarker

@HomeDslMarker
class ResourcesBuilder(var power_state: NavigationLink? = null, var temperature: NavigationLink? = null) {
    fun build() = Resources(power_state, temperature)
}

@HomeDslMarker
class ApiInfoBuilder(var title: String? = null, var links: Map<String, URI>? = null) {
    fun build() = ApiInfo(title ?: "", links)
}

@HomeDslMarker
class NavigationBuilder(
    private val apiInfoBuilder: ApiInfoBuilder = ApiInfoBuilder(),
    private val resourcesBuilder: ResourcesBuilder = ResourcesBuilder()
) {

    fun api(init: ApiInfoBuilder.() -> Unit) {
        apiInfoBuilder.init()
    }

    fun resources(init: ResourcesBuilder.() -> Unit) {
        resourcesBuilder.init()
    }

    fun build(): Navigation {
        return Navigation(
            api = apiInfoBuilder.build(),
            resources = resourcesBuilder.build()
        )
    }
}

fun navigation(init: NavigationBuilder.() -> Unit): Navigation {
    return with(NavigationBuilder()) {
        init()
        build()
    }
}