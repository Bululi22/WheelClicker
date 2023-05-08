package com.example.wheelclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Timer;
import java.util.TimerTask;

public class Menu extends AppCompatActivity {

    private ImageView luzSemaforo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        luzSemaforo = findViewById(R.id.imageView3);
        semaforo_rojo_a_verde();
        luzSemaforo = findViewById(R.id.imageView4);
        semaforo_rojo_a_verde();
        luzSemaforo = findViewById(R.id.imageView5);
        semaforo_rojo_a_verde();
//        apagar_semaforo();


    }

    private void semaforo_rojo_a_verde (){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                luzSemaforo.setImageResource(R.drawable.semaforo_rojo);
            }
        }, 1000);
    }


//    private void semaforo_rojo_a_verde (){
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                luzSemaforo1.setImageResource(R.drawable.semaforo_rojo);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        luzSemaforo2.setImageResource(R.drawable.semaforo_rojo);
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                luzSemaforo3.setImageResource(R.drawable.semaforo_rojo);
//                            }
//                        }, 1000);
//                    }
//                }, 1000);
//            }
//        }, 1000);
//    }
    private void apagar_semaforo (){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                luzSemaforo.setImageResource(R.drawable.circulo_verde);
            }
        }, 1000);
    }
}