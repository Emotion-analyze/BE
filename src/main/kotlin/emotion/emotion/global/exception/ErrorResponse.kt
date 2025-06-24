package emotion.emotion.global.exception

data class ErrorResponse(
    val status: Int,
    val message: String?
)