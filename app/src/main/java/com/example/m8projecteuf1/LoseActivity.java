package com.example.m8projecteuf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        //todo
        //foto from picasso

    }

    public void onClickPedirCredito() {
        Uri uriUrl = Uri.parse("https://www.cofidis.es/es/creditos-prestamos/prestamo-personal.html#");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void onClickCrearNuevousuario() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
