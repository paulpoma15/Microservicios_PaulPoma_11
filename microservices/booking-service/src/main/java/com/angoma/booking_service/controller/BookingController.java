package com.angoma.booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.angoma.booking_service.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings", description = "Booking management endpoints")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    public List<String> getBookings() {
        return List.of(
                "Reserva Machu Picchu",
                "Reserva Paracas",
                "Reserva Ica"
        );
    }

    @Operation(summary = "Get booking by id")
    @GetMapping("/{id}")
    public String getBookingById(@PathVariable String id) {
        return "Booking found: " + id;
    }

    @Operation(summary = "Create new booking")
    @PostMapping
    public String createBooking() {
        return "Booking created successfully";
    }

    @Operation(summary = "Delete booking")
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable String id) {
        return "Booking deleted: " + id;
    }

    @Operation(summary = "Service health check")
    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    @Operation(summary = "Check package availability (calls package service)")
    @GetMapping("/check-package/{id}")
    public String checkPackage(@PathVariable Long id) {
        return bookingService.checkPackageAvailability(id);
    }
}