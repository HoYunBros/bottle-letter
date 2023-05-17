package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.LoginDto

interface UserService {
    fun save(loginDto: LoginDto): User
    fun login(loginDto: LoginDto): String
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
}
