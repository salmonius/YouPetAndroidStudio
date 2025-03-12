package com.example.youpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearCuentaUsuarioActivity extends AppCompatActivity {
    protected Intent pasar;
    protected Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_cuenta_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        b2 = findViewById(R.id.b2_crear_u_crear);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(CrearCuentaUsuarioActivity.this, CrearCuentaMascotasActivity.class);
                startActivity(pasar);
            }
        });
    }
}