package com.angoma.tour_package_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@Tag(name = "Tour Packages", description = "Tour package management endpoints")
public class PackageController {

    @Operation(summary = "Get all tour packages")
    @GetMapping
    public List<String> getPackages() {
        return List.of(
                "Machu Picchu",
                "Paracas",
                "Ica"
        );
    }

    @Operation(summary = "Get package availability")
    @GetMapping("/{id}/availability")
    public String availability(@PathVariable String id) {
        return "AVAILABLE";
    }

    @Operation(summary = "Get package by id")
    @GetMapping("/{id}")
    public String getPackageById(@PathVariable String id) {
        return "Package: " + id;
    }

    @Operation(summary = "Create package")
    @PostMapping
    public String createPackage() {
        return "Package created";
    }

    @Operation(summary = "Service health check")
    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}