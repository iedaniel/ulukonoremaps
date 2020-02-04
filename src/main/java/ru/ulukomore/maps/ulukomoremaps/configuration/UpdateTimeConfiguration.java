package ru.ulukomore.maps.ulukomoremaps.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class UpdateTimeConfiguration {

    @Bean
    public LocalDateTime lastUpdate() {
        return LocalDateTime.now();
    }
}
