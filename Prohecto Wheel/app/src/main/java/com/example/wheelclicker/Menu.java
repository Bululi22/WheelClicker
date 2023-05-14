package com.example.wheelclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Menu extends AppCompatActivity {

    private ImageView luzSemaforo1, luzSemaforo2, luzSemaforo3, luzSemaforo4, luzSemaforo5;
    private Timer timer;
    private double milisegundosTranscurridos;
    private int segundos;
    private TextView tvCronometro, tvSemaforo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Ventana completa (Ocultar barra de abajo y barra de notificaciones)
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        luzSemaforo1 = findViewById(R.id.luzSemaforo1);
        luzSemaforo2 = findViewById(R.id.luzSemaforo2);
        luzSemaforo3 = findViewById(R.id.luzSemaforo3);
        luzSemaforo4 = findViewById(R.id.luzSemaforo4);
        luzSemaforo5 = findViewById(R.id.luzSemaforo5);

        tvSemaforo = findViewById(R.id.tvSemaforo);
        tvCronometro = findViewById(R.id.tvCronometro);


        ConstraintLayout constraint = findViewById(R.id.layoutMinijuego);
        constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararCronometro();
            }
        });

        semaforo_rojo_a_verde();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void pararCronometro(){
        if (timer != null) {
            timer.cancel();
            if (segundos<1){
                ganar();
                return;
            }
            perder();
        }
    }

    private void ganar(){
        tvSemaforo.setText("Buena salida");

        Intent i =new Intent(Menu.this, Juego.class);
        i.putExtra("ganado", true);
        setResult(RESULT_OK, i);
        finish();

    }
    private void perder(){
        tvSemaforo.setText("Has Perdido");
        tvSemaforo.setTextColor(ContextCompat.getColor(this, R.color.rojo));

        Intent i =new Intent(Menu.this, Juego.class);
        i.putExtra("ganado", false);
        setResult(RESULT_OK, i);
        finish();
    }
    private void cronometro () {
        // Inicia el temporizador
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Actualiza el tiempo transcurrido
                milisegundosTranscurridos++;
                segundos = (int) ((milisegundosTranscurridos / 1000) % 60);
                int milesimas = (int) (milisegundosTranscurridos % 1000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCronometro.setText(String.format("%02d.%03d", segundos, milesimas));
                    }
                });
            }
        }, 0, 1);


    }

    private void semaforo_rojo_a_verde (){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                luzSemaforo1.setImageResource(R.drawable.semaforo_rojo);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        luzSemaforo2.setImageResource(R.drawable.semaforo_rojo);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                luzSemaforo3.setImageResource(R.drawable.semaforo_rojo);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        luzSemaforo4.setImageResource(R.drawable.semaforo_rojo);
                                        new Timer().schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                luzSemaforo5.setImageResource(R.drawable.semaforo_rojo);
                                                apagar_semaforo();
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }, 1000);
    }
    private void apagar_semaforo (){
        long min = 1000;
        long max = 4000;
        long tiempoEsperaAleatorio = (long)(Math.random() * (max - min + 1) + min);
//        Toast.makeText(Menu.this, "Tiempo long: ", Toast.LENGTH_LONG).show();
        System.out.println("################################################");
        System.out.println(tiempoEsperaAleatorio);
        System.out.println("################################################");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                luzSemaforo1.setImageResource(R.drawable.semaforo_negro);
                luzSemaforo2.setImageResource(R.drawable.semaforo_negro);
                luzSemaforo3.setImageResource(R.drawable.semaforo_negro);
                luzSemaforo4.setImageResource(R.drawable.semaforo_negro);
                luzSemaforo5.setImageResource(R.drawable.semaforo_negro);
                cronometro();
            }
        }, tiempoEsperaAleatorio);
    }
}