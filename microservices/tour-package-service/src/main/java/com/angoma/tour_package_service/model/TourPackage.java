package com.angoma.tour_package_service.model;

public class TourPackage {

    private Long id;
    private String packageName;
    private String destination;
    private double price;
    private int availableSlots;

    public TourPackage() {}

    public TourPackage(Long id, String packageName, String destination, double price, int availableSlots) {
        this.id = id;
        this.packageName = packageName;
        this.destination = destination;
        this.price = price;
        this.availableSlots = availableSlots;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(int availableSlots) { this.availableSlots = availableSlots; }
}