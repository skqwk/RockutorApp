package ru.rockutor.auth.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.rockutor.auth.api.AuthApiService;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "auth.version", havingValue = "v1", matchIfMissing = true)
public class AuthApiConfig {
    @Bean
    public AuthApiService authApiService(RestTemplate restTemplate,
                                         AuthDataConfig dataConfig) {
        return new AuthApiServiceImplV1(restTemplate, dataConfig.getAuthApiUrl());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
