package io.ggamnyang.bt.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.LoginDto
import io.ggamnyang.bt.repository.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun beforeEach() {
        userRepository.save(User("test", "test"))
    }

    @Test
    @DisplayName("/api/v1/login 테스트")
    fun `login - success`() {
        val loginDto = LoginDto("test", "test")
        val userDtoJson = jacksonObjectMapper().writeValueAsString(loginDto)

        mockMvc.post("/api/v1/users/login") {
            contentType = MediaType.APPLICATION_JSON
            content = loginDto
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }
}
