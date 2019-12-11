package com.example.appbasquet.dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

public class eliminarJugador extends DialogFragment {
    String info;
    String idJugador, nombreJugador;
    public eliminarJugador(String infoJugador){
        this.info=infoJugador;
        String[] palabras = info.split("\n");
        idJugador=palabras[0];
        nombreJugador = palabras[1];
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Eliminar")
                .setMessage("¿Deseas eliminar "+nombreJugador+" ?")
                .setPositiveButton("Si, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Acciones
                        //buscarNombreLiga(nombre);
                        int id=Integer.parseInt(idJugador);
                        eliminarJugador(id);
                    }
                }). setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void eliminarJugador(int idJugador){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        db.delete(Utilidades.TABLA_JUGADOR,Utilidades.CAMPO_IDJUGADOR+"="+idJugador,null);
        Toast.makeText(this.getActivity(),"Jugador eliminado",Toast.LENGTH_SHORT).show();
    }
}
