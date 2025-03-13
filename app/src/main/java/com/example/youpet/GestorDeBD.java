package com.example.youpet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GestorDeBD extends SQLiteOpenHelper {
    protected SQLiteDatabase db;

    public GestorDeBD(@Nullable Context context) {
        super(context, "youpet", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,apellidos TEXT,telefono TEXT,email TEXT,fechaNacimiento TEXT,direccion TEXT,poblacion TEXT,provincia TEXT,contrasenia TEXT,imagen BLOB)");
        db.execSQL("CREATE TABLE mascota (id INTEGER PRIMARY KEY AUTOINCREMENT, usuarioId INTEGER, nombre TEXT, tipo TEXT, fechaNacimiento TEXT, tamano TEXT, sexo TEXT, castrado TEXT, sociabilidad TEXT, imagen BLOB, FOREIGN KEY(usuarioId) REFERENCES usuario(id))");
        db.execSQL("CREATE TABLE evento (id INTEGER PRIMARY KEY AUTOINCREMENT,usuarioId INTEGER,nombre TEXT,descripcion TEXT,fecha TEXT,hora TEXT,ubicacion TEXT, FOREIGN KEY(usuarioId) REFERENCES usuario(id))");
        db.execSQL("CREATE TABLE noticia (id INTEGER PRIMARY KEY AUTOINCREMENT,usuarioId INTEGER,fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, titulo TEXT, descripcion TEXT, FOREIGN KEY(usuarioId) REFERENCES usuario(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Insertar usuario
    public void insertarUsuario(String nombre,String apellidos,String telefono,String email,String fechaNacimiento,String direccion,String poblacion,String provincia,String contrasenia,byte[] imagen){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuario VALUES(null,'"+nombre+"','"+apellidos+"','"+telefono+"','"+email+"','"+fechaNacimiento+"','"+direccion+"','"+poblacion+"','"+provincia+"','"+contrasenia+"',"+ Arrays.toString(imagen) +")");
    }
    //Insertar mascota
    public void insertarMascotas(int usuarioId,String nombre,String tipo,String fecha,String tamano,String sexo,String castrado,String sociabilidad,byte[] imagen){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO mascota VALUES(null,"+usuarioId+",'"+nombre+"','"+tipo+"','"+fecha+"','"+tamano+"','"+sexo+"','"+castrado+"','"+sociabilidad+"',"+ Arrays.toString(imagen) +")");
    }
    //Insertar evento
    public void insertarEvento(int usuarioId,String nombre,String descripcion,String fecha,String hora,String ubicacion){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO evento VALUES(null,"+usuarioId+",'"+nombre+"','"+descripcion+"','"+fecha+"','"+hora+"','"+ubicacion+"')");
    }
    public void insertarNoticia(int usuarioId, Date fecha, String titulo, String descripcion){
        db = this.getWritableDatabase();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = sdf.format(fecha);
        db.execSQL("INSERT INTO noticia VALUES(null," + usuarioId + ",'" + fechaFormateada + "','" + titulo + "','" + descripcion + "')");
    }

}

