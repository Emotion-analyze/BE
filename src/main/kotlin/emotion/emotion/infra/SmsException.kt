package emotion.emotion.infra

import emotion.emotion.global.exception.EmoException

sealed class SmsException(
    override val status: Int,
    override val message: String
): EmoException(status, message) {

    class CodeMissMatchException(): SmsException(401, CODE_MISS_MATCH)

    class ExpiredCodeException(): SmsException(401, EXPIRED_CODE)

    class CodeNotFoundException(): SmsException(404, CODE_NOT_FOUND)

    companion object {
        private const val CODE_MISS_MATCH = "인증 코드가 올바르지 않습니다."
        private const val EXPIRED_CODE = "만료된 인증 코드 입니다"
        private const val CODE_NOT_FOUND = "인증코드가 존재하지 않습니다"
    }
}