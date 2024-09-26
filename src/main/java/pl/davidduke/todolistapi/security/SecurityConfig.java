package pl.davidduke.todolistapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomBasicAuthEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security
    ) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers(getOpenedResources()).permitAll()
                        .requestMatchers(HttpMethod.GET, this.baseUrl + "/user/**").hasAnyAuthority(
                                "ROLE_ADMIN"
                        )
                        .requestMatchers(HttpMethod.PATCH, this.baseUrl + "/user/**").hasAnyAuthority(
                                "ROLE_ADMIN", "ROLE_USER"
                        )
                        .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/user/**").hasAnyAuthority(
                                "ROLE_ADMIN"
                        )
                        .requestMatchers(this.baseUrl + "/user/task/**").hasAnyAuthority(
                                "ROLE_ADMIN", "ROLE_USER"
                        )
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // For h2-console
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(authEntryPoint)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] getOpenedResources() {
        return new String[]{
                "/h2-console/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                this.baseUrl + "/auth/signup"
        };
    }
}
