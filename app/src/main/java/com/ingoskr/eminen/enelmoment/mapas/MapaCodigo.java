package com.ingoskr.eminen.enelmoment.mapas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ingoskr.eminen.enelmoment.R;

public class MapaCodigo extends AppCompatActivity {
    private WebView webView;
    private static final String URL_RUTA = "http://108.59.1.32/~oscarito/enelmomento/mapamedellin/#line";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_codigo);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        Bundle bundle = getIntent().getExtras();
        
        webView.loadUrl(URL_RUTA+bundle.getString("url"));
        webView.setWebViewClient(new WebViewClient() {
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {

                                         return false;
                                     }
                                 }
        );
    }
}

