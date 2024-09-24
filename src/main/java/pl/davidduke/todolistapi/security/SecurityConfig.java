package pl.davidduke.todolistapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomBasicAuthEntryPoint authEntryPoint;

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
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
//                .headers(headers -> headers
//                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // This is for H2 browser console access.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(authEntryPoint))
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.authEntryPoint))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] getOpenedResources() {
        return new String[]{
                "/swagger-ui/**",
                "/v3/api-docs/**",
                this.baseUrl + "/auth/signup"
        };
    }
}
