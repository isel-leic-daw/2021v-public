package isel.leic.daw.hvac.home

import isel.leic.daw.hvac.common.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest

const val SECONDS_IN_A_DAY: Long = 60 * 60 * 24

@RestController
class Home {

    @GetMapping(produces = [JSON_HOME_MEDIA_TYPE])
    fun getNavigation(req: HttpServletRequest): ResponseEntity<Navigation> = ResponseEntity
        .ok()
        .cacheControl(CacheControl.maxAge(SECONDS_IN_A_DAY, TimeUnit.SECONDS).cachePrivate())
        .body(
            /*
            Navigation(
                ApiInfo("HVAC Web API", mapOf(
                        "author" to URI("mailto:palbp@cc.isel.ipl.pt"),
                        "describedBy" to URI("/api-docs"))
                ),
                Resources(
                        power_state = NavigationLink(
                            href = POWER_STATE_PATH,
                            hints = Hints(
                                allow = listOf(HttpMethod.GET, HttpMethod.PUT),
                                 acceptPut = listOf(MediaType.APPLICATION_JSON)
                            )
                        ),
                        temperature = NavigationLink(href = TEMPERATURE_PATH),
                )
            )
                    */
            // TODO: This deserves a DSL (work in progress)
            navigation {

                api {
                    title = "HVAC Web API"
                    links = mapOf(
                        "author" to URI("mailto:palbp@cc.isel.ipl.pt"),
                        "describedBy" to URI("/api-docs")
                    )
                }

                resources {
                    power_state = NavigationLink(href = POWER_STATE_PATH)
                    temperature = NavigationLink(href = TEMPERATURE_PATH)
                }
            }
        )
}