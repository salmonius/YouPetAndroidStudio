package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaguinaPrincipal extends AppCompatActivity {
    protected EditText edit1,edit2;
    protected Button b1,b2;
    protected Intent pasar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_paguina_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edit1 = findViewById(R.id.edit1_principal);
        edit2 = findViewById(R.id.edit2_principal);
        b1 = findViewById(R.id.b1_principal);
        b2 = findViewById(R.id.b2_principal);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(PaguinaPrincipal.this, CrearCuentaUsuarioActivity.class);
                startActivity(pasar);
            }
        });
    }
}