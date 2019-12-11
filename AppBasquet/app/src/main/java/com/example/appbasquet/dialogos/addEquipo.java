package com.example.appbasquet.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appbasquet.R;
import com.example.appbasquet.entidades.Ligas;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addEquipo extends DialogFragment {

    ArrayList<String> listaLigas;
    ArrayList<Ligas> ligas;
    String nombreDeLiga;
    long idDeLiga;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.add_equipo, null);
        builder.setView(dialogView);

        final EditText nombreEquipo = (EditText) dialogView.findViewById(R.id.txtnombreEquipo);
        final Spinner combo=dialogView.findViewById(R.id.spinnerLigas);

        // Add action buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                String nombre=nombreEquipo.getText().toString();
                if ("".equals(nombre)){
                    Toast.makeText(getActivity(),"Escribe un nombre",Toast.LENGTH_LONG).show();
                }else{
                    nombreDeLiga=combo.getSelectedItem().toString();
                    String[] palabras = nombreDeLiga.split("-");
                    idDeLiga= Long.parseLong(palabras[0]);
                    registrarEquipo(nombre, (int) idDeLiga);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addEquipo.this.getDialog().cancel();
                    }
                });
        //Lenar Spinner
        obtenerLigas();
        ArrayAdapter<String> adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,listaLigas);
        combo.setAdapter(adapter);
        return builder.create();
    }

    private void registrarEquipo(String nombre,int idLiga){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        String insert ="INSERT INTO "+ Utilidades.TABLA_EQUIPO +"("+Utilidades.CAMPO_NOMBREQUIPO+","+Utilidades.CAMPO_IDLIGA_EQUIPO+")VALUES('"+nombre+"',"+idLiga+")";

        db.execSQL(insert);
        Toast.makeText(getActivity(), "Equipo agregado" +'\n'+ nombre+"\n IdLiga: "+idLiga, Toast.LENGTH_SHORT).show();
    }

    private void obtenerLigas(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Ligas liga=null;
        ligas=new ArrayList<Ligas>();
        Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_LIGA, null);
        while (c.moveToNext())
        {
            liga=new Ligas();
            liga.setIdLigas(c.getInt(0));
            liga.setNombreLiga(c.getString(1));
            ligas.add(liga);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaLigas=new ArrayList<String>();
        listaLigas.add("Seleccione liga");
        for (int i=0;i<ligas.size();i++){
            listaLigas.add(ligas.get(i).getIdLigas()+"-"+ligas.get(i).getNombreLiga());
        }
    }
}
