package com.epms.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epms.service")
public class EpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpmsApplication.class, args);
	}

}
