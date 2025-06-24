package emotion.emotion.infra.port

import emotion.emotion.infra.presentation.CheckCodeRequest
import emotion.emotion.infra.presentation.SmsRequestDto

interface SmsUseCase {
    fun sendSms(request: SmsRequestDto)

    fun checkCode(request: CheckCodeRequest): Boolean
}