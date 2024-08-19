package ru.rockutor.auth.api.v2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;

@FeignClient(name = "${auth.app.name}")
public interface AuthClient extends AuthApiService {
    @RequestMapping(method = RequestMethod.POST, path = "/verify")
    TokenVerifyRs verify(@RequestParam(name = "token") String accessToken);

    @RequestMapping(method = RequestMethod.POST, path = "/refresh")
    TokenRs refresh(@RequestParam(name = "refresh_token") String refreshToken);
}
