package io.ggamnyang.bt.dto.common

import io.ggamnyang.bt.domain.entity.User

data class UserDto(
    var username: String,
    var password: String
) {
    fun toEntity() = User(
        username = this.username,
        password = this.password
    )
}
