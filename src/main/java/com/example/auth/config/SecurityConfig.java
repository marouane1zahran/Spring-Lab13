package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChainSetup(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 1. Règles d'autorisation des requêtes
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login**", "/error").permitAll() // Routes publiques
                        .anyRequest().authenticated() // Tout le reste nécessite d'être connecté
                )
                // 2. Activation de l'authentification OAuth2
                .oauth2Login(oauth2 -> oauth2
                        // Redirection en cas de succès vers la page dashboard
                        .defaultSuccessUrl("/dashboard", true)
                )
                // 3. Configuration de la déconnexion
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Retour à l'accueil après déconnexion
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return httpSecurity.build();
    }
}