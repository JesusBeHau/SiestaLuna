package com.example.siestaluna.VistasUsuarios;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siestaluna.Clases.Habitacion;
import com.example.siestaluna.R;
import com.example.siestaluna.vistasAdmin.AdapterHabitaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterHabitacionUsuario extends RecyclerView.Adapter<AdapterHabitacionUsuario.ViewHolder> {

    private ArrayList<Habitacion> listaHabitaciones;
    private ArrayList<Habitacion> originalListaHabitaciones;
    private LayoutInflater layoutInflater;
    private Context context;
    final AdapterHabitacionUsuario.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Habitacion habitacion);
    }

    public AdapterHabitacionUsuario(ArrayList<Habitacion> listaHabitaciones, Context context, AdapterHabitacionUsuario.OnItemClickListener listener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.listaHabitaciones = listaHabitaciones;
        this.context = context;
        originalListaHabitaciones=new ArrayList<>();
        originalListaHabitaciones.addAll(listaHabitaciones);
        this.listener = listener;
    }

    @Override
    public AdapterHabitacionUsuario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tarjeta_habitacion_usuario, null);
        return new AdapterHabitacionUsuario.ViewHolder(view);
    }

    @Override
    public int getItemCount() {return listaHabitaciones.size();}

    /**
     * Metodo para Buscar las habitaciones
     * @param txtBusqueda recibe como parametro el texto ingresado al campo de busqueda
     */
    public void filter(String txtBusqueda){
        if(txtBusqueda.length()==0){
            listaHabitaciones.clear();
            listaHabitaciones.addAll(originalListaHabitaciones);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                String[] palabrasBusqueda = txtBusqueda.toLowerCase().split("\\s");
                //Compara si lo buscado se encuentra en el tipo de habitación, en el número o en el precio
                List<Habitacion> collect = originalListaHabitaciones.stream()
                        .filter(i -> {
                            String tipo = i.getTipo().toLowerCase();
                            String numHabitacion = Integer.toString(i.getNumeroHabitacion());
                            String precioHabitacion = Double.toString(i.getPrecio());
                            boolean algunaCoincidencia = true;
                            for(String palabra : palabrasBusqueda){
                                if (!(tipo.contains(palabra) || numHabitacion.contains(palabra)
                                        || precioHabitacion.contains(palabra))){
                                    algunaCoincidencia=false;
                                }
                            }
                            return algunaCoincidencia;
                        })
                        .collect(Collectors.toList());
                listaHabitaciones.clear();
                listaHabitaciones.addAll(collect);
            }else{
                listaHabitaciones.clear();
                String[] palabrasBusqueda = txtBusqueda.toLowerCase().split("\\s");
                for(Habitacion i : originalListaHabitaciones){
                    boolean algunaCoincidencia = true;
                    for(String palabra : palabrasBusqueda){
                        if(!(i.getTipo().toLowerCase().contains(palabra)
                                ||Integer.toString(i.getNumeroHabitacion()).contains(palabra)
                                || Double.toString(i.getPrecio()).contains(palabra))){
                            algunaCoincidencia = false;
                        }
                    }
                    if(algunaCoincidencia){listaHabitaciones.add(i);}
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<Habitacion> items){listaHabitaciones = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoHabitacion;
        TextView numHabitación, detalles;
        Button verMas;
        ViewHolder(View itemView){
            super(itemView);
            numHabitación=itemView.findViewById(R.id.numHabitacion);
            fotoHabitacion= itemView.findViewById(R.id.imagenHab);
            detalles= itemView.findViewById(R.id.detallesGenerales);
            verMas=itemView.findViewById(R.id.btnVerMas);

            //listener del boton ver más
            verMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(listaHabitaciones.get(getAdapterPosition()));
                }
            });
        }

    }

    //Pone los atributos de la habitación en la interfaz
    @Override
    public void onBindViewHolder(final AdapterHabitacionUsuario.ViewHolder holder, final int position) {
        holder.numHabitación.setText("Habitación: "+listaHabitaciones.get(position).getNumeroHabitacion());
        holder.detalles.setText("Tipo: "+listaHabitaciones.get(position).getTipo()+"  Precio: $"+listaHabitaciones.get(position).getPrecio());

        byte[] imagenBytes = listaHabitaciones.get(position).getImagen1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
        holder.fotoHabitacion.setImageBitmap(bitmap);
    }
}