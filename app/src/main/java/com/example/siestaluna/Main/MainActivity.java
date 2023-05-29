package com.example.siestaluna.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.siestaluna.Clases.SessionManager;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioDAO;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.DataBase.DatabaseHelper;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.GestionCuentas.activity_registro;
import com.example.siestaluna.R;
import com.example.siestaluna.VistasUsuarios.FragmentControllerUsuario;
import com.example.siestaluna.vistasAdmin.FragmentControllerAdmin;
import com.example.siestaluna.vistasAdmin.InicioAdminFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int TIEMPO_ESPERA = 1000; // 1 segundo
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = MainActivity.this; // Reemplaza esto con tu propio contexto

        //Se inicia el sessionManager(Clase que maneja el shared preferences)
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        //context.deleteDatabase("DB_HOTEL.db"); //si quieres dejar los datos en 0 y volver ha hace pruebas de 0
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, activity_login.class);
                //Comprueba si se ha iniciado sesion
                if(sessionManager.isSessionSaved()){
                    String[] datosUsuario = sessionManager.getSessionData();

                    //llama al metodo que válida la cuenta en la BD
                    UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);
                    Usuario usuario = usuarioDAO.obtenerUsuario(datosUsuario[0], datosUsuario[1]);
                    UsuarioSingleton.getInstancia().setUsuario(usuario); //se guarda el usuario hasta que cierres la sesion

                    if("1".equals(datosUsuario[2])){//Si se ha iniciado sesion como admin
                        intent = new Intent(MainActivity.this, FragmentControllerAdmin.class);
                    } else if ("2".equals(datosUsuario[2])) {//Si se ha iniciado sesion como usuario
                        intent = new Intent(MainActivity.this, FragmentControllerUsuario.class);
                    }

                }
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, TIEMPO_ESPERA);
    }
}
