package io.ggamnyang.bt.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "USERS")
class User(
    @Column(nullable = false, unique = true)
    var username: String,

    val password: String

) : Base()
