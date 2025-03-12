package com.example.youpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrincipalActivity extends AppCompatActivity {
    protected Button b1,b2,b3,b4,b5,b6,b7,b8;
    protected Intent pasar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1 = findViewById(R.id.b1_principal_crear);
        b2 = findViewById(R.id.b2_principal_eventos);
        b3 = findViewById(R.id.b3_principal_eventos);
        b4 = findViewById(R.id.b4_principal_eventos);
        b5 = findViewById(R.id.b5_principal_eventos);
        b6 = findViewById(R.id.b6_principal_eventos);
        b7 = findViewById(R.id.b7_principal_ver_mas);
        b8 = findViewById(R.id.b8_principal_crear_noticia);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(PrincipalActivity.this,CrearEventoActivity.class);
                startActivity(pasar);

            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(PrincipalActivity.this,ListadoEventosActivity.class);
                startActivity(pasar);

            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()==R.id.item_editar) {
            pasar = new Intent(PrincipalActivity.this,PerfilActivity.class);
            startActivity(pasar);

        }
        if(item.getItemId()==R.id.item_mascota) {
            pasar = new Intent(PrincipalActivity.this, CrearCuentaMascotasActivity.class);
            startActivity(pasar);

        }
        if(item.getItemId()==R.id.item_cerrar) {
            finish();

        }
            return super.onOptionsItemSelected(item);

    }
    public void pasarInfo(View v){
        pasar = new Intent(PrincipalActivity.this,InfoEventoActivity.class);
        startActivity(pasar);
    }
}