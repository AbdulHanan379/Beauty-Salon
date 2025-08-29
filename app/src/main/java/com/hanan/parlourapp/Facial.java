package com.hanan.parlourapp;

public class Facial {
    private final String id;
    private final String name;
    private final String description;
    private final int pricePkr;
    private final int imageRes;

    public Facial(String id, String name, String description, int pricePkr, int imageRes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricePkr = pricePkr;
        this.imageRes = imageRes;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPricePkr() { return pricePkr; }
    public int getImageRes() { return imageRes; }
}
