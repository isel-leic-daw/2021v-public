package isel.leic.daw.hvac.common.authorization

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import org.springframework.util.DigestUtils
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

/**
 * Implementation of our simplistic authentication scheme
 *
 * @param challengeResponse the content of the Authorization Header (tØØhe challenge response)
 * @return  the [UserInfo] instance representing the user role, or null if the credentials are invalid
 */
fun verifyBasicSchemeCredentials(challengeResponse: String): UserInfo? {

    val pretenseUserDB = mapOf(
        "Paulo" to Pair(Owner("Paulo"), DigestUtils.md5Digest("NotAnActualSecret".encodeToByteArray())),
        "Pedro" to Pair(Guest("Pedro"), DigestUtils.md5Digest("NotASecretEither".encodeToByteArray())),
        "TestOwner" to Pair(Owner("TestOwner"), DigestUtils.md5Digest("TestOwner".encodeToByteArray())),
        "TestGuest" to Pair(Guest("TestGuest"), DigestUtils.md5Digest("TestGuest".encodeToByteArray()))
    )

    // Note: This is not a real implementation of a user credential's verification procedure
    fun verifyUserCredentials(userId: String, pwd: String): UserInfo? {
        val credentials = pretenseUserDB[userId]
        return when {
            credentials == null -> null
            credentials.second.contentEquals(DigestUtils.md5Digest(pwd.encodeToByteArray())) -> credentials.first
            else -> null
        }
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
            httpResponse.addHeader(HttpHeaders.WWW_AUTHENTICATE, "$BASIC_SCHEME realm=\"hvac\"")
        }
    }
}
