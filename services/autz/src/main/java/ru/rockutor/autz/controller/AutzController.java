package ru.rockutor.autz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.autz.controller.dto.LoginRq;
import ru.rockutor.autz.controller.dto.RegisterRq;
import ru.rockutor.autz.domain.User;
import ru.rockutor.autz.usecase.CreateTokenUseCase;
import ru.rockutor.autz.usecase.GetUserUseCase;
import ru.rockutor.autz.usecase.RefreshTokenUseCase;
import ru.rockutor.autz.usecase.RegisterUseCase;
import ru.rockutor.autz.usecase.VerifyTokenUseCase;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AutzController {
    private final RegisterUseCase registerUseCase;
    private final GetUserUseCase getUserUseCase;
    private final VerifyTokenUseCase verifyTokenUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final CreateTokenUseCase createTokenUseCase;

    @PostMapping("/register")
    void register(@RequestBody RegisterRq registerRq) {
        registerUseCase.register(registerRq);
    }

    @PostMapping("/token")
    TokenRs token(@RequestBody LoginRq loginRq) {
        return createTokenUseCase.createToken(
                toUserData(getUserUseCase.get(loginRq))
        );
    }

    private UserData toUserData(User user) {
        return new UserData(user.getUsername(), user.getRole().name());
    }

    @PostMapping("/verify")
    TokenVerifyRs verify(@RequestParam(name = "token") String token) {
        return verifyTokenUseCase.verify(token);
    }

    @PostMapping("/refresh")
    TokenRs refresh(@RequestParam(name = "refresh_token") String token) {
        return refreshTokenUseCase.refresh(token);
    }
}
