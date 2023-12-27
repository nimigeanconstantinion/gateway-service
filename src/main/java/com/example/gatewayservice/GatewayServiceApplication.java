package com.example.gatewayservice;

import jakarta.validation.constraints.Max;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource(){
//		CorsConfiguration configuration=new CorsConfiguration();
//		configuration.addAllowedOrigin("http://localhost:3000");
//		configuration.addAllowedMethod("*");
//		configuration.addAllowedHeader("*");
//		configuration.setAllowCredentials(true);
//		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**",configuration);
//		return source;
//	}
//
//	@Bean
//	public WebFluxConfigurer corsConfigurer(){
//		return new WebFluxConfigurer() {
//			public void addCorsMappings(CorsRegistry registry){
//				registry.addMapping("")
//			}
//		}
//	}
//@Bean
//public CorsWebFilter corsFilter() {
//	CorsConfiguration config = new CorsConfiguration();
//	config.setAllowedOrigins(List.of("http://localhost:3000"));
//	config.setAllowedMethods(List.of("GET", "POST","PUT","DELETE"));
//	config.setAllowedHeaders(List.of("Content-Type", "Authorization", "X-Custom-Header"));
//	config.setExposedHeaders(List.of("*"));
//	config.setMaxAge(3600L);
//
//	return new CorsWebFilter(config);
//}
}
