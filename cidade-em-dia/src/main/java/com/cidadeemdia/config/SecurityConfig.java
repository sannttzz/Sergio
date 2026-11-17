package com.cidadeemdia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // CIDADÃO: pode visualizar e criar ocorrências
        UserDetails cidadao = User.builder()
            .username("cidadao")
            .password(passwordEncoder.encode("senha123"))
            .roles("CIDADAO")
            .build();
        
        // ATENDENTE: pode visualizar, criar e atualizar ocorrências
        UserDetails atendente = User.builder()
            .username("atendente")
            .password(passwordEncoder.encode("senha456"))
            .roles("ATENDENTE", "CIDADAO")
            .build();
        
        // ADMIN: acesso total
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN", "ATENDENTE", "CIDADAO")
            .build();
        
        return new InMemoryUserDetailsManager(cidadao, atendente, admin);
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Rotas públicas
                .requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                
                // Categorias
                .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/categorias").hasAnyRole("ADMIN", "ATENDENTE")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categorias/**").hasAnyRole("ADMIN", "ATENDENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categorias/**").hasRole("ADMIN")
                
                // Ocorrências
                .requestMatchers(HttpMethod.GET, "/api/v1/ocorrencias/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/ocorrencias").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/ocorrencias/**").hasAnyRole("ADMIN", "ATENDENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/ocorrencias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/ocorrencias/**/status").hasAnyRole("ADMIN", "ATENDENTE")
                
                // Confirmações
                .requestMatchers(HttpMethod.GET, "/api/v1/confirmacoes/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/confirmacoes").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/confirmacoes/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {})
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        
        return http.build();
    }
}
