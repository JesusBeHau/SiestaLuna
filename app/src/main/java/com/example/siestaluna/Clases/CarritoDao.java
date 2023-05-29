package com.example.siestaluna.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siestaluna.DataBase.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CarritoDao {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public CarritoDao(Context context){
        dbHelper = new DatabaseHelper(context,null);
    }


    /**
     * Agrega una habitacion al carrito de un cliente
     * @param numHab recibe el numero de habitación que se agregara al carrito
     * @param correoCliente recibe el correo del cliente al que se le agregara la habitación
     * @return devuelve true si se realizo el registro.
     */
    public boolean agregar(int numHab, String correoCliente){
        database=dbHelper.getWritableDatabase();

        if(verificarRegistro(numHab, correoCliente)){//Si el usuario ya ha agregado esa habitacion a su carrito
            return false;
        }else{//Si el usuario no ha agregado esa habitación a su carrito
            ContentValues values = new ContentValues();
            values.put("numHab", numHab);
            values.put("correoCliente", correoCliente);

            long d = database.insert("carrito", null, values);

            database.close();
            return true;
        }
    }

    /**
     * verifica si una habitación ya esta agregada en su carrito del usuario
     * @param numHab recibe el número de la habitación que se quiere agregar al carrito
     * @param correoCliente recibe el correo del cliente
     * @return devuelve true si la habitación ya esta registrada en el carrito del cliente
     */
    public boolean verificarRegistro(int numHab, String correoCliente){
        //Obtener una referencia a la BD
        database=dbHelper.getReadableDatabase();

        //Definimos las columnas a recuperar
        String[] columns = {"numHab", "correoCliente"};
        String selection = "numHab = ? AND correoCliente = ?";
        String[] selectionArgs = {String.valueOf(numHab), correoCliente};

        Cursor cursor = database.query("Carrito", columns, selection, selectionArgs, null, null, null);

        boolean existe = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }

        return existe;
    }


    /**
     * Obtiene todas las habitaciones que un cliente ha registrado en su carrito
     * @param correoCliente recibe el correo del cliente
     * @return devuelve la lista de habitaciones que el usuario agrego a su carrito
     */
    public ArrayList<Habitacion> obtenerHabitaciones(String correoCliente){
        ArrayList<Habitacion> listaHabitaciones = new ArrayList<>();

        //Obtener una referencia a la BD
        database=dbHelper.getReadableDatabase();
        //Definimos la sentencia SQL
        String query="SELECT * FROM Carrito INNER JOIN Habitacion ON Carrito.numHab = Habitacion.numHab WHERE Carrito.correoCliente='"+correoCliente+"'";
        Cursor cursor = database.rawQuery(query, null);
        while(cursor.moveToNext()){
            int idHabitacion = cursor.getInt(cursor.getColumnIndex("idHabitacion"));
            String pisoId = cursor.getString(cursor.getColumnIndex("Piso_idPiso"));
            int numeroHabitacion = cursor.getInt(cursor.getColumnIndex("numHab"));
            String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
            String caracteristicas = cursor.getString(cursor.getColumnIndex("caracteristicas"));
            int ocupacion = cursor.getInt(cursor.getColumnIndex("ocupacion"));
            int disponibilidad = cursor.getInt(cursor.getColumnIndex("disponibilidad"));
            byte[] imagen1 = cursor.getBlob(cursor.getColumnIndex("imagen1"));
            byte[] imagen2 = cursor.getBlob(cursor.getColumnIndex("imagen2"));
            byte[] imagen3 = cursor.getBlob(cursor.getColumnIndex("imagen3"));
            byte[] imagen4 = cursor.getBlob(cursor.getColumnIndex("imagen4"));

            //Crear un objeto habitacion y agregarlo a la lista
            Habitacion habitacion = new Habitacion(idHabitacion, pisoId, numeroHabitacion, tipo, precio, caracteristicas, ocupacion, disponibilidad, imagen1, imagen2, imagen3, imagen4);
            listaHabitaciones.add(habitacion);
        }
        //cerramos la conexion a la BD
        cursor.close();
        database.close();

        return listaHabitaciones;
    }

    public boolean eliminarRegistro(int numHab, String correo){
        try{
            database=dbHelper.getWritableDatabase();
            //Definimos la sentencia SQL
            String query="DELETE FROM Carrito WHERE numHab="+numHab+" AND correoCliente='"+correo+"'";
            //Ejecutamos la sentencia SQL
            database.execSQL(query);
            //cerramos la conexion a la BD
            database.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
