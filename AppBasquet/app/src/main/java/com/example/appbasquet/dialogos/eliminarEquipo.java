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

public class eliminarEquipo extends DialogFragment {

    String nombre;

    public eliminarEquipo(String nom){
        this.nombre=nom;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Eliminar")
                .setMessage("¿Deseas eliminar "+nombre+" ?")
                .setPositiveButton("Si, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Acciones
                        //buscarNombreLiga(nombre);
                        eliminarEquipo();
                    }
                }). setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void eliminarEquipo(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] args={String.valueOf(nombre)};
        db.delete(Utilidades.TABLA_EQUIPO,Utilidades.CAMPO_NOMBREQUIPO+"=?",args);
        Toast.makeText(this.getActivity(),"Equipo eliminado",Toast.LENGTH_SHORT).show();
    }
}
