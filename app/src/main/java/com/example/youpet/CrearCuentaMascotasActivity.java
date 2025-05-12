package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearCuentaMascotasActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Código para seleccionar imagen
    protected Button b1, b2, b3;
    protected Intent pasar;
    protected EditText edit1, edit3, edit8;
    protected ImageView iv1;
    protected GestorDeBD gdb;
    protected Bundle extras;
    protected Spinner sp2,sp4,sp5,sp6,sp7;
    protected ArrayAdapter<String> adap2,adap4,adap5,adap6,adap7;
    protected String[] tipo={"Perro","Gato","Pajaro","Pez","Reptil"};
    protected String[] tamanio={"Pequeño","Mediano","Grande"};
    protected String[] sexo={"Macho","Hembra"};
    protected String[] castrado={"Si","No"};
    protected String[] sociabilidad={"Sociable en general","Sociable solo con machos","Sociable solo con hembras","No es sociable"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_cuenta_mascotas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gdb = new GestorDeBD(this);
        getSupportActionBar().hide();

        // Inicializar componentes
        iv1 = findViewById(R.id.iv1_crear_m);
        b1 = findViewById(R.id.b1_crear_m_subir);
        b2 = findViewById(R.id.b2_crear_m_crear);
        b3 = findViewById(R.id.b3_no_crear_mascota);
        edit1 = findViewById(R.id.edit1_crear_m_nombre);
        sp2 = findViewById(R.id.sp2_crear_m_tipo);
        edit3 = findViewById(R.id.edit3_crear_m_edad);
        sp4 = findViewById(R.id.sp4_crear_m_tamanio);
        sp5 = findViewById(R.id.sp5_crear_m_sexo);
        sp6 = findViewById(R.id.sp6_crear_m_castrado);
        sp7 = findViewById(R.id.sp7_crear_m_sociabilidad);
        edit8 = findViewById(R.id.edit8_crear_m_subir);

        adap2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipo);
        sp2.setAdapter(adap2);
        adap4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tamanio);
        sp4.setAdapter(adap4);
        adap5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sexo);
        sp5.setAdapter(adap5);
        adap6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,castrado);
        sp6.setAdapter(adap6);
        adap7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sociabilidad);
        sp7.setAdapter(adap7);

        extras = getIntent().getExtras();

        // Botón para seleccionar imagen
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Botón para crear mascota
        b2.setOnClickListener(v -> {
            String nom = edit1.getText().toString().trim();
            String tipo = sp2.getSelectedItem().toString();
            String fecha = edit3.getText().toString().trim();
            String tamanio = sp4.getSelectedItem().toString();
            String sexo = sp5.getSelectedItem().toString();
            String castrado = sp6.getSelectedItem().toString();
            String social = sp7.getSelectedItem().toString();
            byte[] imageBytes = (byte[]) edit8.getTag();



            // Validar campos
            if (nom.isEmpty() || tipo.isEmpty() || fecha.isEmpty() || tamanio.isEmpty() || sexo.isEmpty() || castrado.isEmpty() || social.isEmpty() || imageBytes == null || imageBytes.length == 0) {
                Toast.makeText(CrearCuentaMascotasActivity.this, "Por favor rellene todos los campos y seleccione una imagen", Toast.LENGTH_SHORT).show();
                return;
            }else{
                try {

                    gdb.insertarMascotas(extras.getInt("ID"), nom, tipo,fecha, tamanio, sexo, castrado, social, imageBytes);
                    Toast.makeText(CrearCuentaMascotasActivity.this, "Mascota guardada con éxito", Toast.LENGTH_SHORT).show();
                    pasar = new Intent(CrearCuentaMascotasActivity.this, PrincipalActivity.class);
                    pasar.putExtra("EMAIL",extras.getString("EMAIL"));
                    pasar.putExtra("PASS",extras.getString("PASS"));
                    pasar.putExtra("ID",extras.getInt("ID"));
                    startActivity(pasar);

                } catch (Exception e) {
                    Log.e("INSERT_ERROR", "Error al guardar la mascota", e);
                    Toast.makeText(CrearCuentaMascotasActivity.this, "Ocurrió un error al guardar la mascota", Toast.LENGTH_SHORT).show();
                }
            }


        });

        // Botón para omitir creación
        b3.setOnClickListener(v -> {

                pasar = new Intent(CrearCuentaMascotasActivity.this, PrincipalActivity.class);
                pasar.putExtra("EMAIL",extras.getString("EMAIL"));
                pasar.putExtra("PASS",extras.getString("PASS"));
                pasar.putExtra("ID",extras.getInt("ID"));
                startActivity(pasar);

        });
    }

    // Manejo de resultado de selección de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byte[] imageBytes = stream.toByteArray();

                    iv1.setImageBitmap(bitmap);

                    edit8.setTag(imageBytes);
                    edit8.setText("Imagen seleccionada correctamente");
                } catch (Exception e) {
                    Log.e("IMAGE_ERROR", "Error al procesar la imagen seleccionada", e);
                    Toast.makeText(this, "Error al procesar la imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No se seleccionó una imagen válida", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Selección de imagen cancelada", Toast.LENGTH_SHORT).show();
        }
    }
}
