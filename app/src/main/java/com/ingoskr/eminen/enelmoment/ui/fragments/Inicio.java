package com.ingoskr.eminen.enelmoment.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.ui.activitys.Login;
import com.ingoskr.eminen.enelmoment.utilidades.LeerCodigo;
import com.ingoskr.eminen.enelmoment.utilidades.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inicio extends Fragment  {
    private Button button;
    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        return view;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_sesion, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar:
                PrefManager manager = new PrefManager(getContext().getApplicationContext());
                manager.mostrarSesion(true);
                startActivity(new Intent(getContext().getApplicationContext(), Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            break;
        }
        return true;
    }

}


