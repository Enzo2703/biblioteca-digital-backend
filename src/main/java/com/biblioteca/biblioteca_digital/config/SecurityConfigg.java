package com.biblioteca.biblioteca_digital.config;

import com.biblioteca.biblioteca_digital.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigg {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfigg(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    
}
