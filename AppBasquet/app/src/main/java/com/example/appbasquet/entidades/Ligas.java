package com.example.appbasquet.entidades;

public class Ligas {

    private long idLigas;
    private String nombreLiga;

    public Ligas(){

    }

    public Ligas(long idLigas, String nombreLiga) {
        this.idLigas = idLigas;
        this.nombreLiga = nombreLiga;
    }

    public Ligas(String nombreLiga) {
        this.nombreLiga = nombreLiga;
    }

    public long getIdLigas() {
        return idLigas;
    }

    public void setIdLigas(long idLigas) {
        this.idLigas = idLigas;
    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public void setNombreLiga(String nombreLiga) {
        this.nombreLiga = nombreLiga;
    }

    @Override
    public String toString() {
        return "Ligas{" + "nombreLiga='" + nombreLiga + '\'' + '}';
    }
}
