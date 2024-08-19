package ru.rockutor.auth.api.v2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@ConditionalOnProperty(name = "auth.version", havingValue = "v2")
public class AuthClientConfig {
}
