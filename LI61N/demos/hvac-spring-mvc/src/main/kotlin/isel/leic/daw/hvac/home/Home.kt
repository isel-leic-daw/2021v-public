package isel.leic.daw.hvac.home

import isel.leic.daw.hvac.common.POWER_STATE_PATH
import isel.leic.daw.hvac.common.TEMPERATURE_PATH
import org.springframework.http.CacheControl
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
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
            // Here's a simple DSL, just for the fun of it =)
            navigation {

                api {
                    title = "HVAC Web API"
                    links(
                        "author" to "mailto:palbp@cc.isel.ipl.pt",
                        "describedBy" to "/api-docs"
                    )
                }

                resources {
                    power_state = link(POWER_STATE_PATH) {
                        hints {
                            allow(HttpMethod.GET, HttpMethod.PUT)
                            acceptPut(MediaType.APPLICATION_JSON)
                        }
                    }
                    temperature = link(TEMPERATURE_PATH)
                }
            }
        )
}