package com.example.conf;

import com.example.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    private static final String[] PRIVATE_URI = {
            "/bookCatalogs/**",
            "/rentals/**",
            "/requests/**",
            "/users/**",
            "/catalogs/**"
    };
    private static final String[] PUBLIC_URI = {
            "/books/**",
            "/rentals/**",
            "/requests/**",
            "/catalogs/**"
    };

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/login/**").permitAll()
                                //.requestMatchers(HttpMethod.GET, PUBLIC_URI).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                //.requestMatchers(HttpMethod.DELETE, PRIVATE_URI).hasRole(Role.ADMIN.name())
                                //.requestMatchers(HttpMethod.DELETE, PRIVATE_URI).hasRole(Role.ADMIN.name())
                                //.requestMatchers(HttpMethod.PUT, PRIVATE_URI).hasRole(Role.ADMIN.name())
                                //.requestMatchers(HttpMethod.POST, PRIVATE_URI).authenticated()

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}