package com.example.wheelclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Menu extends AppCompatActivity {

    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nav = findViewById(R.id.nav);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rueda:
                        Toast.makeText(Menu.this, "Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.mejoras:
                        Toast.makeText(Menu.this, "Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.ajustes:
                        Toast.makeText(Menu.this, "Home", Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
                return true;
            }
        });

    }
}