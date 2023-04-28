package com.demo.book;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableR2dbcAuditing
@OpenAPIDefinition(info = @Info(title = "Book Demo", version = "1.0", description = "Documentation APIs v1.0"))
public class BookCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogApplication.class, args);
	}

}
