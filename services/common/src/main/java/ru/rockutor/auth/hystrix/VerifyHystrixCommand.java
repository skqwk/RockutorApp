package ru.rockutor.auth.hystrix;

import com.netflix.hystrix.HystrixCommand;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenVerifyRs;

public class VerifyHystrixCommand extends HystrixCommand<TokenVerifyRs> {
    private final AuthApiService authApiService;
    private final String accessToken;

    public VerifyHystrixCommand(Setter setter,
                                AuthApiService authApiService,
                                String accessToken) {
        super(setter);
        this.authApiService = authApiService;
        this.accessToken = accessToken;
    }

    @Override
    protected TokenVerifyRs run() throws Exception {
        return authApiService.verify(accessToken);
    }
}
