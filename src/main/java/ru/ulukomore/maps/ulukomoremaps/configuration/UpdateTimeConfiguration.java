package ru.ulukomore.maps.ulukomoremaps.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class UpdateTimeConfiguration {

    @Value("${app.xsls.path}")
    private String pathToXslx;

    @Bean
    public LocalDateTime lastUpdate() {
        try {
            return LocalDateTime.ofInstant(
                    Files.getLastModifiedTime(Paths.get(pathToXslx)).toInstant(),
                    ZoneId.systemDefault()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalDateTime.now();
    }
}
