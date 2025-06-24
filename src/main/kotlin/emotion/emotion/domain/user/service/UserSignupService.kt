package emotion.emotion.domain.user.service

import emotion.emotion.domain.user.presentation.dto.UserSignUpRequest
import emotion.emotion.domain.user.entity.User
import emotion.emotion.domain.user.entity.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSignupService(
    private val userPort: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Transactional
     fun execute(request: UserSignUpRequest) {

        if (userPort.existsByPhoneNumber(request.phoneNumber)) {
            throw IllegalArgumentException("없음")
        }

        val user = request.run {
            User(
                phoneNumber = phoneNumber,
                password = passwordEncoder.encode(request.password),
                name = name
            )
        }

        userPort.save(user)
    }
}