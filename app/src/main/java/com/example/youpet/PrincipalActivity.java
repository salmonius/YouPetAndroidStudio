package com.example.youpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    protected Button b1,b2,b3,b4,b5,b6,b7,b8;
    protected Intent pasar;
    protected Bundle extras;
    protected EditText edit1,edit2;
    protected GestorDeBD gbd;
    protected List<Noticia> listaNoticias;
    protected ArrayList<String> infoNoticiaListado;
    protected ArrayAdapter<String> adaptador;
    protected ListView lv1;
    protected Noticia noticia;


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
        gbd = new GestorDeBD(this);

        b1 = findViewById(R.id.b1_principal_crear);
        b2 = findViewById(R.id.b2_principal_eventos);
        b3 = findViewById(R.id.b3_principal_eventos);
        b4 = findViewById(R.id.b4_principal_eventos);
        b5 = findViewById(R.id.b5_principal_eventos);
        b6 = findViewById(R.id.b6_principal_eventos);
        b7 = findViewById(R.id.b7_principal_ver_mas);
        b8 = findViewById(R.id.b8_principal_crear_noticia);
        edit1 = findViewById(R.id.edit1_principal_titulo);
        edit2= findViewById(R.id.edit2_principal_descripcion);
        lv1 = findViewById(R.id.lv1_principal);

        extras = getIntent().getExtras();

        listaNoticias = gbd.recuperarAllNoticias();
        infoNoticiaListado = new ArrayList<String>();
        for (int i=0;i<listaNoticias.size();i++){
            noticia = listaNoticias.get(i);

            infoNoticiaListado.add(noticia.getTitulo()+"\n"+noticia.getDescripcion()+"\n"+noticia.getFecha());

        }
        adaptador = new ArrayAdapter<String>(PrincipalActivity.this, android.R.layout.simple_list_item_1,infoNoticiaListado);
        lv1.setAdapter(adaptador);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(PrincipalActivity.this,CrearEventoActivity.class);
                pasar.putExtra("ID",extras.getInt("ID"));
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

        //boton para crear noticia
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date fechaHoraActual = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String fechaHoraString = formato.format(fechaHoraActual);

                String titulo = edit1.getText().toString().toUpperCase();
                String descripcion = edit2.getText().toString();

                gbd.insertarNoticia(extras.getInt("ID"),fechaHoraString,titulo,descripcion);

                edit1.setText("");
                edit2.setText("");

                // Recargar la lista de noticias desde la base de datos
                listaNoticias = gbd.recuperarAllNoticias();
                infoNoticiaListado.clear();
                for (Noticia noticia : listaNoticias) {
                    infoNoticiaListado.add(noticia.getTitulo() + "\n" + noticia.getDescripcion()+"\n"+noticia.getFecha());
                }

                // Notificar al adaptador que los datos han cambiado
                adaptador.notifyDataSetChanged();
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
            pasar.putExtra("EMAIL",extras.getString("EMAIL"));
            pasar.putExtra("PASS",extras.getString("PASS"));
            pasar.putExtra("ID",extras.getInt("ID"));
            startActivity(pasar);

        }
        if(item.getItemId()==R.id.item_mascota) {
            pasar = new Intent(PrincipalActivity.this, CrearCuentaMascotasActivity.class);
            pasar.putExtra("ID2",extras.getInt("ID"));
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