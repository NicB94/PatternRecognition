package com.pattern.recognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = "com.pattern.recognition")
public class PatternRecognitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatternRecognitionApplication.class, args);
	}

}
