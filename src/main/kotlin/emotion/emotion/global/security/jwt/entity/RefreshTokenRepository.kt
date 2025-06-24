package emotion.emotion.global.security.jwt.entity

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.repository.CrudRepository

@EnableRedisRepositories
interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByToken(token: String): RefreshToken?
}