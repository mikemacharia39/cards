package com.logicea.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication(scanBasePackages = "com.logicea.cards")
public class CardsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
