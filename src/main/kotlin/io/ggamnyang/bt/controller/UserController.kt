package io.ggamnyang.bt.controller

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.LoginDto
import io.ggamnyang.bt.service.UserService
import io.ggamnyang.bt.service.userdetail.UserDetailsAdapter
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun signUp(
        @RequestBody loginDto: LoginDto
    ): ResponseEntity<User> {
        return ResponseEntity(userService.save(loginDto), HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginDto: LoginDto
    ): ResponseEntity<String> {
        return ResponseEntity(userService.login(loginDto), HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getMe(
        @AuthenticationPrincipal userAdapter: UserDetailsAdapter
    ): ResponseEntity<User> { // FIXME: have to return UserDto
        return ResponseEntity(userAdapter.user, HttpStatus.OK)
    }
}
