package com.example.siestaluna.Clases;

import java.io.Serializable;

public class Habitacion implements Serializable {
    private int idHabitacion;
    private String pisoId;
    private int numeroHabitacion;
    private String tipo;
    private double precio;
    private String caracteristicas;
    private int ocupacion;
    private int disponibilidad;
    private byte[] imagen1;
    private byte[] imagen2;
    private byte[] imagen3;
    private byte[] imagen4;

    public Habitacion(int idHabitacion, String pisoId, int numeroHabitacion, String tipo, double precio,
                      String caracteristicas, int ocupacion, int disponibilidad, byte[] imagen1, byte[] imagen2,
                      byte[] imagen3, byte[] imagen4) {
        this.idHabitacion = idHabitacion;
        this.pisoId = pisoId;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precio = precio;
        this.caracteristicas = caracteristicas;
        this.ocupacion = ocupacion;
        this.disponibilidad = disponibilidad;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
    }
    public Habitacion(){

    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getPisoId() {
        return pisoId;
    }

    public void setPisoId(String pisoId) {
        this.pisoId = pisoId;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public byte[] getImagen1() {
        return imagen1;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public byte[] getImagen3() {
        return imagen3;
    }

    public void setImagen3(byte[] imagen3) {
        this.imagen3 = imagen3;
    }

    public byte[] getImagen4() {
        return imagen4;
    }

    public void setImagen4(byte[] imagen4) {
        this.imagen4 = imagen4;
    }
}
