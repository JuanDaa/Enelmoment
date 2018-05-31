package com.ingoskr.eminen.enelmoment.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.ui.fragments.Conocer;
import com.ingoskr.eminen.enelmoment.ui.fragments.Generar;
import com.ingoskr.eminen.enelmoment.ui.fragments.Inicio;
import com.ingoskr.eminen.enelmoment.ui.fragments.RutaQr;
import com.ingoskr.eminen.enelmoment.utilidades.PrefManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_principal);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContenedor, new Inicio()).commit();
        PrefManager prefManager = new PrefManager(this);
        prefManager.mostrarSesion(false);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.itemInicio) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragContenedor, new Inicio()).commit();
                } else if (tabId == R.id.itemConocer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragContenedor, new Conocer()).commit();
                } else if (tabId == R.id.itemGenerar) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragContenedor, new Generar()).commit();
                } else if (tabId == R.id.itemQr) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragContenedor, new RutaQr()).commit();
                }
            }
        });

    }
}
