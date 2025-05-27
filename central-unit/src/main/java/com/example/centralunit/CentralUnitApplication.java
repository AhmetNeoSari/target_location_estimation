package com.example.centralunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CentralUnitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralUnitApplication.class, args);
	}

}
