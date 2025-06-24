package emotion.emotion.infra.presentation

import jakarta.validation.constraints.NotEmpty

data class SmsRequestDto (
    @field:NotEmpty(message = "휴대폰 번호를 입력해주세요")
    val phoneNumber: String
)