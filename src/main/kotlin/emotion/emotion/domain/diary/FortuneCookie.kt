package emotion.emotion.domain.diary

import emotion.emotion.domain.user.facade.UserFacade
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class FortuneCookie(
    private val diaryRepository: DiaryRepository,
    private val restTemplate: RestTemplate,
    private val userFacade: UserFacade,
    @Value("\${external.api.fortune_url}") private val apiUrl: String
) {
    @Transactional(readOnly = true)
    fun execute(): String {
        val user = userFacade.currentUser()
        val diaryList = diaryRepository.findAllByUserId(user.phoneNumber)
        if (diaryList.count() < 5) {
            throw IllegalArgumentException("일기 개수가 5개 미만입니다.")
        }
        println(diaryList)
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val requestBody = FortuneRequestBody(emotion = diaryList.map { it.emotion.name })
        val httpEntity = HttpEntity(requestBody, headers)

        val response = restTemplate.postForObject(
            apiUrl,
            httpEntity,
            String::class.java
        ) ?: throw IllegalStateException("외부 감정 분석 응답이 null입니다.")

        return response
    }
}

data class FortuneRequestBody(
    val emotion: List<String>
)