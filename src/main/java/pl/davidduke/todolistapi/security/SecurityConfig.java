package pl.davidduke.todolistapi.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {

    final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security
    ) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(
                                    "/api/v1/auth/signup",
                                    "/swagger-ui/**").permitAll();
                            auth.anyRequest().authenticated();
                        }
                );
//        security.sessionManagement(
//                session ->
//                        session
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//        security.exceptionHandling(
//                exceptionHandling ->
//                        exceptionHandling.authenticationEntryPoint(
//                                new CustomAuthenticationEntryPoint()
//                        )
//        );
        security.httpBasic(Customizer.withDefaults());
        return security.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
