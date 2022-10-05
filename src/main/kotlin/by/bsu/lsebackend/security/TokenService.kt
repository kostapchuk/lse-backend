package by.bsu.lsebackend.security

import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.security.Key
import java.time.Duration
import java.util.Date
import javax.crypto.spec.SecretKeySpec

private const val ROLE = "role"

@Component
class TokenService(private val jwtProperties: JwtProperties) {

    private val signingKey: Key = SecretKeySpec(jwtProperties.secret.toByteArray(), SignatureAlgorithm.HS256.jcaName)

    fun retrieveUsername(token: String): String = retrieveClaims(token).subject!!

    fun isValid(token: String): Mono<Boolean> = Mono.just(retrieveClaims(token).expiration.after(Date()))

    fun retrieveClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body!!

    fun <T : BaseUser> generateAccessToken(user: T): String =
        generateToken(user, jwtProperties.accessTokenExpiration)

    fun <T : BaseUser> generateRefreshToken(user: T): String = generateToken(user, jwtProperties.refreshTokenExpiration)

    private fun <T : BaseUser> generateToken(user: T, expiration: Duration): String {
        val creationDate = Date()
        val expirationDate = Date(creationDate.time + expiration.toMillis())
        return Jwts.builder()
            .setClaims(mapOf(ROLE to listOf(user.role)))
            .setSubject(user.email)
            .setIssuedAt(creationDate)
            .setExpiration(expirationDate)
            .signWith(signingKey)
            .compact()
    }
}
