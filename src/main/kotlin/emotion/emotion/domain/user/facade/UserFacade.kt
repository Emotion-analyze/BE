package emotion.emotion.domain.user.facade

import emotion.emotion.domain.user.entity.User
import emotion.emotion.domain.user.entity.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userPort: UserRepository
) {

     fun currentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
         println(accountId)
        return getUserByPhoneNumber(accountId)
    }

    private fun getUserByPhoneNumber(phoneNumber: String): User {
        return userPort.findByPhoneNumber(phoneNumber) ?: throw IllegalArgumentException("존재하지않는폰")
    }
}