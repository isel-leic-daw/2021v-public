package pt.isel.daw.spring.demo.pipeline.exceptionhandling

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.lang.Exception

private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

//@ControllerAdvice
class ExceptionHandlerViewer {

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception ){
        logger.info("Exception thrown {}", ex.javaClass)
        throw ex
    }

}