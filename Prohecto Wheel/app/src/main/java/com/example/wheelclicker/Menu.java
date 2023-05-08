package com.example.wheelclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Timer;
import java.util.TimerTask;

public class Menu extends AppCompatActivity {

    private ImageView luzSemaforo1, luzSemaforo2, luzSemaforo3, luzSemaforo4, luzSemaforo5;
    private int segundosTranscurridos;
    private TextView tvCronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        luzSemaforo1 = findViewById(R.id.luzSemaforo1);
        luzSemaforo2 = findViewById(R.id.luzSemaforo2);
        luzSemaforo3 = findViewById(R.id.luzSemaforo3);

        tvCronometro = findViewById(R.id.tvCronometro);
        semaforo_rojo_a_verde();




    }


    private void cronometro (){
    // Inicia el temporizador
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Actualiza el tiempo transcurrido
                segundosTranscurridos++;
                final int horas = segundosTranscurridos / 3600;
                final int minutos = (segundosTranscurridos % 3600) / 60;
                final int segundos = segundosTranscurridos % 60;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
                    }
                });
            }
        }, 0, 1000);
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
                                apagar_semaforo();
                                cronometro();
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
            }
        }, tiempoEsperaAleatorio);
    }
}