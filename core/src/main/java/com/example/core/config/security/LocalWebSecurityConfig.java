package com.example.core.config.security;

import com.example.core.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(100)
@Profile("local")
public class LocalWebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public HttpSecurityConfiguration localConfig(){
        return new HttpSecurityConfiguration() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                System.out.println("Configuring the web local security");
                http
                        .authorizeRequests()
                        .antMatchers("/h2-console**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        // H2 configuration
                        .csrf().ignoringAntMatchers("/h2-console/**")
                        .and()
                        .headers().frameOptions().disable()

                        .and()
                        .formLogin()
                        .permitAll()
                        .and()
                        .logout()
                        .permitAll();
            }
        };
    }

    @Bean
    @Profile("local")
    public DaoAuthenticationProvider localAuthProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(noOpPasswordEncoder());
        return authProvider;
    }

    // Does not encode passwords in local profile
    @Bean
    @Profile("local")
    public PasswordEncoder noOpPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
