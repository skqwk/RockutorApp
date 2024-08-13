package ru.rockutor.autz.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.autz.config.JwtConfig;
import ru.rockutor.autz.controller.dto.LoginRq;
import ru.rockutor.autz.domain.User;
import ru.rockutor.autz.exception.InvalidPasswordException;
import ru.rockutor.autz.exception.UserNotFoundException;
import ru.rockutor.autz.repo.UserRepo;
import ru.rockutor.autz.usecase.CreateTokenUseCase;
import ru.rockutor.autz.util.JwtTokenUtil;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ru.rockutor.autz.util.Validator.validateUsername;

@Component
@RequiredArgsConstructor
public class CreateTokenUseCaseImpl implements CreateTokenUseCase {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtConfig jwtConfig;

    @Override
    public TokenRs createToken(UserData user) {
        Integer accessTokenExp = jwtConfig.getAccessTokenExpirationInMinutes();
        String accessToken = jwtTokenUtil.generateToken(user.username(), accessTokenExp);

        Integer refreshTokenExp = jwtConfig.getRefreshTokenExpirationInMinutes();
        String refreshToken = jwtTokenUtil.generateToken(user.username(), refreshTokenExp);

        redisTemplate.opsForValue()
                .set(user.username(), serialize(user), refreshTokenExp, TimeUnit.MINUTES);

        return new TokenRs(accessToken, refreshToken, null);
    }

    private String serialize(UserData userData)  {
        try {
            return objectMapper.writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(e);
        }
    }
}
