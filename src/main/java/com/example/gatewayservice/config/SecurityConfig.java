package com.example.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .authorizeExchange(exchanges -> exchanges
                        // CORS preflight
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public: swagger, health, UI, assets Vite
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .pathMatchers("/actuator/health", "/actuator/info").permitAll()
                        .pathMatchers(
                                "/ui/**",
                                "/@vite/**",
                                "/@react-refresh/**",
                                "/assets/**",
                                "/favicon.ico",
                                "/node_modules/**",
                                "/src/**",
                                "/@react-refresh",
                                "/favicon.ico"
                        ).permitAll()
//                        - Path=/@vite/**, /@react-refresh, /@react-refresh/**, /src/**, /node_modules/**, /assets/**, /favicon.ico

//                        .pathMatchers("**").permitAll()
                        // Backend: trebuie token
//                        .pathMatchers("/command/**", "/query/**", "/server/**").authenticated()
                        .pathMatchers("/command/**", "/query/**", "/server/**").authenticated()

                        // orice altceva – cere autentificare
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> reactiveJwtAuthenticationConverter() {
        JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();
        // folosesti convertorul tau de roluri
        delegate.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(delegate);
    }


}
