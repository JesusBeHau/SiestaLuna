package com.example.siestaluna.VistasUsuarios;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.R;

import java.util.ArrayList;

public class AdapterReservacionesUsuario extends RecyclerView.Adapter<AdapterReservacionesUsuario.ViewHolder>{
    private ArrayList<Reservacion> listaReservaciones;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterReservacionesUsuario(ArrayList<Reservacion> listaReservaciones, Context context){
        this.layoutInflater=LayoutInflater.from(context);
        this.listaReservaciones=listaReservaciones;
        this.context=context;
    }

    @Override
    public AdapterReservacionesUsuario.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tarjeta_reservaciones_usuario, null);
        return new AdapterReservacionesUsuario.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listaReservaciones.size();
    }

    public void setItems(ArrayList<Reservacion> items){listaReservaciones=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView numHabitacion, fechaIngreso, fechaSalida, tvOcupacion;
        ImageView imagenHab;
        ViewHolder(View itemView){
            super(itemView);
            numHabitacion=itemView.findViewById(R.id.numHabitacion);
            fechaIngreso=itemView.findViewById(R.id.fechaIngreso);
            fechaSalida=itemView.findViewById(R.id.fechaSalida);
            tvOcupacion=itemView.findViewById(R.id.tvOcupacion);
            imagenHab=itemView.findViewById(R.id.imagenHab);
        }
    }

    @Override
    public void onBindViewHolder(final AdapterReservacionesUsuario.ViewHolder holder, int position) {
        holder.numHabitacion.setText("Habitacion "+listaReservaciones.get(position).getHabitacion_idHabitacion());
        holder.fechaIngreso.setText("Fecha de ingreso: "+listaReservaciones.get(position).getFechaIngreso());
        holder.fechaSalida.setText("Fecha de salida: "+listaReservaciones.get(position).getFechaSalida());
        holder.tvOcupacion.setText("Personas hospedadas: "+listaReservaciones.get(position).getPersonasOcupan());

        byte[] imagenBytes = listaReservaciones.get(position).getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        holder.imagenHab.setImageBitmap(bitmap);
    }
}
