package emotion.emotion.infra

import emotion.emotion.infra.entity.CertificationCode
import emotion.emotion.infra.entity.CertificationCodeRepository
import emotion.emotion.infra.port.SmsUseCase
import emotion.emotion.infra.presentation.CheckCodeRequest
import emotion.emotion.infra.presentation.SmsRequestDto
import emotion.emotion.infra.util.SmsCertificationUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SmsService(
    private val smsCertificationUtil: SmsCertificationUtil,
    private val certificationCodeRepository: CertificationCodeRepository
): SmsUseCase {
    @Transactional
    override fun sendSms(request: SmsRequestDto) {
        val code = (100000..999999).random().toString()

        val certificationCode = certificationCodeRepository.findById(request.phoneNumber)
           .orElseGet { CertificationCode(phoneNumber = request.phoneNumber, code = code) }

        certificationCode.update(code, 60000000000000000)
        certificationCodeRepository.save(certificationCode)

        smsCertificationUtil.sendSMS(request.phoneNumber, code)
    }

    override fun checkCode(request: CheckCodeRequest): Boolean {
        val certificationCode = certificationCodeRepository.findById(request.code)
            .orElseThrow { SmsException.CodeNotFoundException() }
        if (certificationCode.isExpired()) {
            throw SmsException.ExpiredCodeException()
        }

        if (certificationCode.code != request.code) {
            throw SmsException.CodeMissMatchException()
        }

        return true
    }

}
