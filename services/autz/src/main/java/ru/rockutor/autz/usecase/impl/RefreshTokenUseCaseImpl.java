package ru.rockutor.autz.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.autz.usecase.CreateTokenUseCase;
import ru.rockutor.autz.usecase.RefreshTokenUseCase;
import ru.rockutor.autz.usecase.VerifyTokenUseCase;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {
    private final VerifyTokenUseCase verifyTokenUseCase;
    private final CreateTokenUseCase createTokenUseCase;

    @Override
    public TokenRs refresh(String refreshToken) {
        log.info("Рефреш токена");
        TokenVerifyRs result = verifyTokenUseCase.verify(refreshToken);
        return createTokenUseCase.createToken(result.userData());
    }
}
