package com.fastcampus.boardpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MvcBoardPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcBoardPracticeApplication.class, args);
	}

}
