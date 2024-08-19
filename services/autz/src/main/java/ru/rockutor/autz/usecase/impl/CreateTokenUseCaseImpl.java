package ru.rockutor.autz.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.autz.config.JwtConfig;
import ru.rockutor.autz.usecase.CreateTokenUseCase;
import ru.rockutor.autz.util.JwtTokenUtil;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTokenUseCaseImpl implements CreateTokenUseCase {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtConfig jwtConfig;

    @Override
    public TokenRs createToken(UserData user) {
        log.info("Создание токена для - {}", user.username());

        Integer accessTokenExp = jwtConfig.getAccessTokenExpirationInMinutes();
        String accessToken = jwtTokenUtil.generateToken(user.username(), accessTokenExp);

        Integer refreshTokenExp = jwtConfig.getRefreshTokenExpirationInMinutes();
        String refreshToken = jwtTokenUtil.generateToken(user.username(), refreshTokenExp);

        redisTemplate.opsForValue()
                .set(user.username(), serialize(user), refreshTokenExp, TimeUnit.MINUTES);

        return new TokenRs(accessToken, refreshToken);
    }

    private String serialize(UserData userData)  {
        try {
            return objectMapper.writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(e);
        }
    }
}
