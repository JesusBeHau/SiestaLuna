package com.example.siestaluna.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siestaluna.DataBase.DatabaseHelper;

import java.util.ArrayList;

public class HabitacionDao {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;

    public HabitacionDao(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context,null);
    }

    /*
    * Devuelve un ArrayList de las habitaciones guardadas en la BD
    * */
    public ArrayList<Habitacion> obtenerHabitaciones() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();

        // Obtener una referencia a la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(context, null);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Definir las columnas que se van a recuperar
        String[] columnas = {"idHabitacion", "Piso_idPiso", "numHab", "tipo", "precio", "caracteristicas", "ocupacion", "disponibilidad", "imagen1", "imagen2", "imagen3", "imagen4"};

        // Recuperar los datos de la tabla Habitacion
        Cursor cursor = db.query("Habitacion", columnas, null, null, null, null, null);

        // Iterar sobre el cursor y crear objetos Habitacion
        while (cursor.moveToNext()) {
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

            // Crear un objeto Habitacion y agregarlo a la lista
            Habitacion habitacion = new Habitacion(idHabitacion, pisoId, numeroHabitacion, tipo, precio, caracteristicas, ocupacion, disponibilidad, imagen1, imagen2, imagen3, imagen4);
            habitaciones.add(habitacion);
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return habitaciones;
    }

    /*
     * Devuelve un ArrayList de las habitaciones guardadas en la BD en alguna planta
     * */
    public ArrayList<Habitacion> obtenerHabitacionesPlanta(String planta) {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();

        // Obtener una referencia a la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(context, null);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Definir las columnas que se van a recuperar
        String[] columnas = {"idHabitacion", "Piso_idPiso", "numHab", "tipo", "precio", "caracteristicas", "ocupacion", "disponibilidad", "imagen1", "imagen2", "imagen3", "imagen4"};

        String whereClause ="Piso_idPiso = ?";
        String[] whereArgs = {planta};

        // Recuperar los datos de la tabla Habitacion
        Cursor cursor = db.query("Habitacion", columnas, whereClause, whereArgs, null, null, null);

        // Iterar sobre el cursor y crear objetos Habitacion
        while (cursor.moveToNext()) {
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

            // Crear un objeto Habitacion y agregarlo a la lista
            Habitacion habitacion = new Habitacion(idHabitacion, pisoId, numeroHabitacion, tipo, precio, caracteristicas, ocupacion, disponibilidad, imagen1, imagen2, imagen3, imagen4);
            habitaciones.add(habitacion);
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();

        return habitaciones;
    }

    /**
     * Busca alguna habitación en la BD a partir de su id, y la devuelve
     * @param id recibe el id de la habitación
     * @return devuelve un objeto Habitación
     */
    public Habitacion obtenerHabitacion(int id){
        database = dbHelper.getReadableDatabase();
        Habitacion habitacion = null;
        String selection = "idHabitacion = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("Habitacion", null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            habitacion = new Habitacion();
            habitacion.setIdHabitacion(cursor.getInt(0));
            habitacion.setPisoId(cursor.getString(1));
            habitacion.setNumeroHabitacion(cursor.getInt(2));
            habitacion.setTipo(cursor.getString(3));
            habitacion.setPrecio(cursor.getDouble(4));
            habitacion.setCaracteristicas(cursor.getString(5));
            habitacion.setOcupacion(cursor.getInt(6));
            habitacion.setDisponibilidad(cursor.getInt(7));
            habitacion.setImagen1(cursor.getBlob(8));
            habitacion.setImagen2(cursor.getBlob(9));
            habitacion.setImagen3(cursor.getBlob(10));
            habitacion.setImagen4(cursor.getBlob(11));

        }else{
            habitacion = null;
        }


        cursor.close();
        database.close();
        return habitacion;
    }

    public boolean actualizarHabitacion(Habitacion habitacion){
        database = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idHabitacion", habitacion.getIdHabitacion());
        valores.put("Piso_idPiso", habitacion.getPisoId());
        valores.put("numHab", habitacion.getNumeroHabitacion());
        valores.put("tipo", habitacion.getTipo());
        valores.put("precio", habitacion.getPrecio());
        valores.put("caracteristicas", habitacion.getCaracteristicas());
        valores.put("ocupacion", habitacion.getOcupacion());
        valores.put("disponibilidad", habitacion.getDisponibilidad());
        valores.put("imagen1", habitacion.getImagen1());
        valores.put("imagen2", habitacion.getImagen2());
        valores.put("imagen3", habitacion.getImagen3());
        valores.put("imagen4", habitacion.getImagen4());

        String whereClause ="idHabitacion = ?";
        String[] whereArgs = {Integer.toString(habitacion.getIdHabitacion())};

        int filasActualizadas = database.update("Habitacion", valores, whereClause, whereArgs);
        database.close();
        return filasActualizadas>0;
    }

}
