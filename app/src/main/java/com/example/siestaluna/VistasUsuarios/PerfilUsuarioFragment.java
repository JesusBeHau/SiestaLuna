package com.example.siestaluna.VistasUsuarios;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siestaluna.Clases.SessionManager;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioDAO;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.GestionCuentas.activity_registro;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.PerfilAdminFragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUsuarioFragment extends Fragment {
    ImageView btnCerrarSesion;
    Button btnCancelar, btnGuardar;
    TextView tvNombreUsuario, tvCorreoUsuario;
    EditText etTelefono, etNacionalidad, etFechaNac;
    int anioNacimiento, mesNacimiento, diaNacimiento;
    String fechaDB;
    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUsuarioFragment newInstance(String param1, String param2) {
        PerfilUsuarioFragment fragment = new PerfilUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Toma los datos del usuario que inicio sesión
    String nombreUsuario=usuario.getNombres()+" "+usuario.getApellidos();
    String correoUsuario=usuario.getCorreo();
    String telefonoUsuario=usuario.getTelefono();
    String nacionalidad=usuario.getNacionalidad();
    String fechaNac=usuario.getFechaNacimiento();

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
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        Calendar calendar = Calendar.getInstance();

        tvNombreUsuario = view.findViewById(R.id.nombreUsuario);
        tvCorreoUsuario = view.findViewById(R.id.correoUsuario);
        etTelefono = view.findViewById(R.id.telefonoUsuario);
        etNacionalidad = view.findViewById(R.id.nacionalidadUsuario);
        etFechaNac = view.findViewById(R.id.fechaNac);
        btnCerrarSesion = (ImageView) view.findViewById(R.id.btnCerrarSesion);
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
        anioNacimiento = calendar.get(Calendar.YEAR);
        mesNacimiento = calendar.get(Calendar.MONTH);
        diaNacimiento = calendar.get(Calendar.DAY_OF_MONTH);
        fechaDB=fechaNac;

        tvNombreUsuario.setText(nombreUsuario);
        tvCorreoUsuario.setText(correoUsuario);
        etTelefono.setText(telefonoUsuario);
        etNacionalidad.setText(nacionalidad);
        etFechaNac.setText(fechaNac);

        //Implementación del calendario para seleccionar fecha
        etFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Actualiza el texto del EditText
                                etFechaNac.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                fechaDB = year+"-"+ (month + 1) +"-"+dayOfMonth;

                            }
                        }, anioNacimiento, mesNacimiento, diaNacimiento);
                // Muestra el DatePickerDialog
                datePickerDialog.show();
            }
        });

        SessionManager sessionManager = new SessionManager(PerfilUsuarioFragment.this);

        //si presiona cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearSession();//Elimina los datos del sharedPreferences
                Intent intent= new Intent(getActivity(), activity_login.class);
                startActivity(intent);
            }
        });
        //Si presiona cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.PerfilUsuarioFragment);
                Fragment newFragment = new InicioUsuarioFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerClientes, newFragment);
                transaction.addToBackStack(null);
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.commit();
            }
        });
        //Si presiona guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Actualizamos los datos en el objeto usuario
                usuario.setTelefono(etTelefono.getText().toString());
                usuario.setNacionalidad(etNacionalidad.getText().toString());
                usuario.setFechaNacimiento(fechaDB);
                //Actualizamos los datos en la BD
                UsuarioDAO usuarioDao = new UsuarioDAO(getContext());
                boolean actualizado = usuarioDao.actualizarUsuario(usuario);
                if(actualizado){
                    Toast.makeText(getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "No se pudieron guadar los cambios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}