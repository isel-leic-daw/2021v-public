package isel.leic.daw.hvac.common

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * Class used for introducing a moderate amount of chaos to all request processing.
 * The intent is to simulate adverse conditions when in debug mode.
 * TODO: Randomly inject errors
 */
@Component
@Order(Int.MIN_VALUE)
class ChaosFilter(private val env: Environment) : Filter {

    private val logger = LoggerFactory.getLogger(ChaosFilter::class.java)

    private fun getHeaderContent(httpRequest: HttpServletRequest, headerName: String) =
        if (headerName.equals("authorization", ignoreCase = true) || headerName.equals("cookie", ignoreCase = true))
            "<obfuscated>"
        else httpRequest.getHeader(headerName)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest
        val isE2ETests = (env.getProperty("api.e2e.tests") ?: "false").toBoolean()
        if (BuildInfo.isDebugBuild() && !isE2ETests) {
            logger.info("ChaosFilter is delaying processing of request ${httpRequest.method} ${httpRequest.requestURI}")
            Thread.sleep(3000)
        }

        logger.info("Handling ${httpRequest.method} ${httpRequest.requestURI} with headers:")
        val start = System.currentTimeMillis()
        val headers = StringBuilder()
        httpRequest.headerNames.iterator().forEach {
            headers.append("$it: ${getHeaderContent(httpRequest, it)}; ")
        }
        logger.info("Headers - $headers")

        chain?.doFilter(request, response)
        logger.info("Handled ${httpRequest.method} ${httpRequest.requestURI} in ${System.currentTimeMillis() - start} ms")
    }
}
