package com.example.youpet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class GestorDeBD extends SQLiteOpenHelper {
    protected SQLiteDatabase db;

    public GestorDeBD(@Nullable Context context) {
        super(context, "youpet", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,apellidos TEXT,telefono TEXT,email TEXT,fechaNacimiento TEXT,direccion TEXT,poblacion TEXT,provincia TEXT,imagen BLOB)");
        db.execSQL("CREATE TABLE mascota (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,tipo TEXT,fechaNacimiento TEXT,tamano TEXT,sexo TEXT,castrado TEXT,sociabilidad TEXT,imagen BLOB)");
        db.execSQL("CREATE TABLE evento (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,descripcion TEXT,fecha TEXT,hora TEXT,ubicacion TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Insertar usuario
    public void insertarUsuario(String nombre,String apellidos,String telefono,String email,String fechaNacimiento,String direccion,String poblacion,String provincia,byte[] imagen){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuario VALUES(null,'"+nombre+"','"+apellidos+"','"+telefono+"','"+email+"','"+fechaNacimiento+"','"+direccion+"','"+poblacion+"','"+provincia+"',"+ Arrays.toString(imagen) +")");
    }
    //Insertar mascota
    public void insertarMascotas(String nombre,String tipo,String fecha,String tamano,String sexo,String castrado,String sociabilidad,byte[] imagen){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuario VALUES(null,'"+nombre+"','"+tipo+"','"+fecha+"','"+tamano+"','"+sexo+"','"+castrado+"','"+sociabilidad+"',"+ Arrays.toString(imagen) +")");
    }
    //Insertar evento
    public void insertarEvento(String nombre,String descripcion,String fecha,String hora,String ubicacion){
        db = this.getWritableDatabase();
        db.execSQL("INSERT INTO usuario VALUES(null,'"+nombre+"','"+descripcion+"','"+fecha+"','"+hora+"','"+ubicacion+"')");
    }

}
