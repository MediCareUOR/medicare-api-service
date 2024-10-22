package com.rucreativedeveloper.medicare_api_service.config;


import com.rucreativedeveloper.medicare_api_service.jwt.JwtTokenVerifier;
import com.rucreativedeveloper.medicare_api_service.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.rucreativedeveloper.medicare_api_service.service.Impl.SystemUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final SystemUserServiceImpl systemUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, SystemUserServiceImpl systemUserService, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.systemUserService = systemUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, AuthenticationManager authenticationManager) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));

        security.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> corsConfiguration))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager, jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/api/v1/system-users/**").permitAll()
                        .requestMatchers("/api/v1/pharmacist/**").permitAll()
                        .requestMatchers("api/v1/drugs/**").permitAll()
                        .anyRequest()
                        .authenticated());
        return security.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(systemUserService);
        return provider;
    }

}
