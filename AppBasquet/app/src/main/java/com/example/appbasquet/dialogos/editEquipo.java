package com.example.appbasquet.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appbasquet.R;
import com.example.appbasquet.entidades.Ligas;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

import java.util.ArrayList;

public class editEquipo extends DialogFragment {

    String nombreEquipos;
    ArrayList<Ligas> ligas;
    ArrayList<String> listaLigas;

    public editEquipo(String nombreEquipo){
        this.nombreEquipos=nombreEquipo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.datos_equipo, null);
        builder.setView(dialogView);

        final EditText nombreEquipo = (EditText) dialogView.findViewById(R.id.txtEditarNombreEquipo);
        nombreEquipo.setText(nombreEquipos);

        // Add action buttons
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                String nombreTxt = nombreEquipo.getText().toString();
                if ("".equals(nombreTxt)) {
                    Toast.makeText(getActivity(), "No puedes dejar vacio el campo", Toast.LENGTH_SHORT).show();
                } else {
                    ////
                    buscarEquipo(nombreEquipos,nombreTxt);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editEquipo.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void buscarEquipo(String nombreaBuscar, String nombreNuevo) {
        int idEquipo;

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_EQUIPO + " WHERE " + Utilidades.CAMPO_NOMBREQUIPO + "='" + nombreaBuscar + "'", null);
            int cantidad = c.getCount();
            int i = 0;
            int[] arregloId = new int[cantidad];
            String[] arregloNombre = new String[cantidad];

            if (c.moveToFirst()) {
                do {
                    idEquipo = c.getInt(0);
                    String nombreEquipo = c.getString(1);
                    arregloId[i] = idEquipo;
                    arregloNombre[i] = nombreEquipo;
                    i++;
                } while (c.moveToNext());
                c.close();
                ContentValues campos=new ContentValues();
                campos.put(Utilidades.CAMPO_IDEQUIPO,idEquipo);
                campos.put(Utilidades.CAMPO_NOMBREQUIPO,nombreNuevo);
                db.update(Utilidades.TABLA_EQUIPO,campos,Utilidades.CAMPO_IDEQUIPO+"="+idEquipo,null);
                Toast.makeText(this.getActivity(), "Equipo modificado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "No hay informacion", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
