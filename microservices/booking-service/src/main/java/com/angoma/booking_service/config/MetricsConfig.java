package com.angoma.booking_service.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter bookingCounter(MeterRegistry registry) {
        return Counter.builder("bookings.created")
                .description("Number of bookings created")
                .register(registry);
    }
}