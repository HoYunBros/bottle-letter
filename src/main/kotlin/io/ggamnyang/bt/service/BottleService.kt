package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.dto.request.PostBottleRequest
import io.ggamnyang.bt.repository.BottleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BottleService(
    private val bottleRepository: BottleRepository
) {
    fun findAll(user: User, bottleSource: BottleSource): List<Bottle> =
        when (bottleSource) {
            BottleSource.CREATED -> bottleRepository.findAllByCreator(user)
            BottleSource.RECEIVED -> bottleRepository.findAllByReceiver(user)
        }

    fun createBottle(creator: User, dto: PostBottleRequest): Bottle {
        return Bottle(creator, letter = dto.letter)
    }

    fun getBottle(receiver: User): Bottle? {
        val eligibleCount = bottleRepository.countByRemainingGreaterThan(REMAINING_LIMIT)
        if (eligibleCount == 0L) return null

        val randomNumber = (0 until eligibleCount).random()
        if (randomNumber > Int.MAX_VALUE) throw RuntimeException("Eligible Count is too large!")

        val pageable: Pageable = PageRequest.of(randomNumber.toInt(), 1)
        val page = bottleRepository.findByRemainingGreaterThan(REMAINING_LIMIT, pageable)

        return page.content.firstOrNull()
    }

    companion object {
        const val REMAINING_LIMIT = 0
    }
}
