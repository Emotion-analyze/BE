package emotion.emotion.domain.diary

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
data class Diary (
    @Id
    val id: UUID ?= UUID.randomUUID(),
    val title: String,
    @Column(columnDefinition = "TEXT")
    val content: String,
    @Enumerated(value = EnumType.STRING)
    val emotion: Emotion,
    val advice: String,
    val createAt: LocalDate ?= LocalDate.now(),
    val userId: String
)

enum class Emotion(val description: String) {
    FEAR("두려움"),
    SURPRISE("놀람"),
    ANGER("화남"),
    SADNESS("슬픔"),
    NEUTRALITY("중립"),
    HAPPINESS("행복"),
    DISGUST("싫음");

    companion object {
        fun from(value: String): Emotion {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                values().firstOrNull { it.description == value }
                    ?: throw IllegalArgumentException("Invalid emotion: $value")
            }
        }
    }
}