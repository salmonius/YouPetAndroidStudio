package com.example.youpet;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class CrearCuentaUsuarioActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Código para identificar la selección de imágenes
    protected Intent pasar;
    protected Button b1,b2;
    protected EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10;
    protected GestorDeBD gdb;

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
        gdb = new GestorDeBD(this);
        getSupportActionBar().hide();

        edit1 = findViewById(R.id.edit1_crear_u_nombre);
        edit2 = findViewById(R.id.edit2_crear_u_apellidos);
        edit3 = findViewById(R.id.edit3_crear_u_telf);
        edit4 = findViewById(R.id.edit4_crear_u_email);
        edit5 = findViewById(R.id.edit5_crear_u_fecha);
        edit6 = findViewById(R.id.edit6_crear_u_direccion);
        edit7 = findViewById(R.id.edit7_crear_u_poblacion);
        edit8 = findViewById(R.id.edit8_crear_u_provincia);
        edit9 = findViewById(R.id.edit9_crear_u_contraseña);
        edit10 = findViewById(R.id.edit10_crear_u_subir);
        b1 = findViewById(R.id.b1_crear_u_subir);
        b2 = findViewById(R.id.b2_crear_u_crear);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = edit1.getText().toString();
                String ape = edit2.getText().toString();
                String tel = edit3.getText().toString();
                String email = edit4.getText().toString();
                String fecha = edit5.getText().toString();
                String direc = edit6.getText().toString();
                String pobla = edit7.getText().toString();
                String provin = edit8.getText().toString();
                String pass = edit9.getText().toString();
                byte[] imageBytes = (byte[]) edit10.getTag();

                if (!nom.isEmpty() && !ape.isEmpty() && !tel.isEmpty() && !email.isEmpty() && !fecha.isEmpty() && !direc.isEmpty() && !pobla.isEmpty() && !provin.isEmpty() && !pass.isEmpty() && imageBytes.length>0) {
                    // Llama al método para insertar un usuario (ajustar los valores según sea necesario)
                    gdb.insertarUsuario(nom, ape, tel, email, fecha, direc, pobla, provin,pass, imageBytes);
                    Toast.makeText(CrearCuentaUsuarioActivity.this, "Usuario guardado", Toast.LENGTH_SHORT).show();

                    pasar = new Intent(CrearCuentaUsuarioActivity.this, CrearCuentaMascotasActivity.class);
                    startActivity(pasar);
                } else {
                    Toast.makeText(CrearCuentaUsuarioActivity.this, "Por favor rellene los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
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

                    edit10.setTag(imageBytes);
                    edit10.setText("Imagen seleccionada correctamente");
                } catch (Exception e) {
                    e.printStackTrace();
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