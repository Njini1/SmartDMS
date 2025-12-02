package com.company.eduboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartDmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartDmsApplication.class, args);
    }

}
