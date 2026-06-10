package com.angoma.booking_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookingService {

    private final WebClient webClient;

    public BookingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String checkPackageAvailability(Long packageId) {

        String url = "http://localhost:8080/packages/" + packageId + "/availability";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}