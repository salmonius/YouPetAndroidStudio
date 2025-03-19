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

public class AutenticacionActivity extends AppCompatActivity {
    protected EditText edit1,edit2;
    protected Button b1,b2;
    protected Intent pasar;
    protected GestorDeBD gbd;
    protected Usuario u1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_autentificacion_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        edit1 = findViewById(R.id.edit2_principal_descripcion);
        edit2 = findViewById(R.id.edit2_principal);
        b1 = findViewById(R.id.b1_principal);
        b2 = findViewById(R.id.b2_principal);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit1.getText().toString().trim();
                String contrasenia = edit2.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (email.isEmpty() || contrasenia.isEmpty()) {
                    edit1.setError("El correo electrónico es obligatorio");
                    edit2.setError("La contraseña es obligatoria");
                    return;
                }

                gbd = new GestorDeBD(AutenticacionActivity.this);
                boolean autenticado = gbd.autenticarUsuario(email, contrasenia);
                u1 = gbd.usuarioConectado(email,contrasenia);

                if (autenticado) {
                    // Si es exitoso, pasar a la siguiente actividad
                    pasar = new Intent(AutenticacionActivity.this, PrincipalActivity.class);
                    pasar.putExtra("EMAIL",email);
                    pasar.putExtra("PASS",contrasenia);
                    pasar.putExtra("ID",u1.getId());
                    startActivity(pasar);
                    finish();
                } else {
                    // Mostrar mensaje de error
                    edit2.setError("Correo electrónico o contraseña incorrectos");
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(AutenticacionActivity.this, CrearCuentaUsuarioActivity.class);
                startActivity(pasar);
            }
        });
    }
}