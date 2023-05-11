package com.example.wheelclicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Minijuego extends AppCompatActivity {
    private TextView tiempoTranscurrido;
    private Button btnIniciar, btnDetener;
    private Timer timer;
    private long milisegundosTranscurridos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tiempoTranscurrido = findViewById(R.id.tiempo_transcurrido);
        btnIniciar = findViewById(R.id.btn_iniciar);
        btnDetener = findViewById(R.id.btn_detener);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                tiempoTranscurrido.setText(String.format("%02d:%02d:%02d.%d.%03d", horas, minutos, segundos, decimas, milisegundos));
                            }
                        });
                    }
                }, 0, 1);
            }
        });
}
