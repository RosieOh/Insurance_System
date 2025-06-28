package com.insurance.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 * 
 * 주요 기능:
 * - 웹 보안 설정
 * - URL 접근 권한 설정
 * - CSRF 보호 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/modules/**",
                    "/h2-console/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico",
                    "/docs/**",
                    "/examples/**",
                    "/community/**",
                    "/api/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/graphql",
                    "/graphiql"
                ).permitAll()
                .anyRequest().permitAll() // 개발 중에는 전체 허용, 운영 시 authenticated()로 변경
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            );

        return http.build();
    }
} 