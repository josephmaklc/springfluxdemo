package com.optimal.solutions.springfluxdemo.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI().info(
				new Info().title("Sample WebFlux Application").description("This is some description"));
				
	}

}
