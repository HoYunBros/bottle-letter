package io.ggamnyang.bt.repository

import io.ggamnyang.bt.domain.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `WhenFindRandom_thenReturnUsers`() {
        val user1 = User(username = "user1", password = "")
        val user2 = User(username = "user2", password = "")
        val user3 = User(username = "user3", password = "")

        entityManager.persist(user1)
        entityManager.persist(user2)
        entityManager.persist(user3)
        entityManager.flush()

        val randomUsersFound = userRepository.findRandom()
        assertThat(randomUsersFound.size == 2)
    }
}
