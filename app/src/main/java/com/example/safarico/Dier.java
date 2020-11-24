package com.example.safarico;

public class Dier {
    private String naam;
    private double latitude;
    private double longitude;
    private boolean selected;
    private String locatieNaam;

    public Dier(String naam, double latitude, double longitude, boolean selected, String locatieNaam) {
        this.naam = naam;
        this.latitude = latitude;
        this.longitude = longitude;
        this.selected = selected;
        this.locatieNaam = locatieNaam;
    }

    public String getLocatieNaam() {
        return locatieNaam;
    }

    public void setLocatieNaam(String locatieNaam) {
        this.locatieNaam = locatieNaam;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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