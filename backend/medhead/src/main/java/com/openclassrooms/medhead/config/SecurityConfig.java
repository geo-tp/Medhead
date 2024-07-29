package com.openclassrooms.medhead.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .permitAll()
            )
            .csrf(csrf ->
                csrf
                    .ignoringRequestMatchers("/h2-console/**") // Désactive la protection CSRF pour la console H2
            )
            .headers(headers ->
                headers
                    .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Autorise les iframes de la même origine
            );

        return http.build();
    }
}
