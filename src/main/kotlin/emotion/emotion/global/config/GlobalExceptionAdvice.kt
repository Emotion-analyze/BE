package emotion.emotion.global.config

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Hidden
@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionAdvice : ResponseEntityExceptionHandler()
