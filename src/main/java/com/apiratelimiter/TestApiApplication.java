package com.apiratelimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author Hari
 * Entry point to the application
 */
@SpringBootApplication
@EnableScheduling
public class TestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApiApplication.class, args);
    }

}
