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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appbasquet.R;
import com.example.appbasquet.entidades.Equipos;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

import java.util.ArrayList;

public class editJugador extends DialogFragment {

    String info;
    String idJugador,nombreJugador,numeroJugador,fechaNacimiento;
    ArrayList<Equipos> ArrayEquipos;
    ArrayList<String> listaEquipos;

    public editJugador(String cadenaInfo){
        this.info=cadenaInfo;
        String[] palabras = info.split("\n");
        idJugador=palabras[0];
        nombreJugador = palabras[1];
        numeroJugador=palabras[2];
        fechaNacimiento=palabras[3];
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.datos_jugador, null);
        builder.setView(dialogView);

        final EditText nomJugador = (EditText) dialogView.findViewById(R.id.txtEJNombreJugador);
        final EditText numJugador = (EditText) dialogView.findViewById(R.id.txtEJNumeroJugador);
        final EditText fechaNJugador = (EditText) dialogView.findViewById(R.id.txtEJFechaNJugador);
        final Spinner spinnerJEquipos = dialogView.findViewById(R.id.SpinnerEJEquipos);

        nomJugador.setText(nombreJugador);
        numJugador.setText(numeroJugador);
        fechaNJugador.setText(fechaNacimiento);

        //Toast.makeText(getActivity(), nombreJugador+"\n"+numeroJugador+"\n"+fechaNacimiento, Toast.LENGTH_SHORT).show();

        // Add action buttons
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                if ("".equals(nomJugador) || "".equals(numJugador) || "".equals(fechaNJugador)) {
                    Toast.makeText(getActivity(), "No puedes dejar vacios los campos", Toast.LENGTH_SHORT).show();
                } else {
                    ////
                    String cadenaEquipo=spinnerJEquipos.getSelectedItem().toString();
                    String[] palabras = cadenaEquipo.split("-");
                    int idEquipo= Integer.parseInt(palabras[0]);
                    editarJugador(nomJugador.getText().toString(),numJugador.getText().toString(),fechaNJugador.getText().toString(),idEquipo);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editJugador.this.getDialog().cancel();
                    }
                });

        obtenerEquipos();
        ArrayAdapter<String> adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,listaEquipos);
        spinnerJEquipos.setAdapter(adapter);

        return builder.create();
    }

    public void obtenerEquipos(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Equipos equipos=null;
        ArrayEquipos=new ArrayList<Equipos>();
        Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_EQUIPO, null);
        while (c.moveToNext())
        {
            equipos=new Equipos();
            equipos.setIdEquipo(c.getInt(0));
            equipos.setNombreEquipo(c.getString(1));
            ArrayEquipos.add(equipos);
        }
        obtenerLista();
    }

    private void obtenerLista() {

        listaEquipos=new ArrayList<String>();
        listaEquipos.add("Seleccione Equipo");
        for (int i=0;i<ArrayEquipos.size();i++){
            listaEquipos.add(ArrayEquipos.get(i).getIdEquipo()+"-"+ArrayEquipos.get(i).getNombreEquipo());
        }
    }

    public void editarJugador(String nuevoNombre,String nuevoNumero, String nuevaFecha,int idEquipo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        ContentValues campos=new ContentValues();
        campos.put(Utilidades.CAMPO_NOMBREJUGADOR,nuevoNombre);
        campos.put(Utilidades.CAMPO_NUMEROJUGADOR,nuevoNumero);
        campos.put(Utilidades.CAMPO_FECHAJUGADOR,nuevaFecha);
        campos.put(Utilidades.CAMPO_IDEQUIPO_JUGADOR,idEquipo);
        db.update(Utilidades.TABLA_JUGADOR,campos,Utilidades.CAMPO_IDJUGADOR+"="+idJugador,null);
        Toast.makeText(this.getActivity(), "Jugador modificado", Toast.LENGTH_SHORT).show();
    }

}
