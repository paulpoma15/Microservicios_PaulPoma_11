package com.angoma.tour_package_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @GetMapping
    public List<String> getPackages() {
        return List.of(
            "Machu Picchu",
            "Paracas",
            "Ica"
        );
    }
}
