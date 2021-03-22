package isel.leic.daw.hvac.common

import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * This is a sample filter that performs request logging.
 */
@Component
class MySampleFilter(private val env: Environment) : Filter {

    private val logger = LoggerFactory.getLogger(MySampleFilter::class.java)

    private fun getHeaderContent(httpRequest: HttpServletRequest, headerName: String) =
        if (headerName.equals("authorization", ignoreCase = true) || headerName.equals("cookie", ignoreCase = true))
            "<obfuscated>"
        else httpRequest.getHeader(headerName)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest

        val start = System.currentTimeMillis()
        val headers = StringBuilder()
        httpRequest.headerNames.iterator().forEach {
            headers.append("$it: ${getHeaderContent(httpRequest, it)}; ")
        }
        logger.info("SampleFilter: handling ${httpRequest.method} ${httpRequest.requestURI} with headers: $headers")
        chain?.doFilter(request, response)
        logger.info("SampleFilter: handled ${httpRequest.method} ${httpRequest.requestURI} in ${System.currentTimeMillis() - start} ms")
    }
}

/**
 * This is a sample interceptor.
 */
class MySampleInterceptor : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(MySampleInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("SampleInterceptor - preHandle")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info("SampleInterceptor - postHandle")
    }
}
