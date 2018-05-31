package com.ingoskr.eminen.enelmoment.ui.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.mapas.MapaTuRuta;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Generar extends Fragment {
    private EditText editCalle, editNum1, editNum2;
    private Button consultar1, obtenerPosicion;
    private TextView direccion;
    private Spinner spinnerTipo, spinnerLugar;
    private  double lt,lng;
    public Generar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generar, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_norm);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Conocer");
        editCalle = (EditText) view.findViewById(R.id.editCalle);
        editNum1 = (EditText) view.findViewById(R.id.editNum1);
        editNum2 = (EditText) view.findViewById(R.id.editNum2);

        direccion = (TextView) view.findViewById(R.id.txtDireccion);

        spinnerTipo = (Spinner) view.findViewById(R.id.spinnerTipo);
        adapterTipo();
        spinnerLugar = (Spinner) view.findViewById(R.id.spinnerLugar);
        adapterLugar();

        obtenerPosicion = (Button) view.findViewById(R.id.obtenerPosicion);
        obtenerPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Cargando");
                progressDialog.setMessage("Obteniendo Posición...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressDialog.dismiss();
                            lt = 6.301433;
                            lng = -75.568822;
                            setLocation(lt,lng);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Por favor intentelo de nuevo", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },3000);
            }
        });


                consultar1 = (Button) view.findViewById(R.id.calculoge);
        consultar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consula(view);
            }
        });
        return view;
    }

    public void adapterLugar() {
        String[] LugarSpi = {"Medellín", "Bello", "Itagüi", "Envigado", "Copacabana"};
        ArrayAdapter adapter = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, LugarSpi);
        spinnerLugar.setAdapter(adapter);
    }

    public void adapterTipo() {
        String[] tipoSpi = {"Calle", "Carrera", "Transversal", "Circular"};
        ArrayAdapter adapter = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, tipoSpi);
        spinnerTipo.setAdapter(adapter);
    }
    public void setLocation(double lat, double longi) {

        //Obtener la direcci—n de la calle a partir de la latitud y la longitud
        if (lat != 0.0 && longi != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(lat, longi, 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    direccion.setVisibility(View.VISIBLE);
                    direccion.setText("Tu Posición es: \n" + address.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void consula(View view) {
        if (lt == 0.0 && lng == 0.0) {
            Snackbar.make(view, "No puedes continuar hasta que no se obtenga tu posición", Snackbar.LENGTH_LONG).show();
        } else {

            if (editCalle.getText().toString().equals("") && editNum1.getText().toString().equals("") && editNum2.getText().toString().equals("")) {
                Snackbar.make(view, "Debes escribir una dirección", Snackbar.LENGTH_LONG).show();
            } else if (editCalle.getText().toString().equals("") || editNum1.getText().toString().equals("") || editNum2.getText().toString().equals("")) {
                Snackbar.make(view, "Todos los campos son requeridos", Snackbar.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext().getApplicationContext(), MapaTuRuta.class);
                if (spinnerTipo.getSelectedItem().toString().equals("Calle")) {
                    intent.putExtra("tipoSpi", "Cl.");
                } else if (spinnerTipo.getSelectedItem().toString().equals("Carrera")) {
                    intent.putExtra("tipoSpi", "Cra.");
                } else if (spinnerTipo.getSelectedItem().toString().equals("Transversal")) {
                    intent.putExtra("tipoSpi", "Tv.");
                } else if (spinnerTipo.getSelectedItem().toString().equals("Circular")) {
                    intent.putExtra("tipoSpi", "Cq.");
                }
                intent.putExtra("lugarSpi", spinnerTipo.getSelectedItem().toString());
                intent.putExtra("lat", lt);
                intent.putExtra("longi", lng);
                intent.putExtra("calle", editCalle.getText().toString().trim());
                intent.putExtra("numCalle1", editNum1.getText().toString().trim());
                intent.putExtra("numCalle2", editNum2.getText().toString().trim());
                startActivity(intent);
            }
        }


    }
}




