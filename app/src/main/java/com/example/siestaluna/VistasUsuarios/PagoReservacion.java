package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.Clases.ReservacionDAO;
import com.example.siestaluna.R;

public class PagoReservacion extends AppCompatActivity {
    EditText etNumTarjeta, etNombreTarjeta, etFechaExp, etCVV;
    Button btnConfirmar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_reservacion);
        Intent intent = getIntent();
        Reservacion reservacion = (Reservacion) intent.getSerializableExtra("reservacion");

        etNumTarjeta=(EditText) findViewById(R.id.etNumTarjeta);
        etNombreTarjeta=(EditText) findViewById(R.id.etNombreTarjeta);
        etFechaExp=(EditText) findViewById(R.id.etFechaExp);
        etCVV=(EditText) findViewById(R.id.etCVV);
        btnConfirmar=(Button) findViewById(R.id.btnConfirmar);
        btnCancelar=(Button) findViewById(R.id.btnCancelar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampo(etNumTarjeta) && validarCampo(etNombreTarjeta) && validarCampo(etFechaExp) && validarCampo(etCVV)){
                    reservacion.setNombreCliente(etNombreTarjeta.getText().toString());
                    //Lo guardamos en la BD
                    ReservacionDAO reservacionDAO = new ReservacionDAO(PagoReservacion.this);
                    reservacionDAO.registrarReservacion(reservacion);
                    //Mandamos al usuario a la interfaz de confirmación
                    Intent intent= new Intent(PagoReservacion.this, ticketReservacion.class);
                    intent.putExtra("reservacion", reservacion);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Si cancela la reservacion
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PagoReservacion.this, FragmentControllerUsuario.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Valida que los campos esten llenos
     * @param campo recibe el campo que validara
     * @return regresa true si el campo contiene datos
     */
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