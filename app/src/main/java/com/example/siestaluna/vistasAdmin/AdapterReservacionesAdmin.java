package com.example.siestaluna.vistasAdmin;

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

import com.example.siestaluna.Clases.Reservacion;
import com.example.siestaluna.R;
import com.example.siestaluna.VistasUsuarios.AdapterCarrito;
import com.example.siestaluna.VistasUsuarios.AdapterReservacionesUsuario;

import java.util.ArrayList;

public class AdapterReservacionesAdmin extends RecyclerView.Adapter<AdapterReservacionesAdmin.ViewHolder> {
    private ArrayList<Reservacion> listaReservaciones;
    private LayoutInflater layoutInflater;
    private Context context;
    final AdapterReservacionesAdmin.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Reservacion reservacion);//Click a eliminar
    }

    public AdapterReservacionesAdmin(ArrayList<Reservacion> listaReservaciones, Context context, AdapterReservacionesAdmin.OnItemClickListener listener){
        this.layoutInflater=LayoutInflater.from(context);
        this.listaReservaciones = listaReservaciones;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public AdapterReservacionesAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.tarjeta_reservaciones_admin, null);
        return new AdapterReservacionesAdmin.ViewHolder(view);
    }

    @Override
    public int getItemCount(){return listaReservaciones.size();}

    public void setItems(ArrayList<Reservacion> items){listaReservaciones=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView numHabitacion, fechaIngreso, fechaSalida, tvOcupacion;
        ImageView imagenHab;
        Button btnCancelar;

        ViewHolder(View itemView){
            super(itemView);
            numHabitacion=itemView.findViewById(R.id.numHabitacion);
            fechaIngreso=itemView.findViewById(R.id.fechaIngreso);
            fechaSalida=itemView.findViewById(R.id.fechaSalida);
            tvOcupacion=itemView.findViewById(R.id.tvOcupacion);
            imagenHab=itemView.findViewById(R.id.imagenHab);
            btnCancelar=itemView.findViewById(R.id.btnCancelar);

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Llama al metodo onItemClick, pasandole la posicion de la reservacion
                    listener.onItemClick(listaReservaciones.get(getAdapterPosition()));
                }
            });
        }
    }

    public void onBindViewHolder(final AdapterReservacionesAdmin.ViewHolder holder, final int position){
        holder.numHabitacion.setText("Habitacion "+listaReservaciones.get(position).getHabitacion_idHabitacion());
        holder.fechaIngreso.setText("Fecha de ingreso: "+listaReservaciones.get(position).getFechaIngreso());
        holder.fechaSalida.setText("Fecha de salida: "+listaReservaciones.get(position).getFechaSalida());
        holder.tvOcupacion.setText("Personas hospedadas: "+listaReservaciones.get(position).getPersonasOcupan());

        byte[] imagenBytes = listaReservaciones.get(position).getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        holder.imagenHab.setImageBitmap(bitmap);
    }
}
