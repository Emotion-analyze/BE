package emotion.emotion.infra.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cool")
data class CoolSmsProperties (
    val apiKey: String,
    val secretKey: String,
    val phone: String = "01024672532"
)