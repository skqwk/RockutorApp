package ru.rockutor.auth.hystrix;

import com.netflix.hystrix.HystrixCommand;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenRs;

public class RefreshHystrixCommand extends HystrixCommand<TokenRs> {
    private final AuthApiService authApiService;
    private final String refreshToken;

    public RefreshHystrixCommand(Setter setter,
                                 AuthApiService authApiService,
                                 String refreshToken) {
        super(setter);
        this.authApiService = authApiService;
        this.refreshToken = refreshToken;
    }

    @Override
    protected TokenRs run() throws Exception {
        return authApiService.refresh(refreshToken);
    }
}
