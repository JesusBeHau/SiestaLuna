package com.example.siestaluna.VistasUsuarios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.Clases.ReservacionDAO;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistorialUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialUsuarioFragment extends Fragment {
    private ArrayList<Reservacion> listaReservaciones;
    private AdapterReservacionesUsuario adapterReservacionesUsuario;
    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistorialUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialUsuarioFragment newInstance(String param1, String param2) {
        HistorialUsuarioFragment fragment = new HistorialUsuarioFragment();
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
        View view = inflater.inflate(R.layout.fragment_historial_usuario, container, false);
        init(view);
        return view;
    }
    public void init(View view){
        //Se obtienen las reservaciones actuales
        ReservacionDAO reservacionDAO = new ReservacionDAO(getContext());
        listaReservaciones=reservacionDAO.obtenerHistoralUsuario(usuario.getCorreo());
        //Se pasan las reservaciones al adapter
        adapterReservacionesUsuario= new AdapterReservacionesUsuario(listaReservaciones, getContext());
        //Se establece el adapter en el recycler
        RecyclerView recyclerView =view.findViewById(R.id.recyclerHistorial);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterReservacionesUsuario);
    }
}