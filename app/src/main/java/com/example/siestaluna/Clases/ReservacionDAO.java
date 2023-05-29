package com.example.siestaluna.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siestaluna.DataBase.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservacionDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ReservacionDAO(Context context){dbHelper = new DatabaseHelper(context,null);}

    /**
     * Obtiene todas las fechas en la que una habitacion esta reservada
     * @param idHabitacion recibe el id de la habitacion como un int
     * @return devuelve un list de tipo Long con las fechas de las reservaciones
     */
    public List<Long> obtenerFechasReservadas(int idHabitacion){
        //Obtener una referencia a la BD
        database=dbHelper.getReadableDatabase();

        List<Long>fechasReservadas = new ArrayList<>();

        //Definimos las columnas a recuperar
        String[] columns={"fechaEntrada", "fechaSalida"};
        String selection="Habitacion_idHabitacion = ?";
        String[] selectionArgs = {String.valueOf(idHabitacion)};

        Cursor cursor = database.query("Reservacion", columns, selection, selectionArgs, null, null, null);

        //Recorremos el cursor de reservaciones
        while (cursor.moveToNext()){
            String fechaEntradaStr = cursor.getString(cursor.getColumnIndex("fechaEntrada"));
            String fechaSalidaStr = cursor.getString(cursor.getColumnIndex("fechaSalida"));
            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaEntrada = dateFormat.parse(fechaEntradaStr);
                Date fechaSalida = dateFormat.parse(fechaSalidaStr);

                //Agregamos la fecha de entrada
                fechasReservadas.add(fechaEntrada.getTime());

                //Agregamos las fechas de rango
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaEntrada);
                while(calendar.getTime().before(fechaSalida)){
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    fechasReservadas.add(calendar.getTimeInMillis());
                }

                //Agregamos la fecha de salida
                fechasReservadas.add(fechaSalida.getTime());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //cerramos el cursor y la conexión a la BD
        cursor.close();
        database.close();

        return fechasReservadas;
    }

    /**
     * Registra una nueva reservacion
     * @param reservacion recibe un objeto tipo Reservacion
     */
    public void registrarReservacion(Reservacion reservacion){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idReservacion", reservacion.getIdReservacion());
        values.put("Usuario_correo", reservacion.getUsuario_correo());
        values.put("Habitacion_idHabitacion", reservacion.getHabitacion_idHabitacion());
        values.put("personasOcupan", reservacion.getPersonasOcupan());
        values.put("fechaEntrada", reservacion.getFechaIngreso());
        values.put("fechaSalida", reservacion.getFechaSalida());
        values.put("nombreCliente", reservacion.getNombreCliente());
        values.put("pago", reservacion.getPago());

        long d = database.insert("Reservacion", null, values);
        database.close();
    }

    /**
     * Obtiene las reservaciones de un usuario
     * @param correoUsuario recibe el correo del usuario
     * @return devuelve un array de Reservacion con las reservaciones del cliente
     */
    public ArrayList<Reservacion> obtenerReservacionesUsuario(String correoUsuario){
        ArrayList<Reservacion> reservacionesCliente = new ArrayList<>();

        database = dbHelper.getReadableDatabase();

        String[] columns={"r.Habitacion_idHabitacion", "r.Usuario_correo", "r.fechaEntrada", "r.fechaSalida", "r.personasOcupan", "h.imagen1"};
        String tabla="Reservacion r INNER JOIN Habitacion h ON r.Habitacion_idHabitacion=h.idHabitacion";
        String where="r.Usuario_correo = ? AND r.fechaEntrada>=date('now')";
        String[] args = {correoUsuario};
        String orderBy = "r.fechaEntrada ASC";

        Cursor cursor = database.query(tabla, columns, where, args, null, null, orderBy);

        while(cursor.moveToNext()){
            Reservacion reservacion = new Reservacion();
            reservacion.setHabitacion_idHabitacion(cursor.getInt(0));
            reservacion.setUsuario_correo(cursor.getString(1));
            reservacion.setFechaIngreso(cursor.getString(2));
            reservacion.setFechaSalida(cursor.getString(3));
            reservacion.setPersonasOcupan(cursor.getInt(4));
            reservacion.setImagen(cursor.getBlob(5));

            reservacionesCliente.add(reservacion);
        }

        return reservacionesCliente;
    }

    /**
     * Obtiene el historial de un usuario
     * @param correoUsuario recibe el correo del usuario
     * @return devuelve un array de Reservacion con las reservaciones del cliente
     */
    public ArrayList<Reservacion> obtenerHistoralUsuario(String correoUsuario){
        ArrayList<Reservacion> reservacionesCliente = new ArrayList<>();

        database = dbHelper.getReadableDatabase();

        String[] columns={"r.Habitacion_idHabitacion", "r.Usuario_correo", "r.fechaEntrada", "r.fechaSalida", "r.personasOcupan", "h.imagen1"};
        String tabla="Reservacion r INNER JOIN Habitacion h ON r.Habitacion_idHabitacion=h.idHabitacion";
        String where="r.Usuario_correo = ? AND r.fechaEntrada<date('now')";
        String[] args = {correoUsuario};
        String orderBy = "r.fechaEntrada ASC";

        Cursor cursor = database.query(tabla, columns, where, args, null, null, orderBy);

        while(cursor.moveToNext()){
            Reservacion reservacion = new Reservacion();
            reservacion.setHabitacion_idHabitacion(cursor.getInt(0));
            reservacion.setUsuario_correo(cursor.getString(1));
            reservacion.setFechaIngreso(cursor.getString(2));
            reservacion.setFechaSalida(cursor.getString(3));
            reservacion.setPersonasOcupan(cursor.getInt(4));
            reservacion.setImagen(cursor.getBlob(5));

            reservacionesCliente.add(reservacion);
        }

        return reservacionesCliente;
    }

    /**
     * Obtiene las reservaciones de todos los usuarios
     * @return devuelve un array de Reservacion con las reservaciones de todos los clientes
     */
    public ArrayList<Reservacion> obtenerTodasReservaciones(){
        ArrayList<Reservacion> reservaciones = new ArrayList<>();

        database = dbHelper.getReadableDatabase();

        String[] columns={"r.idReservacion","r.Habitacion_idHabitacion", "r.Usuario_correo", "r.fechaEntrada", "r.fechaSalida", "r.personasOcupan", "h.imagen1"};
        String tabla="Reservacion r INNER JOIN Habitacion h ON r.Habitacion_idHabitacion=h.idHabitacion";
        String where="r.fechaEntrada>=date('now')";
        String orderBy = "r.fechaEntrada ASC";

        Cursor cursor = database.query(tabla, columns, where, null, null, null, orderBy);

        while(cursor.moveToNext()){
            Reservacion reservacion = new Reservacion();
            reservacion.setIdReservacion(cursor.getInt(0));
            reservacion.setHabitacion_idHabitacion(cursor.getInt(1));
            reservacion.setUsuario_correo(cursor.getString(2));
            reservacion.setFechaIngreso(cursor.getString(3));
            reservacion.setFechaSalida(cursor.getString(4));
            reservacion.setPersonasOcupan(cursor.getInt(5));
            reservacion.setImagen(cursor.getBlob(6));

            reservaciones.add(reservacion);
        }

        return reservaciones;
    }

    public boolean cancelarReservacion(int idReservacion){
        try{
            database=dbHelper.getWritableDatabase();
            //Definimos la sentencia SQL
            String query="DELETE FROM Reservacion where idReservacion="+idReservacion;
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
