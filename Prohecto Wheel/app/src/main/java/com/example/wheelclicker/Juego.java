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
import android.os.Handler;
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

    private double contador = 0, aumentador = 1;
    private ImageView imgWheel;
    private TextView tvContador, tvContadorMejoras;
    private MediaPlayer mp;
    private BottomNavigationView nav;
    private LinearLayout ven_Wheel, ven_Mejora, ven_Ajuste;
    private String venActual;
    private Button btnAux;
    private DecimalFormat decimalFormat;

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
        tvContadorMejoras = findViewById(R.id.tvContadorMejoras);
        nav = findViewById(R.id.nav);

        ven_Wheel = findViewById(R.id.ven_Wheel);
        ven_Mejora = findViewById(R.id.ven_Mejora);
        ven_Ajuste = findViewById(R.id.ven_Ajuste);

        venActual = "wheel";

        //Formato contador
        decimalFormat = new DecimalFormat("0.00");
        actualizadorContador();

        //Declarar sonido
        mp = MediaPlayer.create(this, R.raw.click_sound);


        //Menu
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rueda:
                        animacion("wheel");
                        break;

                    case R.id.mejoras:
                        animacion("mejoras");
                        break;

                    case R.id.ajustes:
                        animacion("ajustes");
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
                contador+= aumentador;
                actualizadorContador();
                if (mp.isPlaying()) {
                    sonidoClick();
                }
                mp.start();
            }
        });


    }

    //Animacion cambio de ventanas
    private void animacion(String mostrar){

        //WHEEL
        if (mostrar.equals("wheel") && !venActual.equals(mostrar)){
            if(venActual.equals("ajustes")){
                invis_desplazamiento_derecha(ven_Ajuste);
                vis_desplazamiento_derecha(ven_Wheel);
            }else {
                invis_desplazamiento_derecha(ven_Mejora);
                vis_desplazamiento_derecha(ven_Wheel);
            }
            venActual = mostrar;
            return;
        }

        //MEJORAS
        if (mostrar.equals("mejoras") && !venActual.equals(mostrar)){
            if (venActual.equals("wheel")){
                invis_desplazamiento_izquierda(ven_Wheel);
                vis_desplazamiento_izquierda(ven_Mejora);
            }else {
                invis_desplazamiento_derecha(ven_Ajuste);
                vis_desplazamiento_derecha(ven_Mejora);
            }
            venActual = mostrar;
            return;
        }

        //AJUSTES
        if (mostrar.equals("ajustes") && !venActual.equals(mostrar)){
            if(venActual.equals("wheel")){
                invis_desplazamiento_izquierda(ven_Wheel);
                vis_desplazamiento_izquierda(ven_Ajuste);
            }else {
                invis_desplazamiento_izquierda(ven_Mejora);
                vis_desplazamiento_izquierda(ven_Ajuste);
            }
            venActual = mostrar;
            return;
        }
    }

    private void invis_desplazamiento_derecha(LinearLayout ventana){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ventana.setVisibility(View.GONE);


                AnimationSet set = new AnimationSet(true);
                Animation animation = null;

                animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                set.addAnimation(animation);

                animation.setDuration(100);

                LayoutAnimationController controller = new LayoutAnimationController(set, 0.10f);
                ventana.setLayoutAnimation(controller);
                ventana.startAnimation(animation);
                animation.cancel();
            }
        },0);
    }

    private void vis_desplazamiento_derecha(LinearLayout ventana){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ventana.setVisibility(View.VISIBLE);


                AnimationSet set = new AnimationSet(true);
                Animation animation = null;

                animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                set.addAnimation(animation);

                animation.setDuration(100);
                LayoutAnimationController controller = new LayoutAnimationController(set, 0.10f);
                ventana.setLayoutAnimation(controller);
                ventana.startAnimation(animation);
                animation.cancel();
            }
        },0);
    }

    private void invis_desplazamiento_izquierda(LinearLayout ventana){
        ventana.setVisibility(View.GONE);

        AnimationSet set = new AnimationSet(true);
        Animation animation = null;

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        set.addAnimation(animation);

        animation.setDuration(100);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.10f);
        ventana.setLayoutAnimation(controller);
        ventana.startAnimation(animation);
    }

    private void vis_desplazamiento_izquierda(LinearLayout ventana){
        ventana.setVisibility(View.VISIBLE);

        AnimationSet set = new AnimationSet(true);
        Animation animation = null;

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        set.addAnimation(animation);
        animation.setDuration(100);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.10f);
        ventana.setLayoutAnimation(controller);
        ventana.startAnimation(animation);
    }

    //Sonido Click
    private void sonidoClick() {
        mp = MediaPlayer.create(this, R.raw.click_sound);
    }



    public void btn_F1_clickado (View view){
//        Toast.makeText(Juego.this, "pepepepe", Toast.LENGTH_LONG).show();
        btnAux = findViewById(R.id.btnF1);
        comprar("f1", btnAux.getTag().toString());
    }

    private void comprar(String nomMejora, String tag){
        if (tag.equals("Comprar")){
            switch (nomMejora){
                case "f1":
                    double precioCompra = Double.parseDouble(btnAux.getText().toString().substring(0, btnAux.getText().toString().length()-1));
                    if (contador>=precioCompra) {
                        contador -= precioCompra;
                        actualizadorContador();
                        btnAux.setText(decimalFormat.format(preciosMejoras(precioCompra))+"€");
//                        btnAux.setText(String.format("%.2f €", preciosMejoras(precioCompra)));
                        btnAux.setTag("1");
                    }
                    break;

                case "f2":
                    break;
            }
        }else{
            switch (nomMejora){
                case "f1":
                    double precioMejora = Double.parseDouble(btnAux.getText().toString().substring(0, btnAux.getText().toString().length()-1).replace(',','.'));
                    if (contador>=precioMejora) {
                        contador -= precioMejora;
                        actualizadorContador();
                        btnAux.setText(decimalFormat.format(preciosMejoras(precioMejora))+"€");
                        btnAux.setTag((Integer.parseInt(tag)+1)+"");
//                        Toast.makeText(Juego.this, "Tag actual: " + btnAux.getTag(), Toast.LENGTH_LONG).show();
                    }
                    break;

                case "f2":
                    break;
            }
        }

    }
    private void actualizadorContador (){
        //Actualizar los tv Contadores
        tvContador.setText(decimalFormat.format(contador) + "€");
        tvContadorMejoras.setText(decimalFormat.format(contador) + "€");
    }

    private double preciosMejoras (double precioActual){
        double precioNuevo = precioActual + (precioActual/10);
        Toast.makeText(Juego.this, precioNuevo+" 1", Toast.LENGTH_LONG).show();
        return precioNuevo;
    }
}