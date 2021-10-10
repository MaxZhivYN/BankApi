package com.sberbank.maxzhiv.bankapi;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }
}
