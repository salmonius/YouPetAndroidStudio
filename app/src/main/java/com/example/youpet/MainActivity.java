package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    protected TextView tv1;
    protected ImageView iv1, iv2, iv3, iv4;
    protected Intent pasar;
    protected TimerTask tt1, tt2, tt3, tt4, tt5, tt6;
    protected Timer t;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        tv1 = findViewById(R.id.tv1_main);
        iv1 = findViewById(R.id.iv1_main_perro);
        iv2 = findViewById(R.id.iv2_main_brid);
        iv3 = findViewById(R.id.iv3_main_oso);
        iv4 = findViewById(R.id.iv4_main_glu);

        tv1.setVisibility(View.INVISIBLE);
        iv1.setVisibility(View.INVISIBLE);
        iv2.setVisibility(View.INVISIBLE);
        iv3.setVisibility(View.INVISIBLE);
        iv4.setVisibility(View.INVISIBLE);

        tt1 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> tv1.setVisibility(View.VISIBLE));
            }
        };
        tt2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> iv1.setVisibility(View.VISIBLE));
            }
        };
        tt3 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> iv2.setVisibility(View.VISIBLE));
            }
        };
        tt4 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> iv3.setVisibility(View.VISIBLE));
            }
        };
        tt5 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> iv4.setVisibility(View.VISIBLE));
            }
        };
        tt6 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    pasar = new Intent(MainActivity.this, AutenticacionActivity.class);
                    startActivity(pasar);
                    finish();
                });
            }
        };
        t = new Timer();
        t.schedule(tt1, 500);
        t.schedule(tt2, 1500);
        t.schedule(tt3, 2000);
        t.schedule(tt4, 2500);
        t.schedule(tt5, 3000);
        t.schedule(tt6, 3500);  // Actualizamos el horario de tt6 a 3500 ms
    }
}