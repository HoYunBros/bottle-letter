package io.ggamnyang.bt.service

import io.ggamnyang.bt.domain.entity.Bottle
import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.domain.enum.BottleSource
import io.ggamnyang.bt.repository.BottleRepository
import org.springframework.stereotype.Service

@Service
class BottleServiceImpl(
    private val bottleRepository: BottleRepository
) : BottleService {

    override fun findAll(user: User, bottleSource: BottleSource): List<Bottle> =
        when (bottleSource) {
            BottleSource.CREATED -> bottleRepository.findAllByCreator(user)
            BottleSource.RECEIVED -> bottleRepository.findAllByReceiver(user)
        }

    override fun save(bottle: Bottle) = bottleRepository.save(bottle)
}
