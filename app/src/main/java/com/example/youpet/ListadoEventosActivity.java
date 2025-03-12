package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class ListadoEventosActivity extends AppCompatActivity {
    protected RecyclerView rv1;
    protected ImageButton ib1;
    protected Intent atras;

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

        rv1 = findViewById(R.id.rv1_eventos);
        ib1 = findViewById(R.id.ib1_eventos_atras);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv1.setLayoutManager(llm);

        rv1.setAdapter(new AdaptadorFrutas());

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(ListadoEventosActivity.this, PrincipalActivity.class);
                startActivity(atras);

            }
        });
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
            return 0;
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

            }

            @Override
            public void onClick(View v) {
                Toast.makeText(ListadoEventosActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        }
    }
}