package com.ingoskr.eminen.enelmoment.utilidades;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.mapas.MapaCodigo;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class LeerCodigo extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView scanner;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_codigo);
        scanner = new ZBarScannerView(this);
        setContentView(scanner);
        scanner.setResultHandler(this);
        scanner.startCamera();

    }

    @Override
    public void onResume() {
        super.onResume();
        scanner.setResultHandler(this);
        setContentView(scanner);// Register ourselves as a handler for scan results.
        scanner.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        scanner.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado:");
        builder.setMessage("Ruta localizada...");
        AlertDialog alert1 = builder.create();
        alert1.show();

        if(rawResult.getContents()!="") {
            if (rawResult.getContents().contains("/RU")) {
                startActivity(new Intent(this, MapaCodigo.class).putExtra("url",rawResult.getContents()));

            } else {
                builder.setTitle("No es una Ruta");
                builder.setMessage("No es un Codigo de Ruta");
                AlertDialog alert2 = builder.create();
                alert1.show();
            }
        }
        else {
            builder.setTitle("QR Vacio:");
            builder.setMessage("El codigo esta vacio o esta dañado");
            AlertDialog alert2 = builder.create();
            alert1.show();
        }
        // If you would like to resume scanning, call this method below:
        scanner.resumeCameraPreview(this);
    }
}
