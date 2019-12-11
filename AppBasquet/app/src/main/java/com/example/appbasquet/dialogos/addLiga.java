package com.example.appbasquet.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

public class addLiga extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.add_liga, null);
        builder.setView(dialogView);

        final EditText nombreLiga = (EditText) dialogView.findViewById(R.id.txtnombreLiga);
        // Add action buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                nombreLiga.setError(null);
                String nombre=nombreLiga.getText().toString();
                if ("".equals(nombre)){
                    nombreLiga.setError("Escribe el nombre de la liga");
                    nombreLiga.requestFocus();
                }else{
                    registrarLiga(nombre);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addLiga.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void registrarLiga(String nombre){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        String insert ="INSERT INTO "+ Utilidades.TABLA_LIGA +"("+Utilidades.CAMPO_NOMBRELIGA+")VALUES('"+nombre+"')";

        db.execSQL(insert);
        Toast.makeText(getActivity(), "Liga agregada" +'\n'+ nombre, Toast.LENGTH_SHORT).show();
    }

}
