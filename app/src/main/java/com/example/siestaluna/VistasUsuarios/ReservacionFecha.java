package com.example.siestaluna.VistasUsuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.Clases.HabitacionDao;
import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.Clases.ReservacionDAO;
import com.example.siestaluna.Clases.Usuario;
import com.example.siestaluna.Clases.UsuarioSingleton;
import com.example.siestaluna.GestionCuentas.activity_login;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.ActualizarHabitacion;
import com.example.siestaluna.vistasAdmin.FragmentControllerAdmin;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservacionFecha extends AppCompatActivity {
    private List<Long> fechasReservadas = new ArrayList<>();
    private Usuario usuario = UsuarioSingleton.getInstancia().getUsuario();
    Reservacion reservacion = new Reservacion();
    private Calendar currentMonth;
    private TextView monthTextView;
    EditText etFechaIngreso, etFechaSalida, etNumPersonas;
    Button btnCancelar, btnContinuar;
    String fechaIngreso, fechaSalida;
    int anioIngreso, mesIngreso, diaIngreso, anioSalida, mesSalida, diaSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_fecha);
        Calendar calendario = Calendar.getInstance();

        //Enlazamos con los elementos de la interfaz
        monthTextView = findViewById(R.id.monthTextView);
        etFechaIngreso= (EditText) findViewById(R.id.etFechaIngreso);
        etFechaSalida=(EditText) findViewById(R.id.etFechaSalida);
        etNumPersonas=(EditText) findViewById(R.id.etNumPersonas);
        btnCancelar=(Button) findViewById(R.id.btnCancelar);
        btnContinuar=(Button) findViewById(R.id.btnContinuar);
        anioIngreso = calendario.get(Calendar.YEAR);
        mesIngreso = calendario.get(Calendar.MONTH);
        diaIngreso = calendario.get(Calendar.DAY_OF_MONTH);
        anioSalida = calendario.get(Calendar.YEAR);
        mesSalida = calendario.get(Calendar.MONTH);
        diaSalida = calendario.get(Calendar.DAY_OF_MONTH);

        //Recuperamos el id de la habitacion
        int idHabitacion = getIntent().getIntExtra("idHabitacion", 0);

        //Se recupera la habitación
        HabitacionDao habitacionDao = new HabitacionDao(ReservacionFecha.this);
        Habitacion habitacion = habitacionDao.obtenerHabitacion(idHabitacion);

        //Se recuperan las fechas reservadas de la habitación
        ReservacionDAO reservacionDAO = new ReservacionDAO(ReservacionFecha.this);
        fechasReservadas=reservacionDAO.obtenerFechasReservadas(idHabitacion);

        currentMonth = Calendar.getInstance();
        //Se instancia el calendario
        updateCalendarTable();

        //Para visualizar el mes anterior
        ImageView btnMesAnterior = findViewById(R.id.btnMesAnterior);
        btnMesAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth.add(Calendar.MONTH, -1);
                updateCalendarTable();
            }
        });

        //Para visualizar el mes siguiente
        ImageView btnMesSiguiente = findViewById(R.id.btnMesSig);
        btnMesSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMonth.add(Calendar.MONTH, 1);
                updateCalendarTable();
            }
        });

        //DatePicker para la fecha de ingreso
        etFechaIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservacionFecha.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //Actualiza el texto del EditText
                                etFechaIngreso.setText(day+"/"+(month+1)+"/"+year);
                                fechaIngreso=year+"-";
                                if(month<9){
                                    fechaIngreso+="0"+(month+1)+"-";
                                }else{
                                    fechaIngreso+=(month+1)+"-";
                                }
                                if(day<10){
                                    fechaIngreso+="0"+day;
                                }else{
                                    fechaIngreso+=day;
                                }
                            }
                        }, anioIngreso, mesIngreso, diaIngreso);
                // Muestra el DatePickerDialog
                datePickerDialog.show();
            }
        });

        //DatePicker para la fecha de salida
        etFechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReservacionFecha.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //Actualiza el texto del EditText
                                etFechaSalida.setText(day+"/"+(month+1)+"/"+year);
                                fechaSalida=year+"-";
                                if(month<9){
                                    fechaSalida+="0"+(month+1)+"-";
                                }else{
                                    fechaSalida+=(month+1)+"-";
                                }
                                if(day<10){
                                    fechaSalida+="0"+day;
                                }else{
                                    fechaSalida+=day;
                                }
                            }
                        }, anioSalida, mesSalida, diaSalida);
                // Muestra el DatePickerDialog
                datePickerDialog.show();
            }
        });


        //Si le da a continuar la reservacion
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validar que los campos esten llenos
                if(validarCampo(etFechaIngreso) && validarCampo(etFechaSalida) && validarCampo(etNumPersonas)){
                    //Validar el número de personas
                    String numPersonasString=etNumPersonas.getText().toString();
                    int numPersonas = Integer.parseInt(numPersonasString);
                    if(numPersonas>habitacion.getOcupacion()){
                        etNumPersonas.setError("El número de personas para esa habitación no puede ser mayor a "+habitacion.getOcupacion());
                    }else{
                        Boolean fechasValidas=fechasValidas(fechaIngreso, fechaSalida);
                        Boolean fechasDisp=fechasDisponibles(fechaIngreso, fechaSalida);

                        if(fechasValidas==true && fechasDisp == true){
                            reservacion.setHabitacion_idHabitacion(habitacion.getIdHabitacion());
                            reservacion.setFechaIngreso(fechaIngreso);
                            reservacion.setFechaSalida(fechaSalida);
                            reservacion.setUsuario_correo(usuario.getCorreo());
                            reservacion.setPersonasOcupan(Integer.parseInt(etNumPersonas.getText().toString()));
                            int diasHospedaje=diasHospedaje(fechaIngreso, fechaSalida);
                            reservacion.setPago(diasHospedaje*habitacion.getPrecio());
                            Intent intent= new Intent(ReservacionFecha.this, PagoReservacion.class);
                            intent.putExtra("reservacion", reservacion);
                            startActivity(intent);
                        }

                    }
                }
            }
        });


        //Si cancela el proceso de reservacion
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ReservacionFecha.this, FragmentControllerUsuario.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Calcula los dias de hospedaje en la reservacion
     * @param fechaIngreso recibe la fecha de ingreso como String
     * @param fechaSalida recibe la fecha de salida como String
     * @return devuelve el número de días que tendra la reservación
     */
    public int diasHospedaje(String fechaIngreso, String fechaSalida){
        int dias=0;
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio=dateFormat.parse(fechaIngreso);
            Date fechaFinal=dateFormat.parse(fechaSalida);

            Calendar calendarInicial = Calendar.getInstance();
            calendarInicial.setTime(fechaInicio);
            calendarInicial.set(Calendar.HOUR_OF_DAY, 0);
            calendarInicial.set(Calendar.MINUTE, 0);
            calendarInicial.set(Calendar.SECOND, 0);
            calendarInicial.set(Calendar.MILLISECOND, 0);

            Calendar calendarFinal = Calendar.getInstance();
            calendarFinal.setTime(fechaFinal);
            calendarFinal.set(Calendar.HOUR_OF_DAY, 0);
            calendarFinal.set(Calendar.MINUTE, 0);
            calendarFinal.set(Calendar.SECOND, 0);
            calendarFinal.set(Calendar.MILLISECOND, 0);

            long milisegundosPorDia = 86_400_000L; // Número de milisegundos en un día
            long diferenciaMilisegundos = calendarFinal.getTimeInMillis() - calendarInicial.getTimeInMillis();
            dias = (int) (diferenciaMilisegundos / milisegundosPorDia) + 1;

        }catch (Exception e){

        }
        return dias;
    }

    //Comprueba que las fechas sean posteriores a la actual y que la fecha de salida sea mayor a la de ingreso
    public boolean fechasValidas(String fechaIngreso, String fechaSalida){
        boolean fechasValidas=true;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try{

            Date fechaInicio=dateFormat.parse(fechaIngreso);
            Date fechaFinal=dateFormat.parse(fechaSalida);
            if(fechaInicio.after(new Date())){//Si la fecha de ingreso es posterior a la actual
                if(fechaInicio.after(fechaFinal)){//si la fecha de ingreso es posterior a la final
                    fechasValidas=false;
                    Toast.makeText(ReservacionFecha.this, "La fecha de ingreso no puede ser posterior a la de salida", Toast.LENGTH_LONG).show();
                }
            }else{
                fechasValidas=false;
                Toast.makeText(ReservacionFecha.this, "La fecha de ingreso debe ser posterior al día de hoy", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            fechasValidas=false;
        }
        return fechasValidas;
    }

    //Valida que las fechas de la nueva reservación no choquen con alguna reservacion ya hecha
    public boolean fechasDisponibles(String fechaIngreso, String fechaSalida){
        boolean fechaCorrecta=true;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Convertir las fechas de entrada y salida a milisegundos
            Date fechaEntrada=dateFormat.parse(fechaIngreso);
            Date fechaFinal=dateFormat.parse(fechaSalida);
            long fechaEntradaMilis=fechaEntrada.getTime();
            long fechaFinalMilis=fechaFinal.getTime();

            //Comprobar si el rango de fechas choca con alguna ya registrada
            for(Long fechaReservadaMilis: fechasReservadas){
                if(fechaEntradaMilis<=fechaReservadaMilis && fechaFinalMilis>fechaReservadaMilis){
                    fechaCorrecta=false;
                    break;
                }
                if(fechaEntradaMilis>=fechaReservadaMilis && fechaEntradaMilis<fechaReservadaMilis+86400000L){
                    fechaCorrecta=false;
                    break;
                }
            }

        } catch (ParseException e) {
            fechaCorrecta=false;
        }
        if(!fechaCorrecta){
            Toast.makeText(ReservacionFecha.this, "Ya existe una reservacion en las fechas seleccionadas", Toast.LENGTH_LONG).show();
        }
        return fechaCorrecta;
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


    /**
     * METODOS PARA LA VISUALIZACION DEL CALENDARIO CON LAS FECHAS DISPONIBLES O RESERVADAS
     */
    private void updateCalendarTable(){
        TableLayout calendarTableLayout = findViewById(R.id.calendarTableLayout);
        calendarTableLayout.removeAllViews();

        //Obtiene el número de dias del mes
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        monthTextView.setText(getMonthYearString(currentMonth));

        int startOffset = currentMonth.get(Calendar.DAY_OF_WEEK) -1;
        int rowCount=0;

        TableRow tableRow = new TableRow(ReservacionFecha.this);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        //Agregar celdar vacías antes del primer dia del mes
        for (int i = 0; i < startOffset; i++) {
            TextView emptyCell = createEmptyCell();
            emptyCell.setVisibility(View.INVISIBLE);
            tableRow.addView(emptyCell);
        }

        //Se agregan los días del mes al calendario
        for(int day=1; day<=daysInMonth; day++){
            Calendar currentDay = (Calendar) currentMonth.clone();
            currentDay.set(Calendar.DAY_OF_MONTH, day);
            currentDay.set(Calendar.HOUR_OF_DAY, 0);
            currentDay.set(Calendar.MINUTE, 0);
            currentDay.set(Calendar.SECOND, 0);
            currentDay.set(Calendar.MILLISECOND, 0);

            //Comprueba si esa fecha ya se encuentra reservada
            Long selectedTimestamp = currentDay.getTimeInMillis();
            boolean estaReservada=fechasReservadas.contains(selectedTimestamp);

            //llama al metodo que crea la celda del dia correspondiente
            TextView dayTextView = createDayTextView(day, estaReservada);

            //Se agrega la celda a la tabla del calendario
            tableRow.addView(dayTextView);
            //Calcula cuantas celdas vacias quedan despues del ultimo dia del mes
            if ((day + startOffset) % 7 == 0) {
                calendarTableLayout.addView(tableRow);
                tableRow = new TableRow(ReservacionFecha.this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                rowCount++;
            }
        }

        //Agregar celdas vacías después del último dia del mes
        int remainingCells = 7 - ((daysInMonth + startOffset) % 7);
        if (remainingCells < 7) {
            for (int i = 0; i < remainingCells; i++) {
                TextView emptyCell = createEmptyCell();
                emptyCell.setVisibility(View.INVISIBLE);
                tableRow.addView(emptyCell);
            }
        }

        if(rowCount<6){
            calendarTableLayout.addView(tableRow);
        }
    }

    //Metodo que crea las celdas vacias
    private TextView createEmptyCell(){
        TextView emptyCell = new TextView(ReservacionFecha.this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        emptyCell.setLayoutParams(layoutParams);
        emptyCell.setVisibility(View.INVISIBLE);
        return emptyCell;
    }

    //Metodo que crea las celdas de los dias del mes
    private TextView createDayTextView(int dia, boolean reservado){
        TextView dayTextView = new TextView(ReservacionFecha.this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        dayTextView.setLayoutParams(layoutParams);
        dayTextView.setGravity(Gravity.CENTER);
        dayTextView.setTextSize(15);
        dayTextView.setText(String.valueOf(dia));

        //Si esta reservado pone un fondo azul a la celda de la fecha
        if(reservado){
            dayTextView.setBackgroundColor(Color.parseColor("#98F8EB"));
            dayTextView.setTextColor(Color.BLACK);
        }else{
            dayTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            dayTextView.setTextColor(Color.BLACK);
        }

        return dayTextView;
    }

    private String getMonthYearString(Calendar calendar){
        int month=calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
        String monthName = dateFormatSymbols.getMonths()[month];

        return monthName+" "+year;
    }
}