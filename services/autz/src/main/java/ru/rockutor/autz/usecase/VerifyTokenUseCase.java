package ru.rockutor.autz.usecase;

import ru.rockutor.auth.dto.TokenVerifyRs;

public interface VerifyTokenUseCase {
    TokenVerifyRs verify(String token);
}
