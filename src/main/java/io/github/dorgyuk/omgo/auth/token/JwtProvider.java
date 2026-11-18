package io.github.dorgyuk.omgo.auth.token;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Component 
public class JwtProvider {
    public static final Duration ACCESS_TOKEN_VALIDITY = Duration.ofMinutes(15);

    private final SecretKey key;
    private final Clock clock;

    public JwtProvider(@Value("${jwt.secret}") String secret, Clock clock) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.clock = clock;
    }
    
    public String createAccessToken(Long userId) {
        Instant now = clock.instant();
        Instant expiresAt = now.plus(ACCESS_TOKEN_VALIDITY);
        
        return Jwts.builder()
                        .subject(userId.toString())
                        .issuedAt(Date.from(now))
                        .expiration(Date.from(expiresAt))
                        .signWith(key)
                        .compact();

    }

    public AccessTokenClaims parse(String accessToken) {
        Claims claims = Jwts.parser()
                                .verifyWith(key)
                                .clock(() -> Date.from(clock.instant()))
                                .build()
                                .parseSignedClaims(accessToken)
                                .getPayload();
        
        return new AccessTokenClaims(Long.valueOf(claims.getSubject()), claims.getExpiration().toInstant());
    }
}
