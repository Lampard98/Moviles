package com.example.appbasquet.ui.slideshow;

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
import com.example.appbasquet.dialogos.addJugador;
import com.example.appbasquet.dialogos.editJugador;
import com.example.appbasquet.dialogos.eliminarJugador;
import com.example.appbasquet.entidades.Jugadores;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    ListView listaJugadores;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ListView listView = root.findViewById(R.id.ListViewJugadores);

        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            mainActivity.showFloatingActionButton(); //fuerza la visibilidad

            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);

            fab.setImageResource(R.drawable.ic_person_add); //Cambiar icono

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new addJugador();
                    newFragment.show(getFragmentManager(), "Dialog");
                }
            });
        }

        ///Capturar datos cuando presiona
        final ArrayList<Jugadores> arrayList=new ArrayList<Jugadores>();
        ArrayAdapter<Jugadores> adapter=new ArrayAdapter<Jugadores>(this.getActivity(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cadenaInfoJugador  = (String) listView.getItemAtPosition(position);
                DialogFragment newFragment=new editJugador(cadenaInfoJugador);
                newFragment.show(getFragmentManager(),"Dialog");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String cadenaInfoJugador  = (String) listView.getItemAtPosition(position);
                DialogFragment newFragment=new eliminarJugador(cadenaInfoJugador);
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
        mostrarJugadores();
    }

    private void mostrarJugadores(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        if (db != null)
        {
            Cursor c=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_JUGADOR,null);
            int cantidad=c.getCount();
            int i=0;
            String[] arreglo=new String[cantidad];
            if (c.moveToFirst()){
                do{
                    String datos=c.getInt(0)+
                            "\n"+c.getString(1)+
                            "\n"+c.getString(2)+
                            "\n"+c.getString(3);
                    arreglo[i]=datos;
                    i++;
                }while(c.moveToNext());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,arreglo);
            listaJugadores=(ListView) getActivity().findViewById(R.id.ListViewJugadores);
            listaJugadores.setAdapter(adapter);
        }else{
            Toast.makeText(this.getActivity(),"No hay informacion",Toast.LENGTH_SHORT).show();
        }
    }
}