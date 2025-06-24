package emotion.emotion.domain.diary

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "diary API")
@RestController
@RequestMapping("/diary")
class CreateDiaryController(
    private val createDiaryService: CreateDiaryService,
    private val queryDiaryService: QueryDiaryService,
    private val fortuneCookie: FortuneCookie
) {
    @Operation(summary = "일기 생성 api")
    @PostMapping
    fun createDiary(@RequestBody createDiaryRequest: CreateDiaryRequest): DiaryResponse {
        return createDiaryService.execute(createDiaryRequest)
    }

    @Operation(summary = "일기 상세조회")
    @GetMapping("/detail/{id}")
    fun queryDetailDiary(@PathVariable id: UUID) =  queryDiaryService.queryDetailDiary(id)

    @Operation(summary = "n년 n월 다이어리 조회")
    @GetMapping("/month")
    fun queryDiary(@RequestParam(required = false) year: Int, @RequestParam(required = false) month: Int): QueryDiaryResponse =
        queryDiaryService.queryDiary(year, month)

    @Operation(summary = "n년 n월 다이어리의 감정상태 갯수 조회")
    @GetMapping("/sgraph")
    fun emotionGraph(@RequestParam(required = false) year: Int?, @RequestParam(required =  false) month: Int?) =
        queryDiaryService.emotionGraph(year, month)


    @Operation(summary = "포춘쿠키")
    @GetMapping("/fortune")
    fun fortune(): String {
        return fortuneCookie.execute()
    }

}