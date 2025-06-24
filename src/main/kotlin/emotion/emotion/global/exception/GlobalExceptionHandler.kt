package emotion.emotion.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EmoException::class)
    fun handlingEmoException(e: EmoException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.status, e.message),
            HttpStatus.valueOf(e.status),
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}