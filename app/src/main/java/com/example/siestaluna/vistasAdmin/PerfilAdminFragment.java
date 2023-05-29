package com.example.siestaluna.vistasAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siestaluna.Clases.SessionManager;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.GestionCuentas.activity_registro;
import com.example.siestaluna.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilAdminFragment extends Fragment {
    ImageView btnCerrarSesion;
    TextView tvNombreUsuario, tvCorreoUsuario, tvTelefono, tvNacionalidad, tvFechaNac;
    private Usuario admin = UsuarioSingleton.getInstancia().getUsuario();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilAdminFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilAdminFragment newInstance(String param1, String param2) {
        PerfilAdminFragment fragment = new PerfilAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Toma los datos del usuario que inicio sesión
    String nombreUsuario=admin.getNombres()+" "+admin.getApellidos();
    String correoUsuario=admin.getCorreo();
    String telefonoUsuario=admin.getTelefono();
    String nacionalidad=admin.getNacionalidad();
    String fechaNac=admin.getFechaNacimiento();

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
        View view = inflater.inflate(R.layout.fragment_perfil_admin, container, false);
        tvNombreUsuario = view.findViewById(R.id.nombreUsuario);
        tvCorreoUsuario = view.findViewById(R.id.correoUsuario);
        tvTelefono = view.findViewById(R.id.telefonoUsuario);
        tvNacionalidad = view.findViewById(R.id.nacionalidadUsuario);
        tvFechaNac = view.findViewById(R.id.fechaNac);
        btnCerrarSesion = (ImageView) view.findViewById(R.id.btnCerrarSesion);

        tvNombreUsuario.setText(nombreUsuario);
        tvCorreoUsuario.setText(correoUsuario);
        tvTelefono.setText(telefonoUsuario);
        tvNacionalidad.setText(nacionalidad);
        tvFechaNac.setText(fechaNac);

        SessionManager sessionManager = new SessionManager(PerfilAdminFragment.this);

        //si presiona cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearSession();//Elimina los datos del sharedPreferences
                Intent intent= new Intent(getActivity(), activity_login.class);
                startActivity(intent);
            }
        });

        return view;
    }


}