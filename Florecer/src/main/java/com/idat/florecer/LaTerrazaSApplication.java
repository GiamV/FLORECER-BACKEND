package com.idat.florecer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;

@SpringBootApplication
public class LaTerrazaSApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaTerrazaSApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("http://localhost:4200"); // Permitir el uso de credenciales
	        }
	    };
	}
	@Bean
	public TemplateEngine templateEngine() {
	  return new TemplateEngine(); 
	}
}
