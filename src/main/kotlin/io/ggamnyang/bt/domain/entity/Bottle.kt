package io.ggamnyang.bt.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "BOTTLES")
class Bottle(

    @ManyToOne
    @JoinColumn(name = "creator_id")
    val creator: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    val receiver: User? = null,

    @Column(columnDefinition = "TEXT")
    val letter: String,

    @Column
    var remaining: Int = 50
) : BaseEntity()
