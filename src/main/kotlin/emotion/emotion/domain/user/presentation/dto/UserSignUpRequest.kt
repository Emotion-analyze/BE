package emotion.emotion.domain.user.presentation.dto

data class UserSignUpRequest (
    val phoneNumber: String,
    val name: String,
    val password: String
)