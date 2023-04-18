package com.example.wheelclicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Juego extends AppCompatActivity {

    private float contador = 0;
    private ImageView imgWheel;
    private TextView tvContador;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imgWheel = findViewById(R.id.imgWheel);
        tvContador = findViewById(R.id.tvContador);


        DecimalFormat decimalFormat = new DecimalFormat("#");



        tvContador.setText(contador + "");

        mp = MediaPlayer.create(this, R.raw.click_sound);

        imgWheel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                contador++;
                tvContador.setText(decimalFormat.format(contador) + "");
                if (mp.isPlaying()) {
                    jugar();
                }
                mp.start();

            }
        });


    }
    private void jugar() {
        mp = MediaPlayer.create(this, R.raw.click_sound);
    }

}