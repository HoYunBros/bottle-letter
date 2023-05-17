package io.ggamnyang.bt.utils

import io.ggamnyang.bt.service.userdetail.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtils(
    @Value("\${jwt.secret}") val secret: String,
    private val userDetailsService: UserDetailsServiceImpl // UserDetailsServiceImpl이 맞나?
) {

    val EXP_TIME: Long = 1000L * 60 * 3
    val JWT_SECRET: String = "secret"
    val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256
    val KEY: Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun createToken(username: String): String {
        val claims: Claims = Jwts.claims()
        claims["username"] = username

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + EXP_TIME))
            .signWith(KEY, SIGNATURE_ALG)
            .compact()
    }

    fun validation(token: String): Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseUsername(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["username"] as String
    }

    fun getAuthentication(username: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parse(token)
            .body as Claims
    }
}
