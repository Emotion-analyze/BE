package emotion.emotion.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class User(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val phoneNumber: String,
    val password: String,
    val name: String
)