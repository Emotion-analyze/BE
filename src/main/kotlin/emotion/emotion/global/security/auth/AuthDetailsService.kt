package emotion.emotion.global.security.auth

import emotion.emotion.domain.user.entity.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(phoneNumber: String): UserDetails {
        val user = userRepository.findByPhoneNumber(phoneNumber)
        return AuthDetails(user!!.phoneNumber)
    }

}