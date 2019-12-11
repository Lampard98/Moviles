package com.example.appbasquet.entidades;

public class Equipos {
    private long idEquipo;
    private String nombreEquipo;
    private long idLiga;

    public Equipos(){

    }

    public Equipos(long idEquipo, String nombreEquipo, long idLiga) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.idLiga = idLiga;
    }

    public Equipos(long idEquipo, String nombreEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    public Equipos(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public long getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(long idLiga) {
        this.idLiga = idLiga;
    }

    @Override
    public String toString() {
        return "Equipos{" + "nombreEquipo='" + nombreEquipo + '\'' + '}';
    }
}
