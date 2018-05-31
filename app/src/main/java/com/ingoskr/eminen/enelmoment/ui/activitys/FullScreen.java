package com.ingoskr.eminen.enelmoment.ui.activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.utilidades.PrefManager;

public class FullScreen extends AppCompatActivity {

    private TextView tiutuloFull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        tiutuloFull = (TextView) findViewById(R.id.titulofull);
        tiutuloFull.setText("En el Momento");
        final PrefManager prefManager = new PrefManager(FullScreen.this);
        //Log.i("pref", String.valueOf(prefManager.seMostraraIntro()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.seMostraraIntro()==true && prefManager.seMostraraSesion()==true) {
                    startActivity(new Intent(FullScreen.this, Introduccion.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

                }else if (prefManager.seMostraraIntro()==false && prefManager.seMostraraSesion()==true) {
                    startActivity(new Intent(FullScreen.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                else if (prefManager.seMostraraIntro()== false && prefManager.seMostraraSesion()== false) {
                    startActivity(new Intent(FullScreen.this, MenuPrincipal.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }



            }
        }, 3000);
    }
}
