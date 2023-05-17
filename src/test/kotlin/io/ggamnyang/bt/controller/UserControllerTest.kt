package io.ggamnyang.bt.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ggamnyang.bt.auth.WithAuthUser
import io.ggamnyang.bt.dto.common.LoginDto
import io.ggamnyang.bt.repository.UserRepository
import io.ggamnyang.bt.service.UserService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userService: UserService

    @BeforeEach
    fun beforeEach() {
        userService.save(LoginDto("test", "test")) // FIXME: 다른 방법 없나?
    }

    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("POST /api/v1/users/login 테스트 - 성공")
    fun `login - success`() {
        val loginDto = LoginDto("test", "test")
        val loginDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users/login") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDtoJson
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("POST /api/v1/users/login 테스트 - 실패")
    fun `login - fail 403 Unauthorized`() {
        val loginDto = LoginDto("fail", "fail")
        val loginDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users/login") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDtoJson
        }
            .andExpect {
                status { isForbidden() }
            }
    }

    @Test
    @DisplayName("POST /api/v1/users 테스트 - 성공")
    fun `signUp - success`() {
        val loginDto = LoginDto("signup", "signup")
        val loginDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDtoJson
        }
            .andExpect {
                status { isCreated() }
            }
    }

    @Test
    @DisplayName("POST /api/v1/users 테스트 - 실패 중복 username")
    fun `signUp - fail 중복 username`() {
        val loginDto = LoginDto("test", "test")
        val loginDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDtoJson
        }
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    @WithAuthUser("test", "user")
    @DisplayName("GET /api/v1/users/me 테스트 - 성공")
    fun `me - success`() {
        mockMvc.get("/api/v1/users/me") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("GET /api/v1/users/me 테스트 - 403 Forbidden")
    fun `me - fail no auth user == 403 Forbidden`() {
        mockMvc.get("/api/v1/users/me") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isForbidden() }
            }
    }
}
