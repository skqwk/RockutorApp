package ru.rockutor.autz.usecase;

import ru.rockutor.autz.controller.dto.LoginRq;
import ru.rockutor.autz.domain.User;

public interface GetUserUseCase {
    User get(LoginRq loginRq);
}
