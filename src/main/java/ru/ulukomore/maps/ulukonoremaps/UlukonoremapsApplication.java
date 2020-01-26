package ru.ulukomore.maps.ulukonoremaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ru.ulukomore.maps")
public class UlukonoremapsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UlukonoremapsApplication.class, args);
    }

}
