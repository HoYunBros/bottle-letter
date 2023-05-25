package io.ggamnyang.bt.repository

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BottleRepository : JpaRepository<Bottle, Long> {
    fun findAllByCreator(creator: User): List<Bottle>
    fun findAllByReceiver(receiver: User): List<Bottle>
}
