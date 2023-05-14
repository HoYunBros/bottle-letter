package io.ggamnyang.bt.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ggamnyang.bt.dto.common.LoginDto
import io.ggamnyang.bt.repository.UserRepository
import io.ggamnyang.bt.service.UserService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
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

    @Test
    @DisplayName("/api/v1/login 테스트")
    fun `login - success`() {
        val loginDto = LoginDto("test", "test")
        val loginDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users/login") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDtoJson
        }
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }
    }
}
