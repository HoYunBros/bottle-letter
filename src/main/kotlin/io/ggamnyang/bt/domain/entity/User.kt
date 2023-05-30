package io.ggamnyang.bt.domain.entity

import io.ggamnyang.bt.dto.common.LoginDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "USERS")
class User(
    @Column(nullable = false, unique = true)
    var username: String = "",

    val password: String,

    @OneToMany(mappedBy = "creator")
    val createdBottles: List<Bottle> = arrayListOf(),

    @OneToMany(mappedBy = "receiver")
    val receivedBottles: List<Bottle> = arrayListOf()

) : Base() {

    companion object {
        fun fromLoginDto(loginDto: LoginDto): User = User(loginDto.username, loginDto.password)
    }
}
