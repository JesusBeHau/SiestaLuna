package com.example.siestaluna.VistasUsuarios;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.siestaluna.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioUsuarioFragment extends Fragment implements OnMapReadyCallback {
    BuscarUsuarioFragment buscarUsuarioFragment = new BuscarUsuarioFragment();
    Button reservaciones, croquis;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioUsuarioFragment newInstance(String param1, String param2) {
        InicioUsuarioFragment fragment = new InicioUsuarioFragment();
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

    GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_usuario, container, false);
        // Obtiene el fragmento de mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        // Configura el objeto GoogleMap
        mapFragment.getMapAsync(this);
        reservaciones=(Button)view.findViewById(R.id.btnReservaciones);
        croquis=(Button)view.findViewById(R.id.btnCroquis);

        //Al hacer click en croquis
        croquis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CroquisUsuarioActivity.class);
                startActivity(intent);
            }
        });

        //Al hacer click en reservaciones
        reservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReservacionesUsuario.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Agrega un marcador en una ubicación específica y muestra el título
        LatLng ubicacion = new LatLng(21.5165375, -87.684749);
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Hotel siesta luna"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
    }
}