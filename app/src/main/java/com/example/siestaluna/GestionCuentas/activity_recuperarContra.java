package com.example.siestaluna.GestionCuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioDAO;
import com.example.siestaluna.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class activity_recuperarContra extends AppCompatActivity {
    EditText txtCorreo;
    Button enviarCodigo;
    ImageView atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);
        //Asociaciamos los elementos con los de la interfaz
        txtCorreo=(EditText) findViewById(R.id.etCorreo);
        atras=(ImageView) findViewById(R.id.atras);
        enviarCodigo=(Button) findViewById(R.id.btnEnviarContra);

        //el presionar "Enviar contraseña"
        enviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo=txtCorreo.getText().toString();
                //Validamos el formato delcorreo
                if(correo.trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    txtCorreo.setError("Formato de correo inválido");
                    return;
                }else{
                    txtCorreo.setError(null);
                }

                //Validamos que el usuario este registrado
                UsuarioDAO usuarioDAO = new UsuarioDAO(activity_recuperarContra.this);
                Boolean usuarioRegistrado = usuarioDAO.verificarCorreoExistente(correo);

                if(usuarioRegistrado){//Si el usuario esta registrado
                    String contrasena=usuarioDAO.obtenerContrasena(correo);
                    //Se llama al metodo que envía la contraseña al correo
                    new EnviarCorreoTask().execute(correo, contrasena);
                }else{
                    Toast.makeText(activity_recuperarContra.this, "No existe niguna cuenta asociada a ese correo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton para salir
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_recuperarContra.this, activity_login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private class EnviarCorreoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String correoDestinatario = strings[0];
            String contrasena = strings[1];
            String correoRemitente = "APPSiestaLuna@gmail.com";
            String passwordRemitente = "mjwwihjgpchmwzsc";

            Properties props = new Properties();
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(correoRemitente, passwordRemitente);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correoRemitente));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
                message.setSubject("Recuperación de contraseña Siesta Luna");
                message.setText("La contraseña de su cuenta registrada en la Aplicación Siesta luna es la siguiente:\n"+contrasena);

                Transport.send(message);

                return "La contraseña se ha enviado a tu correo.";
            } catch (AddressException e) {
                e.printStackTrace();
                return "Dirección de correo electrónico inválida.";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error al enviar el correo electrónico.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(activity_recuperarContra.this, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(activity_recuperarContra.this, activity_login.class);
        startActivity(intent);
    }
}