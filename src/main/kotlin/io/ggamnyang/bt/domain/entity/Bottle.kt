package io.ggamnyang.bt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "BOTTLES")
class Bottle(

    @ManyToOne
    @JoinColumn(name = "creator_id")
    val creator: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    val receiver: User?,

    @Column(columnDefinition = "TEXT")
    val letter: String

) : Base()
