package io.ggamnyang.bt.dto.common

import io.ggamnyang.bt.domain.entity.Bottle

data class BottleDto(
    val letter: String
) {
    companion object {
        fun fromBottle(bottle: Bottle) = BottleDto(bottle.letter)
    }
}
