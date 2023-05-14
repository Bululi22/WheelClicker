package com.example.wheelclicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

public class Juego extends AppCompatActivity {

    private double contador = 0, aumentador = 1, multiplicador = 1;
    private ImageView imgWheel;
    private TextView tvContador, tvContadorMejoras, tvCuentaAtras;
    private MediaPlayer mp;
    private BottomNavigationView nav;
    private LinearLayout ven_Wheel, ven_Mejora, ven_Ajuste;
    private String venActual;
    private Button btnAux;
    private DecimalFormat decimalFormat, enteroFormat;
    private SharedPreferences shared;

    private RelativeLayout tarjetaPor2;

    private Boolean bonificacion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Ventana completa (Ocultar barra de abajo y barra de notificaciones)
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        //Rotacion desabilitada
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        imgWheel = findViewById(R.id.imgWheel);
        tvContador = findViewById(R.id.tvContador);
        tvContadorMejoras = findViewById(R.id.tvContadorMejoras);
        tarjetaPor2 = findViewById(R.id.tarjetaPor2);
        tvCuentaAtras = findViewById(R.id.tvCuentaAtras);
        nav = findViewById(R.id.nav);

        ven_Wheel = findViewById(R.id.ven_Wheel);
        ven_Mejora = findViewById(R.id.ven_Mejora);
        ven_Ajuste = findViewById(R.id.ven_Ajuste);

        venActual = "wheel";

        shared = getSharedPreferences("datos", Context.MODE_PRIVATE);

        //Formato contador
        decimalFormat = new DecimalFormat("0.00");
        enteroFormat = new DecimalFormat("0");

        //Recoger datos de BBDD
        recogerDatosBBDD();


        //Actualizar contador
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
                //Si el sonido ya esta sonando, lo para y lo inicia de nuevo
                if (mp.isPlaying()) {
                    sonidoClick();
                }
                mp.start();

                //Suma el precio del click y lo actualiza
                if (bonificacion){
                    //Si esta la bonificacion x2 ACTIVADA
                    contador= contador + aumentador*multiplicador*2;
                }else{
                    //Si esta la bonificacion x2 DESACTIVADA
                    contador= contador + aumentador*multiplicador;
                }

                actualizadorContador();
                actualizarBBDDContador();
            }
        });
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
        if(comprar(btnAux.getTag().toString(),"concesionario")){
            //Efecto compra-mejora
            aumentador += 1;
        }
    }

    //Boton de compra o mejora de F1
    public void btn_F1_clickado (View view){
        btnAux = findViewById(R.id.btnF1);
        //Comprueba si tiene dinero suficiente
        if(comprar(btnAux.getTag().toString(), "f1")){
            //Efecto compra-mejora
            aumentador *= 2;
        }
    }

    private Boolean comprar(String tag, String nomMejora){
        float precioCompra_o_Mejora = Float.parseFloat(btnAux.getText().toString().substring(0, btnAux.getText().toString().length()-1).replace(',','.'));

        //Si nunca se a comprado, su tag es "Compra"
        //Si se a comprado, su tag es el numero de veces que se a mejorado más la compra(1)
        if (tag.equals("0")){
            if (contador>=precioCompra_o_Mejora) {
                contador -= precioCompra_o_Mejora;
                actualizadorContador();
                actualizarBBDDContador();
                btnAux.setTag("1");
                Float precioNuevo = preciosMejoras(precioCompra_o_Mejora);
                btnAux.setText(decimalFormat.format(precioNuevo) + "€");
                actualizarBBDDMejoras(nomMejora,1, precioNuevo);
                return true;
            }
        }else{
            if (contador>=precioCompra_o_Mejora) {
                contador -= precioCompra_o_Mejora;
                actualizadorContador();
                actualizarBBDDContador();
                btnAux.setTag((Integer.parseInt(tag)+1)+"");
                Float precioNuevo = preciosMejoras(precioCompra_o_Mejora);
                btnAux.setText(decimalFormat.format(precioNuevo) + "€");
                actualizarBBDDMejoras(nomMejora,Integer.parseInt(tag)+1, precioNuevo);
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
            tvContador.setText(enteroFormat.format(aux.substring(0,aux.length()-5))+ "M");

        }else{
            tvContador.setText(decimalFormat.format(contador) + "€");
            tvContadorMejoras.setText(decimalFormat.format(contador) + "€");
        }

    }

    //Subidas de precio
    private float preciosMejoras (float precioActual){
        //Suma de un 10%
        float precioNuevo = precioActual + (precioActual/10);
        return precioNuevo;
    }

    //Recoger datos de BBDD
    private void recogerDatosBBDD (){
        //Recoger datos de BBDD
        contador= shared.getFloat("contador", 0);
        Toast.makeText(Juego.this, "Recoger:"+contador, Toast.LENGTH_LONG).show();

        btnAux = findViewById(R.id.btnConcesionario);
        btnAux.setTag(shared.getInt("numero_mejora_concesionario", 0));
        btnAux.setText(decimalFormat.format(shared.getFloat("precio_mejora_concesionario", (float) 2.00))+"€");

        btnAux = findViewById(R.id.btnF1);
        btnAux.setTag(shared.getInt("numero_mejora_f1", 0));
        btnAux.setText(decimalFormat.format(shared.getFloat("precio_mejora_f1", (float) 2.00))+"€");

        actualizadorContador();
    }
    private void actualizarBBDDContador(){
        Toast.makeText(Juego.this, "Guarda:"+contador, Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat("contador", (float)contador);
        editor.commit();
    }
    private void actualizarBBDDMejoras(String nomMejora, int numMejora, Float precioMejora){
        SharedPreferences.Editor editor = shared.edit();

        switch (nomMejora) {
            case "concesionario":
                editor.putInt("numero_mejora_concesionario", numMejora);
                editor.putFloat("precio_mejora_concesionario", precioMejora);
                editor.commit();
                break;
            case "f1":
                editor.putInt("numero_mejora_f1", numMejora);
                editor.putFloat("precio_mejora_f1", precioMejora);
                editor.commit();
                break;
        }
    }

    public void resetearJuego(View view){
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat("contador", 0);
        editor.putInt("numero_mejora_concesionario", 0);
        editor.putFloat("precio_mejora_concesionario", (float) 2.00);
        editor.putInt("numero_mejora_f1", 0);
        editor.putFloat("precio_mejora_f1", (float) 2.00);
        editor.commit();
        contador = 0;
        actualizadorContador();
        recogerDatosBBDD();
    }
    public void minijuego (View view){
        Intent i =new Intent(Juego.this, Menu.class);
        startActivityForResult(i, 1235);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1235 && resultCode == RESULT_OK) {
            bonificacion = data.getExtras().getBoolean("ganado");
            if (bonificacion) {
                Toast.makeText(Juego.this, "x2 Activado", Toast.LENGTH_LONG).show();
                tarjetaPor2.setVisibility(View.VISIBLE);
                new CountDownTimer(60000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Se llama cada segundo (1000 milisegundos) durante el tiempo de cuenta regresiva
                        long seconds = millisUntilFinished / 1000;
                        // Actualiza la vista con el tiempo restante (en segundos)
                        tvCuentaAtras.setText(seconds+"");
                    }

                    public void onFinish() {
                        // Se llama al finalizar la cuenta regresiva (después de 1 minuto)
                        tarjetaPor2.setVisibility(View.INVISIBLE);
                        bonificacion=false;
                    }
                }.start();
            }else{
                Toast.makeText(Juego.this, "Suerte en la proxima", Toast.LENGTH_LONG).show();
            }
        }
    }
}