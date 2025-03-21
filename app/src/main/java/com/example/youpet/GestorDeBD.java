package com.example.youpet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GestorDeBD extends SQLiteOpenHelper {
    protected SQLiteDatabase db;

    public GestorDeBD(@Nullable Context context) {
        super(context, "youpet", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellidos TEXT, telefono TEXT, email TEXT, fechaNacimiento TEXT, direccion TEXT, poblacion TEXT, provincia TEXT, contrasenia TEXT, imagen BLOB)"); // Cambio aquí: fechaNacimiento -> fecha
        db.execSQL("CREATE TABLE mascota (id INTEGER PRIMARY KEY AUTOINCREMENT, usuarioId INTEGER, nombre TEXT, tipo TEXT, fecha TEXT, tamano TEXT, sexo TEXT, castrado TEXT, sociabilidad TEXT, imagen BLOB, FOREIGN KEY(usuarioId) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE)"); // Cambio aquí: fechaNacimiento -> fecha
        db.execSQL("CREATE TABLE evento (id INTEGER PRIMARY KEY AUTOINCREMENT, usuarioId INTEGER, nombre TEXT, descripcion TEXT, fecha TEXT, hora TEXT, ubicacion TEXT, FOREIGN KEY(usuarioId) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE noticia (id INTEGER PRIMARY KEY AUTOINCREMENT, usuarioId INTEGER, fecha_hora TEXT, titulo TEXT, descripcion TEXT, FOREIGN KEY(usuarioId) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    // Insertar usuario con ContentValues
    public void insertarUsuario(String nombre, String apellidos, String telefono, String email, String fechaNacimiento, String direccion, String poblacion, String provincia, String contrasenia, byte[] imagen) {
        db = this.getWritableDatabase();

        // Usamos ContentValues para pasar los valores
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("telefono", telefono);
        values.put("email", email);
        values.put("fechaNacimiento", fechaNacimiento);
        values.put("direccion", direccion);
        values.put("poblacion", poblacion);
        values.put("provincia", provincia);
        values.put("contrasenia", contrasenia);
        values.put("imagen", imagen); // Aquí pasamos directamente el array de bytes

        // Inserción de datos en la tabla usuario
        long resultado = db.insert("usuario", null, values);

        if (resultado == -1) {
            // Si la inserción falla
            Log.e("DB_ERROR", "Error al insertar el usuario en la base de datos");
        } else {
            // Si la inserción tiene éxito
            Log.d("DB_SUCCESS", "Usuario insertado con éxito, ID: " + resultado);
        }
    }

    //Insertar mascota
    public void insertarMascotas(int usuarioId,String nombre,String tipo,String fecha,String tamano,String sexo,String castrado,String sociabilidad,byte[] imagen){
        db = this.getWritableDatabase();

        // Usamos ContentValues para pasar los valores
        ContentValues values = new ContentValues();
        values.put("usuarioId", usuarioId);
        values.put("nombre", nombre);
        values.put("tipo", tipo);
        values.put("fecha", fecha);
        values.put("tamano", tamano);
        values.put("sexo", sexo);
        values.put("castrado", castrado);
        values.put("sociabilidad", sociabilidad);
        values.put("imagen", imagen); // Aquí pasamos directamente el array de bytes

        // Inserción de datos en la tabla usuario
        long resultado = db.insert("mascota", null, values);

        if (resultado == -1) {
            // Si la inserción falla
            Log.e("DB_ERROR", "Error al insertar el mascota en la base de datos");
        } else {
            // Si la inserción tiene éxito
            Log.d("DB_SUCCESS", "Mascota insertada con éxito, ID: " + resultado);
        }
    }
    //Insertar evento
    public void insertarEvento(int usuarioId,String nombre,String descripcion,String fecha,String hora,String ubicacion){
        db = this.getWritableDatabase();

        // Usamos ContentValues para pasar los valores
        ContentValues values = new ContentValues();
        values.put("usuarioId", usuarioId);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("fecha", fecha);
        values.put("hora", hora);
        values.put("ubicacion", ubicacion);

        // Inserción de datos en la tabla usuario
        long resultado = db.insert("evento", null, values);

        if (resultado == -1) {
            // Si la inserción falla
            Log.e("DB_ERROR", "Error al insertar el evento en la base de datos");
        } else {
            // Si la inserción tiene éxito
            Log.d("DB_SUCCESS", "Evento insertado con éxito, ID: " + resultado);
        }
    }
    public void insertarNoticia(int usuarioId, String fecha_hora, String titulo, String descripcion){
        db = this.getWritableDatabase();


        // Usamos ContentValues para evitar construir consultas SQL manuales
        ContentValues values = new ContentValues();
        values.put("usuarioId", usuarioId);
        values.put("fecha_hora", fecha_hora); // Campo 'DATETIME'
        values.put("titulo", titulo);
        values.put("descripcion", descripcion);

        // Inserción de los valores en la tabla 'noticia'
        long resultado = db.insert("noticia", null, values);

        if (resultado == -1) {
            // Si la inserción falla
            Log.e("DB_ERROR", "Error al insertar la noticia en la base de datos");
        } else {
            // Si la inserción tiene éxito
            Log.d("DB_SUCCESS", "Noticia insertada con éxito, ID: " + resultado);
        }
    }

    public boolean actualizarUsuario(int id, String nombre, String apellidos, String telefono, String email, String fechaNacimiento, String direccion, String poblacion, String provincia,String contrasenia, byte[] imagen) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("apellidos", apellidos);
        values.put("telefono", telefono);
        values.put("email", email);
        values.put("fechaNacimiento", fechaNacimiento);
        values.put("direccion", direccion);
        values.put("poblacion", poblacion);
        values.put("provincia", provincia);
        values.put("contrasenia", contrasenia);
        values.put("imagen", imagen);

        int filasAfectadas = db.update("usuario", values, "id = ?", new String[]{String.valueOf(id)});
        return filasAfectadas > 0; // Retorna true si se actualizó al menos una fila
    }


    public boolean actualizarMascota(int id, String nombre, String tipo, String fecha, String tamano, String sexo, String castrado, String sociabilidad,byte[] imagen) {
        // Abrimos la base de datos en modo escritura
        db = this.getWritableDatabase();

        // Creamos un ContentValues para almacenar los datos que queremos actualizar
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("tipo", tipo);
        values.put("fecha", fecha);
        values.put("tamano", tamano);
        values.put("sexo", sexo);
        values.put("castrado", castrado);
        values.put("sociabilidad", sociabilidad);
        values.put("imagen", imagen);


        // Actualizamos el registro en la tabla "mascotas" donde el ID coincida con el que nos pasaron
        int filasAfectadas = db.update("mascota", values, "id = ?", new String[]{String.valueOf(id)});
        return filasAfectadas > 0; // Retorna true si se actualizó al menos una fila

    }
    public boolean autenticarUsuario(String email, String contrasenia) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuario WHERE email = ? AND contrasenia = ?", new String[]{email, contrasenia});

        boolean autenticado = cursor.moveToFirst(); // Devuelve true si encontró un usuario
        cursor.close();
        return autenticado;
    }
    public Usuario usuarioConectado(String email, String contrasenia) {
        db = this.getReadableDatabase();
        Usuario usuario = null;

        // Usa consultas parametrizadas para evitar inyección SQL
        Cursor cursor = db.rawQuery("SELECT * FROM usuario WHERE email = ? AND contrasenia = ?", new String[]{email, contrasenia});

        if (cursor.moveToFirst()) { // Si encuentra un registro
            // Extrae los datos del cursor y crea el objeto Usuario
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            String apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"));
            String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
            String fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento"));
            String direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"));
            String poblacion = cursor.getString(cursor.getColumnIndexOrThrow("poblacion"));
            String provincia = cursor.getString(cursor.getColumnIndexOrThrow("provincia"));
            String contraseña = cursor.getString(cursor.getColumnIndexOrThrow("contrasenia"));
            byte[] imagen = cursor.getBlob(cursor.getColumnIndexOrThrow("imagen")); // Campo tipo BLOB

            // Crea el objeto Usuario
            usuario = new Usuario(id, nombre, apellidos, telefono, email, fechaNacimiento,
                    direccion, poblacion, provincia, contraseña, imagen);
        }

        cursor.close(); // Siempre cierra el cursor para liberar recursos
        return usuario; // Devuelve el objeto Usuario o null si no se encontró
    }

    public List<Mascota> recuperarMascotasPorUsuario(int idU) {
        db = this.getReadableDatabase();
        List<Mascota> listaMascotas = new ArrayList<>();

        // Usa una consulta parametrizada para obtener todas las mascotas de un usuario específico
        Cursor cursor = db.rawQuery("SELECT * FROM mascota WHERE usuarioId = ?", new String[]{String.valueOf(idU)});

        if (cursor.moveToFirst()) {
            do {
                // Extrae los datos del cursor y crea el objeto Mascota
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow("usuarioId"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String tamano = cursor.getString(cursor.getColumnIndexOrThrow("tamano"));
                String sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"));
                String castrado = cursor.getString(cursor.getColumnIndexOrThrow("castrado"));
                String sociabilidad = cursor.getString(cursor.getColumnIndexOrThrow("sociabilidad"));
                byte[] imagen = cursor.getBlob(cursor.getColumnIndexOrThrow("imagen")); // Campo tipo BLOB

                // Crea el objeto Mascota y añádelo a la lista
                Mascota mascota = new Mascota(id, usuarioId, nombre, tipo, fecha, tamano, sexo, castrado, sociabilidad, imagen);
                listaMascotas.add(mascota);
            } while (cursor.moveToNext()); // Continúa mientras haya más filas
        }

        cursor.close(); // Siempre cierra el cursor para liberar recursos
        return listaMascotas; // Devuelve la lista de mascotas para el usuario con idU (vacía si no se encuentra ninguna)
    }





}

