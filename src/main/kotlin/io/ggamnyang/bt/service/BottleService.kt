package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource

interface BottleService {
    fun findAll(user: User, bottleSource: BottleSource): List<Bottle>
}
