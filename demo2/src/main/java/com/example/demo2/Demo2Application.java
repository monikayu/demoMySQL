package com.example.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Demo2Application.class, args);
        System.out.println("Hello world!");
        Thread.sleep(60000);
        System.exit(100);
    }
}
