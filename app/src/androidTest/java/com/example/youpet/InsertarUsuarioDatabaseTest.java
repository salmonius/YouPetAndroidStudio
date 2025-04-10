package com.example.youpet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InsertarUsuarioDatabaseTest {
    private GestorDeBD dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // Obtener el contexto de la aplicación
        Context context = ApplicationProvider.getApplicationContext();
        // Inicializar el helper de base de datos
        dbHelper = new GestorDeBD(context);
        // Obtener base de datos en modo escritura
        db = dbHelper.getWritableDatabase();
    }

    @Test
    public void testInsertarUsuario() {
        // Datos de prueba
        String nombre = "Juan";
        String apellidos = "Pérez";
        String telefono = "123456789";
        String email = "juan@example.com";
        String fechaNacimiento = "1990-01-01";
        String direccion = "Calle Falsa 123";
        String poblacion = "Sevilla";
        String provincia = "Sevilla";
        String contrasenia = "contrasena123";
        byte[] imagen = new byte[0]; // Imagen vacía para la prueba

        // Insertar el usuario
        dbHelper.insertarUsuario(nombre, apellidos, telefono, email, fechaNacimiento,
                direccion, poblacion, provincia, contrasenia, imagen);

        // Verificar que se haya insertado correctamente
        Cursor cursor = db.query("usuario", null, "email = ?", new String[]{email}, null, null, null);
        assertNotNull(cursor);
        cursor.moveToFirst();
        assertEquals("Juan", cursor.getString(cursor.getColumnIndex("nombre")));
        cursor.close();
    }

    @After
    public void tearDown() throws Exception {
        // Cerrar base de datos
        dbHelper.close();
    }
}
