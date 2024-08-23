package ru.rockutor.discovery;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableDiscoveryClient
@ConditionalOnProperty(name = "eureka.enable.discovery", havingValue = "true")
public class DiscoveryConfig {
    @PostConstruct
    void run() {
        log.info("Eureka enable discovery");
    }
}
