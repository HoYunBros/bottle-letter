package io.ggamnyang.bt.repository

import io.ggamnyang.bt.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    @Query(value = "SELECT * FROM users order by RAND() limit 2", nativeQuery = true)
    fun findRandom(): List<User>
}
