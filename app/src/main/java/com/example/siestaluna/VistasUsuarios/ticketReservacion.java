package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.R;

public class ticketReservacion extends AppCompatActivity {
    TextView tvFechaIngreso,tvNumeroHabitacion;
    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservacion);
        Intent intent = getIntent();
        Reservacion reservacion = (Reservacion) intent.getSerializableExtra("reservacion");

        tvFechaIngreso=(TextView) findViewById(R.id.tvFechaIngreso);
        tvNumeroHabitacion=(TextView) findViewById(R.id.tvNumHabitacion);
        btnRegresar=(Button) findViewById(R.id.btnRegresar);

        tvFechaIngreso.setText("Fecha de ingreso: "+reservacion.getFechaIngreso()+" a las 11:00 a.m");
        tvNumeroHabitacion.setText("Número de habitación: "+reservacion.getHabitacion_idHabitacion());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ticketReservacion.this, FragmentControllerUsuario.class);
                startActivity(intent);
                finish();
            }
        });
    }
}