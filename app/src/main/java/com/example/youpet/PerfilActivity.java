package com.example.youpet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PerfilActivity extends AppCompatActivity {
    protected ImageButton ib1,ib2,ib3,ib4,ib5;
    protected Button b1,b2;
    protected ImageView iv1;
    protected TextView tv1;
    protected EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8;
    protected Intent atras;
    protected Bundle extra;
    protected String email,contrasenia;
    protected Usuario u1;
    protected GestorDeBD gbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        ib1 = findViewById(R.id.ib1_perfil_atras);
        ib2 = findViewById(R.id.ib2_perfil_editar_foto);
        ib3 = findViewById(R.id.ib3_perfil_editar_datos_usuario);
        ib4 = findViewById(R.id.ib4_perfil_editar_datos_mascota);
        ib5 = findViewById(R.id.ib5_perfil_aniadir);
        b1 = findViewById(R.id.b1_perfil_guardar);
        b2 = findViewById(R.id.b2_perfil_eliminar);
        iv1 = findViewById(R.id.iv1_perfil);
        tv1 = findViewById(R.id.tv1_perfil_usuario);
        edit1 = findViewById(R.id.edit1_perfil_nombre);
        edit2 = findViewById(R.id.edit2_perfil_apellidos);
        edit3 = findViewById(R.id.edit3_perfil_fecha);
        edit4 = findViewById(R.id.edit4_perfil_poblacion);
        edit5 = findViewById(R.id.edit5_perfil_telefono);
        edit6 = findViewById(R.id.edit6_perfil_email);
        edit7 = findViewById(R.id.edit7_perfil_direccion);
        edit8 = findViewById(R.id.edit8_perfil_provincia);

        desabilitarEdicion();

        extra = getIntent().getExtras();


        if (extra != null) {
            email = extra.getString("EMAIL");
            contrasenia = extra.getString("PASS");
        } else {
            Toast.makeText(this, "No se recibieron datos extra.", Toast.LENGTH_SHORT).show();
            return; // Opcionalmente termina aquí si no hay extras
        }
        gbd = new GestorDeBD(this);
        u1 = gbd.usuarioConectado(email,contrasenia);

        if (u1 != null) {
            byte[] imagenBytes = u1.getImagen();
            if (imagenBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                iv1.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "La imagen no carga.", Toast.LENGTH_SHORT).show();
            }

            tv1.setText(u1.getNombre());
            edit1.setText(u1.getNombre());
            edit2.setText(u1.getApellidos());
            edit3.setText(u1.getFechaNacimiento());
            edit4.setText(u1.getPoblacion());
            edit5.setText(u1.getTelefono());
            edit6.setText(u1.getEmail());
            edit7.setText(u1.getDireccion());
            edit8.setText(u1.getProvincia());
        } else {
            Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(PerfilActivity.this, PrincipalActivity.class);
                startActivity(atras);
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit1.setEnabled(true);
                edit1.setBackgroundColor(Color.WHITE);
                edit2.setEnabled(true);
                edit2.setBackgroundColor(Color.WHITE);
                edit3.setEnabled(true);
                edit3.setBackgroundColor(Color.WHITE);
                edit4.setEnabled(true);
                edit4.setBackgroundColor(Color.WHITE);
                edit5.setEnabled(true);
                edit5.setBackgroundColor(Color.WHITE);
                edit6.setEnabled(true);
                edit6.setBackgroundColor(Color.WHITE);
                edit7.setEnabled(true);
                edit7.setBackgroundColor(Color.WHITE);
                edit8.setEnabled(true);
                edit8.setBackgroundColor(Color.WHITE);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
            }
        });
    }
    public void desabilitarEdicion(){
        edit1.setEnabled(false);
        edit2.setEnabled(false);
        edit3.setEnabled(false);
        edit4.setEnabled(false);
        edit5.setEnabled(false);
        edit6.setEnabled(false);
        edit7.setEnabled(false);
        edit8.setEnabled(false);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
    }
}