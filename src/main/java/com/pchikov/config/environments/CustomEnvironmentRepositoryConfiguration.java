package com.pchikov.config.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("custom")
public class CustomEnvironmentRepositoryConfiguration {

    @Value("${spring.cloud.config.server.custom.order:0x7fffffff}")
    private int order;

    @Bean
    CustomEnvironmentRepository customEnvironmentRepository() {

        CustomEnvironmentRepository repo = new CustomEnvironmentRepository();
        repo.setOrder(order);

        return repo;
    }
}