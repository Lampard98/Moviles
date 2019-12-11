package com.example.appbasquet.ui.gallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appbasquet.MainActivity;
import com.example.appbasquet.R;
import com.example.appbasquet.dialogos.addEquipo;
import com.example.appbasquet.dialogos.editEquipo;
import com.example.appbasquet.dialogos.eliminarEquipo;
import com.example.appbasquet.entidades.Equipos;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    ListView listaEquipo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final ListView listViewEquipo = root.findViewById(R.id.listaEquipos);

//*Accion de floatinbutton
        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            mainActivity.showFloatingActionButton(); //fuerza la visibilidad

            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);

            fab.setImageResource(R.drawable.ic_group_add); //Cambiar icono

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new addEquipo();
                    newFragment.show(getFragmentManager(), "Dialog");
                }
            });
        }

        ///Capturar datos cuando presiona
        final ArrayList<Equipos> arrayList=new ArrayList<Equipos>();
        ArrayAdapter<Equipos> adapter=new ArrayAdapter<Equipos>(this.getActivity(),android.R.layout.simple_list_item_1,arrayList);
        listViewEquipo.setAdapter(adapter);
        listViewEquipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreE  = (String) listViewEquipo.getItemAtPosition(position);
                Toast.makeText(getActivity(),nombreE,Toast.LENGTH_SHORT).show();

                DialogFragment newFragment = new editEquipo(nombreE);
                newFragment.show(getFragmentManager(), "Dialogo");
            }
        });

        listViewEquipo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String nom  = (String) listViewEquipo.getItemAtPosition(position);

                Toast.makeText(getActivity(),"Toque largo: "+nom,Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new eliminarEquipo(nom);
                newFragment.show(getFragmentManager(),"Dialog");
                return true;
            }
        });

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        mostrarEquipos();
    }

    private void mostrarEquipos(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        if (db != null)
        {
            Cursor c=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_EQUIPO,null);
            int cantidad=c.getCount();
            int i=0;
            String[] arreglo=new String[cantidad];
            if (c.moveToFirst()){
                do{
                    String datos=c.getString(1);
                    arreglo[i]=datos;
                    i++;
                }while(c.moveToNext());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,arreglo);
            listaEquipo=(ListView) getActivity().findViewById(R.id.listaEquipos);
            listaEquipo.setAdapter(adapter);
        }else{
            Toast.makeText(this.getActivity(),"No hay informacion",Toast.LENGTH_SHORT).show();
        }
    }
}