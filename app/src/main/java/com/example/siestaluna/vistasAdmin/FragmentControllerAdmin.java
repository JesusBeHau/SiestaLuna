package com.example.siestaluna.vistasAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.GestionCuentas.activity_registro;
import com.example.siestaluna.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentControllerAdmin extends AppCompatActivity {

    InicioAdminFragment inicioAdminFragment = new InicioAdminFragment();
    BuscarAdminFragment buscarAdminFragment = new BuscarAdminFragment();
    PerfilAdminFragment perfilAdminFragment = new PerfilAdminFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_controller_admin);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(inicioAdminFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             switch(item.getItemId()){
                 case R.id.InicioAdminFragment:
                     loadFragment(inicioAdminFragment);
                     return true;
                 case R.id.BuscarAdminFragment:
                     loadFragment(buscarAdminFragment);
                     return true;
                 case R.id.PerfilAdminFragment:
                     loadFragment(perfilAdminFragment);
                     return true;
             }
             return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    //para evitar el encolamiento
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}