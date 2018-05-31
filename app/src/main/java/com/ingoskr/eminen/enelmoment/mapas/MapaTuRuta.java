package com.ingoskr.eminen.enelmoment.mapas;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.modelo.Rutas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MapaTuRuta extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {




    private GoogleMap mMap;
    RequestQueue requestQueue;
    private GoogleApiClient googleApiClient;
    private String calle,numCalle1,numCalle2, lugarSpi, tipoSpi;
    private  double lat, longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        Bundle bundle=getIntent().getExtras();

        try {
             calle=bundle.getString("calle");
             numCalle1=bundle.getString("numCalle1");
             numCalle2=bundle.getString("numCalle2");
             lugarSpi=bundle.getString("lugarSpi");
             tipoSpi=bundle.getString("tipoSpi");
             lat=bundle.getDouble("lat");
             longi=bundle.getDouble("longi");

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("no dio", String.valueOf(e));
        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        getDirection();



    }



    private void getDirection() {

        String url = makeURL(lat, longi,tipoSpi,calle,numCalle1 , numCalle2,lugarSpi);
//                    final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Getting Route", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // loading.dismiss();
                        //Calling the method drawPath to draw the path
                        drawPath(response);
                        Log.i("json", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  loading.dismiss();
                    }
                });

        //Adding the request to request queue
        requestQueue.add(stringRequest);
    }

    private void drawPath(String response) {
        //Getting both the coordinates
        LatLng from = new LatLng(lat,longi);

        //Calculating the distance in meters
        // Double distance = SphericalUtil.computeDistanceBetween(from, to);

        //Displaying the distance
         // Toast.makeText(this,String.valueOf(distance+" Meters"),Toast.LENGTH_SHORT).show();


        try {
            //Parsing json
            final JSONObject json = new JSONObject(response);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

            JSONObject endLocation = routes.getJSONObject("end_location");
            double endLat = endLocation.getDouble("lat");
            double endLng = endLocation.getDouble("lng");
            LatLng to = new LatLng(6.300497,-75.559297);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(10)
                    .color(getResources().getColor(R.color.colorPry))
                    .geodesic(true)
            );
            mMap.addMarker(new MarkerOptions().position(from).title("Estas aquí "));
            mMap.addMarker(new MarkerOptions().position(to).title(tipoSpi+" "+calle+" "+" # "+ numCalle1 +" - "+ numCalle2 +", "+lugarSpi+" Antioquia"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(from));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,longi), 14));

        }
        catch (JSONException e) {

        }

    }

    private String makeURL(double lat, double longi, String tipoSpi, String calle, String numCalle1, String numCalle2, String lugarSpi) {

        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(String.valueOf(lat));
        urlString.append(",");
        urlString.append(String.valueOf(longi));
        urlString.append("&destination=");// to
        urlString.append(tipoSpi+calle+numCalle1+"-"+numCalle2+","+lugarSpi+","+"Antioquia");
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyAfT1n8b9pIixAQ4101YQV7f-rMHe02aAg");
        return urlString.toString();

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
