package com.example.youpet;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

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
    protected List<String> listaEventos;
    protected ArrayList<String> infoNoticiaListado;
    protected ArrayAdapter<String> adaptador;
    protected ListView lv1;
    protected Noticia noticia;
    protected TextView tv3n,tv3e,tv3l,tv3f,tv3i,tv4n,tv4e,tv4l,tv4f,tv4i,tv5n,tv5e,tv5l,tv5f,tv5i,tv6n,tv6e,tv6l,tv6f,tv6i,tv7n,tv7e,tv7l,tv7f,tv7i;


    @SuppressLint("MissingInflatedId")
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
        b2 = findViewById(R.id.b2_principal_info);
        b3 = findViewById(R.id.b3_principal_info);
        b4 = findViewById(R.id.b4_principal_info);
        b5 = findViewById(R.id.b5_principal_info);
        b6 = findViewById(R.id.b6_principal_info);
        b7 = findViewById(R.id.b7_principal_ver_mas);
        b8 = findViewById(R.id.b8_principal_crear_noticia);
        edit1 = findViewById(R.id.edit1_principal_titulo);
        edit2= findViewById(R.id.edit2_principal_descripcion);
        lv1 = findViewById(R.id.lv1_principal);
        tv3n = findViewById(R.id.tv3_principal_creador);
        tv3e = findViewById(R.id.tv3_principal_nombre);
        tv3l = findViewById(R.id.tv3_principal_lugar);
        tv3f = findViewById(R.id.tv3_principal_fecha);
        tv3i = findViewById(R.id.tv3_principal_invisible);
        tv4n = findViewById(R.id.tv4_principal_creador);
        tv4e = findViewById(R.id.tv4_principal_nombre);
        tv4l = findViewById(R.id.tv4_principal_lugar);
        tv4f = findViewById(R.id.tv4_principal_fecha);
        tv4i = findViewById(R.id.tv4_principal_invisible);
        tv5n = findViewById(R.id.tv5_principal_creador);
        tv5e = findViewById(R.id.tv5_principal_nombre);
        tv5l = findViewById(R.id.tv5_principal_lugar);
        tv5f = findViewById(R.id.tv5_principal_fecha);
        tv5i = findViewById(R.id.tv5_principal_invisible);
        tv6n = findViewById(R.id.tv6_principal_creador);
        tv6e = findViewById(R.id.tv6_principal_nombre);
        tv6l = findViewById(R.id.tv6_principal_lugar);
        tv6f = findViewById(R.id.tv6_principal_fecha);
        tv6i = findViewById(R.id.tv6_principal_invisible);
        tv7n = findViewById(R.id.tv7_principal_creador);
        tv7e = findViewById(R.id.tv7_principal_nombre);
        tv7l = findViewById(R.id.tv7_principal_lugar);
        tv7f = findViewById(R.id.tv7_principal_fecha);
        tv7i = findViewById(R.id.tv7_principal_invisible);

        extras = getIntent().getExtras();

        textInvisibles();
        listaEventos = gbd.getTodosEventos();

        rellenarEventos();

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
                pasar.putExtra("EMAIL",extras.getString("EMAIL"));
                pasar.putExtra("PASS",extras.getString("PASS"));
                startActivity(pasar);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tv3i.getText().toString());
                String usuario = tv3n.getText().toString();
                Evento e1=recogeEvento(id);
                pasarPantalla(e1,usuario);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tv4i.getText().toString());
                String usuario = tv4n.getText().toString();
                Evento e1=recogeEvento(id);
                pasarPantalla(e1,usuario);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tv5i.getText().toString());
                String usuario = tv5n.getText().toString();
                Evento e1=recogeEvento(id);
                pasarPantalla(e1,usuario);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tv6i.getText().toString());
                String usuario = tv6n.getText().toString();
                Evento e1=recogeEvento(id);
                pasarPantalla(e1,usuario);

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tv7i.getText().toString());
                String usuario = tv7n.getText().toString();
                Evento e1=recogeEvento(id);
                pasarPantalla(e1,usuario);

            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasar = new Intent(PrincipalActivity.this,ListadoEventosActivity.class);
                pasar.putExtra("EMAIL",extras.getString("EMAIL"));
                pasar.putExtra("PASS",extras.getString("PASS"));
                pasar.putExtra("ID",extras.getInt("ID"));
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

                if(titulo.isEmpty()||descripcion.isEmpty()){
                    Toast.makeText(PrincipalActivity.this, "Debe rellenar los campos titulo y descripción", Toast.LENGTH_SHORT).show();
                }else{
                    gbd.insertarNoticia(extras.getInt("ID"),fechaHoraString,titulo,descripcion);

                    edit1.setText("");
                    edit2.setText("");
                }


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
            pasar.putExtra("ID",extras.getInt("ID"));
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
    public void textInvisibles(){
        tv3i.setVisibility(View.GONE);
        tv4i.setVisibility(View.GONE);
        tv5i.setVisibility(View.GONE);
        tv6i.setVisibility(View.GONE);
        tv7i.setVisibility(View.GONE);
    }
    public void rellenarEventos() {
        if (listaEventos.size() > 0) tv3n.setText(listaEventos.get(0));
        if (listaEventos.size() > 1) tv3i.setText(listaEventos.get(1));
        if (listaEventos.size() > 2) tv3e.setText(listaEventos.get(2));
        if (listaEventos.size() > 3) tv3l.setText(listaEventos.get(3));
        if (listaEventos.size() > 4) tv3f.setText(listaEventos.get(4));

        if (listaEventos.size() > 5) tv4n.setText(listaEventos.get(5));
        if (listaEventos.size() > 6) tv4i.setText(listaEventos.get(6));
        if (listaEventos.size() > 7) tv4e.setText(listaEventos.get(7));
        if (listaEventos.size() > 8) tv4l.setText(listaEventos.get(8));
        if (listaEventos.size() > 9) tv4f.setText(listaEventos.get(9));

        if (listaEventos.size() > 10) tv5n.setText(listaEventos.get(10));
        if (listaEventos.size() > 11) tv5i.setText(listaEventos.get(11));
        if (listaEventos.size() > 12) tv5e.setText(listaEventos.get(12));
        if (listaEventos.size() > 13) tv5l.setText(listaEventos.get(13));
        if (listaEventos.size() > 14) tv5f.setText(listaEventos.get(14));

        if (listaEventos.size() > 15) tv6n.setText(listaEventos.get(15));
        if (listaEventos.size() > 16) tv6i.setText(listaEventos.get(16));
        if (listaEventos.size() > 17) tv6e.setText(listaEventos.get(17));
        if (listaEventos.size() > 18) tv6l.setText(listaEventos.get(18));
        if (listaEventos.size() > 19) tv6f.setText(listaEventos.get(19));

        if (listaEventos.size() > 20) tv7n.setText(listaEventos.get(20));
        if (listaEventos.size() > 21) tv7i.setText(listaEventos.get(21));
        if (listaEventos.size() > 22) tv7e.setText(listaEventos.get(22));
        if (listaEventos.size() > 23) tv7l.setText(listaEventos.get(23));
        if (listaEventos.size() > 24) tv7f.setText(listaEventos.get(24));
    }

    public Evento recogeEvento(int id){
        Evento e1= gbd.getEvento(id);
        return e1;
    }

    public void pasarPantalla(Evento e1,String usuario){
        pasar = new Intent(PrincipalActivity.this, InfoEventoActivity.class);
        pasar.putExtra("EVENTO",e1.getNombre());
        pasar.putExtra("FECHA",e1.getFecha());
        pasar.putExtra("HORA",e1.getHora());
        pasar.putExtra("LUGAR",e1.getUbicacion());
        pasar.putExtra("USUARIO",usuario);
        pasar.putExtra("DESCRIP",e1.getDescripcion());
        pasar.putExtra("FOTO",e1.getSeleccion());
        pasar.putExtra("EMAIL",extras.getString("EMAIL"));
        pasar.putExtra("PASS",extras.getString("PASS"));
        pasar.putExtra("ID",extras.getString("ID"));
        startActivity(pasar);
    }

}