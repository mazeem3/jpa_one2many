package com.library.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibraryRdbmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryRdbmsApplication.class, args);
	}

}
