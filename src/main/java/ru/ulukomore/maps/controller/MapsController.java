package ru.ulukomore.maps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapsController {

    @GetMapping
    public String hello() {
        return "ulukomore";
    }
}
