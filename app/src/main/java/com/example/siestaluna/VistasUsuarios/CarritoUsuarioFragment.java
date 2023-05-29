package com.example.siestaluna.VistasUsuarios;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.siestaluna.Clases.CarritoDao;
import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.ActualizarHabitacion;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoUsuarioFragment extends Fragment {
    private ArrayList<Habitacion> listaHabitaciones;
    private AdapterCarrito adapterCarrito;
    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();
    private FragmentControllerUsuario fragmentControllerUsuario;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarritoUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarritoUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarritoUsuarioFragment newInstance(String param1, String param2) {
        CarritoUsuarioFragment fragment = new CarritoUsuarioFragment();
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
        View view = inflater.inflate(R.layout.fragment_carrito_usuario, container, false);
        fragmentControllerUsuario=(FragmentControllerUsuario) getActivity();
        init(view);
        return view;
    }

    public void init(View view){
        //Se obtienen las habitaciones agregadas en el carrito del usuario
        CarritoDao carritoDao = new CarritoDao(getContext());
        listaHabitaciones=carritoDao.obtenerHabitaciones(usuario.getCorreo());
        //se pasan las habitaciones al Adapter
        adapterCarrito=new AdapterCarrito(listaHabitaciones, getContext(), new AdapterCarrito.OnItemClickListener() {
            @Override
            public void onItemClick(Habitacion habitacion) {moveToEliminar(habitacion);}

            @Override
            public void onReservarClick(Habitacion habitacion) {moveToReservar(habitacion);}
        });
        //Se establece el adapter en el recycler
        RecyclerView recyclerCarrito = view.findViewById(R.id.recyclerCarrito);
        //recyclerCarrito.setHasFixedSize(true);
        recyclerCarrito.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCarrito.setAdapter(adapterCarrito);
    }

    public void moveToEliminar(Habitacion habitacion){
        CarritoDao carritoDao = new CarritoDao(getContext());
        boolean eliminado= carritoDao.eliminarRegistro(habitacion.getNumeroHabitacion(), usuario.getCorreo());
        if(eliminado){
            refrescarFragment();
        }else{
            Toast.makeText(getContext(), "Error al intentar eliminar del carrito", Toast.LENGTH_LONG).show();
        }
    }

    public void moveToReservar(Habitacion habitacion){
        //Envia a la interfaz para reservar una habitación
        Intent intent= new Intent(getContext(), ReservacionFecha.class);
        //Enviamos el id de la habitación
        intent.putExtra("idHabitacion", habitacion.getIdHabitacion());
        startActivity(intent);
    }

    public void refrescarFragment(){
        CarritoUsuarioFragment carritoUsuarioFragment =  new CarritoUsuarioFragment();
        fragmentControllerUsuario.loadFragment(carritoUsuarioFragment);
    }
}