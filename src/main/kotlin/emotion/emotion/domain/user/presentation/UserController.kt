package emotion.emotion.domain.user.presentation

import emotion.emotion.domain.diary.QueryDiaryService
import emotion.emotion.domain.user.presentation.dto.UserLoginRequest
import emotion.emotion.domain.user.presentation.dto.UserSignUpRequest
import emotion.emotion.domain.user.service.UserLoginService
import emotion.emotion.domain.user.service.UserSignupService
import emotion.emotion.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "user API")
@RestController
@RequestMapping("/auth")
class UserController(
    private val userSignupUseCase: UserSignupService,
    private val userLoginService: UserLoginService,
    private val queryDiaryService: QueryDiaryService
) {
    @Operation(summary = "유저 회원가입 API")
    @PostMapping("/signup")
    fun signup(@RequestBody request: UserSignUpRequest) =
        userSignupUseCase.execute(request)

    @Operation(summary = "유저 로그인 API")
    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): TokenResponse =
        userLoginService.execute(request)
}