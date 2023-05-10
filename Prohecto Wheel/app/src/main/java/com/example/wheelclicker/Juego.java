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
import android.view.Window;
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

    private double contador = 0, aumentador = 1, multiplicador = 1;
    private ImageView imgWheel;
    private TextView tvContador, tvContadorMejoras;
    private MediaPlayer mp;
    private BottomNavigationView nav;
    private LinearLayout ven_Wheel, ven_Mejora, ven_Ajuste;
    private String venActual;
    private Button btnAux;
    private DecimalFormat decimalFormat, enteroFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Ventana completa
        ocultarUI();

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
        enteroFormat = new DecimalFormat("0");
        actualizadorContador();

        //Declarar sonido del click de la rueda
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
                contador= contador + aumentador*multiplicador;
                actualizadorContador();
                if (mp.isPlaying()) {
                    sonidoClick();
                }
                mp.start();
            }
        });
    }

    //Ocultar barra de abajo y barra de notificaciones
    private void ocultarUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                );
    }

    //Animacion cambio de ventanas
    private void animacion(String ventana_a_mostrar){

        //WHEEL
        if (ventana_a_mostrar.equals("wheel") && !venActual.equals(ventana_a_mostrar)){
            if(venActual.equals("ajustes")){
                invis_desplazamiento_derecha(ven_Ajuste);
                vis_desplazamiento_derecha(ven_Wheel);
            }else {
                invis_desplazamiento_derecha(ven_Mejora);
                vis_desplazamiento_derecha(ven_Wheel);
            }
            venActual = ventana_a_mostrar;
            return;
        }

        //MEJORAS
        if (ventana_a_mostrar.equals("mejoras") && !venActual.equals(ventana_a_mostrar)){
            if (venActual.equals("wheel")){
                invis_desplazamiento_izquierda(ven_Wheel);
                vis_desplazamiento_izquierda(ven_Mejora);
            }else {
                invis_desplazamiento_derecha(ven_Ajuste);
                vis_desplazamiento_derecha(ven_Mejora);
            }
            venActual = ventana_a_mostrar;
            return;
        }

        //AJUSTES
        if (ventana_a_mostrar.equals("ajustes") && !venActual.equals(ventana_a_mostrar)){
            if(venActual.equals("wheel")){
                invis_desplazamiento_izquierda(ven_Wheel);
                vis_desplazamiento_izquierda(ven_Ajuste);
            }else {
                invis_desplazamiento_izquierda(ven_Mejora);
                vis_desplazamiento_izquierda(ven_Ajuste);
            }
            venActual = ventana_a_mostrar;
            return;
        }
    }

    //Animacion de desaparecer ventana desplazamiento derecha
    private void invis_desplazamiento_derecha(LinearLayout ventana){
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

    //Animacion de aparecer ventana desplazamiento derecha
    private void vis_desplazamiento_derecha(LinearLayout ventana){
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

    //Animacion de desaparecer ventana desplazamiento izquierda
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

    //Animacion de aparecer ventana desplazamiento izquierda
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

    //Resetear sonido Click rueda
    private void sonidoClick() {
        mp = MediaPlayer.create(this, R.raw.click_sound);
    }

    //Boton de compra o mejora de Concesionario
    public void btn_Concesionario_clickado (View view){
        btnAux = findViewById(R.id.btnConcesionario);
        //Comprueba si tiene dinero suficiente
        if(comprar(btnAux.getTag().toString())){
            //Efecto compra/mejora
            aumentador += 1;
        }
    }

    //Boton de compra o mejora de F1
    public void btn_F1_clickado (View view){
//        Toast.makeText(Juego.this, "pepepepe", Toast.LENGTH_LONG).show();
        btnAux = findViewById(R.id.btnF1);
        //Comprueba si tiene dinero suficiente
        if(comprar(btnAux.getTag().toString())){
            //Efecto compra/mejora
            aumentador *= 2;
        }
    }

    private Boolean comprar(String tag){
        double precioCompra_o_Mejora = Double.parseDouble(btnAux.getText().toString().substring(0, btnAux.getText().toString().length()-1).replace(',','.'));

        //Si nunca se a comprado, su tag es "Compra"
        //Si se a comprado, su tag es el numero de veces que se a mejorado más la compra(1)
        if (tag.equals("Comprar")){
            if (contador>=precioCompra_o_Mejora) {
                contador -= precioCompra_o_Mejora;
                actualizadorContador();
                btnAux.setTag("1");
                btnAux.setText(decimalFormat.format(preciosMejoras(precioCompra_o_Mejora)) + "€");
                return true;
            }
        }else{
            if (contador>=precioCompra_o_Mejora) {
                contador -= precioCompra_o_Mejora;
                actualizadorContador();
                btnAux.setText(decimalFormat.format(preciosMejoras(precioCompra_o_Mejora))+"€");
                btnAux.setTag((Integer.parseInt(tag)+1)+"");
//                Toast.makeText(Juego.this, "Tag actual: " + btnAux.getTag(), Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    //Actualizar los tv Contador
    private void actualizadorContador (){
        //Actualizar los tv Contadores
        //Si es mayor a 100 se quitan los decimales
        if (contador>100){
            tvContador.setText(enteroFormat.format(contador) + "€");
            tvContadorMejoras.setText(enteroFormat.format(contador) + "€");
        }else if (contador>999999999){
            String aux=contador+"";
            tvContador.setText(aux.substring(0,aux.length()-6)+ "M€");

        }else{
            tvContador.setText(decimalFormat.format(contador) + "€");
            tvContadorMejoras.setText(decimalFormat.format(contador) + "€");
        }

    }

    //Subidas de precio
    private double preciosMejoras (double precioActual){
        //Suma de un 10%
        double precioNuevo = precioActual + (precioActual/10);
//        Toast.makeText(Juego.this, precioNuevo+" 1", Toast.LENGTH_LONG).show();
        return precioNuevo;
    }
}