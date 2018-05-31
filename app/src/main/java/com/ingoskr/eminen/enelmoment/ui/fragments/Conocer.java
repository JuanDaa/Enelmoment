package com.ingoskr.eminen.enelmoment.ui.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.ui.activitys.ListadoRutas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Conocer extends Fragment {
    AutoCompleteTextView origen;
    EditText destino;
    Button consultar;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    StringRequest request;
    private Spinner calcRuta;
    private static final String URL = "http://108.59.1.32/~oscarito/enelmomento/consultalista.php";

    public Conocer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_conocer, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_norm);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((Actio)getActivity()).getSupportActionBar().setTitle("Generar");
        origen= (AutoCompleteTextView) view.findViewById(R.id.origen);
        destino=(EditText)view.findViewById(R.id.destino);
        consultar=(Button)view.findViewById(R.id.calculo);
        requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consul();
            }
        });
        consultarDatos();

        return view;
    }
    private void consultarDatos() {
        JsonArrayRequest peticion = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String lugar = object.getString("nombre");
                        Log.i("lugares", String.valueOf(response));

                        final String[] sitios =new String[response.length()];

                        sitios[i] =lugar;

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, sitios);
                        origen.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(peticion);
    }


    private void consul() {


        Intent intento=new Intent(getContext().getApplicationContext(), ListadoRutas.class);
        intento.putExtra("origen",origen.getText().toString());
        intento.putExtra("destino",destino.getText().toString());
        startActivity(intento);

    }
}
