package com.example.siestaluna.VistasUsuarios;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.R;

import java.util.ArrayList;

public class AdapterCarrito extends RecyclerView.Adapter<AdapterCarrito.ViewHolder>{
    private ArrayList<Habitacion> listaHabitaciones;
    private LayoutInflater layoutInflater;
    private Context context;
    final AdapterCarrito.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Habitacion habitacion);//click a eliminar
        void onReservarClick(Habitacion habitacion);//click a reservar
    }

    public AdapterCarrito(ArrayList<Habitacion> listaHabitaciones,Context context, AdapterCarrito.OnItemClickListener listener ){
        this.layoutInflater=LayoutInflater.from(context);
        this.listaHabitaciones = listaHabitaciones;
        this.context = context;
        this.listener=listener;
    }
    @Override
    public AdapterCarrito.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.tarjeta_habitacion_carrito, null);
        return new AdapterCarrito.ViewHolder(view);
    }

    @Override
    public int getItemCount(){return listaHabitaciones.size();}

    public void setItems(ArrayList<Habitacion> items){listaHabitaciones = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoHabitacion;
        TextView numHabitacion, detalles;
        Button eliminar, reservar;

        ViewHolder(View itemView){
            super(itemView);
            numHabitacion=itemView.findViewById(R.id.numHabitacion);
            fotoHabitacion=itemView.findViewById(R.id.imagenHab);
            detalles=itemView.findViewById(R.id.detallesGenerales);
            eliminar=itemView.findViewById(R.id.btnEliminar);
            reservar=itemView.findViewById(R.id.btnReservar);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Llama al metodo onItemClick, pasandole la posicion de la habitación
                    listener.onItemClick(listaHabitaciones.get(getAdapterPosition()));
                }
            });

            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onReservarClick(listaHabitaciones.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final AdapterCarrito.ViewHolder holder, final int position){
        holder.numHabitacion.setText("Habitación: "+listaHabitaciones.get(position).getNumeroHabitacion());
        holder.detalles.setText("Tipo: "+listaHabitaciones.get(position).getTipo()+"  Precio: $"+listaHabitaciones.get(position).getPrecio());

        byte[] imagenBytes = listaHabitaciones.get(position).getImagen1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        holder.fotoHabitacion.setImageBitmap(bitmap);
    }
}
