package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.dto.request.PostBottleRequest

interface BottleService {
    fun findAll(user: User, bottleSource: BottleSource): List<Bottle>
    fun save(bottle: Bottle): Bottle
    fun createBottle(creator: User, dto: PostBottleRequest): Bottle
}
