package ru.ulukomore.maps.ulukomoremaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ru.ulukomore.maps")
public class UlukomoremapsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UlukomoremapsApplication.class, args);
    }

}
