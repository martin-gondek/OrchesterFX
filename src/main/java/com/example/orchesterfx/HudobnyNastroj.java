package com.example.orchesterfx;

public abstract class HudobnyNastroj {
    protected String nazov;
    protected double cena;
    protected String zvuk;
    protected String hrac;

    public HudobnyNastroj(String nazov, double cena, String zvuk, String hrac) {
        this.nazov = nazov;
        this.cena = cena;
        this.zvuk = zvuk;
        this.hrac = hrac;
    }

    // GETTERY
    public String getNazov() {
        return nazov;
    }

    public double getCena() {
        return cena;
    }

    public String getZvuk() {
        return zvuk;
    }

    public String getHrac() {
        return hrac;
    }

    // SETTERY
    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setZvuk(String zvuk) {
        this.zvuk = zvuk;
    }

    public void setHrac(String hrac) {
        this.hrac = hrac;
    }

    public abstract String getTyp();
    public abstract String getDetail();
}
