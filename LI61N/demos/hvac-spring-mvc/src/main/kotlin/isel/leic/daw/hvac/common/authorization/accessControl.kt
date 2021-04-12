package isel.leic.daw.hvac.common.authorization

import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Target(AnnotationTarget.FUNCTION)
annotation class RestrictedAccess

/**
 * This is an interceptor that verifies if resource access can be performed by the current user.
 */
class AccessControlInterceptor : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(AccessControlInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val routeHandler = handler as? HandlerMethod
        val userInfo = request.getAttribute(USER_ATTRIBUTE_KEY) as? UserInfo
        logger.info("SampleInterceptor - preHandle with handler ${handler.javaClass.name} and user is ${userInfo?.name}")

        val isRestricted = routeHandler?.hasMethodAnnotation(RestrictedAccess::class.java) ?: false
        return when {
            isRestricted && userInfo is Owner -> true
            !isRestricted && userInfo is Guest -> true
            else -> {
                // We could have used 404 (Not Found) instead
                response.status = HttpServletResponse.SC_FORBIDDEN
                false
            }
        }
    }
}
