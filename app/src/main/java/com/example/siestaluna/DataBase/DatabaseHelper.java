package com.example.siestaluna.DataBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.siestaluna.R;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_HOTEL.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tablas
        db.execSQL("CREATE TABLE Rol (idRol INTEGER PRIMARY KEY AUTOINCREMENT, Rol VARCHAR(50) NOT NULL);");
        db.execSQL("CREATE TABLE Usuario (correo VARCHAR(100) PRIMARY KEY, nombres VARCHAR(100) NOT NULL, apellidos VARCHAR(100) NOT NULL, fechaNacimiento DATE NOT NULL, nacionalidad VARCHAR(45) NOT NULL, telefono VARCHAR(20) NOT NULL, contrasenia VARCHAR(20) NOT NULL, rol INTEGER NOT NULL, FOREIGN KEY (rol) REFERENCES Rol (idRol) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE Planta (idPlanta VARCHAR(20) PRIMARY KEY, numeroPlanta INTEGER NOT NULL, nombreImg TEXT, imagen BLOB DEFAULT NULL);");
        db.execSQL("CREATE TABLE Habitacion (idHabitacion INTEGER PRIMARY KEY, Piso_idPiso VARCHAR(20) NOT NULL, numHab INTEGER NOT NULL, tipo VARCHAR(45), precio REAL, caracteristicas TEXT, ocupacion INTEGER, disponibilidad INTEGER DEFAULT 0,imagen1 BLOB, imagen2 BLOB DEFAULT NULL, imagen3 BLOB DEFAULT NULL, imagen4 BLOB DEFAULT NULL,FOREIGN KEY (Piso_idPiso) REFERENCES Planta (idPlanta) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE Carrito (numHab INTEGER NOT NULL, correoCliente  VARCHAR(100) NOT NULL);");
        db.execSQL("CREATE TABLE Reservacion (idReservacion INTEGER PRIMARY KEY AUTOINCREMENT, Usuario_correo VARCHAR(100) NOT NULL, Habitacion_idHabitacion INTEGER NOT NULL, personasOcupan INTEGER NOT NULL, fechaEntrada DATE NOT NULL, fechaSalida DATE NOT NULL, nombreCliente VARCHAR(100) NOT NULL, pago REAL NOT NULL, FOREIGN KEY (Habitacion_idHabitacion) REFERENCES Habitacion (idHabitacion) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (Usuario_correo) REFERENCES Usuario (correo) ON DELETE CASCADE ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE Historico (idHistorico INTEGER PRIMARY KEY AUTOINCREMENT, Usuario_correo VARCHAR(100) NOT NULL, habitacion INTEGER NOT NULL, fechaEntrada DATE NOT NULL, fechaSalida DATE NOT NULL, personasOcupo INTEGER NOT NULL, pago REAL NOT NULL, FOREIGN KEY (Usuario_correo) REFERENCES Usuario (correo) ON DELETE CASCADE ON UPDATE CASCADE);");

        // Insertar datos iniciales
        db.execSQL("INSERT INTO Rol (Rol) VALUES ('Administrador'), ('Cliente');");
        db.execSQL("INSERT INTO Usuario (correo, nombres, apellidos, fechaNacimiento, nacionalidad, telefono, contrasenia, rol) VALUES ('adminmain@Hot.mx', 'NombresAdmin', 'ApellidosAdmin', '2000-01-01', 'Mexicano', '9998889898', 'Main;Admin;1234', 1);");
        db.execSQL("INSERT INTO Planta (idPlanta, numeroPlanta) VALUES ('1', 1), ('2', 2);");
        db.execSQL("INSERT INTO Habitacion (idHabitacion, Piso_idPiso, numHab, tipo, precio, caracteristicas, ocupacion, disponibilidad) VALUES (1, '1', 1, 'Deluxe', 600,'Caracteristicas de la hab1', 4, 1), (2, '1', 2, 'Deluxe', 600, 'Caracteristicas de la hab2', 4, 1), (3, '1', 3, 'Básica', 400, 'Caracteristicas de la hab3', 4, 1), (4, '1', 4, 'Deluxe', 1000, 'Caracteristicas de la hab4', 4, 1), (5, '1', 5, 'Deluxe', 600, 'Caracteristicas de la hab5', 4, 1);");
        db.execSQL("INSERT INTO Habitacion (idHabitacion, Piso_idPiso, numHab, tipo, precio, caracteristicas, ocupacion, disponibilidad) VALUES (6, '2', 6, 'Deluxe', 700, 'Caracteristicas de la hab6', 4, 1), (7, '2', 7, 'Básica', 600, 'Caracteristicas de la hab7', 4, 1), (8, '2', 8, 'Deluxe', 700, 'Caracteristicas de la hab8', 4, 1),(9, '2', 9, 'Deluxe', 700, 'Caracteristicas de la hab9', 4, 1), (10, '2', 10, 'Deluxe', 700, 'Caracteristicas de la hab10', 4, 1);");
        //Este es de prueba
        db.execSQL("INSERT INTO Reservacion (Usuario_correo, Habitacion_idHabitacion, personasOcupan, fechaEntrada, fechaSalida, nombreCliente, pago) VALUES ('jesusbe933@gmail.com', 1, 4, '2023-05-28', '2023-05-29', 'Jesus', 900)");

        // Obtener los bytes de la imagen por defecto para las habitaciones
        byte[] bytesImagenPorDefecto = obtenerBytesImagenPorDefecto();

        // Actualizar las habitaciones con la imagen por defecto
        ContentValues valoresHabitacion = new ContentValues();
        valoresHabitacion.put("imagen1", bytesImagenPorDefecto);
        valoresHabitacion.put("imagen2", bytesImagenPorDefecto);
        valoresHabitacion.put("imagen3", bytesImagenPorDefecto);
        valoresHabitacion.put("imagen4", bytesImagenPorDefecto);
        db.update("Habitacion", valoresHabitacion, null, null);

        //actualiza las plantas con la foto del croquis por defecto
        ContentValues valoresPlanta = new ContentValues();
        valoresPlanta.put("imagen", bytesImagenPorDefecto);
        db.update("Planta",valoresPlanta, null, null);
    }

    //No implementadas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Devuelve los bytes de la imagen que carga por defecto la aplicación
    private byte[] obtenerBytesImagenPorDefecto() {
        // Obtener el ID del recurso de la imagen por defecto
        int idImagenPorDefecto = R.drawable.fotodefecto;

        // Obtener los bytes de la imagen por defecto
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), idImagenPorDefecto);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytesImagenPorDefecto = outputStream.toByteArray();

        return bytesImagenPorDefecto;
    }

}
