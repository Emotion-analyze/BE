package emotion.emotion.global.security.jwt

import emotion.emotion.global.security.auth.AuthDetailsService
import emotion.emotion.global.security.jwt.dto.TokenResponse
import emotion.emotion.global.security.jwt.entity.RefreshToken
import emotion.emotion.global.security.jwt.entity.RefreshTokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authDetailsService: AuthDetailsService,
) {

    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
        private const val BEARER_PREFIX = "Bearer "
        private const val CLAIM_TYPE = "type"
    }

    fun generateToken(number: String): TokenResponse {
        val accessToken = createAccessToken(number)
        val refreshToken = createRefreshToken(number)
        return TokenResponse(accessToken, refreshToken)
    }

    fun createAccessToken(number: String): String {
        return createToken(number, ACCESS_KEY, jwtProperties.accessExp)
    }

    fun createRefreshToken(number: String): String {
        val refreshToken = createToken(number, REFRESH_KEY, jwtProperties.refreshExp)

        refreshTokenRepository.save(
            RefreshToken(
                id = number,
                token = refreshToken,
                ttl = jwtProperties.refreshExp * 1000,
            )
        )

        return refreshToken
    }

    private fun createToken(number: String, tokenType: String, exp: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + exp * 1000)

        return Jwts.builder()
            .setSubject(number)
            .setId(number)
            .claim(CLAIM_TYPE, tokenType)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(jwtProperties.header)
        return if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else null
    }

    fun reIssue(refreshToken: String): TokenResponse {
        if (!isRefreshToken(refreshToken)) {
            throw TokenException.InvalidTokenException()
        }

        val tokenEntity = refreshTokenRepository.findByToken(refreshToken)
            ?: throw TokenException.InvalidTokenException()

        val newTokens = generateToken(tokenEntity.id)
        tokenEntity.update(newTokens.refreshToken, jwtProperties.refreshExp)
        refreshTokenRepository.save(tokenEntity)

        return newTokens
    }

    private fun isRefreshToken(token: String): Boolean {
        return getClaims(token)[CLAIM_TYPE] == REFRESH_KEY
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails =
            authDetailsService.loadUserByUsername(getClaims(token).subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secretKey.toByteArray())
            .parseClaimsJws(token)
            .body
    }
}
