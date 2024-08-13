package ru.rockutor.autz.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import ru.rockutor.autz.controller.dto.RegisterRq;
import ru.rockutor.autz.domain.User;
import ru.rockutor.autz.exception.UserAlreadyExistException;
import ru.rockutor.autz.repo.UserRepo;
import ru.rockutor.autz.usecase.RegisterUseCase;

import static ru.rockutor.autz.util.Validator.*;

@Controller
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRq registerRq) {
        validateRequest(registerRq);

        userRepo.findUserByUsername(registerRq.username())
                .ifPresent(u -> {
                    throw new UserAlreadyExistException();
                });

        User user = new User();
        user.setUsername(registerRq.username());
        user.setPassword(passwordEncoder.encode(registerRq.password()));
        user.setRole(registerRq.role());

        return userRepo.save(user);
    }

    private void validateRequest(RegisterRq registerRq) {
        validatePassword(registerRq.password());
        validateUsername(registerRq.username());
        validateRole(registerRq.role());
    }
}
