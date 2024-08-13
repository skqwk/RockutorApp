package ru.rockutor.autz.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * Данные конфигурации для генерации JWT-токена
 */
@Getter
@Configuration
public class JwtConfig {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessTokenExpirationInMinutes}")
    private Integer accessTokenExpirationInMinutes;

    @Value("${jwt.refreshTokenExpirationInMinutes}")
    private Integer refreshTokenExpirationInMinutes;

    @Bean
    public SecretKey getSecretKeyForSigning() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
