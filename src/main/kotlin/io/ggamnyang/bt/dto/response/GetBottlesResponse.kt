package io.ggamnyang.bt.dto.response

import io.ggamnyang.bt.dto.common.BottleDto

data class GetBottlesResponse(
    val bottles: List<BottleDto>
)
