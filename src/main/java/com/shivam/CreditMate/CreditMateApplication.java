package com.shivam.CreditMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shivam.CreditMate"})
public class CreditMateApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditMateApplication.class, args);
    }
}
