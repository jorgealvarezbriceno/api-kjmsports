package com.kjm_sports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KjmSportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KjmSportsApplication.class, args);
    }

    // --- CONFIGURACIÓN DE SEGURIDAD (CORS) ---
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todas las rutas
                        .allowedOriginPatterns("*") // Permitir CUALQUIER origen (React en 5173, 5174, etc.)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Todos los métodos
                        .allowedHeaders("*") // Todas las cabeceras
                        .allowCredentials(true); // Permitir credenciales (cookies, auth)
            }
        };
    }
}