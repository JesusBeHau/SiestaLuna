package com.example.siestaluna.VistasUsuarios;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.AdapterHabitaciones;
import com.example.siestaluna.vistasAdmin.HabitacionActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarUsuarioFragment extends Fragment implements SearchView.OnQueryTextListener {
    private ArrayList<Habitacion> listaHabitaciones;
    private SearchView svSearch;
    private AdapterHabitacionUsuario adapterHabitacionUsuario;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscarUsuarioFragment newInstance(String param1, String param2) {
        BuscarUsuarioFragment fragment = new BuscarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar_usuario, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        //Se obtienen las habitaciones registradas
        HabitacionDao habitacionDao = new HabitacionDao(getContext());
        listaHabitaciones = habitacionDao.obtenerHabitaciones();
        //Se pasa la lista de habitaciones al adapter
        adapterHabitacionUsuario = new AdapterHabitacionUsuario(listaHabitaciones, getContext(), new AdapterHabitacionUsuario.OnItemClickListener() {
            @Override
            public void onItemClick(Habitacion habitacion) {
                moveToDescription(habitacion);
            }

        });
        //Se establece el adapter en el recycler
        RecyclerView recyclerHabitaciones = view.findViewById(R.id.recyclerHabitacionesUsuario);
        //recyclerHabitaciones.setHasFixedSize(true);
        recyclerHabitaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHabitaciones.setAdapter(adapterHabitacionUsuario);

        svSearch = view.findViewById(R.id.svBuscar);
        initListener();
    }
    public void moveToDescription(Habitacion habitacion){
        //Envia a la interfaz para ver la habitación, le pasa como parametro el ID de la habitación
        Intent intent = new Intent(getContext(), HabitacionActivityUsuario.class);
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