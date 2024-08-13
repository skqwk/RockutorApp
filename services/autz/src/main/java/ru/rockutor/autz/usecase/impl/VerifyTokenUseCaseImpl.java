package ru.rockutor.autz.usecase.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.autz.usecase.VerifyTokenUseCase;
import ru.rockutor.autz.util.JwtTokenUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class VerifyTokenUseCaseImpl implements VerifyTokenUseCase {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public TokenVerifyRs verify(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String dumped = redisTemplate.opsForValue().get(username);
        UserData userData = deserialized(dumped);
        return createTokenVerifyRs(userData);
    }

    private UserData deserialized(String dumped) {
        try {
            return objectMapper.readValue(dumped, UserData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TokenVerifyRs createTokenVerifyRs(UserData userData) {
        return new TokenVerifyRs(
                true,
                userData
        );
    }
}
