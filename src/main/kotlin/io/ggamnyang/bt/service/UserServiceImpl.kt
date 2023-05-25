package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.LoginDto
import io.ggamnyang.bt.repository.UserRepository
import io.ggamnyang.bt.utils.JwtUtils
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) : UserService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    // FIXME: userDto가 toEntity 로직을 가지고 있는게 맞을까?
    override fun save(loginDto: LoginDto): User {
        loginDto.password = passwordEncoder.encode(loginDto.password)

        return userRepository.save(User.fromLoginDto(loginDto))
    }

    override fun findById(id: Long): User? = userRepository.findByIdOrNull(id)

    override fun findByUsername(username: String): User? = userRepository.findByUsername(username)

    override fun login(loginDto: LoginDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password, null)
        )

        return jwtUtils.createToken(loginDto.username)
    }
}
