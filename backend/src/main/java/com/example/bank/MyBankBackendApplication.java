package com.example.bank;  // This should be the root package

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.bank.repository")
@EntityScan("com.example.bank.model")
@ComponentScan("com.example.bank")
public class MyBankBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyBankBackendApplication.class, args);
	}
}