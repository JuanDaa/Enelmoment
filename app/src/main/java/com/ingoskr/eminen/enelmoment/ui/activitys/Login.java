package com.ingoskr.eminen.enelmoment.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.dialogs.LoginDialog;
import com.ingoskr.eminen.enelmoment.dialogs.RegistroDialog;


public class Login extends AppCompatActivity {

    Button showIngresar,showRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LayoutInflater inflater = Login.this.getLayoutInflater();

        showIngresar=(Button)findViewById(R.id.show_ingresar);
        showIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginDialog().show(getSupportFragmentManager(), "LoginDialog");
                

            }
        });


        showRegistrarse=(Button)findViewById(R.id.show_registrarse);
        showRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegistroDialog().show(getSupportFragmentManager(), "RegisterDialog");



            }
        });
    }


}
