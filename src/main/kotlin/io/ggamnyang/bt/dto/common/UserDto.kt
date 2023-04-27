package io.ggamnyang.bt.dto.common

import io.ggamnyang.bt.domain.entity.User

data class UserDto(
    var nickName: String,
    var password: String
) {
    fun toEntity() = User(
        nickName = this.nickName,
        password = this.password
    )
}
