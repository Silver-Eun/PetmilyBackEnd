package com.petmily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PetmilyRealApplication {

    public static void main(String[] args) {

        SpringApplication.run(PetmilyRealApplication.class, args);
    }

}
