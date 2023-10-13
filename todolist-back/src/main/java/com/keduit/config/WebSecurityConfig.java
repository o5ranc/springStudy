package com.keduit.config;

import com.keduit.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // WebSecurityConfigurerAdapter 대신
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/", "/auth/**",
                        "/swagger-ui/**", "/swagger/**",
                        "/swagger-resources/**", "/v3/api-docs").permitAll()
                .anyRequest().authenticated();

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }

//    @Bean
//    private SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.cors()
//                .and()
//                .csrf().disable()
//                .httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().antMatchers("/", "/auth/**").permitAll()
//                .anyRequest().authenticated();
//
//        http.addFilterAfter(
//                jwtAuthenticationFilter, CorsFilter.class
//        );
//    }

}
