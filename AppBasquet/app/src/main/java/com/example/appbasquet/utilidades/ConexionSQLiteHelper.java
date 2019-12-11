package com.example.appbasquet.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_LIGA);
        db.execSQL(Utilidades.CREAR_TABLA_EQUIPO);
        db.execSQL(Utilidades.CREAR_TABLA_JUGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_LIGA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_EQUIPO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_JUGADOR);
        onCreate(db);
    }
}
