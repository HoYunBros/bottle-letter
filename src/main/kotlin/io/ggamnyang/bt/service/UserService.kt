package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.dto.common.UserDto

interface UserService {
    fun save(userDto: UserDto): User
}
