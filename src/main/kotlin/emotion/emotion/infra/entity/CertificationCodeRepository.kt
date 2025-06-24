package emotion.emotion.infra.entity

import emotion.emotion.infra.entity.CertificationCode
import org.springframework.data.repository.CrudRepository

interface CertificationCodeRepository : CrudRepository<CertificationCode, String> {
    fun findByCode(code: String): CertificationCode
}