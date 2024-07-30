package com.openclassrooms.medhead.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/h2-console/**").permitAll() // Autoriser le connecteur H2 base de données
                    .requestMatchers("/hospitals/**").permitAll() // Autoriser l'endpoint public pour la liste des hopitaux
                    .anyRequest().authenticated() // Le reste des endpoints necessitent l'authentification
            )
            .httpBasic(withDefaults()) // Sécurité basique pour les requêtes API
            .formLogin(withDefaults()) // Formulaire de connexion dans le navigateur
            .logout(withDefaults());

        // Désactive CSRF pour simplifier les requêtes API
        http.csrf(csrf -> csrf.disable());

        // Configure CORS
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        // Autoriser les iframes de la même origine
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
