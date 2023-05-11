package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.UserDto
import io.ggamnyang.bt.repository.UserRepository
import io.ggamnyang.bt.utils.JwtUtils
import org.slf4j.LoggerFactory
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
    override fun save(userDto: UserDto): User {
        logger.info(userRepository.findAll().toString())

        return userRepository.save(userDto.toEntity())
    }

    override fun login(userDto: UserDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userDto.username, userDto.password, null)
        )

        val token = jwtUtils.createToken(userDto.username)

        return token
    }
}
