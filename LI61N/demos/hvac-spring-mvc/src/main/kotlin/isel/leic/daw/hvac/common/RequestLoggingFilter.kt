package isel.leic.daw.hvac.common

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * This is a sample filter that performs request logging.
 */
@Component
class RequestLoggingFilter : Filter {

    private val logger = LoggerFactory.getLogger(RequestLoggingFilter::class.java)

    private fun getHeaderContent(httpRequest: HttpServletRequest, headerName: String) =
        if (headerName.equals("authorization", ignoreCase = true) || headerName.equals("cookie", ignoreCase = true))
            "<obfuscated>"
        else httpRequest.getHeader(headerName)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest

        val start = System.currentTimeMillis()
        val headers = StringBuilder()
        httpRequest.headerNames.iterator().forEach {
            headers.append("\t\t$it: ${getHeaderContent(httpRequest, it)}\n ")
        }

        logger.info("Handling ${httpRequest.method} ${httpRequest.requestURI} with headers: \n $headers")
        chain?.doFilter(request, response)
        logger.info("Handled ${httpRequest.method} ${httpRequest.requestURI} in ${System.currentTimeMillis() - start} ms")
    }
}
