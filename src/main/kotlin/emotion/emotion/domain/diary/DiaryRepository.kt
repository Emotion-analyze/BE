package emotion.emotion.domain.diary

import emotion.emotion.domain.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface DiaryRepository: JpaRepository<Diary, UUID> {
    fun findByCreateAtBetweenAndUserId(startDate: LocalDate, endDate: LocalDate, userId: String): List<Diary>
    fun findAllByUserId(userId: String): List<Diary>
}