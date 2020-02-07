package ru.ulukomore.maps.ulukomoremaps.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static ru.ulukomore.maps.Constants.PATH_TO_XLSX;

@Configuration
public class UpdateTimeConfiguration {

    @Bean
    public LocalDateTime lastUpdate() {
        try {
            return LocalDateTime.ofInstant(
                    Files.getLastModifiedTime(Paths.get(PATH_TO_XLSX)).toInstant(),
                    ZoneId.systemDefault()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalDateTime.now();
    }
}
