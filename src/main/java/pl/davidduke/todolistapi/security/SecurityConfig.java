package pl.davidduke.todolistapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.davidduke.todolistapi.security.handlers.CustomAccessDeniedHandler;
import pl.davidduke.todolistapi.security.handlers.CustomBasicAuthEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN_ENDPOINT = "/users/**";
    private static final String USER_ENDPOINT = "/users/me/**";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    private final CustomBasicAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;
//    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security
    ) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorization)
                .sessionManagement(this::configureSessionManagement)
                .headers(this::configureHeaders)
                .httpBasic(this::configureHttpBasic)
                .build();
    }

    private void configureAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry http
    ) {
        http
                .requestMatchers(getOpenedResources()).permitAll()
                .requestMatchers(HttpMethod.GET, this.baseUrl + USER_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN, ROLE_USER
                )
                .requestMatchers(HttpMethod.GET, this.baseUrl + ADMIN_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN
                )
                .requestMatchers(HttpMethod.PATCH, this.baseUrl + USER_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN, ROLE_USER
                )
                .requestMatchers(HttpMethod.PATCH, this.baseUrl + ADMIN_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN
                )
                .requestMatchers(HttpMethod.DELETE, this.baseUrl + USER_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN, ROLE_USER
                )
                .requestMatchers(HttpMethod.DELETE, this.baseUrl + ADMIN_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN
                )
                .requestMatchers(this.baseUrl + USER_ENDPOINT).hasAnyAuthority(
                        ROLE_ADMIN, ROLE_USER
                )
                .anyRequest().authenticated();
    }

    private String[] getOpenedResources() {
        return new String[]{
                "/h2-console/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                this.baseUrl + "/auth/signup"
        };
    }

    private void configureSessionManagement(
            SessionManagementConfigurer<HttpSecurity> session
    ) {
        session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureHeaders(
            HeadersConfigurer<HttpSecurity> httpHeaders
    ) {
        httpHeaders
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable); // For h2-console
    }

    private void configureHttpBasic(
            HttpBasicConfigurer<HttpSecurity> httpBasic
    ) {
        httpBasic
                .authenticationEntryPoint(authEntryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
