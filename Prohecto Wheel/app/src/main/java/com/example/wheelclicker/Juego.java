package com.example.wheelclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

public class Juego extends AppCompatActivity {

    private float contador = 0;
    private ImageView imgWheel;
    private TextView tvContador;
    private MediaPlayer mp;
    private BottomNavigationView nav;
    private LinearLayout ven_Wheel;
    private String venActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Rotacion desabilitada
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imgWheel = findViewById(R.id.imgWheel);
        tvContador = findViewById(R.id.tvContador);
        nav = findViewById(R.id.nav);
        ven_Wheel = findViewById(R.id.ven_Wheel);

        venActual = "Ventana Wheel";

        //Formato contador
        DecimalFormat decimalFormat = new DecimalFormat("#");

        tvContador.setText(contador + "");

        //Declarar sonido
        mp = MediaPlayer.create(this, R.raw.click_sound);


        //Menu
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rueda:
                        Toast.makeText(Juego.this, "Wheel", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.mejoras:
                        Toast.makeText(Juego.this, "Mejoras", Toast.LENGTH_LONG).show();
                        animacion("desaparecer");
                        break;

                    case R.id.ajustes:
                        Toast.makeText(Juego.this, "Ajustes", Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
                return true;
            }
        });

        //Click de la rueda
        imgWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador++;
                tvContador.setText(decimalFormat.format(contador) + "");
                if (mp.isPlaying()) {
                    sonidoClick();
                }
                mp.start();
            }
        });
    }

    //Animacion cambio de ventanas
    private void animacion(String mostrar){

        ven_Wheel.setVisibility(View.GONE);

        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar.equals("aparecer")){
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);}

        if (mostrar.equals("desaparecer")){
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.1f,
                    Animation.RELATIVE_TO_SELF, 0.1f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);}

        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.20f);
        ven_Wheel.setLayoutAnimation(controller);
        ven_Wheel.startAnimation(animation);

    }

    //Sonido Click
    private void sonidoClick() {
        mp = MediaPlayer.create(this, R.raw.click_sound);
    }

}