package com.example.appbasquet.entidades;

public class Jugadores {

    private String nombreJugador, numeroJugador, FNJugador;
    private long idEquipo;

    public Jugadores() {
    }

    public Jugadores(String nombreJugador, String numeroJugador, String FNJugador, long idEquipo) {
        this.nombreJugador = nombreJugador;
        this.numeroJugador = numeroJugador;
        this.FNJugador = FNJugador;
        this.idEquipo = idEquipo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(String numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public String getFNJugador() {
        return FNJugador;
    }

    public void setFNJugador(String FNJugador) {
        this.FNJugador = FNJugador;
    }

    public long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(long idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public String toString() {
        return "Jugadores{" + "nombreJugador='" + nombreJugador + '\'' + ", numeroJugador='" + numeroJugador + '\'' +
                ", FNJugador='" + FNJugador + '\'' +
                '}';
    }
}
