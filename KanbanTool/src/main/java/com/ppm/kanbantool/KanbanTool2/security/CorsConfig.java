package com.ppm.kanbantool.KanbanTool2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Frontend origin
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Apply CORS configuration only to specific endpoints
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
