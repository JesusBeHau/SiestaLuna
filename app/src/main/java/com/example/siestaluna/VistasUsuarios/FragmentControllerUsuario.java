package com.example.siestaluna.VistasUsuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siestaluna.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentControllerUsuario extends AppCompatActivity {
    InicioUsuarioFragment inicioUsuarioFragment = new InicioUsuarioFragment();
    BuscarUsuarioFragment buscarUsuarioFragment = new BuscarUsuarioFragment();
    CarritoUsuarioFragment carritoUsuarioFragment = new CarritoUsuarioFragment();
    HistorialUsuarioFragment historialUsuarioFragment = new HistorialUsuarioFragment();
    PerfilUsuarioFragment perfilUsuarioFragment = new PerfilUsuarioFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_controller_usuario);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_cliente);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(inicioUsuarioFragment);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.InicioUsuarioFragment:
                    loadFragment(inicioUsuarioFragment);
                    return true;
                case R.id.BuscarUsuarioFragment:
                    loadFragment(buscarUsuarioFragment);
                    return true;
                case R.id.CarritoUsuarioFragment:
                    loadFragment(carritoUsuarioFragment);
                    return true;
                case R.id.HistorialUsuarioFragment:
                    loadFragment(historialUsuarioFragment);
                    return true;
                case R.id.PerfilUsuarioFragment:
                    loadFragment(perfilUsuarioFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerClientes, fragment);
        transaction.commit();
    }

    //para evitar el encolamiento
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}