package com.jbaldwin.flowist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories("com.jbaldwin.flowist.repository")
@SpringBootApplication
public class FlowistApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowistApplication.class, args);
    }
}
