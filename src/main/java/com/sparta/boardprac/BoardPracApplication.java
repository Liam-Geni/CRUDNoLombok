package com.sparta.boardprac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardPracApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardPracApplication.class, args);
	}

}
