package com.example.siestaluna.Clases;

import java.io.Serializable;

public class Reservacion implements Serializable {
    private int idReservacion;
    private String Usuario_correo;
    private int Habitacion_idHabitacion;
    private int personasOcupan;
    private String fechaIngreso;
    private String fechaSalida;
    private String nombreCliente;
    private double pago;
    private byte[] imagen;

    //constructor
    public  Reservacion(){

    }

    //Getters y setters
    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getUsuario_correo() {
        return Usuario_correo;
    }

    public void setUsuario_correo(String usuario_correo) {
        Usuario_correo = usuario_correo;
    }

    public int getHabitacion_idHabitacion() {
        return Habitacion_idHabitacion;
    }

    public void setHabitacion_idHabitacion(int habitacion_idHabitacion) {
        Habitacion_idHabitacion = habitacion_idHabitacion;
    }

    public int getPersonasOcupan() {
        return personasOcupan;
    }

    public void setPersonasOcupan(int personasOcupan) {
        this.personasOcupan = personasOcupan;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
