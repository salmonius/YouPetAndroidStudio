package com.example.youpet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsuarioAutenticadoTest {
    private GestorDeBD dbHelper;  // Cambia a tu clase de base de datos
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // Obtener el contexto de la aplicación (esto es necesario en las pruebas instrumentadas)
        Context context = ApplicationProvider.getApplicationContext();

        // Inicializar el helper de base de datos
        dbHelper = new GestorDeBD(context);

        // Usar la base de datos en memoria para pruebas (no persistente)
        db = dbHelper.getWritableDatabase();
    }

    @Test
    public void testInsertarUsuario() {
        // Preparar los datos de prueba
        String nombre = "Juan";
        String apellidos = "Pérez";
        String telefono = "123456789";
        String email = "juan@example.com";
        String fechaNacimiento = "1990-01-01";
        String direccion = "Calle Falsa 123";
        String poblacion = "Sevilla";
        String provincia = "Sevilla";
        String contrasenia = "contrasena123";
        byte[] imagen = new byte[0];  // No añadimos imagen en esta prueba

        // Insertar el usuario
        dbHelper.insertarUsuario(nombre, apellidos, telefono, email, fechaNacimiento, direccion, poblacion, provincia, contrasenia, imagen);

        // Realizar la consulta para comprobar si se insertó correctamente
        Cursor cursor = db.query("usuario", null, "email = ?", new String[]{email}, null, null, null);
        assertNotNull(cursor);
        cursor.moveToFirst();
        assertEquals("Juan", cursor.getString(cursor.getColumnIndex("nombre")));
        cursor.close();
    }

    @Test
    public void testAutenticarUsuario_Exitoso() {
        // Datos del usuario de prueba
        String email = "juan@example.com";
        String contrasenia = "contrasena123";

        // Insertar el usuario antes de autenticarlo
        String nombre = "Juan";
        String apellidos = "Pérez";
        String telefono = "123456789";
        String fechaNacimiento = "1990-01-01";
        String direccion = "Calle Falsa 123";
        String poblacion = "Sevilla";
        String provincia = "Sevilla";
        byte[] imagen = new byte[0];  // No añadimos imagen en esta prueba
        dbHelper.insertarUsuario(nombre, apellidos, telefono, email, fechaNacimiento, direccion, poblacion, provincia, contrasenia, imagen);

        // Ahora intentar autenticar al usuario con la contraseña correcta
        boolean autenticado = dbHelper.autenticarUsuario(email, contrasenia);
        assertTrue(autenticado); // Debería ser true si la autenticación es exitosa
    }

    @Test
    public void testAutenticarUsuario_Fallido() {
        // Intentamos autenticar con un email o contraseña incorrectos
        String email = "juan@example.com";
        String contrasenia = "contrasenaIncorrecta";

        // Intentar autenticar al usuario con la contraseña incorrecta
        boolean autenticado = dbHelper.autenticarUsuario(email, contrasenia);
        assertFalse(autenticado); // Debería ser false si la autenticación falla
    }

    @After
    public void tearDown() throws Exception {
        // Limpiar y cerrar la base de datos después de las pruebas
        dbHelper.close();
    }
}
