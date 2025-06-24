package emotion.emotion.domain.diary

data class ChatRequest(
    val contents: MutableList<Content> = mutableListOf(),
    val generationConfig: GenerationConfig = GenerationConfig()
) {
    constructor(prompt: String) : this() {
        val parts = Parts(text = prompt)
        val content = Content(parts = parts)
        contents.add(content)
    }

    data class Content(
        var parts: Parts
    )

    data class Parts(
        var text: String
    )

    data class GenerationConfig(
        var candidate_count: Int = 1,
        var max_output_tokens: Int = 1000,
        var temperature: Double = 0.7
    )
}
