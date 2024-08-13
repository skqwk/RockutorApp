package ru.rockutor.autz.usecase;

import ru.rockutor.autz.controller.dto.RegisterRq;
import ru.rockutor.autz.domain.User;

public interface RegisterUseCase {
    User register(RegisterRq registerRq);
}
