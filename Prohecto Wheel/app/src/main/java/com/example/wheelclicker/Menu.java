package com.example.wheelclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Menu extends AppCompatActivity {

    private ImageView luzSemaforo1, luzSemaforo2, luzSemaforo3, luzSemaforo4, luzSemaforo5;
    private Timer timer;
    private int milisegundosTranscurridos;
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


    private void cronometro () {
        // Inicia el temporizador
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Inicia el temporizador
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        // Actualiza el tiempo transcurrido
                        milisegundosTranscurridos += 1;
                        final int horas = (int) (milisegundosTranscurridos / 3600000);
                        final int minutos = (int) ((milisegundosTranscurridos / 60000) % 60);
                        final int segundos = (int) ((milisegundosTranscurridos / 1000) % 60);
                        final int decimas = (int) ((milisegundosTranscurridos / 100) % 10);
                        final int milisegundos = (int) (milisegundosTranscurridos % 1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvCronometro.setText(String.format("%02d:%02d:%02d.%d.%03d", horas, minutos, segundos, decimas, milisegundos));
                            }
                        });
                    }
                }, 0, 1);
            }
        });
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