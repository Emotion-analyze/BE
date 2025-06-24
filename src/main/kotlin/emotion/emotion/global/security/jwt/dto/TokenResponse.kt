package emotion.emotion.global.security.jwt.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)