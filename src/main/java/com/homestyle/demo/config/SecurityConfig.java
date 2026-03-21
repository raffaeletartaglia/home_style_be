package com.homestyle.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig (SecurityFilterChain): configura Spring Security:
 *
 * dice che l’app è stateless e usa JWT di un provider esterno (oauth2ResourceServer().jwt()),
 *
 * decide quali endpoint sono pubblici e quali richiedono autenticazione/ruoli,
 *
 * abilita/disabilita CSRF e aggancia CORS dentro la chain di sicurezza.
 * In pratica è il “firewall” interno dell’app che controlla autenticazione e autorizzazione su tutte le richieste.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // endpoint pubblici se ne hai (es. health, swagger)
                        .requestMatchers("/actuator/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // tutto il resto richiede autenticazione
                        .anyRequest().permitAll()
                )
                // dice a Spring: questa app è un resource server che accetta JWT da un provider esterno
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // puoi passare anche il "strength" es. new BCryptPasswordEncoder(12)
    }
}
