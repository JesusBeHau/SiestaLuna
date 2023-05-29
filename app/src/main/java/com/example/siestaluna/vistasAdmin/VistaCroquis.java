package com.example.siestaluna.vistasAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.siestaluna.DataBase.DatabaseHelper;
import com.example.siestaluna.R;

import java.io.ByteArrayOutputStream;

public class VistaCroquis extends AppCompatActivity {
    ImageView croquis1, croquis2;
    Button btn1, btn2, btnCancelar, btnGuardar;
    private int croquisCambiado=0;
    private byte[] bytesCroquis1=null, bytesCroquis2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_croquis);
        //enlazamos los atributos con los elementos de la interfaz
        croquis1= (ImageView) findViewById(R.id.croquis1);
        croquis2= (ImageView) findViewById(R.id.croquis2);
        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        btnCancelar= (Button) findViewById(R.id.btnCancelar);
        btnGuardar= (Button) findViewById(R.id.btnGuardar);

        // Obtener una referencia a la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this, null);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Definir las columnas que se van a recuperar
        String[] columnas = {"imagen"};
        // Recuperamos las imagenes de los croquis
        Cursor cursor = db.query("Planta", columnas, null, null, null, null, null);

        //Mostramos los croquis guardados en la BD
        if (cursor.moveToFirst()) {
            bytesCroquis1 = cursor.getBlob(cursor.getColumnIndex("imagen"));
            if (cursor.moveToNext()) {
                // Obtener la segunda imagen y guardarla en otra variable
                bytesCroquis2 = cursor.getBlob(cursor.getColumnIndex("imagen"));
                croquis1.setImageBitmap(BitmapFactory.decodeByteArray(bytesCroquis1, 0, bytesCroquis1.length));
                croquis2.setImageBitmap(BitmapFactory.decodeByteArray(bytesCroquis2, 0, bytesCroquis2.length));
            }
        }
        //Para cambiar el croquis 1
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                croquisCambiado=1;
                cambiarImagen();
            }
        });
        //Para cambiar el croquis 2
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                croquisCambiado=2;
                cambiarImagen();
            }
        });

        //Para guardar los cambios
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener una referencia a la base de datos
                DatabaseHelper dbHelper = new DatabaseHelper(VistaCroquis.this, null);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //Se actualiza el primer croquis en la BD
                ContentValues valores = new ContentValues();
                valores.put("imagen", bytesCroquis1);
                String whereClause="idPlanta = ?";
                String[] whereArgs={"1"};
                int primeroActualizado=db.update("Planta", valores, whereClause, whereArgs);
                //Se actualiza el segundo croquis en la BD
                valores = new ContentValues();
                valores.put("imagen", bytesCroquis2);
                whereClause="idPlanta = ?";
                String[] args={"2"};
                int segundoActualizado=db.update("Planta", valores, whereClause, args);
                if(primeroActualizado>0 && segundoActualizado>0){
                    Toast.makeText(VistaCroquis.this, "Croquis Actualizados", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Para cancelar los cambios
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(VistaCroquis.this, FragmentControllerAdmin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Abre la galeria al seleccionar cambiar alguna imagen
    public void cambiarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona la aplicacion"),10);
    }

    //Pone las nuevas imagenes en la interfaz y en el objeto habitacion
    @Override
    protected void onActivityResult(int requesCode, int resultCode, Intent data){
        super.onActivityResult(requesCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            switch (croquisCambiado){
                case 1:
                    croquis1.setImageURI(path);
                    bytesCroquis1=obtenerByte(path);
                    break;
                case 2:
                    croquis2.setImageURI(path);
                    bytesCroquis2=obtenerByte(path);
                    break;
            }

        }
    }

    /**
     * Metodo para convertir una imagen a byte
     * @param imagenUri recibe la imagen a convertir
     * @return regresa la imagen en t byte[]
     */
    private byte[] obtenerByte(Uri imagenUri){
        byte[] byteArray = null;
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
        }catch (Exception e) {

        }
        return byteArray;
    }
}