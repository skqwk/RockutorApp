package ru.rockutor.autz.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.autz.exception.JwtTokenExpiredException;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Методы для генерации JWT-токена
 */
@Component
@AllArgsConstructor
public class JwtTokenUtil {
    private final SecretKey secretKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(String username,
                                int expirationInMinutes) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(getExpiration(expirationInMinutes))
                .signWith(secretKey)
                .compact();
    }

    private Date getExpiration(int expirationInMinutes) {
        Instant expiration = Instant.now().plus(expirationInMinutes, MINUTES);
        return Date.from(expiration);
    }

    public String getUsernameFromToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();
            return Optional.of(parser.parseClaimsJws(token))
                    .map(Jwt::getBody)
                    .map(Claims::getSubject)
                    .orElse(null);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException();
        }
    }
}