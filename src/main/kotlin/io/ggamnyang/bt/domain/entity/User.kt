package io.ggamnyang.bt.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    var nickName: String,

    var password: String
) : Base()
