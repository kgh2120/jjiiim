package com.kk.jjiiim.config;


import com.kk.jjiiim.config.security.*;
import com.kk.jjiiim.domain.Role;
import com.kk.jjiiim.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CommonService commonService;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(commonService)
                .passwordEncoder(passwordEncoder);
        authenticationManager = authenticationManagerBuilder.build();


        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtProvider, commonService,
                authenticationFailureHandler());
        jwtAuthenticationFilter.setFilterProcessesUrl("/common/login");
        http.formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(AntPathRequestMatcher.antMatcher(
                                "/h2-console/**")).permitAll()
                                .requestMatchers("/common/**",
                                        "/customer/signup", "/manager/signup").permitAll()
                                .requestMatchers("/manager/**").hasRole(Role.ROLE_MANAGER.getRoleName())
                                .requestMatchers("/customer/**").hasRole(Role.ROLE_CUSTOMER.getRoleName())
                               )
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider, commonService)
                        , JwtAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(),
                        JwtAuthorizationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
