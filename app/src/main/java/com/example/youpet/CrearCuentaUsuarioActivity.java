package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class CrearCuentaUsuarioActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1; // Código para identificar la selección de imágenes
    protected Intent pasar;
    protected Button b1,b2;
    protected EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10;
    protected ImageView iv1;
    protected GestorDeBD gdb;
    protected Usuario u1;

    @SuppressLint("MissingInflatedId")
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

        iv1 = findViewById(R.id.iv1_crear_u);
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
                try {
                    String nom = edit1.getText().toString().trim();
                    String ape = edit2.getText().toString().trim();
                    String tel = edit3.getText().toString().trim();
                    String email = edit4.getText().toString().trim();
                    String fecha = edit5.getText().toString().trim();
                    String direc = edit6.getText().toString().trim();
                    String pobla = edit7.getText().toString().trim();
                    String provin = edit8.getText().toString().trim();
                    String pass = edit9.getText().toString().trim();
                    byte[] imageBytes = (byte[]) edit10.getTag();

                    if (nom.isEmpty() || ape.isEmpty() || tel.isEmpty() || email.isEmpty() || fecha.isEmpty() || direc.isEmpty() || pobla.isEmpty() || provin.isEmpty() || pass.isEmpty() || imageBytes == null || imageBytes.length == 0) {
                        Toast.makeText(CrearCuentaUsuarioActivity.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (tel.length() != 9 || !tel.matches("\\d{9}")) {
                        Toast.makeText(CrearCuentaUsuarioActivity.this, "El teléfono debe tener 9 dígitos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!email.contains("@") || !email.contains(".")) {
                        Toast.makeText(CrearCuentaUsuarioActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (pass.length() < 6) {
                        Toast.makeText(CrearCuentaUsuarioActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!fecha.matches("\\d{2}-\\d{2}-\\d{2}")) {
                        Toast.makeText(CrearCuentaUsuarioActivity.this, "La fecha debe tener el formato dd-MM-yy", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Insertar usuario
                    gdb.insertarUsuario(nom, ape, tel, email, fecha, direc, pobla, provin, pass, imageBytes);
                    Toast.makeText(CrearCuentaUsuarioActivity.this, "Usuario guardado", Toast.LENGTH_SHORT).show();

                    u1 = gdb.usuarioConectado(email, pass);

                    pasar = new Intent(CrearCuentaUsuarioActivity.this, CrearCuentaMascotasActivity.class);
                    pasar.putExtra("EMAIL", email);
                    pasar.putExtra("PASS", pass);
                    pasar.putExtra("ID", u1.getId());
                    startActivity(pasar);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CrearCuentaUsuarioActivity.this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
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

                    // Establece el Bitmap en el ImageView
                    iv1.setImageBitmap(bitmap);

                    // Guarda la imagen como un Tag en el EditText (como ya hacías)
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