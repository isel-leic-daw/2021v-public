package pt.isel.daw.spring.demo

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = LoggerFactory.getLogger(ExampleFilter::class.java)


/**
 * Example servlet filter to log the message processing.
 * This filter is automatically registered because it is found as a component
 * via component scan.
 */
@Component
class ExampleFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val sw = StopWatch()
        sw.start()
        try {
            chain.doFilter(request, response)
            sw.stop()
            log.info(
                "Request processed: method={}, path={}, status={}, t={}",
                httpRequest.method,
                httpRequest.requestURI,
                httpResponse.status,
                sw.totalTimeMillis
            )
        } catch (th: Throwable) {
            sw.stop()
            log.error("Request processed with exception: e={}, t={}", th.message, sw.totalTimeMillis)
            throw th
        }
    }
}