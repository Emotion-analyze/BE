package emotion.emotion.domain.diary

import java.time.LocalDate
import java.util.UUID

data class QueryDiaryDetailResponse(
    val title: String,
    val content: String,
    val emotion: Emotion,
    val advice: String,
    val createAt: LocalDate,
    val emotionSummary: String
)

data class QueryDiaryResponse(
    val diaries: List<QueryDiaryDto>
)

data class QueryDiaryDto(
    val id: UUID,
    val title: String,
    val content: String,
    val emotion: String,
    val createAt: LocalDate,
    val advice: String,
    val emotionSummary: String
)

data class QueryDiaryEmotionListResponse(
    val emotionList: List<QueryDiaryEmotionResponse>
)

data class QueryDiaryEmotionResponse(
    val creatAt: LocalDate,
    val emotion: String
)

data class QueryEmotionCount(
    val fear: Int,
    val surprise: Int,
    val anger: Int,
    val sadness: Int,
    val neutrality: Int,
    val happiness: Int,
    val disgust: Int
)

