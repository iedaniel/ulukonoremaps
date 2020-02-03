package ru.ulukomore.maps.ulukomoremaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "ru.ulukomore.maps")
@EnableScheduling
public class UlukomoremapsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UlukomoremapsApplication.class, args);
    }

}
