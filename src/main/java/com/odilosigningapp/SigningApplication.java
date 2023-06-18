package com.odilosigningapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration

public class SigningApplication {
	public static void main(String[] args) {
		SpringApplication.run(SigningApplication.class, args);
	}

}
