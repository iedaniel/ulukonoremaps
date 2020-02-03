package ru.ulukomore.maps.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CheckUpdatesService {

    @Scheduled(fixedDelay = 3000)
    public void check() {
        System.out.println("any updates on file?)))))");
    }
}
