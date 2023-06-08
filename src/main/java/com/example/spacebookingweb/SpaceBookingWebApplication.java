package com.example.spacebookingweb;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpaceBookingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceBookingWebApplication.class, args);
	}

}
