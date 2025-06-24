package emotion.emotion.domain.user.entity

import emotion.emotion.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.Repository
import java.util.*

interface UserRepository : Repository<User, UUID> {
    fun findById(id: UUID): User
    fun save(userEntity: User)
    fun existsByPhoneNumber(phoneNumber: String): Boolean
    fun findByPhoneNumber(phoneNumber: String): User?
}