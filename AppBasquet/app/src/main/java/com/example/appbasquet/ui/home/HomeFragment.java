package com.example.appbasquet.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.appbasquet.MainActivity;
import com.example.appbasquet.R;
import com.example.appbasquet.dialogos.addLiga;
import com.example.appbasquet.dialogos.editLiga;
import com.example.appbasquet.dialogos.eliminarLiga;
import com.example.appbasquet.entidades.Ligas;
import com.example.appbasquet.utilidades.ConexionSQLiteHelper;
import com.example.appbasquet.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ListView lista;
    List<Ligas> ligasList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listViewLigas = root.findViewById(R.id.listaLigas);
//Cambiar la accion del floating Button
        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            mainActivity.showFloatingActionButton(); //fuerza la visibilidad

            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);

            fab.setImageResource(R.drawable.ic_action_add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new addLiga();
                    newFragment.show(getFragmentManager(), "Dialog");
                }
            });
        }
//seleccionar item del listview

        final ArrayList<Ligas> arrayList=new ArrayList<Ligas>();
        ArrayAdapter<Ligas> adapter=new ArrayAdapter<Ligas>(this.getActivity(),android.R.layout.simple_list_item_1,arrayList);
        listViewLigas.setAdapter(adapter);
        listViewLigas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre  = (String) listViewLigas.getItemAtPosition(position);
                Toast.makeText(getActivity(),nombre,Toast.LENGTH_SHORT).show();

                DialogFragment newFragment = new editLiga(nombre);
                newFragment.show(getFragmentManager(), "Dialog");
                //Intent open = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                //open2.putExtra(nombre,position);
                //startActivity(open);
            }
        });

        listViewLigas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String nom  = (String) listViewLigas.getItemAtPosition(position);

                Toast.makeText(getActivity(),"Toque largo: "+nom,Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new eliminarLiga(nom);
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
        mostrarLigas();
    }

    private void mostrarLigas(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(),"bd_ligas",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        if (db != null)
        {
            Cursor c=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_LIGA,null);
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
            lista=(ListView) getActivity().findViewById(R.id.listaLigas);
            lista.setAdapter(adapter);
        }else{
            Toast.makeText(this.getActivity(),"No hay informacion",Toast.LENGTH_SHORT).show();
        }
    }

}