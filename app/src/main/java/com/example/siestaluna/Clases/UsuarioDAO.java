package com.example.siestaluna.Clases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.siestaluna.DataBase.DatabaseHelper;

import java.io.IOException;

public class UsuarioDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
       dbHelper = new DatabaseHelper(context,null);
    }

    //Metodo para obtener el registro de usuario
    public Usuario obtenerUsuario(String correo, String contrasenia) {
        database = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String selection = "correo = ? AND contrasenia = ?";
        String[] selectionArgs = {correo,contrasenia};

        Cursor cursor = database.query("Usuario", null, selection, selectionArgs, null, null, null);


        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setCorreo(cursor.getString(0));
            usuario.setNombres(cursor.getString(1));
            usuario.setApellidos(cursor.getString(2));
            usuario.setFechaNacimiento(cursor.getString(3));
            usuario.setNacionalidad(cursor.getString(4));
            usuario.setTelefono(cursor.getString(5));
            usuario.setContrasenia(cursor.getString(6));
            usuario.setRol(cursor.getInt(7));
        }else{
            usuario = null;
        }

        cursor.close();
        database.close();
        return usuario;
    }


    //Metodo para actualizar al usuario
    public boolean actualizarUsuario(Usuario usuario) {
        database = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombres", usuario.getNombres());
        valores.put("apellidos", usuario.getApellidos());
        valores.put("fechaNacimiento", usuario.getFechaNacimiento());
        valores.put("nacionalidad", usuario.getNacionalidad());
        valores.put("telefono", usuario.getTelefono());
        valores.put("contrasenia", usuario.getContrasenia());
        valores.put("rol", usuario.getRol());

        String whereClause = "correo = ?";
        String[] whereArgs = {usuario.getCorreo()};

        int filasActualizadas = database.update("Usuario", valores, whereClause, whereArgs);
        database.close();
        return filasActualizadas > 0;
    }


    //Metodo para registrar a usuario
    public boolean registrarCliente(Usuario cliente) {
        database = dbHelper.getWritableDatabase();

        boolean resultado = false,comprobarCorreo = false;
        comprobarCorreo = verificarCorreoExistente(cliente.getCorreo());
        if (!comprobarCorreo){
            ContentValues values = new ContentValues();
            values.put("correo", cliente.getCorreo());
            values.put("nombres", cliente.getNombres());
            values.put("apellidos", cliente.getApellidos());
            values.put("fechaNacimiento", cliente.getFechaNacimiento());
            values.put("nacionalidad", cliente.getNacionalidad());
            values.put("telefono", cliente.getTelefono());
            values.put("contrasenia", cliente.getContrasenia());
            values.put("rol", cliente.getRol());

            long d = database.insert("Usuario", null, values);
            resultado = true;
        }

        database.close();
        return resultado ;
    }

    //Verificar correo
    public boolean verificarCorreoExistente(String correo) {
         database = dbHelper.getReadableDatabase();
        String[] columns = {"correo"};
        String selection = "correo = ?";
        String[] selectionArgs = {correo};

        Cursor cursor = database.query("Usuario", columns, selection, selectionArgs, null, null, null);

        boolean existe = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }


        return existe;
    }

    //Metodo para obtener la contraseña de una cuenta
    public String obtenerContrasena(String correo) {
        database = dbHelper.getReadableDatabase();
        String selection = "correo = ?";
        String[] selectionArgs = {correo};
        String contrasena="";

        Cursor cursor = database.query("Usuario", null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            contrasena = cursor.getString(cursor.getColumnIndex("contrasenia"));
        }
        cursor.close();
        database.close();
        return contrasena;
    }

    //Metodo de solo prueba
    public void mostrarDatosUsuarios() {
       database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Usuario";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String correo = cursor.getString(0);
            String nombres = cursor.getString(1);
            String apellidos = cursor.getString(2);
            // Obtener otros campos según la estructura de la tabla

            // Mostrar los datos
            Log.d("DATOS_USUARIO", "Correo: " + correo);
            Log.d("DATOS_USUARIO", "Nombres: " + nombres);
            Log.d("DATOS_USUARIO", "Apellidos: " + apellidos);
            // Mostrar otros campos según corresponda
        }
        database.close();
    }

}

