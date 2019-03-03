package com.example.core.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Order(99)
@EnableJpaRepositories(basePackages = {"com.example.core.dao"})
@EntityScan( basePackages = "com.example.core.domain")
public class JpaConfiguration {
}
