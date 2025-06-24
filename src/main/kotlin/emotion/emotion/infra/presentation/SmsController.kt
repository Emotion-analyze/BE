package emotion.emotion.infra.presentation

import emotion.emotion.infra.port.SmsUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/certification/code")
class SmsController(
    private val smsUseCase: SmsUseCase
) {
    @PostMapping("/send")
    fun sendSms(@Valid @RequestBody smsRequestDto: SmsRequestDto) {
        smsUseCase.sendSms(smsRequestDto)
    }

    @PostMapping("/check")
    fun checkCode(@Valid @RequestBody smsCheckCodeRequest: CheckCodeRequest): Boolean {
        return smsUseCase.checkCode(smsCheckCodeRequest)
    }
}