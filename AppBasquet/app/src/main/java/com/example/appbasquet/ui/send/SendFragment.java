package com.example.appbasquet.ui.send;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appbasquet.MainActivity;
import com.example.appbasquet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            mainActivity.showFloatingActionButton(); //fuerza la visibilidad

            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);

            fab.setImageResource(R.drawable.ic_action_facebook); //Cambiar icono

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //com.facebook .katana
                    //com.google.android.googlequicksearchbox
                    Intent open = getActivity().getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                    startActivity(open);
                }
            });
        }

        return root;
    }
}