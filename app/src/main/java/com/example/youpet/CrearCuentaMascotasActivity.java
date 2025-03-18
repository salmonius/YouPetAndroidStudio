package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class CrearCuentaMascotasActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Código para seleccionar imagen
    protected Button b1, b2, b3;
    protected Intent pasar;
    protected EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8;
    protected ImageView iv1;
    protected GestorDeBD gdb;
    protected Bundle extras;
    protected int id = 0, id2 = 0;

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
        edit2 = findViewById(R.id.edit2_crear_m_tipo);
        edit3 = findViewById(R.id.edit3_crear_m_edad);
        edit4 = findViewById(R.id.edit4_crear_m_tamanio);
        edit5 = findViewById(R.id.edit5_crear_m_sexo);
        edit6 = findViewById(R.id.edit6_crear_m_castrado);
        edit7 = findViewById(R.id.edit7_crear_m_sociabilidad);
        edit8 = findViewById(R.id.edit8_crear_m_subir);

        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("ID");
            id2 = extras.getInt("ID2");
            Log.d("EXTRAS", "ID: " + id + ", ID2: " + id2);
        }

        // Botón para seleccionar imagen
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Botón para crear mascota
        b2.setOnClickListener(v -> {
            String nom = edit1.getText().toString().trim();
            String tipo = edit2.getText().toString().trim();
            String edad = edit3.getText().toString().trim();
            String tamanio = edit4.getText().toString().trim();
            String sexo = edit5.getText().toString().trim();
            String castrado = edit6.getText().toString().trim();
            String social = edit7.getText().toString().trim();
            byte[] imageBytes = (byte[]) edit8.getTag();

            // Validar campos
            if (nom.isEmpty() || tipo.isEmpty() || edad.isEmpty() || tamanio.isEmpty() || sexo.isEmpty() || castrado.isEmpty() || social.isEmpty() || imageBytes == null || imageBytes.length == 0) {
                Toast.makeText(CrearCuentaMascotasActivity.this, "Por favor rellene todos los campos y seleccione una imagen", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insertar datos
            try {
                if (id != 0) {
                    gdb.insertarMascotas(id, nom, tipo, edad, tamanio, sexo, castrado, social, imageBytes);
                    Toast.makeText(CrearCuentaMascotasActivity.this, "Mascota guardada con éxito", Toast.LENGTH_SHORT).show();
                }
                if (id2 != 0) {
                    gdb.insertarMascotas(id2, nom, tipo, edad, tamanio, sexo, castrado, social, imageBytes);
                    Toast.makeText(CrearCuentaMascotasActivity.this, "Mascota guardada con éxito", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("INSERT_ERROR", "Error al guardar la mascota", e);
                Toast.makeText(CrearCuentaMascotasActivity.this, "Ocurrió un error al guardar la mascota", Toast.LENGTH_SHORT).show();
            }

            // Redirigir a la actividad principal
            pasar = new Intent(CrearCuentaMascotasActivity.this, PrincipalActivity.class);
            pasar.putExtra("EMAIL",extras.getString("EMAIL"));
            pasar.putExtra("PASS",extras.getString("PASS"));
            startActivity(pasar);
        });

        // Botón para omitir creación
        b3.setOnClickListener(v -> {
            pasar = new Intent(CrearCuentaMascotasActivity.this, PrincipalActivity.class);
            pasar.putExtra("EMAIL",extras.getString("EMAIL"));
            pasar.putExtra("PASS",extras.getString("PASS"));
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
