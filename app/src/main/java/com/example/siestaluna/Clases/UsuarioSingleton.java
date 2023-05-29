package com.example.siestaluna.Clases;

import android.content.SharedPreferences;

import java.io.Serializable;

public class UsuarioSingleton implements Serializable {
    private static UsuarioSingleton instancia;
    private Usuario usuario;

    private UsuarioSingleton() {
    }

    public static synchronized UsuarioSingleton getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioSingleton();
        }
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
