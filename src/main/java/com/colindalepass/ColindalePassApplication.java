package com.colindalepass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ColindalePassApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColindalePassApplication.class, args);
	}

}
