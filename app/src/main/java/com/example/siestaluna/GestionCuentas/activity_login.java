package com.example.siestaluna.GestionCuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siestaluna.Clases.SessionManager;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioDAO;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.VistasUsuarios.FragmentControllerUsuario;
import com.example.siestaluna.vistasAdmin.FragmentControllerAdmin;
import com.example.siestaluna.R;

public class activity_login extends AppCompatActivity {
    Button btnAcceder;
    EditText txtCorreo, txtContrasena;
    TextView btnRegistro;
    TextView btnRecuperarContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager sessionManager = new SessionManager(activity_login.this);

        //Asociamos las variables con los elementos de la interfaz
        btnAcceder= (Button) findViewById(R.id.btnAcceder);
        txtCorreo= (EditText) findViewById(R.id.etCorreo);
        txtContrasena= (EditText) findViewById(R.id.etPassword);
        btnRegistro=(TextView) findViewById(R.id.btnRegistro);
        btnRecuperarContra=(TextView) findViewById(R.id.btnRecuperarContra);

        //lo mandamos la interfaz de Bienvenida
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo= txtCorreo.getText().toString();
                String contrasena= txtContrasena.getText().toString();

                //Validamos los datos
                if(correo.trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    txtCorreo.setError("Ingrese un correo válido");
                    return;
                }else{
                    txtCorreo.setError(null);
                }
                if(contrasena.trim().isEmpty()){
                    txtContrasena.setError("Ingrese su contraseña");
                    return;
                }else{
                    txtContrasena.setError(null);
                }

                //llama al metodo que válida la cuenta en la BD
                UsuarioDAO usuarioDAO = new UsuarioDAO(activity_login.this);
                Usuario usuario = usuarioDAO.obtenerUsuario(correo, contrasena);

                //Comprobar si existe registro
                if (usuario != null) {
                    int rol = usuario.getRol();
                    int rolAdmin = 1;
                    int rolCliente = 2;
                    UsuarioSingleton.getInstancia().setUsuario(usuario); //se guarda el usuario hasta que cierres la sesion
                    sessionManager.saveSession(correo, contrasena, rol);

                    //vamos a las otras ventanas
                    if (rol == rolAdmin) {
                        Intent intent= new Intent(activity_login.this, FragmentControllerAdmin.class);
                        startActivity(intent);

                    } else if (rol == rolCliente) {
                        Intent intent= new Intent(activity_login.this, FragmentControllerUsuario.class);
                        startActivity(intent);
                    }
                } else {
                    // mensaje de error
                    Toast.makeText(activity_login.this, "Datos erroneos", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //lo mandamos a la interfaz de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(activity_login.this, activity_registro.class);
                startActivity(intent);
                finish();
            }
        });

        //lo mandamos a la interfaz de recuperar contraseña
        btnRecuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(activity_login.this, activity_recuperarContra.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //para evitar el encolamiento
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}