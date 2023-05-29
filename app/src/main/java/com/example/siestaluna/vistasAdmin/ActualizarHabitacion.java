package com.example.siestaluna.vistasAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.R;
import com.example.siestaluna.VistasUsuarios.FragmentControllerUsuario;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;

public class ActualizarHabitacion extends AppCompatActivity {
    TextView tvNumHabitacion;
    ImageView img1, img2, img3, img4;
    Button cambiar1, cambiar2, cambiar3, cambiar4, guardar, cancelar;
    EditText etTipo, etOcupacion, etPrecio, etPlanta, etDescripcion;
    private int imgCambiada=0;
    Habitacion habitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_habitacion);
        //Enlazamos las variables con los elementos de la interfaz
        tvNumHabitacion=(TextView) findViewById(R.id.tvNumHabitación);
        img1=(ImageView) findViewById(R.id.ivImagen1);
        img2=(ImageView) findViewById(R.id.ivImagen2);
        img3=(ImageView) findViewById(R.id.ivImagen3);
        img4=(ImageView) findViewById(R.id.ivImagen4);
        cambiar1=(Button) findViewById(R.id.btnCambiarImg1);
        cambiar2=(Button) findViewById(R.id.btnCambiarImg2);
        cambiar3=(Button) findViewById(R.id.btnCambiarImg3);
        cambiar4=(Button) findViewById(R.id.btnCambiarImg4);
        etTipo=(EditText) findViewById(R.id.etTipo);
        etOcupacion=(EditText) findViewById(R.id.etOcupacion);
        etPrecio=(EditText) findViewById(R.id.etPrecio);
        etPlanta=(EditText) findViewById(R.id.etPlanta);
        etDescripcion=(EditText) findViewById(R.id.etDescripcion);
        guardar=(Button) findViewById(R.id.btnGuardar);
        cancelar=(Button) findViewById(R.id.btnCancelar);

        //Recuperamos el id de la habitación
        String idHabitacion = getIntent().getStringExtra("idHabitacion");

        //Buscamos la habitación en la BD
        HabitacionDao habitacionDao = new HabitacionDao(ActualizarHabitacion.this);
        habitacion = habitacionDao.obtenerHabitacion(Integer.parseInt(idHabitacion));

        //Establecemos los datos de la habitacion en la interfaz
        tvNumHabitacion.setText("Habitación: "+habitacion.getNumeroHabitacion());
        img1.setImageBitmap(obtenerImagen(habitacion.getImagen1()));
        img2.setImageBitmap(obtenerImagen(habitacion.getImagen2()));
        img3.setImageBitmap(obtenerImagen(habitacion.getImagen3()));
        img4.setImageBitmap(obtenerImagen(habitacion.getImagen4()));
        etTipo.setText(habitacion.getTipo());
        etOcupacion.setText(Integer.toString(habitacion.getOcupacion()));
        etPrecio.setText(Double.toString(habitacion.getPrecio()));
        etPlanta.setText(habitacion.getPisoId());
        etDescripcion.setText(habitacion.getCaracteristicas());

        //para cambiar alguna imagen
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btnCambiarImg1:
                        imgCambiada=1;
                        cambiarImagen();
                        break;
                    case R.id.btnCambiarImg2:
                        imgCambiada=2;
                        cambiarImagen();
                        break;
                    case R.id.btnCambiarImg3:
                        imgCambiada=3;
                        cambiarImagen();
                        break;
                    case R.id.btnCambiarImg4:
                        imgCambiada=4;
                        cambiarImagen();
                        break;
                }
            }
        };
        cambiar1.setOnClickListener(listener);
        cambiar2.setOnClickListener(listener);
        cambiar3.setOnClickListener(listener);
        cambiar4.setOnClickListener(listener);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recoge todos los datos nuevos
                habitacion.setTipo(etTipo.getText().toString());
                habitacion.setOcupacion(Integer.parseInt(etOcupacion.getText().toString()));
                habitacion.setPrecio(Double.parseDouble(etPrecio.getText().toString()));
                habitacion.setPisoId(etPlanta.getText().toString());
                habitacion.setCaracteristicas(etDescripcion.getText().toString());
                if(habitacionDao.actualizarHabitacion(habitacion)){
                    Toast.makeText(ActualizarHabitacion.this, "Datos Actualizados", Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(ActualizarHabitacion.this, FragmentControllerAdmin.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ActualizarHabitacion.this, "ERROR: los datos no puederon ser actualizados", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActualizarHabitacion.this, FragmentControllerAdmin.class);
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
            switch (imgCambiada){
                case 1:
                    img1.setImageURI(path);
                    habitacion.setImagen1(obtenerByte(path));
                    break;
                case 2:
                    img2.setImageURI(path);
                    habitacion.setImagen2(obtenerByte(path));
                    break;
                case 3:
                    img3.setImageURI(path);
                    habitacion.setImagen3(obtenerByte(path));
                    break;
                case 4:
                    img4.setImageURI(path);
                    habitacion.setImagen4(obtenerByte(path));
                    break;
            }

        }
    }


    /**
     * metodo para reconstruir las imagenes
     * @param imagenByte recibe la imagen en byte
     * @return devulelve la imagen en Bitmap
     */
    private Bitmap obtenerImagen(byte[] imagenByte){
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenByte, 0, imagenByte.length);
        return bitmap;
    }

    /**
     * Metodo para convertir una imagen a byte
     * @param imagenUri recibe la imagen a convertir
     * @return regresa la imagen en byte[]
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