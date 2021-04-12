package isel.leic.daw.hvac.common.authorization

import isel.leic.daw.hvac.common.RequestLoggingFilter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Contract to be supported by credential's verification procedures
 */
typealias CredentialsVerifier = (credentials: String) -> UserInfo?

/**
 * Closed hierarchy (i.e. a sum type) used to support our simplistic RBAC (role-based access control) approach
 */
sealed class UserInfo(val name: String)
class Guest(name: String) : UserInfo(name)
class Owner(name: String) : UserInfo(name)

const val USER_ATTRIBUTE_KEY = "user-attribute"
const val BASIC_SCHEME = "Basic"
const val CHALLENGE_HEADER = "WWW-Authenticate"

/**
 * Implementation of our simplistic authentication scheme
 *
 * @param challengeResponse the content of the Authorization Header (the challenge response)
 * @return  the [UserInfo] instance representing the user role, or null if the credentials are invalid
 */
fun verifyBasicSchemeCredentials(challengeResponse: String): UserInfo? {

    fun verifyUserCredentials(userId: String, pwd: String) = when {
        userId == "Paulo" && pwd == "NotAnActualSecret" -> Owner(userId)
        userId == "Pedro" && pwd == "NotASecretEither" -> Guest(userId)
        else -> null
    }

    val trimmedChallengeResponse = challengeResponse.trim()
    return if (trimmedChallengeResponse.startsWith(BASIC_SCHEME, ignoreCase = true)) {
        val userCredentials = trimmedChallengeResponse.drop(BASIC_SCHEME.length + 1).trim()
        val (userId, pwd) = String(Base64Utils.decodeFromString(userCredentials)).split(':')
        verifyUserCredentials(userId, pwd)
    }
    else {
        null
    }
}

/**
 * This is a sample filter that performs request logging.
 */
@Component
class AuthenticationFilter(private val credentialsVerifier: CredentialsVerifier) : Filter {

    private val logger = LoggerFactory.getLogger(AuthenticationFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest
        val authorizationHeader: String = httpRequest.getHeader("authorization") ?: ""

        val userInfo = credentialsVerifier(authorizationHeader)
        if (userInfo != null) {
            logger.info("User credentials are valid. Proceeding.")
            httpRequest.setAttribute(USER_ATTRIBUTE_KEY, userInfo)
            chain?.doFilter(request, response)
        }
        else {
            logger.info("User credentials are invalid or were not provided. Issuing challenge.")
            val httpResponse = response as HttpServletResponse
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.addHeader(CHALLENGE_HEADER, "$BASIC_SCHEME realm=\"hvac\"")
        }
    }
}
