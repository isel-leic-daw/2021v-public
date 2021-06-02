package isel.leic.daw.hvac.home

import isel.leic.daw.hvac.common.POWER_STATE_PATH
import isel.leic.daw.hvac.common.TEMPERATURE_PATH
import isel.leic.daw.hvac.common.authorization.isFromOwner
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class Home {

    @GetMapping(produces = [JSON_HOME_MEDIA_TYPE])
    fun getNavigation(req: HttpServletRequest): ResponseEntity<Navigation> = ResponseEntity
        .ok()
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
                    power_state = if (req.isFromOwner()) link(POWER_STATE_PATH) {
                        hints {
                            allow(HttpMethod.GET, HttpMethod.PUT)
                            acceptPut(MediaType.APPLICATION_JSON)
                        }
                    } else link(POWER_STATE_PATH)
                    temperature = link(TEMPERATURE_PATH)
                }
            }
        )
}