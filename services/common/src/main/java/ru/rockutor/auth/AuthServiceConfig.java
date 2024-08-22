package ru.rockutor.auth;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rockutor.auth.api.AuthApiService;

@Configuration
public class AuthServiceConfig {
    private static final String HYSTRIX_AUTH_GROUP_KEY = "HYSTRIX_AUTH";

    @Bean
    public AuthService authService(AuthApiService authApiService,
                                   HystrixCommand.Setter config,
                                   @Value("${auth.hystrix.enabled}") boolean hystrixEnabled) {
        return new AuthService(authApiService, config, hystrixEnabled);
    }

    @Bean
    public
    HystrixCommand.Setter hystrixConfig(@Value("${hystrix.timeout.ms}") int timeoutMs,
                                        @Value("${hystrix.window.ms}") int windowMs,
                                        @Value("${hystrix.request.threshold}") int threshold) {
        HystrixCommand.Setter config = HystrixCommand.Setter
            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(HYSTRIX_AUTH_GROUP_KEY));

        HystrixCommandProperties.Setter properties = HystrixCommandProperties.Setter();
        properties.withExecutionTimeoutInMilliseconds(timeoutMs);
        properties.withCircuitBreakerSleepWindowInMilliseconds(windowMs);
        properties.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
        properties.withCircuitBreakerEnabled(true);
        properties.withCircuitBreakerRequestVolumeThreshold(threshold);

        config.andCommandPropertiesDefaults(properties);
        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(1)
                .withCoreSize(1)
                .withQueueSizeRejectionThreshold(1));

        return config;
    }

}
