package emotion.emotion.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate


@Configuration
class GeminiRestTemplateConfig {
    @Bean
    fun geminiRestTemplate(): RestTemplate = RestTemplate()

}
