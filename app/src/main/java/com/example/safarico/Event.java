package com.example.safarico;

import java.util.Date;

public class Event {
    private String park;
    private String omschrijving;
    private Date tijd;

    public Event(String park, String omschrijving, Date tijd) {
        this.park = park;
        this.omschrijving = omschrijving;
        this.tijd = tijd;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Date getTijd() {
        return tijd;
    }

    public void setTijd(Date tijd) {
        this.tijd = tijd;
    }
}
