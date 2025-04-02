package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearEventoActivity extends AppCompatActivity {

    protected ImageButton ib1;
    protected Intent atras;
    protected TextView tv1,tv2,tv3,tv4,tv5,tv6;
    protected EditText edit1,edit2,edit3,edit4,edit5;
    protected Button b1;
    protected GestorDeBD gdb;
    protected Bundle extras;

    protected int id = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();// esconde la barra de navegacion


        ib1 = findViewById(R.id.ib1_crear_evento_atras);
        tv1 = findViewById(R.id.tv1_crear_evento);
        tv2 = findViewById(R.id.tv2_crear_evento_nombre);
        tv3 = findViewById(R.id.tv3_crear_evento_descripcion);
        tv4 = findViewById(R.id.tv4_crear_evento_fecha);
        tv5 = findViewById(R.id.tv5_crear_evento_hora);
        tv6 = findViewById(R.id.tv6_crear_evento_ubicacion);
        edit1 = findViewById(R.id.edit1_crear_evento_nombre);
        edit2 = findViewById(R.id.edit2_crear_evento_descripcion);
        edit3 = findViewById(R.id.edit3_crear_evento_fecha);
        edit4 = findViewById(R.id.edit4_crear_evento_hora);
        edit5 = findViewById(R.id.edit5_crear_evento_ubicacion);
        b1 = findViewById(R.id.b1_crear_evento);

        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            Log.d("EXTRAS", "ID: " + id);
        }
        Toast.makeText(this, "id 1 : " +id, Toast.LENGTH_SHORT).show();

        gdb = new GestorDeBD(this);

        //BOTON CREAR EVENTO
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edit1.getText().toString().trim();
                String descripcion = edit2.getText().toString().trim();
                String fecha = edit3.getText().toString().trim();
                String hora = edit4.getText().toString().trim();
                String ubicacion = edit5.getText().toString().trim();

                // Validacion de campos
                if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(CrearEventoActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;  // No continuar si hay campos vacíos
                }

                // Funcion que inserta datos del evento

                try {
                    if (id != 0) {
                        gdb.insertarEvento(id, nombre, descripcion, fecha, hora, ubicacion);
                        Toast.makeText(CrearEventoActivity.this, "Evento creado exitosamente", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("INSERT_ERROR", "Error al guardar la mascota", e);
                    Toast.makeText(CrearEventoActivity.this, "Ocurrió un error al guardar la mascota", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //BOTON ATRAS
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(CrearEventoActivity.this,PrincipalActivity.class);
                atras.putExtra("ID",extras.getInt("ID"));
                startActivity(atras);

            }
        });
    }
}