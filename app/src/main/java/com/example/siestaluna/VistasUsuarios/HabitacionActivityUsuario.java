package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siestaluna.Clases.CarritoDao;
import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.HabitacionActivity;

public class HabitacionActivityUsuario extends AppCompatActivity {
    TextView tvNumHabitacion;
    ImageView img1, img2, img3, img4;
    TextView tvTipo, tvOcupacion, tvPrecio, tvPlanta, tvDescripcion;
    Button btnAgregarCarrito, btnReservar;

    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitacion_usuario);
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
        btnAgregarCarrito=(Button) findViewById(R.id.btnAgregarCarrito);
        btnReservar=(Button) findViewById(R.id.btnReservar);

        //Recuperamos el id de la habitación
        String idHabitacion = getIntent().getStringExtra("idHabitacion");

        //Buscamos la habitación en la BD
        HabitacionDao habitacionDao = new HabitacionDao(HabitacionActivityUsuario.this);
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

        //Al dar click en agregar al carrito
        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarritoDao carritoDao = new CarritoDao(HabitacionActivityUsuario.this);
                boolean agregado=carritoDao.agregar(habitacion.getIdHabitacion(), usuario.getCorreo());
                if(agregado){
                    Toast.makeText(HabitacionActivityUsuario.this, "Habitación agregada al carrito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HabitacionActivityUsuario.this, "La habitación ya se encuentra agregada al carrito", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Al dar click en reservar
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HabitacionActivityUsuario.this, ReservacionFecha.class);
                intent.putExtra("idHabitacion", habitacion.getIdHabitacion());
                startActivity(intent);
            }
        });
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