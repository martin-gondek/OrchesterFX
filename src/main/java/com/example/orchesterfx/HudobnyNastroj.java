package com.example.orchesterfx;

public abstract class HudobnyNastroj {
    protected String nazov;
    protected double cena;

    public HudobnyNastroj(String nazov, double cena) {
        this.nazov = nazov;
        this.cena = cena;
    }

    public String getNazov() {
        return nazov;
    }

    public double getCena() {
        return cena;
    }

    public abstract String getTyp();
    public abstract String getDetail();
}
