package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.siestaluna.DataBase.DatabaseHelper;
import com.example.siestaluna.R;

public class CroquisUsuarioActivity extends AppCompatActivity {
    ImageView croquis1, croquis2;
    Button verPlanta1, verPlanta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croquis_usuario);
        //enlazamos los atributos con los elementos de la interfaz
        croquis1=(ImageView) findViewById(R.id.croquis1);
        croquis2=(ImageView) findViewById(R.id.croquis2);
        verPlanta1=(Button) findViewById(R.id.verPlanta1);
        verPlanta2=(Button)findViewById(R.id.verPlanta2);

        // Obtener una referencia a la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this, null);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Definir las columnas que se van a recuperar
        String[] columnas = {"imagen"};
        // Recuperar las imagenes de los croquis
        Cursor cursor = db.query("Planta", columnas, null, null, null, null, null);

        //Mostrar los croquis guardados en la BD
        if (cursor.moveToFirst()) {
            byte[] bytesCroquis1 = cursor.getBlob(cursor.getColumnIndex("imagen"));
            if (cursor.moveToNext()) {
                byte[] bytesCroquis2 = cursor.getBlob(cursor.getColumnIndex("imagen"));
                croquis1.setImageBitmap(BitmapFactory.decodeByteArray(bytesCroquis1, 0, bytesCroquis1.length));
                croquis2.setImageBitmap(BitmapFactory.decodeByteArray(bytesCroquis2, 0, bytesCroquis2.length));
            }
        }
        verPlanta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CroquisUsuarioActivity.this, HabitacionesPorPlanta.class);
                intent.putExtra("numPlanta", "1");
                startActivity(intent);
            }
        });

        verPlanta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CroquisUsuarioActivity.this, HabitacionesPorPlanta.class);
                intent.putExtra("numPlanta", "2");
                startActivity(intent);
            }
        });
    }
}