package ru.rockutor.autz.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.rockutor.autz.controller.dto.LoginRq;
import ru.rockutor.autz.domain.User;
import ru.rockutor.autz.exception.InvalidPasswordException;
import ru.rockutor.autz.exception.UserNotFoundException;
import ru.rockutor.autz.repo.UserRepo;
import ru.rockutor.autz.usecase.GetUserUseCase;

import java.util.Optional;

import static ru.rockutor.autz.util.Validator.validateUsername;

@Component
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public User get(LoginRq loginRq) {
        String username = loginRq.username();
        validateUsername(username);

        Optional<User> userOpt = userRepo.findUserByUsername(username);
        User user = userOpt.orElseThrow(() -> new UserNotFoundException(username));
        verifyPassword(user, loginRq.password());

        return user;
    }

    private void verifyPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
    }
}
