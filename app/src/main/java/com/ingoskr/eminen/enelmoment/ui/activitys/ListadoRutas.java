package com.ingoskr.eminen.enelmoment.ui.activitys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.adaptadores.RutaAdapter;
import com.ingoskr.eminen.enelmoment.modelo.Rutas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListadoRutas extends AppCompatActivity {

    String or1,des1;
    private RecyclerView recyclerView;
    private RutaAdapter adapatador;
    private ArrayList<Rutas> rutas= new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    TextView m1,m2;

    private static final String URL = "http://108.59.1.32/~oscarito/enelmomento/consultru.php";
 //   private static final String URL = "http://192.168.43.116/enelmomento/consultru.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_lista);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        or1=bundle.getString("origen");
        des1=bundle.getString("destino");
        m1=(TextView)findViewById(R.id.punto1);
        m2=(TextView)findViewById(R.id.punto2);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.listado);
       m1.setText(or1);
        m2.setText(des1);
       envio();
    }

    private void envio() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapatador = new RutaAdapter(this, rutas);

        StringRequest request= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object= new JSONArray(response);
                    for (int i=0;i<object.length();i++){
                        JSONObject object1= (JSONObject) object.get(i);
                        String bus=object1.getString("idBus");
                        String color=object1.getString("color");
                        rutas.add(new Rutas(bus,color));
                    }
                    recyclerView.setAdapter(adapatador);
                    System.out.println("datos "+object+" Cantidad: "+object.length());
                } catch (JSONException e) {
                    System.out.println("error "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error "+error);
            }
        }){
            protected Map<String,String> getParams(){
                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put("origen",or1);
                hashMap.put("destino",des1);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}







