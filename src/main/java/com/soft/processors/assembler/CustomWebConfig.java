package com.soft.processors.assembler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to configure CORS (Cross Origin Resource Sharing) for REST API.
 *
 * @author kalin
 */
@Configuration("customWebConfig")
public class CustomWebConfig {

    /**
     * Bean to configure CORS mappings
     *
     * @return WebMvcConfigurer object with CORS configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                      .allowedOrigins("*")
                      .allowedMethods("*")
                      .allowedHeaders("*");
            }
        };
    }
}
