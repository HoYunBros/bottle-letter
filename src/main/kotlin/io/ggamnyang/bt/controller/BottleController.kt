package io.ggamnyang.bt.controller

import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.dto.common.BottleDto
import io.ggamnyang.bt.dto.request.PostBottleRequest
import io.ggamnyang.bt.dto.response.GetBottlesResponse
import io.ggamnyang.bt.dto.response.PostBottleResponse
import io.ggamnyang.bt.service.BottleService
import io.ggamnyang.bt.service.userdetail.UserDetailsAdapter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/bottles")
class BottleController(
    private val bottleService: BottleService
) {

    @GetMapping
    fun getBottles(
        @RequestParam bottleSource: BottleSource = BottleSource.CREATED,
        @AuthenticationPrincipal userDetailsAdapter: UserDetailsAdapter
    ): ResponseEntity<GetBottlesResponse> {
        val user = userDetailsAdapter.user
        val bottles = bottleService.findAll(user, bottleSource)

        return ResponseEntity(
            GetBottlesResponse(bottles.map(BottleDto::fromBottle)),
            HttpStatus.OK
        )
    }

    @PostMapping
    fun addBottle(
        @RequestBody postBottleRequest: PostBottleRequest,
        @AuthenticationPrincipal userDetailsAdapter: UserDetailsAdapter
    ): ResponseEntity<PostBottleResponse> {
        val creator = userDetailsAdapter.user
        val bottle = bottleService.createBottle(creator = creator, dto = postBottleRequest)

        return ResponseEntity(
            PostBottleResponse(BottleDto.fromBottle(bottle)),
            HttpStatus.OK
        )
    }
}
