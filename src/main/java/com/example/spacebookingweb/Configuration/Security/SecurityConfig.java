package com.example.spacebookingweb.Configuration.Security;

import com.example.spacebookingweb.Configuration.Security.auth.jwt.AuthEntryPointJwt;
import com.example.spacebookingweb.Configuration.Security.auth.jwt.AuthTokenFilter;
import com.example.spacebookingweb.Configuration.Security.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:3000")
public class SecurityConfig {

    UserDetailsServiceImpl userDetailsService;

    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/test/all").permitAll()
                                .requestMatchers("/api/test/user").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(
                                        //API FLOOR
                                        "/api/floor",
                                        "/api/floor/{id}",
                                        "/api/floor/{id}/reservations/{date}",
                                        //API SPACE
                                        "/api/space",
                                        "/api/space/{id}",
                                        "/api/space/type/{type}",
                                        "/api/space/heightAdjustable/{bool}",
                                        "/api/space/{id}/{type}/{date}/{status}",
                                        // API RESERVATION
                                        "/api/reservation",
                                        "/api/reservation/{id}",
                                        //API USER
                                        "/api/user",
                                        "/api/user/{id}",
                                        "/api/user/name/{username}",
                                        "/api/user/{id}/reservations",
                                        "/api/user/{id}/reservations/{status}",
                                        "/api/user/{id}/details"
                                ).hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/test/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
