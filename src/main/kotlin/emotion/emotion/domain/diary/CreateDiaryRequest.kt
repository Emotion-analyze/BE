package emotion.emotion.domain.diary

import java.time.LocalDate

data class CreateDiaryRequest (
    val title: String,
    val content: String,
)