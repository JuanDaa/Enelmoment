package com.ingoskr.eminen.enelmoment.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.utilidades.LeerCodigo;

/**
 * A simple {@link Fragment} subclass.
 */
public class RutaQr extends Fragment {

    private Button cerrarSesion;
    private Button button;
    public RutaQr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_qr, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_norm);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inicio");
        button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), LeerCodigo.class));
            }
        });

        return view;
    }

}
