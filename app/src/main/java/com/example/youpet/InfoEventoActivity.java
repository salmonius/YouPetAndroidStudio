package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InfoEventoActivity extends AppCompatActivity {
    protected ImageButton ib1;
    protected Intent atras;
    protected Bundle extras;
    protected TextView tv1,tv3,tv5,tv7,tv9,tv11;
    protected ImageView iv1;
    protected Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        ib1 = findViewById(R.id.ib1_info_evento_atras);
        iv1 = findViewById(R.id.iv1_info_evento_foto);
        tv1 = findViewById(R.id.tv1_info_evento_nombre);
        tv3 = findViewById(R.id.tv3_info_evento_fecha_rellenable);
        tv5 = findViewById(R.id.tv5_info_evento_hora_rellenable);
        tv7 = findViewById(R.id.tv7_info_evento_ubicacion_rellenable);
        tv9 = findViewById(R.id.tv9_info_evento_creadopor_rellenable);
        tv11 = findViewById(R.id.tv11_info_evento_descripcion_rellenable);
        b1= findViewById(R.id.b1_info_evento);


        extras = getIntent().getExtras();

        if (extras!=null){
            tv1.setText(extras.getString("EVENTO"));
            tv3.setText(extras.getString("FECHA"));
            tv5.setText(extras.getString("HORA"));
            tv7.setText(extras.getString("LUGAR"));
            tv9.setText(extras.getString("USUARIO"));
            tv11.setText(extras.getString("DESCRIP"));
            if(extras.getString("FOTO").equalsIgnoreCase("Campo")){
                iv1.setImageResource(R.drawable.campo);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Playa")){
                iv1.setImageResource(R.drawable.playa);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Domicilio")){
                iv1.setImageResource(R.drawable.domicilio);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Pipicam")){
                iv1.setImageResource(R.drawable.pipican);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Paseo")){
                iv1.setImageResource(R.drawable.paseo);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Tienda de mascotas")){
                iv1.setImageResource(R.drawable.tiendamascotas);
            }
            if(extras.getString("FOTO").equalsIgnoreCase("Parque")){
                iv1.setImageResource(R.drawable.parque);
            }
        }

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(InfoEventoActivity.this, PrincipalActivity.class);
                atras.putExtra("EMAIL",extras.getString("EMAIL"));
                atras.putExtra("PASS",extras.getString("PASS"));
                atras.putExtra("ID",extras.getString("ID"));
                startActivity(atras);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoEventoActivity.this, "Te has apuntado al evento", Toast.LENGTH_SHORT).show();
            }
        });
    }
}