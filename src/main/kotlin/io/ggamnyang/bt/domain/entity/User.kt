package io.ggamnyang.bt.domain.entity

import io.ggamnyang.bt.dto.common.LoginDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "USERS")
class User(
    @Column(nullable = false, unique = true)
    var username: String,

    val password: String

) : Base() {

    companion object {
        fun fromUserDto(loginDto: LoginDto): User = User(loginDto.username, loginDto.password)
    }
}
