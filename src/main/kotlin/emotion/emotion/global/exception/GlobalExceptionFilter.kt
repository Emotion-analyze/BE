package emotion.emotion.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets

class GlobalExceptionFilter (
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter(){


    @Throws(IOException::class)
    private fun writeErrorResponse(
        response: HttpServletResponse,
        exception: EmoException
    ){
        val errorResponse = ErrorResponse(exception.status, exception.message)
        response.status = exception.status
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: EmoException) {
            writeErrorResponse(response, e)
        }
    }

}