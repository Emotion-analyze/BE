package emotion.emotion.domain.diary

import emotion.emotion.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Transactional(readOnly = true)
@Service
class QueryDiaryService(
    private val diaryRepository: DiaryRepository,
    private val userFacade: UserFacade
) {

    fun queryDetailDiary(id: UUID): QueryDiaryDetailResponse {
        val diary = diaryRepository.findById(id).orElseThrow {
            IllegalArgumentException("다이어리를 찾을 수 없습니다.")
        }

        val userName = userFacade.currentUser().name
        val emotionSummary = "$userName 님의 감정 상태는 ${emotionTranslation(diary.emotion)}으로 보입니다."

        return QueryDiaryDetailResponse(
            title = diary.title,
            content = diary.content,
            emotion = diary.emotion,
            advice = diary.advice,
            createAt = diary.createAt!!,
            emotionSummary = emotionSummary
        )
    }

    fun queryDiary(year: Int, month: Int): QueryDiaryResponse {
        val (startDate, endDate) = getDateRange(year, month)
        val userPhone = getCurrentUserPhoneNumber()
        val diaries = diaryRepository.findByCreateAtBetweenAndUserId(startDate, endDate, userPhone)
        val userName = userFacade.currentUser().name


        val diaryDto = diaries.map {
            QueryDiaryDto(
                id = it.id!!,
                title = it.title,
                content = it.content,
                emotion = emotionTranslation(it.emotion),
                createAt = it.createAt!!,
                advice = it.advice,
                emotionSummary = "$userName 님의 감정 상태는 ${emotionTranslation(it.emotion)}으로 보입니다."
            )
        }

        return QueryDiaryResponse(diaryDto)
    }

    fun emotionGraph(year: Int?, month: Int?): QueryEmotionCount {
        val userPhone = getCurrentUserPhoneNumber()
        val diaries = if (year != null && month != null) {
            val (startDate, endDate) = getDateRange(year, month)
            diaryRepository.findByCreateAtBetweenAndUserId(startDate, endDate, userPhone)
        } else {
            diaryRepository.findAllByUserId(userPhone)
        }

        return countEmotions(diaries)
    }

    private fun getCurrentUserPhoneNumber(): String {
        return userFacade.currentUser().phoneNumber
    }

    private fun countEmotions(diaries: List<Diary>): QueryEmotionCount {
        return QueryEmotionCount(
            fear = diaries.count { it.emotion == Emotion.FEAR },
            surprise = diaries.count { it.emotion == Emotion.SURPRISE },
            anger = diaries.count { it.emotion == Emotion.ANGER },
            sadness = diaries.count { it.emotion == Emotion.SADNESS },
            neutrality = diaries.count { it.emotion == Emotion.NEUTRALITY },
            happiness = diaries.count { it.emotion == Emotion.HAPPINESS },
            disgust = diaries.count { it.emotion == Emotion.DISGUST }
        )
    }

    private fun getDateRange(year: Int, month: Int): Pair<LocalDate, LocalDate> {
        val start = LocalDate.of(year, month, 1)
        val end = start.withDayOfMonth(start.lengthOfMonth())
        return start to end
    }

    private fun emotionTranslation(emotion: Emotion): String {
        return when (emotion) {
            Emotion.FEAR -> "공포"
            Emotion.SURPRISE -> "놀람"
            Emotion.ANGER -> "분노"
            Emotion.SADNESS -> "슬픔"
            Emotion.NEUTRALITY -> "중립"
            Emotion.HAPPINESS -> "행복"
            Emotion.DISGUST -> "혐오"
        }
    }
}