package io.ggamnyang.bt.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ggamnyang.bt.auth.WithAuthUser
import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.dto.request.PostBottleRequest
import io.ggamnyang.bt.service.BottleService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@WithMockUser(username = "test")
internal class BottleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var bottleService: BottleService

    private lateinit var bottle: Bottle

    @BeforeEach
    fun beforeEach() {
        val creator = User("creator", "password")
        val receiver = User("receiver", "password")

        bottle = Bottle(creator, receiver, "test letter")
    }

//    override suspend fun beforeSpec(spec: Spec) {
//        val creator = User("creator", "password")
//        val receiver = User("receiver", "password")
//
//        bottle = Bottle(creator, receiver, "test letter")
//    }

//    init {
//        Given("GET : /api/v1/bottles") {
//            whenever(bottleService.findAll(any(), eq(BottleSource.CREATED))).thenReturn(arrayListOf(bottle))
//
//            When("정상적 요청") {
//                Then("Status 200") {
//                    mockMvc.get("/api/v1/bottles") {
//                        contentType = MediaType.APPLICATION_JSON
//                        param("bottleSource", "CREATED")
//                    }
//                        .andDo { print() }
//                        .andExpect {
//                            status { isOk() }
//                            // FIXME: response 검증
//                        }
//                }
//            }
//        }
//    }

    @Test
    @DisplayName("GET /api/v1/bottles 테스트 - 성공")
    @WithAuthUser("creator")
    fun `get bottles + bottleSource == CREATED - success`() {
        // FIXME: 더 나은 방식이 있을 것 같다..
        whenever(bottleService.findAll(any(), eq(BottleSource.CREATED))).thenReturn(arrayListOf(bottle))

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders.get("/api/v1/bottles")
                    .param("bottleSource", BottleSource.CREATED.toString())
                    .accept(MediaType.APPLICATION_JSON)
            )

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "get-bottles",
                    queryParameters(
                        parameterWithName("bottleSource").description("Creator / Receiver")
                    ),
                    responseFields(
                        fieldWithPath("bottles").description("List of BottleDto"),
                        fieldWithPath("bottles[].letter").description("Letter context")
                    )
                )
            )

//        mockMvc.get("/api/v1/bottles") {
//            contentType = MediaType.APPLICATION_JSON
//            param("bottleSource", "CREATED")
//        }
//            .andExpect {
//                status { isOk() }
//                // FIXME: response 검증
//            }
//            .andDo {  }
    }

    @Test
    @DisplayName("POST /api/v1/bottles 테스트 - 성공")
    @WithAuthUser("creator")
    fun `post bottle - success`() {
        whenever(bottleService.createBottle(any(), any())).thenReturn(bottle)

        val request = PostBottleRequest(bottle.letter)
        val requestJson = jacksonObjectMapper().writeValueAsString(request)

        mockMvc.post("/api/v1/bottles") {
            contentType = MediaType.APPLICATION_JSON
            content = requestJson
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    // TODO: POST /api/v1/bottles 실패 케이스? HOW?
}
