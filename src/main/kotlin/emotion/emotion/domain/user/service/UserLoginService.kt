package emotion.emotion.domain.user.service

import emotion.emotion.domain.user.presentation.dto.UserLoginRequest
import emotion.emotion.domain.user.entity.UserRepository
import emotion.emotion.global.security.jwt.JwtTokenProvider
import emotion.emotion.global.security.jwt.dto.TokenResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserLoginService(
    private val userPort: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional(readOnly = true)
    fun execute(request: UserLoginRequest): TokenResponse {
        val user = userPort.findByPhoneNumber(request.phoneNumber) ?:
            throw IllegalArgumentException("없는 폰번호 또는 틀림")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비번틀림")
        }

        return jwtTokenProvider.generateToken(user.phoneNumber)
    }
}