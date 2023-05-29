package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.Clases.ReservacionDAO;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.R;

import java.util.ArrayList;

public class ReservacionesUsuario extends AppCompatActivity {
    private ArrayList<Reservacion> listaReservaciones;
    private AdapterReservacionesUsuario adapterReservacionesUsuario;
    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones_usuario);
        //Se obtienen las reservaciones actuales
        ReservacionDAO reservacionDAO = new ReservacionDAO(ReservacionesUsuario.this);
        listaReservaciones=reservacionDAO.obtenerReservacionesUsuario(usuario.getCorreo());
        //Se pasan las reservaciones al adapter
        adapterReservacionesUsuario= new AdapterReservacionesUsuario(listaReservaciones, ReservacionesUsuario.this);
        //Se establece el adapter en el recycler
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerReservacionUsuario);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReservacionesUsuario.this));
        recyclerView.setAdapter(adapterReservacionesUsuario);
    }
}