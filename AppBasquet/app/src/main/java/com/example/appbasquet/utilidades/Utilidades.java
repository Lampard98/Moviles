package com.example.appbasquet.utilidades;

public class Utilidades {

    ///LIGA
    public static final String TABLA_LIGA = "liga";
    public static final String CAMPO_ID = "idLiga";
    public static final String CAMPO_NOMBRELIGA = "nombreLiga";

    public static final String CREAR_TABLA_LIGA = "CREATE TABLE " + TABLA_LIGA + " (" + CAMPO_ID + " " +
            "INTEGER primary key autoincrement, " +
            CAMPO_NOMBRELIGA + " TEXT)";
    ///EQUIPO
    public static final String TABLA_EQUIPO = "equipo";
    public static final String CAMPO_IDEQUIPO = "idEquipo";
    public static final String CAMPO_NOMBREQUIPO = "nombreEquipo";
    public static final String CAMPO_IDLIGA_EQUIPO = "idLiga";

    public static final String CREAR_TABLA_EQUIPO = "CREATE TABLE " + TABLA_EQUIPO + " (" + CAMPO_IDEQUIPO + " " +
            "INTEGER primary key autoincrement, " +
            CAMPO_NOMBREQUIPO + " TEXT, " + CAMPO_IDLIGA_EQUIPO + " INT)";

    ///JUGADOR
    public static final String TABLA_JUGADOR = "jugador";
    public static final String CAMPO_IDJUGADOR = "idJugador";
    public static final String CAMPO_NOMBREJUGADOR = "nombreJugador";
    public static final String CAMPO_NUMEROJUGADOR = "numeroJugador";
    public static final String CAMPO_FECHAJUGADOR = "fechaJugador";
    public static final String CAMPO_IDEQUIPO_JUGADOR="idEquipo";

    public static final String CREAR_TABLA_JUGADOR = "CREATE TABLE " + TABLA_JUGADOR + "("+CAMPO_IDJUGADOR+" "+
            "INTEGER primary key autoincrement, "+ CAMPO_NOMBREJUGADOR+" TEXT, "+CAMPO_NUMEROJUGADOR+" TEXT, "+
            CAMPO_FECHAJUGADOR+" TEXT, "+CAMPO_IDEQUIPO_JUGADOR+" INT)";
}
