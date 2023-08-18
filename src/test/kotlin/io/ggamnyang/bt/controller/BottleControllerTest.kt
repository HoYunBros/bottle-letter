package io.ggamnyang.bt.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.ggamnyang.bt.auth.WithAuthUser
import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.dto.request.PostBottleRequest
import io.ggamnyang.bt.service.BottleService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.filter.GenericFilterBean

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(
    controllers = [BottleController::class],
    excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [GenericFilterBean::class])]
)
internal class BottleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var bottleService: BottleService

    private lateinit var bottle: Bottle

    @BeforeEach
    fun beforeEach() {
        val creator = User("creator", "password")
        val receiver = User("receiver", "password")

        bottle = Bottle(creator, receiver, "test letter")
    }

    @Test
    @DisplayName("GET /api/v1/bottles/me 테스트")
    @WithAuthUser("creator")
    fun `내가 만든 편지 요청에 정상적으로 응답한다`() {
        every { bottleService.findAll(any(), eq(BottleSource.CREATED)) } returns arrayListOf(bottle)

        val result = mockMvc
            .perform(
                get("/api/v1/bottles/me")
                    .param("bottleSource", BottleSource.CREATED.toString())
                    .accept(MediaType.APPLICATION_JSON)
            )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "bottle-me-get",
                    queryParameters(
                        parameterWithName("bottleSource").description("Creator / Receiver")
                    ),
                    responseFields(
                        fieldWithPath("bottles").description("List of BottleDto"),
                        fieldWithPath("bottles[].letter").description("Letter context")
                    )
                )
            )
    }

    @Test
    @DisplayName("GET /api/v1/bottles 테스트")
    @WithAuthUser("creator")
    fun `편지를 줍는다`() {
        // Kotlin DSL 느낌으로
        every { bottleService.getBottle(any()) } returns bottle

        val result = mockMvc
            .perform(
                get("/api/v1/bottles")
                    .accept(MediaType.APPLICATION_JSON)
            )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "bottle-get",
                    responseFields(
                        fieldWithPath("bottle").description("BottleDto"),
                        fieldWithPath("bottle.letter").description("Letter context of bottle")
                    )
                )
            )
    }

    @Test
    @DisplayName("POST /api/v1/bottles 테스트 - 성공")
    @WithAuthUser("creator")
    fun `post bottle - success`() {
        every { bottleService.createBottle(any(), any()) } returns bottle

        val request = PostBottleRequest(bottle.letter)
        val requestJson = jacksonObjectMapper().writeValueAsString(request)

        val result = mockMvc
            .perform(
                post("/api/v1/bottles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(requestJson)
                    .with(csrf())
            )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "bottle-post",
                    requestFields(
                        fieldWithPath("letter").description("Letter context")
                    ),
                    responseFields(
                        fieldWithPath("bottle").description("BottleDto"),
                        fieldWithPath("bottle.letter").description("Letter context of bottle")
                    )
                )
            )
    }
}
