package ru.rockutor.discovery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@ConditionalOnProperty(name = "eureka.enable.discovery", havingValue = "true")
public class DiscoveryConfig {
}
