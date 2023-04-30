package com.example.spacebookingweb.Configuration.Security;

import com.example.spacebookingweb.Service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacebookingweb.Configuration.Security.auth.ApplicationUserRole.ADMIN;
import static com.example.spacebookingweb.Configuration.Security.auth.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //TODO: Przerobic security system
//    PasswordConfig passwordConfig;
    PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public SecurityConfig(ApplicationUserService applicationUserService, PasswordEncoder passwordEncoder) {
        this.applicationUserService = applicationUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/api/**").hasRole(ADMIN.name());
                    auth.requestMatchers("/web", "/web/logout", "/web/reservations", "/web/profile", "/web/booking", "/web/booking/**").hasRole(USER.name());
                    auth.requestMatchers("/web/login", "/web/register", "/web/TermsOfServicePage", "/css/**").permitAll();
                    auth.requestMatchers("/api/addUser", "/api/**").permitAll();
                    //TODO: DOROBIC swaggera zeby dzialal po zalogowaniu na ADMIN
                })
                .formLogin()
                .loginPage("/web/login")
                .defaultSuccessUrl("/web")
                //TODO: DOROBIC Auto przekierowywanie dla roli ADMIN
//                .successHandler((request, response, authentication) -> {
//                    if (request.isUserInRole("ADMIN")) {
//                        response.sendRedirect("/swagger-ui/index.html");
//                    }
//                })
                .usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and()
                .logout()
                .logoutUrl("/web/logout")
                .logoutSuccessUrl("/web/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html");
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider()); // dodaj dostawcę autoryzacji z bazy danych
        // dodaj inne dostawcy autoryzacji, jeśli są potrzebne
        return new ProviderManager(providers);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService); // skonfiguruj dostawcę autoryzacji z serwisem użytkowników
        return provider;
    }
}
