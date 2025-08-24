package com.example.karina_project.sehyukPage.login_page.config;

import com.example.karina_project.sehyukPage.login_page.jwt.JWTFilter;
import com.example.karina_project.sehyukPage.login_page.jwt.JWTUtil;
import com.example.karina_project.sehyukPage.login_page.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity // Security를 위한 Config이기 때문에 추가
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };


    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtill;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource(){
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:5173",
                                "http://localhost:3000",
                                "https://javadream.info",
                                "https://jabeodream.netlify.app"
                        ));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()          // ← Swagger 허용
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // ← CORS preflight 허용(권장)
                        .requestMatchers("/", "/index", "/public/**", "/user/**").permitAll()
                        .requestMatchers("/fisher/mypage/profile").authenticated()
                        .requestMatchers("/fisher/**").hasAnyAuthority("ROLE_FISHER", "ROLE_ADMIN")
                        .requestMatchers("/factory/**").hasAnyAuthority("ROLE_FACTORY", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                );


        http
                .addFilterBefore(new JWTFilter(jwtUtill), UsernamePasswordAuthenticationFilter.class);

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtill);
        loginFilter.setFilterProcessesUrl("/user/login");
        loginFilter.setUsernameParameter("loginId");

        //AuthenticationManager()와 JWTUtil 인수 전달
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
