package isel.leic.daw.hvac.common.authorization

import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * This annotation is intended for use on controllers that contain operations on protected resources.
 * @see AccessControlInterceptor
 */
@Target(AnnotationTarget.CLASS)
annotation class ProtectedResource

/**
 * This annotation is intended for use on controller methods (actions) that require special privileges. Controllers that
 * contain restricted actions (methods annotated with [RestrictedAccess]) MUST be annotated with [ProtectedResource]
 * @see AccessControlInterceptor
 */
@Target(AnnotationTarget.FUNCTION)
annotation class RestrictedAccess

/**
 * This is an interceptor that verifies if a resource access can be performed by the current user.
 */
class AccessControlInterceptor : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(AccessControlInterceptor::class.java)

    private enum class AccessLevel { PROTECTED, PROTECTED_AND_RESTRICTED, PUBLIC }

    private fun getAccessLevel (handlerMethod: HandlerMethod?): AccessLevel {
        val isProtected = handlerMethod?.beanType?.getAnnotation(ProtectedResource::class.java)  != null
        val isRestricted = handlerMethod?.hasMethodAnnotation(RestrictedAccess::class.java) ?: false
        return when {
            handlerMethod == null -> AccessLevel.PUBLIC
            isProtected && !isRestricted -> AccessLevel.PROTECTED
            isProtected && isRestricted -> AccessLevel.PROTECTED_AND_RESTRICTED
            else -> AccessLevel.PUBLIC
        }
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val routeHandler = handler as? HandlerMethod
        val userInfo = request.getAttribute(USER_ATTRIBUTE_KEY) as? UserInfo
        logger.info("SampleInterceptor - preHandle with handler ${handler.javaClass.name} and user is ${userInfo?.name}")

        val accessLevel = getAccessLevel(routeHandler)
        return when {
            accessLevel == AccessLevel.PUBLIC -> true
            accessLevel == AccessLevel.PROTECTED && userInfo != null -> true
            accessLevel == AccessLevel.PROTECTED_AND_RESTRICTED && userInfo is Owner -> true
            else -> {
                // We could have used 404 (Not Found) instead
                response.status = HttpServletResponse.SC_FORBIDDEN
                false
            }
        }
    }
}
