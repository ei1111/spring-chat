package com.chat_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private static final String[] AUTH_ALLOWLIST = {
            "/swagger-ui/**", "/v3/**", "/login/**", "/images/**", "/css/**", "/js/**",
            "/login.html"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 정적 리소스와 login.html은 인증 없이 접근 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_ALLOWLIST).permitAll()
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                // 커스텀 로그인 페이지 지정
                .formLogin(login -> login
                        .loginPage("/login.html")           // 로그인 페이지 URL (static/login.html)
                        .loginProcessingUrl("/perform_login") // form submit URL
                        .defaultSuccessUrl("/index.html", true) // 로그인 성공 시 이동
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 개발용, 운영 시 CSRF 켜야 함

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
