package com.example.appbasquet.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.DynamicsProcessing;
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
import com.example.appbasquet.entidades.Ligas;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;

import java.util.ArrayList;

public class addJugador extends DialogFragment {

    ArrayList<Equipos> ArrayEquipos;
    ArrayList<String> listaEquipos;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.add_jugador, null);
        builder.setView(dialogView);

        final EditText nomJugador = (EditText) dialogView.findViewById(R.id.txtJNombreJugador);
        final EditText numJugador = (EditText) dialogView.findViewById(R.id.txtJNumeroJugador);
        final EditText fechaNJugador = (EditText) dialogView.findViewById(R.id.txtJFechaNJugador);
        final Spinner spinnerJEquipos = dialogView.findViewById(R.id.SpinnerJEquipos);

        // Add action buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                String nombreJugador=nomJugador.getText().toString();
                String numeroJugador=numJugador.getText().toString();
                String fechaNacimiento=fechaNJugador.getText().toString();

                if ("".equals(nombreJugador) || "".equals(numeroJugador) || "".equals(fechaNacimiento)){
                    Toast.makeText(getActivity(),"No dejes campos vacios",Toast.LENGTH_LONG).show();
                }else{
                    String cadenaEquipo=spinnerJEquipos.getSelectedItem().toString();
                    String[] palabras = cadenaEquipo.split("-");
                    int idEquipo= Integer.parseInt(palabras[0]);
                    registrarJugador(nombreJugador,numeroJugador,fechaNacimiento,idEquipo);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addJugador.this.getDialog().cancel();
                    }
                });

        //Lenar Spinner
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

    private void registrarJugador(String nombre,String numero,String fecha ,int idEquipo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        String insert ="INSERT INTO "+ Utilidades.TABLA_JUGADOR +"("+Utilidades.CAMPO_NOMBREJUGADOR+","+Utilidades.CAMPO_NUMEROJUGADOR+","+
                Utilidades.CAMPO_FECHAJUGADOR+","+Utilidades.CAMPO_IDEQUIPO_JUGADOR+")" +
                "VALUES('"+nombre+"','"+numero+"','"+fecha+"',"+idEquipo+")";

        db.execSQL(insert);
        Toast.makeText(getActivity(), "Jugador agregado" +'\n'+ nombre+"\n"+numero+"\n"+fecha+"\n IdEquipo: "+idEquipo, Toast.LENGTH_SHORT).show();
    }
}
