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

    public abstract String getTyp();

    public abstract String getDetail();
}