package com.example.security.config;

import com.example.model.enums.Role;
import com.example.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private static final String[] ALL_ENDPOINTS = {
            "/bookCatalogs/**",
            "/books/**",
            "/requests/**",
            "/catalogs/**",
            "/users/**",
            "/rentals/**"
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
                                .requestMatchers(HttpMethod.GET, ALL_ENDPOINTS).hasAuthority(Role.ADMIN.getAuthority())

                                .requestMatchers(HttpMethod.GET, ALL_ENDPOINTS).authenticated()
                                .requestMatchers(HttpMethod.POST, "/request/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/rentals/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/rentals/**").authenticated()

                                .requestMatchers(HttpMethod.DELETE, ALL_ENDPOINTS).hasAuthority(Role.ADMIN.getAuthority())
                                .requestMatchers(HttpMethod.PUT, ALL_ENDPOINTS).hasAuthority(Role.ADMIN.getAuthority())
                                .requestMatchers(HttpMethod.POST, ALL_ENDPOINTS).hasAuthority(Role.ADMIN.getAuthority())

                                .requestMatchers(HttpMethod.GET, "/rentals/**").hasAuthority(Role.ADMIN.getAuthority())

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