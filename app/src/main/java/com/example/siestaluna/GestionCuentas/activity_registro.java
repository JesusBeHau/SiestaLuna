package com.example.siestaluna.GestionCuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siestaluna.Clases.SessionManager;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioDAO;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.R;
import com.example.siestaluna.VistasUsuarios.FragmentControllerUsuario;
import com.example.siestaluna.vistasAdmin.FragmentControllerAdmin;

import java.util.Calendar;

public class activity_registro extends AppCompatActivity{
    Button guardar, cancelar;
    EditText txtNombre, txtApellidos, txtCorreo, txtTelefono, txtNacionalidad, txtFechaNacimiento, txtContrasena;
    int anioNacimiento, mesNacimiento, diaNacimiento;
    String fechaDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Calendar calendar = Calendar.getInstance();

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        //Asociamos las variables con los elementos de la interfaz
        guardar=(Button)findViewById(R.id.btnRegistrar);
        cancelar=(Button)findViewById(R.id.btnCancelar);
        txtNombre=(EditText)findViewById(R.id.etNombre);
        txtApellidos=(EditText)findViewById(R.id.etApellido);
        txtCorreo=(EditText)findViewById(R.id.etCorreo);
        txtTelefono=(EditText)findViewById(R.id.etTelefono);
        txtNacionalidad=(EditText)findViewById(R.id.etNacionalidad);
        txtContrasena=(EditText)findViewById(R.id.etContra);
        txtFechaNacimiento=(EditText)findViewById(R.id.etFechaNac);
        anioNacimiento = calendar.get(Calendar.YEAR);
        mesNacimiento = calendar.get(Calendar.MONTH);
        diaNacimiento = calendar.get(Calendar.DAY_OF_MONTH);

        //implementación calendario
        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_registro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Actualiza el texto del EditText
                                txtFechaNacimiento.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                fechaDB = year+"-"+ (month + 1) +"-"+dayOfMonth;

                            }
                        }, anioNacimiento, mesNacimiento, diaNacimiento);
                // Muestra el DatePickerDialog
                datePickerDialog.show();
            }
        });


        //Guardar Registro
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validamos que los campos esten llenados
                if(validarCampo(txtNombre)&&validarCampo(txtApellidos)&&validarCampo(txtCorreo)&&validarCampo(txtTelefono)&&validarCampo(txtNacionalidad)&&validarCampo(txtFechaNacimiento)&&validarCampo(txtContrasena)){
                    //Recogemos todos los datos
                    Usuario nuevoUsaurio = new Usuario();
                    nuevoUsaurio.setNombres(txtNombre.getText().toString());
                    nuevoUsaurio.setApellidos(txtApellidos.getText().toString());
                    nuevoUsaurio.setCorreo(txtCorreo.getText().toString());
                    nuevoUsaurio.setTelefono(txtTelefono.getText().toString());
                    nuevoUsaurio.setNacionalidad(txtNacionalidad.getText().toString());
                    nuevoUsaurio.setFechaNacimiento(fechaDB);
                    nuevoUsaurio.setContrasenia(txtContrasena.getText().toString());
                    nuevoUsaurio.setRol(2);

                    //llama al metodo que válida la cuenta en la BD
                    UsuarioDAO usuarioDAO = new UsuarioDAO(activity_registro.this);
                    Boolean conseguido = usuarioDAO.registrarCliente(nuevoUsaurio);
                    usuarioDAO.mostrarDatosUsuarios();

                    //Si se logra registrar que pase directo a su ventana de cliente
                    if (conseguido){
                       UsuarioSingleton.getInstancia().setUsuario(nuevoUsaurio); //se guarda el usuario hasta que cierres
                        sessionManager.saveSession(nuevoUsaurio.getCorreo(), nuevoUsaurio.getContrasenia(), nuevoUsaurio.getRol());
                       // aqui solo queria probar que envie a una ventana, ya luego creas y pones la de el cliente-
                        Intent intent= new Intent(activity_registro.this, FragmentControllerUsuario.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //Si hay fallo o usuario ya registrado
                        Toast.makeText(activity_registro.this, "Ya existe una cuenta registrada con ese correo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        //Cancela el registro
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_registro.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //para evitar el encolamiento
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(activity_registro.this, activity_login.class);
        startActivity(intent);
    }

    public boolean validarCampo(EditText campo){
        boolean retorno=true;
        String texto = campo.getText().toString();
        if(texto.trim().isEmpty()){
            campo.setError("Por favor rellene este campo");
            retorno=false;
        }else{
            campo.setError(null);
        }
        return retorno;
    }
}