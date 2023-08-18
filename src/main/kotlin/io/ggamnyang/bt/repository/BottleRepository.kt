package io.ggamnyang.bt.repository

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BottleRepository : JpaRepository<Bottle, Long> {
    fun findAllByCreator(creator: User): List<Bottle>
    fun findAllByReceiver(receiver: User): List<Bottle>

    fun countByRemainingGreaterThan(remaining: Int): Long
    fun findByRemainingGreaterThan(remaining: Int, pageable: Pageable): Page<Bottle>
}
