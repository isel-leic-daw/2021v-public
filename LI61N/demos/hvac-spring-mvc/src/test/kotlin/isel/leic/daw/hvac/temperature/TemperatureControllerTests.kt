package isel.leic.daw.hvac.temperature

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
class TemperatureControllerTests {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun getTemperature_fromAnonymous_shouldReturn401() {
        mvc.get(TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
        }.andExpect {
            status { isUnauthorized() }
            header { exists(HttpHeaders.WWW_AUTHENTICATE) }
        }
    }

    @Test
    fun getTemperature_fromGuest_shouldReturn200AndATemperatureInfoSirenPayloadWithNoActions() {

        mvc.get(TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("TemperatureInfo") }
            jsonPath("$.properties.target") { isNumber() }
            jsonPath("$.properties.current") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun getTemperature_fromOwner_shouldReturn200AndATemperatureInfoSirenPayloadWithNoActions() {

        mvc.get(TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("TemperatureInfo") }
            jsonPath("$.properties.target") { isNumber() }
            jsonPath("$.properties.current") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun getCurrentTemperature_fromAnonymous_shouldReturn401() {

        mvc.get(CURRENT_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
        }.andExpect {
            status { isUnauthorized() }
            header { exists(HttpHeaders.WWW_AUTHENTICATE) }
        }
    }

    @Test
    fun getCurrentTemperature_fromGuest_shouldReturn200AndATemperatureSirenPayloadWithNoActions() {

        mvc.get(CURRENT_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("Temperature") }
            jsonPath("$.properties.value") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun getCurrentTemperature_fromOwner_shouldReturn200AndATemperatureSirenPayloadWithNoActions() {

        mvc.get(CURRENT_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("Temperature") }
            jsonPath("$.properties.value") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun getTargetTemperature_fromAnonymous_shouldReturn401() {

        mvc.get(TARGET_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
        }.andExpect {
            status { isUnauthorized() }
            header { exists(HttpHeaders.WWW_AUTHENTICATE) }
        }
    }

    @Test
    fun getTargetTemperature_fromGuest_shouldReturn200AndATemperatureSirenPayloadWithNoActions() {

        mvc.get(TARGET_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("Temperature") }
            jsonPath("$.properties.value") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { doesNotExist() }
        }
    }

    @Test
    fun getTargetTemperature_fromOwner_shouldReturn200AndATemperatureSirenPayloadWithSetAction() {

        mvc.get(TARGET_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("Temperature") }
            jsonPath("$.properties.value") { isNumber() }
            jsonPath("$.links") { isArray() }
            jsonPath("$.actions") { isArray() }
            jsonPath("$.actions[0].name") { value("set-target-temperature")}
        }
    }

    @Test
    fun putTargetTemperature_fromGuest_withValidPayload_shouldReturn403() {

        mvc.put(TARGET_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestGuest", "TestGuest") }
            contentType = MediaType.APPLICATION_JSON
            content = "{ \"value\": 25 } "
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    fun putTargetTemperature_fromOwner_withValidPayload_shouldReturn200() {

        mvc.put(TARGET_TEMPERATURE_PATH) {
            accept = MediaType(APPLICATION_TYPE, SIREN_SUBTYPE)
            headers { setBasicAuth("TestOwner", "TestOwner") }
            contentType = MediaType.APPLICATION_JSON
            content = "{ \"value\": 25 } "
        }.andExpect {
            status { isOk() }
            content { contentType(SIREN_MEDIA_TYPE) }
            jsonPath("$.class[0]") { value("TemperatureInfo") }
            jsonPath("$.properties.target") { value(25) }
            jsonPath("$.links") { isArray() }
        }
    }
}