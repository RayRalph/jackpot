package com.sporty.jackpot.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan("com.sporty.jackpot.service.model.entity")
@SpringBootApplication
public class JackpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JackpotApplication.class, args);
	}

}
