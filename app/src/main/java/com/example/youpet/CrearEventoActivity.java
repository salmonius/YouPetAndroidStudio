package com.example.youpet;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
    protected Spinner s1;
    protected String[] itemSpinner ={"Campo","Playa","Domicilio","Pipicam","Paseo","Tienda de mascotas","Parque"};
    protected ArrayAdapter<String> adaptador;
    protected MediaPlayer mp;

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
        gdb = new GestorDeBD(this);

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
        s1 = findViewById(R.id.s1_crear_evento);

        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            Log.d("EXTRAS", "ID: " + id);
        }
        Toast.makeText(this, "id 1 : " +id, Toast.LENGTH_SHORT).show();

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemSpinner);
        s1.setAdapter(adaptador);

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(CrearEventoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edit3.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },2025,0,1);
                dpd.show();
            }
        });

        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog tpd = new TimePickerDialog(CrearEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edit4.setText(hourOfDay+":"+minute);
                    }
                },10,30,true);
                tpd.show();
            }
        });
        //BOTON CREAR EVENTO
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp = MediaPlayer.create(CrearEventoActivity.this, R.raw.bubbles);
                mp.start();
                // Obtener los valores de los campos de texto
                String nombre = edit1.getText().toString().trim();
                String descripcion = edit2.getText().toString().trim();
                String fecha = edit3.getText().toString().trim();
                String hora = edit4.getText().toString().trim();
                String ubicacion = edit5.getText().toString().trim();
                String seleccion = s1.getSelectedItem().toString();

                // Validacion de campos
                if (nombre.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(CrearEventoActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;  // No continuar si hay campos vacíos
                }

                // Funcion que inserta datos del evento

                try {
                    if (id != 0) {
                        gdb.insertarEvento(id, nombre, descripcion, fecha, hora, ubicacion,seleccion);
                        Toast.makeText(CrearEventoActivity.this, "Evento creado exitosamente", Toast.LENGTH_SHORT).show();
                        atras = new Intent(CrearEventoActivity.this, PrincipalActivity.class);
                        atras.putExtra("ID",extras.getInt("ID"));
                        atras.putExtra("EMAIL",extras.getString("EMAIL"));
                        atras.putExtra("PASS",extras.getString("PASS"));
                        startActivity(atras);
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
                mp = MediaPlayer.create(CrearEventoActivity.this, R.raw.bubbles);
                mp.start();
                atras = new Intent(CrearEventoActivity.this,PrincipalActivity.class);
                atras.putExtra("ID",extras.getInt("ID"));
                atras.putExtra("EMAIL",extras.getString("EMAIL"));
                atras.putExtra("PASS",extras.getString("PASS"));
                startActivity(atras);

            }
        });
    }
}