package com.example.m8projecteuf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        ImageView iv = findViewById(R.id.imageViewBancarrota);
        Glide.with(this).load("https://cdn4.iconfinder.com/data/icons/business-corporate-company/290/entrepreneur-007-512.png").into(iv);
    }

    public void onClickPedirCredito(View v) {
        Uri uriUrl = Uri.parse("https://www.google.com/search?rlz=1C1CHBF_esES867ES867&ei=kbfvXaGVG6mNlwTm0qPACQ&q=pedir+prestamo&oq=pedir+prestamo&gs_l=psy-ab.3..0l10.2599.4008..4169...0.2..0.77.956.15......0....1..gws-wiz.....0..0i71j0i131j0i67j0i131i67.ptpg_OF9K3A&ved=0ahUKEwjhoZzWsKvmAhWpxoUKHWbpCJgQ4dUDCAs&uact=5");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void onClickCrearNuevoUsuario(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
