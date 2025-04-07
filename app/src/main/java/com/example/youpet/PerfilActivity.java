package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int PICK_IMAGEN = 2;
    protected RecyclerView rv1,rv2;
    protected ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7;
    protected Button b1,b2;
    protected ImageView iv1,iv1t,iv1e;
    protected TextView tv1;
    protected EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit8t;
    protected Intent atras;
    protected Bundle extra;
    protected String email,contrasenia;
    protected Usuario u1;
    protected GestorDeBD gbd;
    protected List<Mascota> listaMascota;
    protected List<Evento> listaEvento;

    protected int[] id,usuarioId,idE,usarioIdE;
    protected String[] nombre,nombreE,tipo,edad,tamanio,sexo,castrado,sociabilidad,descripcion,fechaE,hora,ubicacion,seleccion;
    protected byte[][] imagenes;
    protected Mascota m1;
    protected Evento e1;
    protected boolean edicionMascotaHabilitada = false;
    protected boolean edicionUsuarioHabilitada = false;
    protected boolean edicionEventoHabilitada = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();

        //bloque de referenciacion
        rv1 = findViewById(R.id.rv1_perfil);
        rv2 = findViewById(R.id.rv2_perfil);
        ib1 = findViewById(R.id.ib1_perfil_atras);
        ib2 = findViewById(R.id.ib2_perfil_editar_foto);
        ib3 = findViewById(R.id.ib3_perfil_editar_datos_usuario);
        ib4 = findViewById(R.id.ib4_perfil_editar_datos_mascota);
        ib5 = findViewById(R.id.ib5_perfil_aniadir);
        ib6 = findViewById(R.id.ib6_perfil_editar_datos_eventos);
        ib7 = findViewById(R.id.ib7_perfil_aniadir);
        b1 = findViewById(R.id.b1_perfil_guardar);
        b2 = findViewById(R.id.b2_perfil_eliminar);
        iv1 = findViewById(R.id.iv1_perfil);
        tv1 = findViewById(R.id.tv1_perfil_usuario);
        edit1 = findViewById(R.id.edit1_perfil_nombre);
        edit2 = findViewById(R.id.edit2_perfil_apellidos);
        edit3 = findViewById(R.id.edit3_perfil_fecha);
        edit4 = findViewById(R.id.edit4_perfil_poblacion);
        edit5 = findViewById(R.id.edit5_perfil_telefono);
        edit6 = findViewById(R.id.edit6_perfil_email);
        edit7 = findViewById(R.id.edit7_perfil_direccion);
        edit8 = findViewById(R.id.edit8_perfil_provincia);
        edit9 = findViewById(R.id.edit9_perfil_imagen);

        //metodo que inhabilita los EditText
        desabilitarEdicion();

        //recogemos datos de otras pantallas
        extra = getIntent().getExtras();

        //apartir del email y de la contraseña creamos un objeto de tipo Usuario
        if (extra != null) {
            email = extra.getString("EMAIL");
            contrasenia = extra.getString("PASS");
        } else {
            Toast.makeText(this, "No se recibieron datos extra.", Toast.LENGTH_SHORT).show();
            return; // Opcionalmente termina aquí si no hay extras
        }
        gbd = new GestorDeBD(this);
        u1 = gbd.usuarioConectado(email, contrasenia);

        //recuperamos la imagen y el resto de datos para mostrarlos
        if (u1 != null) {
            byte[] imagenBytes = u1.getImagen();
            if (imagenBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                iv1.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "La imagen no carga.", Toast.LENGTH_SHORT).show();
            }

            tv1.setText(u1.getNombre());
            edit1.setText(u1.getNombre());
            edit2.setText(u1.getApellidos());
            edit3.setText(u1.getFechaNacimiento());
            edit4.setText(u1.getPoblacion());
            edit5.setText(u1.getTelefono());
            edit6.setText(u1.getEmail());
            edit7.setText(u1.getDireccion());
            edit8.setText(u1.getProvincia());
        } else {
            Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        //llamaos a la construcion de los RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv1.setLayoutManager(llm);
        rv1.setAdapter(new AdaptadorMascotas());

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        rv2.setLayoutManager(llm2);
        rv2.setAdapter(new AdaptadorEventos());

        //recuperamos los datos de las mascotas de un Usuario por su ID
        listaMascota = gbd.recuperarMascotasPorUsuario(extra.getInt("ID"));
        //inicializamos los Arrays necesarios para los RecyclerView
        id = new int[listaMascota.size()];
        usuarioId = new int[listaMascota.size()];
        nombre = new String[listaMascota.size()];
        tipo = new String[listaMascota.size()];
        edad = new String[listaMascota.size()];
        tamanio = new String[listaMascota.size()];
        sexo = new String[listaMascota.size()];
        castrado = new String[listaMascota.size()];
        sociabilidad = new String[listaMascota.size()];
        imagenes = new byte[listaMascota.size()][];

        listaEvento = gbd.recuperarEventosPorUsuario(extra.getInt("ID"));

        idE = new int[listaEvento.size()];
        usarioIdE = new int[listaEvento.size()];
        nombreE = new String[listaEvento.size()];
        descripcion = new String[listaEvento.size()];
        fechaE = new String[listaEvento.size()];
        hora = new String[listaEvento.size()];
        ubicacion= new String[listaEvento.size()];
        seleccion= new String[listaEvento.size()];

        //rellenamos con un bucle for los Arrays
        for (int i = 0; i < listaMascota.size(); i++) {

            m1 = listaMascota.get(i);

            id[i] = m1.getId();
            usuarioId[i] = m1.getUsuarioId();
            nombre[i] = m1.getNombre();
            tipo[i] = m1.getTipo();
            edad[i] = m1.getFecha();
            tamanio[i] = m1.getTamano();
            sexo[i] = m1.getSexo();
            castrado[i] = m1.getCastrado();
            sociabilidad[i] = m1.getSociabilidad();
            imagenes[i] = m1.getImagen();
        }

        for (int i=0;i< listaEvento.size();i++){
            e1 = listaEvento.get(i);

            idE[i] = e1.getId();
            usuarioId[i] = e1.getUsuarioId();
            nombreE[i] = e1.getNombre();
            descripcion[i] = e1.getDescripcion();
            fechaE[i] = e1.getFecha();
            hora[i] = e1.getHora();
            ubicacion[i] = e1.getUbicacion();
            seleccion[i] = e1.getSeleccion();
        }

        //ImagenButton para ir hacia atras
        ib1.setOnClickListener(v -> {
            atras = new Intent(PerfilActivity.this, PrincipalActivity.class);
            atras.putExtra("ID",extra.getInt("ID"));
            atras.putExtra("EMAIL",extra.getString("EMAIL"));
            atras.putExtra("PASS",extra.getString("PASS"));
            startActivity(atras);

        });

        //ImagenButton para editar los datos del Usuario
        ib3.setOnClickListener(v -> {

            edicionUsuarioHabilitada = !edicionUsuarioHabilitada;
            if (edicionUsuarioHabilitada) {
                edit1.setEnabled(true);
                edit1.setBackgroundColor(Color.WHITE);
                edit2.setEnabled(true);
                edit2.setBackgroundColor(Color.WHITE);
                edit3.setEnabled(true);
                edit3.setBackgroundColor(Color.WHITE);
                edit4.setEnabled(true);
                edit4.setBackgroundColor(Color.WHITE);
                edit5.setEnabled(true);
                edit5.setBackgroundColor(Color.WHITE);
                edit6.setEnabled(true);
                edit6.setBackgroundColor(Color.WHITE);
                edit7.setEnabled(true);
                edit7.setBackgroundColor(Color.WHITE);
                edit8.setEnabled(true);
                edit8.setBackgroundColor(Color.WHITE);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                ib2.setVisibility(View.VISIBLE);
            }else{
                edit1.setEnabled(false);
                edit1.setBackgroundColor(Color.TRANSPARENT);
                edit2.setEnabled(false);
                edit2.setBackgroundColor(Color.TRANSPARENT);
                edit3.setEnabled(false);
                edit3.setBackgroundColor(Color.TRANSPARENT);
                edit4.setEnabled(false);
                edit4.setBackgroundColor(Color.TRANSPARENT);
                edit5.setEnabled(false);
                edit5.setBackgroundColor(Color.TRANSPARENT);
                edit6.setEnabled(false);
                edit6.setBackgroundColor(Color.TRANSPARENT);
                edit7.setEnabled(false);
                edit7.setBackgroundColor(Color.TRANSPARENT);
                edit8.setEnabled(false);
                edit8.setBackgroundColor(Color.TRANSPARENT);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                ib2.setVisibility(View.INVISIBLE);

            }
        });

        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(PerfilActivity.this, CrearCuentaMascotasActivity.class);
                atras.putExtra("ID",extra.getInt("ID"));
                atras.putExtra("EMAIL",extra.getString("EMAIL"));
                atras.putExtra("PASS",extra.getString("PASS"));
                startActivity(atras);
            }
        });

        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras = new Intent(PerfilActivity.this, CrearEventoActivity.class);
                atras.putExtra("ID",extra.getInt("ID"));
                startActivity(atras);
            }
        });


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nombre = edit1.getText().toString();
                    String apellidos = edit2.getText().toString();
                    String fecha = edit3.getText().toString();
                    String poblacion = edit4.getText().toString();
                    String telefono = edit5.getText().toString();
                    String email = edit6.getText().toString();
                    String direccion = edit7.getText().toString();
                    String provincia = edit8.getText().toString();
                    byte[] imagen = (byte[]) edit9.getTag();

                    if (nombre.isEmpty()||apellidos.isEmpty()||fecha.isEmpty()||poblacion.isEmpty()||telefono.isEmpty()||email.isEmpty()||direccion.isEmpty()||provincia.isEmpty()){
                        Toast.makeText(PerfilActivity.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(imagen == null ||imagen.length ==0) {
                        boolean esActualizable = gbd.actualizarUsuario(extra.getInt("ID"), nombre, apellidos, telefono, email, fecha, direccion, poblacion, provincia, extra.getString("PASS"), u1.getImagen());


                        if (esActualizable){
                            Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PerfilActivity.this, "Ocurrio algun error inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        boolean esActualizable = gbd.actualizarUsuario(extra.getInt("ID"), nombre, apellidos, telefono, email, fecha, direccion, poblacion, provincia, extra.getString("PASS"), imagen);

                        if (esActualizable){
                            Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PerfilActivity.this, "Ocurrio algun error inesperado", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Verifica el estado
                    desabilitarEdicion();
                    edit1.setBackgroundColor(Color.TRANSPARENT);
                    edit2.setBackgroundColor(Color.TRANSPARENT);
                    edit3.setBackgroundColor(Color.TRANSPARENT);
                    edit4.setBackgroundColor(Color.TRANSPARENT);
                    edit5.setBackgroundColor(Color.TRANSPARENT);
                    edit6.setBackgroundColor(Color.TRANSPARENT);
                    edit7.setBackgroundColor(Color.TRANSPARENT);
                    edit8.setBackgroundColor(Color.TRANSPARENT);
                    rv1.getAdapter().notifyDataSetChanged();

                }
            });


        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        //ImagenButton para habilitar los campos del RecyclerView
        ib4.setOnClickListener(v -> {
            edicionMascotaHabilitada = !edicionMascotaHabilitada;
            Log.d("PerfilActivity", "Edición habilitada: " + edicionMascotaHabilitada); // Verifica el estado
            rv1.getAdapter().notifyDataSetChanged();
        });

        ib6.setOnClickListener(v -> {
            edicionEventoHabilitada = !edicionEventoHabilitada;
            Log.d("PerfilActivity", "Edición habilitada: " + edicionEventoHabilitada); // Verifica el estado
            rv2.getAdapter().notifyDataSetChanged();
        });


    }

    public void desabilitarEdicion() {
        edit1.setEnabled(false);
        edit2.setEnabled(false);
        edit3.setEnabled(false);
        edit4.setEnabled(false);
        edit5.setEnabled(false);
        edit6.setEnabled(false);
        edit7.setEnabled(false);
        edit8.setEnabled(false);
        edit9.setVisibility(View.GONE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        ib2.setVisibility(View.INVISIBLE);
    }

    private class AdaptadorMascotas extends RecyclerView.Adapter<AdaptadorMascotas.AdaptadorMascotasHolder> {

        @NonNull
        @Override
        public AdaptadorMascotasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorMascotasHolder(getLayoutInflater().inflate(R.layout.item_perfil_mascota, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorMascotasHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return listaMascota.size();
        }

        private class AdaptadorMascotasHolder extends RecyclerView.ViewHolder {

            EditText edit1t, edit2t, edit3t, edit4t, edit5t, edit6t, edit7t;

            Button b1t, b2t;
            ImageButton ib1t;

            public AdaptadorMascotasHolder(@NonNull View itemView) {
                super(itemView);

                edit1t = itemView.findViewById(R.id.item_edit1_mascotas_nombre);
                edit2t = itemView.findViewById(R.id.item_edit2_mascotas_tipo);
                edit3t = itemView.findViewById(R.id.item_edit3_mascotas_edad);
                edit4t = itemView.findViewById(R.id.item_edit4_mascotas_tamano);
                edit5t = itemView.findViewById(R.id.item_edit5_mascotas_sexo);
                edit6t = itemView.findViewById(R.id.item_edit6_mascotas_castrado);
                edit7t = itemView.findViewById(R.id.item_edit7_mascotas_sociabilidad);
                edit8t = itemView.findViewById(R.id.item_edit8_mascotas_imagen);
                iv1t = itemView.findViewById(R.id.item_iv1_mascota);
                b1t = itemView.findViewById(R.id.item_b1_mascotas_guardar);
                b2t = itemView.findViewById(R.id.item_b2_mascotas_eliminar);
                ib1t = itemView.findViewById(R.id.item_ib1_mascotas_subir);
            }

            @SuppressLint("ResourceType")
            public void imprimir(int position) {
                // Configuración de la imagen de la mascota
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenes[position], 0, imagenes[position].length);
                iv1t.setImageBitmap(bitmap);

                // Establecer valores de texto
                edit1t.setText(nombre[position]);
                edit2t.setText(tipo[position]);
                edit3t.setText(edad[position]);
                edit4t.setText(tamanio[position]);
                edit5t.setText(sexo[position]);
                edit6t.setText(castrado[position]);
                edit7t.setText(sociabilidad[position]);

                // Habilitar/deshabilitar la edición
                boolean esEditable = edicionMascotaHabilitada;
                edit1t.setEnabled(esEditable);
                edit2t.setEnabled(esEditable);
                edit3t.setEnabled(esEditable);
                edit4t.setEnabled(esEditable);
                edit5t.setEnabled(esEditable);
                edit6t.setEnabled(esEditable);
                edit7t.setEnabled(esEditable);
                edit8t.setVisibility(View.GONE);

                // Cambiar el color de fondo solo si la edición está habilitada
                if (esEditable) {
                    edit1t.setBackgroundColor(Color.WHITE);
                    edit2t.setBackgroundColor(Color.WHITE);
                    edit3t.setBackgroundColor(Color.WHITE);
                    edit4t.setBackgroundColor(Color.WHITE);
                    edit5t.setBackgroundColor(Color.WHITE);
                    edit6t.setBackgroundColor(Color.WHITE);
                    edit7t.setBackgroundColor(Color.WHITE);
                }else{
                    edit1t.setBackgroundColor(Color.TRANSPARENT);
                    edit2t.setBackgroundColor(Color.TRANSPARENT);
                    edit3t.setBackgroundColor(Color.TRANSPARENT);
                    edit4t.setBackgroundColor(Color.TRANSPARENT);
                    edit5t.setBackgroundColor(Color.TRANSPARENT);
                    edit6t.setBackgroundColor(Color.TRANSPARENT);
                    edit7t.setBackgroundColor(Color.TRANSPARENT);
                }

                // Mostrar/ocultar los botones
                ib1t.setVisibility(esEditable? View.VISIBLE : View.INVISIBLE);
                if (esEditable){
                    ib1t.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_IMAGEN);
                        }
                    });
                }
                b1t.setVisibility(esEditable ? View.VISIBLE : View.INVISIBLE);
                if (esEditable) {
                    b1t.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nombre = edit1t.getText().toString();
                            String tipo = edit2t.getText().toString();
                            String edad = edit3t.getText().toString();
                            String tamanio = edit4t.getText().toString();
                            String sexo = edit5t.getText().toString();
                            String castrado = edit6t.getText().toString();
                            String sociabilidad = edit7t.getText().toString();
                            byte[] imagen = (byte[]) edit8t.getTag();

                            if (nombre.isEmpty()||tipo.isEmpty()||edad.isEmpty()||tamanio.isEmpty()||sexo.isEmpty()||castrado.isEmpty()||sociabilidad.isEmpty()){
                                Toast.makeText(PerfilActivity.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(imagen == null ||imagen.length ==0) {
                                boolean esActualizable = gbd.actualizarMascota(extra.getInt("ID"), nombre, tipo, edad, tamanio, sexo, castrado, sociabilidad, imagenes[position]);


                                if (esActualizable){
                                    Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PerfilActivity.this, "Ocurrio algun error inesperado", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                boolean esActualizable = gbd.actualizarMascota(extra.getInt("ID"), nombre, tipo, edad, tamanio, sexo, castrado, sociabilidad, imagen);

                                if (esActualizable){
                                    Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PerfilActivity.this, "Ocurrio algun error inesperado", Toast.LENGTH_SHORT).show();
                                }
                            }
                            edicionMascotaHabilitada = !edicionMascotaHabilitada;
                            Log.d("PerfilActivity", "Edición habilitada: " + edicionMascotaHabilitada); // Verifica el estado
                            rv1.getAdapter().notifyDataSetChanged();

                        }
                    });
                }
                b2t.setVisibility(esEditable? View.VISIBLE : View.INVISIBLE);

                // Hacer que el ImageView sea clickeable solo cuando la edición está habilitada
                iv1t.setClickable(esEditable);
                iv1t.setEnabled(esEditable);
            }

        }
    }

    private class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.AdaptadorEventosHolder> {

        @NonNull
        @Override
        public AdaptadorEventosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorEventosHolder(getLayoutInflater().inflate(R.layout.item_perfil_eventos, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorEventosHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return listaEvento.size();
        }

        private class AdaptadorEventosHolder extends RecyclerView.ViewHolder {

            EditText edit1e, edit2e, edit3e, edit4e, edit5e;

            Button b1e, b2e;
            Spinner s1e;
            ArrayAdapter<String> adaptadorE;
            String[] itemSpinner ={"Campo","Playa","Domicilio","Pipicam","Paseo","Tienda de mascotas","Parque"};

            public AdaptadorEventosHolder(@NonNull View itemView) {
                super(itemView);

                edit1e = itemView.findViewById(R.id.item_edit1_eventos_usuario);
                edit2e = itemView.findViewById(R.id.item_edit2_eventos_mascota);
                edit3e = itemView.findViewById(R.id.item_edit3_eventos_fecha);
                edit4e = itemView.findViewById(R.id.item_edit4_eventos_hora);
                edit5e = itemView.findViewById(R.id.item_edit5_eventos_ubicacion);
                iv1e = itemView.findViewById(R.id.item_iv1_evento);
                b1e = itemView.findViewById(R.id.item_b1_eventos_guardar);
                b2e = itemView.findViewById(R.id.item_b2_eventos_eliminar);
                s1e = itemView.findViewById(R.id.item_s1_eventos);

                adaptadorE = new ArrayAdapter<String>(PerfilActivity.this, android.R.layout.simple_spinner_item,itemSpinner);
                s1e.setAdapter(adaptadorE);
            }

            @SuppressLint("ResourceType")
            public void imprimir(int position) {
                // Establecer valores de texto
                edit1e.setText(nombreE[position]);
                edit2e.setText(descripcion[position]);
                edit3e.setText(fechaE[position]);
                edit4e.setText(hora[position]);
                edit5e.setText(ubicacion[position]);
                if(seleccion[position].equalsIgnoreCase("campo")){
                    iv1e.setImageResource(R.drawable.campo);
                }
                if(seleccion[position].equalsIgnoreCase("playa")){
                    iv1e.setImageResource(R.drawable.playa);
                }
                if(seleccion[position].equalsIgnoreCase("domicilio")){
                    iv1e.setImageResource(R.drawable.domicilio);
                }
                if(seleccion[position].equalsIgnoreCase("pipicam")){
                    iv1e.setImageResource(R.drawable.pipican);
                }
                if(seleccion[position].equalsIgnoreCase("paseo")){
                    iv1e.setImageResource(R.drawable.paseo);
                }
                if(seleccion[position].equalsIgnoreCase("tienda de mascotas")){
                    iv1e.setImageResource(R.drawable.tiendamascotas);
                }
                if(seleccion[position].equalsIgnoreCase("parque")){
                    iv1e.setImageResource(R.drawable.parque);
                }


                // Habilitar/deshabilitar la edición
                boolean esEditable = edicionEventoHabilitada;
                edit1e.setEnabled(esEditable);
                edit2e.setEnabled(esEditable);
                edit3e.setEnabled(esEditable);
                edit4e.setEnabled(esEditable);
                edit5e.setEnabled(esEditable);
                s1e.setEnabled(esEditable);

                // Cambiar el color de fondo solo si la edición está habilitada
                if (esEditable) {
                    edit1e.setBackgroundColor(Color.WHITE);
                    edit2e.setBackgroundColor(Color.WHITE);
                    edit3e.setBackgroundColor(Color.WHITE);
                    edit4e.setBackgroundColor(Color.WHITE);
                    edit5e.setBackgroundColor(Color.WHITE);
                    s1e.setBackgroundColor(Color.WHITE);
                }else{
                    edit1e.setBackgroundColor(Color.TRANSPARENT);
                    edit2e.setBackgroundColor(Color.TRANSPARENT);
                    edit3e.setBackgroundColor(Color.TRANSPARENT);
                    edit4e.setBackgroundColor(Color.TRANSPARENT);
                    edit5e.setBackgroundColor(Color.TRANSPARENT);
                    s1e.setBackgroundColor(Color.TRANSPARENT);
                }

                // Mostrar/ocultar los botones
                b1e.setVisibility(esEditable ? View.VISIBLE : View.INVISIBLE);
                if (esEditable) {
                    b1e.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nombre = edit1e.getText().toString();
                            String descripcion = edit2e.getText().toString();
                            String fechaE = edit3e.getText().toString();
                            String hora = edit4e.getText().toString();
                            String ubicacion = edit5e.getText().toString();
                            String seleccion = s1e.getSelectedItem().toString();


                            if (nombre.isEmpty()||descripcion.isEmpty()||fechaE.isEmpty()||hora.isEmpty()||ubicacion.isEmpty()){
                                Toast.makeText(PerfilActivity.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                boolean esActualizable = gbd.actualizarEvento(extra.getInt("ID"), nombre, descripcion, fechaE, hora, ubicacion,seleccion);


                                if (esActualizable){
                                    Toast.makeText(PerfilActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PerfilActivity.this, "Ocurrio algun error inesperado", Toast.LENGTH_SHORT).show();
                                }
                            }
                            edicionEventoHabilitada = !edicionEventoHabilitada;
                            Log.d("PerfilActivity", "Edición habilitada: " + edicionEventoHabilitada); // Verifica el estado
                            rv1.getAdapter().notifyDataSetChanged();

                        }
                    });
                }
                b2e.setVisibility(esEditable? View.VISIBLE : View.INVISIBLE);

                // Hacer que el ImageView sea clickeable solo cuando la edición está habilitada
                iv1t.setClickable(esEditable);
                iv1t.setEnabled(esEditable);
            }

        }
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
                    edit9.setTag(imageBytes);

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

        if (requestCode == PICK_IMAGEN && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byte[] imageBytes = stream.toByteArray();

                    // Establece el Bitmap en el ImageView
                    iv1t.setImageBitmap(bitmap);

                    // Guarda la imagen como un Tag en el EditText (como ya hacías)
                    edit8t.setTag(imageBytes);

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