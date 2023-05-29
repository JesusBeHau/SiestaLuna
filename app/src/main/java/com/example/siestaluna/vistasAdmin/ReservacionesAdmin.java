package com.example.siestaluna.vistasAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.Clases.ReservacionDAO;
import com.example.siestaluna.R;

import java.util.ArrayList;

public class ReservacionesAdmin extends AppCompatActivity {
    private ArrayList<Reservacion> listaReservaciones;
    private AdapterReservacionesAdmin adapterReservacionesAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones_admin);
        //Se obtienen las reservaciones
        ReservacionDAO reservacionDAO = new ReservacionDAO(ReservacionesAdmin.this);
        listaReservaciones=reservacionDAO.obtenerTodasReservaciones();
        //Se pasan las habitaciones al adapter
        adapterReservacionesAdmin= new AdapterReservacionesAdmin(listaReservaciones, ReservacionesAdmin.this, new AdapterReservacionesAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(Reservacion reservacion) {moveToCancelar(reservacion);}
        });
        //Se establece el adapter en el recycler
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerReservacionAdmin);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReservacionesAdmin.this));
        recyclerView.setAdapter(adapterReservacionesAdmin);
    }

    public void moveToCancelar(Reservacion reservacion){
        ReservacionDAO reservacionDAO = new ReservacionDAO(ReservacionesAdmin.this);
        boolean cancelar=reservacionDAO.cancelarReservacion(reservacion.getIdReservacion());
        if(cancelar){
            Toast.makeText(ReservacionesAdmin.this, "Reservacion cancelada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(ReservacionesAdmin.this, "La reservacion no se pudo cancelar", Toast.LENGTH_LONG).show();
        }
    }
}