package emotion.emotion.global.exception

abstract class EmoException(
    open val status: Int,
    override val message: String
) : RuntimeException()