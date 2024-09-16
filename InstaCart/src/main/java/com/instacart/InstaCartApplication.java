package com.instacart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class InstaCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(InstaCartApplication.class, args);
	}
}
