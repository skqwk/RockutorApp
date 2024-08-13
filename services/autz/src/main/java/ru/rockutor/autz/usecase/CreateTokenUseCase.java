package ru.rockutor.autz.usecase;

import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.autz.controller.dto.LoginRq;

public interface CreateTokenUseCase {
    TokenRs createToken(UserData user);
}
