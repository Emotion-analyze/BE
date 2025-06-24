package emotion.emotion.global.security.jwt

import emotion.emotion.global.exception.EmoException

sealed class TokenException (
    override val status: Int,
    override val message: String
): EmoException(status, message) {

    class InvalidTokenException(): TokenException(401, INVALID_TOKEN)

    class ExpiredTokenException(): TokenException(401, EXPIRED_TOKEN)


    companion object {
        private const val INVALID_TOKEN = "Invalid Token"
        private const val  EXPIRED_TOKEN = "Expired Token"
    }
}
