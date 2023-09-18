package com.melitaltd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
//@EnableFeignClients
public class StartCareSystemApplication {

    public static final String API_VERSION_1 = "/api/v1";

    public static void main(String[] args) {
        SpringApplication.run(StartCareSystemApplication.class, args);
    }

}
