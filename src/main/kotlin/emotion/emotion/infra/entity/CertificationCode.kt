package emotion.emotion.infra.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
@RedisHash("certification_code")
class CertificationCode(
    @Indexed
    val phoneNumber: String,

    @Id
    var code: String,

    @TimeToLive
    var ttl: Long = 999999999999
) {
    fun update(code: String, ttl: Long) {
        this.code = code
        this.ttl = ttl  // TTL 값을 다시 설정
    }

    fun isExpired(): Boolean {
        return ttl <= 0
    }
}
