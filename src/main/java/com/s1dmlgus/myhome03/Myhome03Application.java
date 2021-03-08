package com.s1dmlgus.myhome03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class Myhome03Application {

	public static void main(String[] args) {
		SpringApplication.run(Myhome03Application.class, args);
	}




}
