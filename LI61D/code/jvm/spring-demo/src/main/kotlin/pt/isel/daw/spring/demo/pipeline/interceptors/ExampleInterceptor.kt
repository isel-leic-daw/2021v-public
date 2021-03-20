package pt.isel.daw.spring.demo.pipeline.interceptors

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import pt.isel.daw.utils.loggerFor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private val log = loggerFor<ExampleInterceptor>()

@Component
class ExampleInterceptor : HandlerInterceptor {

    override fun preHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any): Boolean {

        log.info("Before calling $handler (${handler.javaClass.name})")
        if(handler is HandlerMethod) {
            log.info("Before calling ${handler.method.name}")
        }
        return true
    }

    override fun postHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any, modelAndView: ModelAndView?) {
        log.info("After calling $handler")
    }
}