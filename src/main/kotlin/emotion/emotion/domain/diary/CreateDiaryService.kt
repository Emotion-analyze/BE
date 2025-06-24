package emotion.emotion.domain.diary

import emotion.emotion.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateDiaryService(
    private val diaryRepository: DiaryRepository,
    private val geminiClient: GeminiClient,
    private val userFacade: UserFacade
) {

    @Transactional
    fun execute(request: CreateDiaryRequest): DiaryResponse {
        val response = geminiClient.analyzeEmotion(request.content)

        val diary = Diary(
            title = request.title,
            content = request.content,
            emotion = change(response.emotion),
            advice = response.feedback,
            userId = userFacade.currentUser().phoneNumber
        )

        diaryRepository.save(diary)

        return DiaryResponse(
            title = request.title,
            content = request.content
        )
    }

    private fun change(emotion: String): Emotion {
        return Emotion.from(emotion)
    }
}
data class DiaryResponse(
    val title: String,
    val content: String
)