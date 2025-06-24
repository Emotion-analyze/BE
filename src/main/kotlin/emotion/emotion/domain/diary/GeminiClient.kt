package emotion.emotion.domain.diary

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GeminiClient(
    private val restTemplate: RestTemplate,
    @Value("\${gemini.api.key}") private val geminiApiKey: String,
    private val jacksonObjectMapper: ObjectMapper
) {

    fun analyzeEmotion(content: String): ChatResponse {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            // 만약 Authorization 헤더가 필요하면 아래 주석 해제
            // set("Authorization", "Bearer $geminiApiKey")
        }

        val prompt = buildPrompt(content)

        val requestBody = mapOf(
            "contents" to listOf(
                mapOf("parts" to listOf(mapOf("text" to prompt)))
            )
        )

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.postForObject(
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${geminiApiKey}",
            entity,
            GeminiApiResponse::class.java
        ) ?: throw IllegalStateException("Gemini API 응답이 null입니다.")

        val generatedText = response.candidates?.firstOrNull()
            ?.content?.parts?.firstOrNull()?.text
            ?: throw IllegalStateException("감정 분석 결과를 파싱할 수 없습니다.")

        val json = try {
            jacksonObjectMapper.readTree(generatedText)
        } catch (e: Exception) {
            throw IllegalStateException("JSON 파싱 실패: ${e.message}")
        }

        return ChatResponse(
            emotion = json["emotion"]?.asText() ?: throw IllegalStateException("emotion 필드가 없습니다."),
            feedback = json["feedback"]?.asText() ?: throw IllegalStateException("feedback 필드가 없습니다.")
        )
    }

    private fun buildPrompt(diary: String): String = """
        다음 일기를 읽고 반드시 아래 예시처럼 JSON만(코드블록 없이) 출력해줘.
        예시1: {"emotion": "기쁨", "feedback": "오늘 하루가 즐거우셨군요! 😄"}
        예시2: {"emotion": "우울", "feedback": "힘든 하루였군요. 위로가 필요할 때는 음악이 도움이 될 수 있어요. 💙"}
        예시3: {"emotion": "설렘", "feedback": "설레는 하루였네요! ✨"}

        일기:
        $diary
    """.trimIndent()
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeminiApiResponse(
    val candidates: List<Candidate>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Candidate(
    val content: Content
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Content(
    val parts: List<Part>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Part(
    val text: String
)

