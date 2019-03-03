package com.example.core.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityConfiguration {
    void configure(HttpSecurity http) throws Exception;
}
