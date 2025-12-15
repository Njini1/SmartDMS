package com.company.eduboard.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable()) // 개발 중엔 임시로 비활성화
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // H2 콘솔 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ckeditor-custom/**", "/css/**", "/js/**", "/images/**").permitAll() // ckeditor5.js 로딩이 실패하는 문제 해결
                        .requestMatchers("/", "/users/register", "/board/list", "/board/{id:[0-9]+}").permitAll()
                        .requestMatchers("/board/**", "/like/**", "/comment/**", "/document/**").authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login") // 사용자 지정 로그인 페이지
                        .loginProcessingUrl("/users/login") // form action 주소
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/board/list")
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.sendRedirect("/board/list");
//                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .invalidSessionUrl("/users/login?expired")  // 세션 만료 시 이동 URL
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)            // 기존 세션 만료 허용
                        .expiredUrl("/users/login?expired")   // 추가: 세션 만료 시 동일 페이지로 이동
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    // 로그인 성공시 사용자 정보 세션 저장
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String email = authentication.getName();
//            request.getSession().setAttribute("loginEmail", email);
            response.sendRedirect("/board/list");
        };
    }

    // 로그인 실패시 처리
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        // 로그인 실패 시 예외 종류(계정 없음 / 비밀번호 틀림 / 잠금 등)에 따라 다른 메시지 출력 가능
        return (request, response, exception) -> {
            request.getSession().setAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            response.sendRedirect("/users/login");
        };
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

