package com.example.safarico;

public class Dier {
    private String naam;
    private double latitude;
    private double longitude;

    public Dier(String naam, double latitude, double longitude) {
        this.naam = naam;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
