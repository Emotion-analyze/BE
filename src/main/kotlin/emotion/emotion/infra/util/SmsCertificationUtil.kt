package emotion.emotion.infra.util

import jakarta.annotation.PostConstruct
import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.stereotype.Component

@Component
class SmsCertificationUtil(
    private val coolSmsProperties: CoolSmsProperties,
) {
    private lateinit var messageService: DefaultMessageService

    @PostConstruct
    fun init() {
        messageService =
            NurigoApp.initialize(coolSmsProperties.apiKey, coolSmsProperties.secretKey, "https://api.coolsms.co.kr")
    }

    fun sendSMS(to: String, certificationCode: String) {
        val message = Message().apply {
            from = coolSmsProperties.phone
            this.to = to
            text = "본인확인 인증번호는 $certificationCode 입니다."
        }

        messageService.sendOne(SingleMessageSendingRequest(message))
    }
}