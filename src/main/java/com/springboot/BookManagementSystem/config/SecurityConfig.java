package com.springboot.BookManagementSystem.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/sign-up")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/book/all-book")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/book/book-by-isbn")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/book/add")
                        .hasAuthority("AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/api/book/update/**")
                        .hasAuthority("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/book/delete/**")
                        .hasAuthority("AUTHOR")
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());  //Spring understands that i am using this technique
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
