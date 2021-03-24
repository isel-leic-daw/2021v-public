package pt.isel.daw.spring.demo.pipeline.exceptionhandling

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pt.isel.daw.spring.demo.models.ProblemJsonModel
import java.net.URI

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ) : ResponseEntity<Any> {

        logger.info("Handling MethodArgumentNotValidException")
        return ResponseEntity
            .status(404)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(ProblemJsonModel(
                type = URI("https://example.org/problems/not-found")) as Any)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ) : ResponseEntity<Any> {

        logger.info("Handling MethodArgumentNotValidException")
        return ResponseEntity
            .status(404)
            .contentType(ProblemJsonModel.MEDIA_TYPE)
            .body(ProblemJsonModel(
                type = URI("https://example.org/problems/not-found")) as Any)
    }

}