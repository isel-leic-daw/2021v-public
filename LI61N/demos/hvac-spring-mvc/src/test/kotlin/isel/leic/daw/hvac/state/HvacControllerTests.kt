package isel.leic.daw.hvac.state

import isel.leic.daw.hvac.common.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put


/**
 * For more information regarding this kind of testing, see:
 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework">
 *     Spring Testing
 * </a> and <a href="https://github.com/jayway/JsonPath">JsonPath</a>
 *
 * <a href="https://jsonpath.com/">Json Path evaluator</a>
 */
@SpringBootTest
@AutoConfigureMockMvc
class HvacControllerTests {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun getPowerState_fromAnonymous_shouldReturn401() {
        mvc.get(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
        }.andExpect {
            status { isUnauthorized() }
            header { exists(HttpHeaders.WWW_AUTHENTICATE) }
        }
    }

    @Test
    fun putPowerState_fromAnonymous_shouldReturn401() {
        mvc.put(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            contentType = MediaType.APPLICATION_JSON
            content = " { \"value\": \"ON\" } "
        }.andExpect {
            status { isUnauthorized() }
            header {  exists(HttpHeaders.WWW_AUTHENTICATE) }
        }
    }

    @Test
    fun getPowerState_fromGuest_shouldReturn200AndPowerStateSirenPayloadWithNoActions() {
        mvc.get(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("PowerState") }
            jsonPath("$.properties.value") { isString() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun putPowerState_fromGuest_withValidPayload_shouldReturn403() {
        mvc.put(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
            contentType = MediaType.APPLICATION_JSON
            content = " { \"value\": \"ON\" } "
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    fun getPowerState_fromOwner_shouldReturn200AndPowerStateSirenPayloadWithSetAction() {
        mvc.get(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("PowerState") }
            jsonPath("$.properties.value") { isString() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { isArray() }
            jsonPath("$.actions[0].name") { value("set-power-state")}
        }
    }

    @Test
    fun putPowerState_fromOwner_withValidPayload_shouldReturn200AndPowerStateSirenPayload() {
        mvc.put(POWER_STATE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
            contentType = MediaType.APPLICATION_JSON
            content = " { \"value\": \"ON\" } "
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("PowerState") }
            jsonPath("$.properties.value") { value("ON") }
            jsonPath("$.links") { isArray() }
        }
    }
}