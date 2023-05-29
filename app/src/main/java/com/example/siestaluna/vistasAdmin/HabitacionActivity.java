package com.example.siestaluna.vistasAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.R;

public class HabitacionActivity extends AppCompatActivity {
    TextView tvNumHabitacion;
    ImageView img1, img2, img3, img4;
    TextView tvTipo, tvOcupacion, tvPrecio, tvPlanta, tvDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion);
        //Enlazamos los elementos de la interfaz con los atributos
        tvNumHabitacion=(TextView) findViewById(R.id.tvNumHabitación);
        img1=(ImageView) findViewById(R.id.ivImagen1);
        img2=(ImageView) findViewById(R.id.ivImagen2);
        img3=(ImageView) findViewById(R.id.ivImagen3);
        img4=(ImageView) findViewById(R.id.ivImagen4);
        tvTipo=(TextView) findViewById(R.id.tvTipo);
        tvOcupacion=(TextView) findViewById(R.id.tvOcupacion);
        tvPrecio=(TextView) findViewById(R.id.tvPrecio);
        tvPlanta=(TextView) findViewById(R.id.tvPlanta);
        tvDescripcion=(TextView) findViewById(R.id.tvDescripcion);

        //Recuperamos el id de la habitación
        String idHabitacion = getIntent().getStringExtra("idHabitacion");

        //Buscamos la habitación en la BD
        HabitacionDao habitacionDao = new HabitacionDao(HabitacionActivity.this);
        Habitacion habitacion = habitacionDao.obtenerHabitacion(Integer.parseInt(idHabitacion));


        //Establecemos los datos de la habitacion en la interfaz
        tvNumHabitacion.setText("Habitación: "+habitacion.getNumeroHabitacion());
        tvTipo.setText("Tipo: "+habitacion.getTipo());
        tvOcupacion.setText("Personas: habitacion para "+habitacion.getOcupacion()+" personas.");
        tvPrecio.setText("Precio por día: $"+habitacion.getPrecio());
        tvPlanta.setText("Planta: "+habitacion.getPisoId());
        tvDescripcion.setText("Descripción: \n\n"+habitacion.getCaracteristicas());
        img1.setImageBitmap(convertirImagen(habitacion.getImagen1()));
        img2.setImageBitmap(convertirImagen(habitacion.getImagen2()));
        img3.setImageBitmap(convertirImagen(habitacion.getImagen3()));
        img4.setImageBitmap(convertirImagen(habitacion.getImagen4()));

    }

    /**
     * metodo para reconstruis las imagenes
     * @param imagenByte recibe la imagen en byte
     * @return devulelve la imagen en Bitmap
     */
    private Bitmap convertirImagen(byte[] imagenByte){
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenByte, 0, imagenByte.length);
        return bitmap;
    }
}