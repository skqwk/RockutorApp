package ru.rockutor.auth;

import com.netflix.hystrix.HystrixCommand;
import lombok.AllArgsConstructor;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.hystrix.RefreshHystrixCommand;
import ru.rockutor.auth.hystrix.VerifyHystrixCommand;

@AllArgsConstructor
public class AuthService implements AuthApiService {
    private final AuthApiService authApiService;
    private final HystrixCommand.Setter config;
    private boolean hystrixEnabled;

    @Override
    public TokenVerifyRs verify(String accessToken) {
        if (hystrixEnabled) {
            return new VerifyHystrixCommand(config, authApiService, accessToken)
                    .execute();
        }
        return authApiService.verify(accessToken);
    }

    @Override
    public TokenRs refresh(String refreshToken) {
        if (hystrixEnabled) {
            return new RefreshHystrixCommand(config, authApiService, refreshToken)
                    .execute();
        }
        return authApiService.refresh(refreshToken);
    }
}
