package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListadoEventosActivity extends AppCompatActivity {
    protected RecyclerView rv1;
    protected ImageButton ib1;
    protected Intent atras;
    protected Bundle extras;
    protected GestorDeBD gbd;
    protected List<Evento> listaEventos;
    protected String[] nombre,fecha,hora,seleccion;
    protected Evento e1;
    protected Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_eventos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();
        gbd = new GestorDeBD(this);

        rv1 = findViewById(R.id.rv1_eventos);
        ib1 = findViewById(R.id.ib1_eventos_atras);
        b1 = findViewById(R.id.b1_eventos);

        extras = getIntent().getExtras();


        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv1.setLayoutManager(llm);

        rv1.setAdapter(new AdaptadorFrutas());

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(ListadoEventosActivity.this, PrincipalActivity.class);
                atras.putExtra("EMAIL",extras.getString("EMAIL"));
                atras.putExtra("PASS",extras.getString("PASS"));
                atras.putExtra("ID",extras.getInt("ID"));
                startActivity(atras);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(ListadoEventosActivity.this, CrearEventoActivity.class);
                atras.putExtra("ID",extras.getInt("ID"));
                atras.putExtra("EMAIL",extras.getString("EMAIL"));
                atras.putExtra("PASS",extras.getString("PASS"));
                startActivity(atras);
            }
        });

        listaEventos = gbd.getAllEvento();

        nombre = new String[listaEventos.size()];
        fecha = new String[listaEventos.size()];
        hora = new String[listaEventos.size()];
        seleccion= new String[listaEventos.size()];

        for (int i=0;i< listaEventos.size();i++){
            e1 = listaEventos.get(i);

            nombre[i] = e1.getNombre();
            fecha[i] = e1.getFecha();
            hora[i] = e1.getHora();
            seleccion[i] = e1.getSeleccion();
        }

    }

    private class AdaptadorFrutas extends RecyclerView.Adapter<AdaptadorFrutas.AdaptadorFrutasHolder> {

        @NonNull
        @Override
        public AdaptadorFrutasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorFrutasHolder(getLayoutInflater().inflate(R.layout.item_listado_eventos,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorFrutasHolder holder, int position) {
            holder.imprimir(position);

        }

        @Override
        public int getItemCount() {
            return listaEventos.size();
        }
        private class AdaptadorFrutasHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView tv1,tv2,tv3;
            ImageView iv1;
            public AdaptadorFrutasHolder(@NonNull View itemView) {
                super(itemView);
                iv1 = itemView.findViewById(R.id.item_iv1_listado_eventos);
                tv1 = itemView.findViewById(R.id.item_tv1_listado_evento_nombre);
                tv2 = itemView.findViewById(R.id.item_tv2_listado_evento_fecha);
                tv3 = itemView.findViewById(R.id.item_tv3_listado_evento_hora);
                itemView.setOnClickListener(this);
            }

            public void imprimir(int position) {

                tv1.setText(nombre[position]);
                tv2.setText(fecha[position]);
                tv3.setText(hora[position]);

                if(seleccion[position].equalsIgnoreCase("Campo")){
                    iv1.setImageResource(R.drawable.campo);
                }
                if(seleccion[position].equalsIgnoreCase("Playa")){
                    iv1.setImageResource(R.drawable.playa);
                }
                if(seleccion[position].equalsIgnoreCase("Domicilio")){
                    iv1.setImageResource(R.drawable.domicilio);
                }
                if(seleccion[position].equalsIgnoreCase("Pipicam")){
                    iv1.setImageResource(R.drawable.pipican);
                }
                if(seleccion[position].equalsIgnoreCase("Paseo")){
                    iv1.setImageResource(R.drawable.paseo);
                }
                if(seleccion[position].equalsIgnoreCase("Tienda de mascotas")){
                    iv1.setImageResource(R.drawable.tiendamascotas);
                }
                if(seleccion[position].equalsIgnoreCase("Parque")){
                    iv1.setImageResource(R.drawable.parque);
                }
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(ListadoEventosActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        }
    }
}