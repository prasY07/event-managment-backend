package com.example.event_management.common.jwt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.event_management.common.jwt.CustomAuthEntryPoint;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    
    @Autowired
    private CustomAuthEntryPoint customAuthEntryPoint;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/admin/auth/login").permitAll() // allow login
                        .requestMatchers("/api/app/**").permitAll()           // allow app APIs
                        .anyRequest().authenticated()                        // everything else -> needs JWT
                
                )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthEntryPoint))
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            // .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    // @Bean
    // public OncePerRequestFilter jwtFilter() {
    //     return new OncePerRequestFilter() {
    //         @Override
    //         protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    //                 throws IOException, ServletException {

    //             String auth = req.getHeader("Authorization");
    //             if (auth != null && auth.startsWith("Bearer ")) {
    //                 String token = auth.substring(7);
    //                 if (jwtService.isTokenValid(token)) {
    //                     // Extract all required claims
    //                     String userId = jwtService.extractClaim(token, "userId", String.class);
    //                     String role = jwtService.extractClaim(token, "role", String.class);
    //                     String name = jwtService.extractClaim(token, "fullName", String.class);
    //                     String tclEmpId = jwtService.extractClaim(token, "tclEmpId", String.class);

    //                     // Create principal object
    //                     JwtUser principal = new JwtUser(userId, role, name, tclEmpId);

    //                     UsernamePasswordAuthenticationToken authToken =
    //                             new UsernamePasswordAuthenticationToken(principal, null, List.of());
    //                     SecurityContextHolder.getContext().setAuthentication(authToken);
    //                 }
    //             }
    //             chain.doFilter(req, res);
    //         }
    //     };
    // }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
