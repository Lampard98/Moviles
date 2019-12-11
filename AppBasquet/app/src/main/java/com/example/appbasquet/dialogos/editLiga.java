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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appbasquet.R;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

public class editLiga extends DialogFragment {

    String nombre;

    public editLiga(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.datos_liga, null);
        builder.setView(dialogView);

        final EditText nombreLiga = (EditText) dialogView.findViewById(R.id.txtEditarNombreLiga);
        nombreLiga.setText(nombre);

        // Add action buttons
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                nombreLiga.setError(null);
                String nombreTxt = nombreLiga.getText().toString();
                if ("".equals(nombreTxt)) {
                    nombreLiga.setError("Escribe el nombre de la liga");
                    nombreLiga.requestFocus();
                } else {
                    ////
                    buscarLiga(nombre,nombreTxt);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editLiga.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void buscarLiga(String nombreaBuscar,String nombreNuevo) {
        int idLiga;

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "bd_ligas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_LIGA + " WHERE " + Utilidades.CAMPO_NOMBRELIGA + "='" + nombreaBuscar + "'", null);
            int cantidad = c.getCount();
            int i = 0;
            int[] arregloId = new int[cantidad];
            String[] arregloNombre = new String[cantidad];

            if (c.moveToFirst()) {
                do {
                    idLiga = c.getInt(0);
                    String nombreLiga = c.getString(1);
                    arregloId[i] = idLiga;
                    arregloNombre[i] = nombreLiga;
                    i++;
                } while (c.moveToNext());
                c.close();
                ContentValues campos=new ContentValues();
                campos.put(Utilidades.CAMPO_ID,idLiga);
                campos.put(Utilidades.CAMPO_NOMBRELIGA,nombreNuevo);
                db.update(Utilidades.TABLA_LIGA,campos,Utilidades.CAMPO_ID+"="+idLiga,null);
            } else {
                Toast.makeText(this.getActivity(), "No hay informacion", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
