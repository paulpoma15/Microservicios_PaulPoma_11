package com.angoma.booking_service.model;

import java.time.LocalDate;

public class Booking {

    private Long id;
    private String customerName;
    private Long packageId;
    private LocalDate bookingDate;
    private String status;

    public Booking() {}

    public Booking(Long id, String customerName, Long packageId, LocalDate bookingDate, String status) {
        this.id = id;
        this.customerName = customerName;
        this.packageId = packageId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Long getPackageId() { return packageId; }
    public void setPackageId(Long packageId) { this.packageId = packageId; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}