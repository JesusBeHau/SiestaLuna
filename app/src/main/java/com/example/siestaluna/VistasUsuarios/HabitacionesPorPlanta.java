package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.R;

import java.util.ArrayList;

public class HabitacionesPorPlanta extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ArrayList<Habitacion> listaHabitaciones;
    TextView planta;
    private SearchView svSearch;
    private AdapterHabitacionUsuario adapterHabitacionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitaciones_por_planta);

        //Recuperamos el id de la planta
        String idPlanta = getIntent().getStringExtra("numPlanta");

        //Se obtienen las habitaciones correspondientes a la planta
        HabitacionDao habitacionDao = new HabitacionDao(HabitacionesPorPlanta.this);
        listaHabitaciones = habitacionDao.obtenerHabitacionesPlanta(idPlanta);
        planta=(TextView) findViewById(R.id.tvPlanta);
        planta.setText("Planta "+idPlanta);

        //Se pasa la lista de habitaciones al adapter
        adapterHabitacionUsuario = new AdapterHabitacionUsuario(listaHabitaciones, HabitacionesPorPlanta.this, new AdapterHabitacionUsuario.OnItemClickListener() {
            @Override
            public void onItemClick(Habitacion habitacion) {
                moveToDescription(habitacion);
            }

        });
        //Se establece el adapter en el recycler
        RecyclerView recyclerHabitaciones = findViewById(R.id.recyclerHabitacionesPlanta);
        //recyclerHabitaciones.setHasFixedSize(true);
        recyclerHabitaciones.setLayoutManager(new LinearLayoutManager(HabitacionesPorPlanta.this));
        recyclerHabitaciones.setAdapter(adapterHabitacionUsuario);

        svSearch = (SearchView) findViewById(R.id.svBuscar);
        initListener();

    }

    public void moveToDescription(Habitacion habitacion){
        //Envia a la interfaz para ver la habitación, le pasa como parametro el ID de la habitación
        Intent intent = new Intent(HabitacionesPorPlanta.this, HabitacionActivityUsuario.class);
        intent.putExtra("idHabitacion", String.valueOf(habitacion.getIdHabitacion()));
        startActivity(intent);
    }

    public void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //al detectar cambios en el buscador activa en metodo filtro
    @Override
    public boolean onQueryTextChange(String newText) {
        adapterHabitacionUsuario.filter(newText);
        return false;
    }
}