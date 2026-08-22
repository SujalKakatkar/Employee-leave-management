package com.example.EmployeeManagement.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth

                                //Public endpoints
                                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()

                                //hr work
                                .requestMatchers(HttpMethod.PUT ,"/api/hr/*").hasRole("HR")
                                .requestMatchers(HttpMethod.PATCH, "/api/hr/*").hasRole("HR")
                                .requestMatchers(HttpMethod.GET, "/api/hr/*").hasRole("HR")

                                //leave type post and get
                                .requestMatchers(HttpMethod.POST, "/api/leavetype").hasRole("HR")
                                .requestMatchers(HttpMethod.GET, "/api/leavetype").authenticated()

                                //Holidays post and get
                                .requestMatchers(HttpMethod.POST, "/api/holidays").hasRole("HR")
                                .requestMatchers(HttpMethod.DELETE, "/api/holidays").hasRole("HR")
                                .requestMatchers(HttpMethod.GET,"/api/holidays").authenticated()

                                //leave balance sheet table
                                .requestMatchers(HttpMethod.POST, "/api/leavebalance").hasRole("HR")
                                .requestMatchers(HttpMethod.GET,"/api/leavebalance/*").authenticated()

                                //leave Request
                                .requestMatchers(HttpMethod.POST, "/api/leaverequest/").hasAnyRole("MANAGER","EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/leaverequest/all").hasAnyRole("MANAGER","HR")
                                .requestMatchers(HttpMethod.PATCH, "/api/leaverequest/review").hasAnyRole("MANAGER","HR")
                                .requestMatchers(HttpMethod.GET, "/api/leaverequest/me").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/leavebalance/*").hasAnyRole("MANAGER","HR")


                                .anyRequest().authenticated()
                );
        http.addFilterBefore(
                (Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
