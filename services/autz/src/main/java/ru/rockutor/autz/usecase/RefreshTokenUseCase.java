package ru.rockutor.autz.usecase;

import ru.rockutor.auth.dto.TokenRs;

public interface RefreshTokenUseCase {
    TokenRs refresh(String token);
}
