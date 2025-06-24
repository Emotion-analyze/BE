package emotion.emotion.domain.user.presentation.dto

data class UserLoginRequest (
    val phoneNumber: String,
    val password: String
)