package emotion.emotion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class EmotionApplication

fun main(args: Array<String>) {
    runApplication<EmotionApplication>(*args)
}
