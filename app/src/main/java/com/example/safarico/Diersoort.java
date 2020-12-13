package com.example.safarico;

public class Diersoort {
    private String naam;
    private int count;
    private boolean bedreigd;
    private String[] oorzaak;

    public Diersoort(String naam, int count, boolean bedreigd, String[] oorzaak) {
        this.naam = naam;
        this.count = count;
        this.bedreigd = bedreigd;
        this.oorzaak = oorzaak;
    }

    public String[] getOorzaak() {
        return oorzaak;
    }

    public void setOorzaak(String[] oorzaak) {
        this.oorzaak = oorzaak;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isBedreigd() {
        return bedreigd;
    }

    public void setBedreigd(boolean bedreigd) {
        this.bedreigd = bedreigd;
    }
}
