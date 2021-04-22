package isel.leic.daw.hvac.home

import isel.leic.daw.hvac.common.APPLICATION_TYPE
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI

const val JSON_HOME_SUBTYPE = "json-home"
const val JSON_HOME_MEDIA_TYPE = "${APPLICATION_TYPE}/${JSON_HOME_SUBTYPE}"

/**
 * Output model used to describe the API's navigation links, as specified in [json-home](https://mnot.github.io/I-D/json-home/).
 */
data class Navigation(val api: ApiInfo, val resources: Resources)

/**
 * Used to describe the set of resources reachable from the home resource.
 */
data class Resources(
    val power_state: NavigationLink? = null,
    val temperature: NavigationLink? = null,
)

/**
 * Used in the [Navigation] output model and contains the API information.
 */
data class ApiInfo(val title: String, val links: Map<String, URI>?)

/**
 * Used in the [Navigation] output model and contains the link to a resource.
 */
data class NavigationLink(
    val href: URI,
    val hrefTemplate: String? = null,
    val hrefVars: Map<String, String>? = null,
    val hints: Hints? = null
)

/**
 * An extremely minimalistic version of the Hints property.
 * TODO: Add remaining properties to it
 */
data class Hints(
    val allow: List<HttpMethod>? = null,
    val acceptPut: List<MediaType>? = null,
    val acceptPost: List<MediaType>? = null
)

