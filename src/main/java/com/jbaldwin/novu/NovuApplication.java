package com.jbaldwin.novu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.jbaldwin.novu.repository")
@SpringBootApplication
public class NovuApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovuApplication.class, args);
	}
}
