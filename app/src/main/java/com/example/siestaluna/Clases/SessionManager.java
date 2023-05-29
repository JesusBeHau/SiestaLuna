package com.example.siestaluna.Clases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.siestaluna.VistasUsuarios.PerfilUsuarioFragment;
import com.example.siestaluna.vistasAdmin.PerfilAdminFragment;

public class SessionManager {
    private SharedPreferences sharedPreferences;

    //Constructores
    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("my_app_prefs", context.MODE_PRIVATE);
    }
    public SessionManager(PerfilAdminFragment fragment) {
        Context context = fragment.getContext();
        sharedPreferences = context.getSharedPreferences("my_app_prefs", context.MODE_PRIVATE);
    }
    public SessionManager(PerfilUsuarioFragment fragment) {
        Context context = fragment.getContext();
        sharedPreferences = context.getSharedPreferences("my_app_prefs", context.MODE_PRIVATE);
    }

    //Guarda los datos de la sesión en el shared preferences
    public void saveSession(String correoUsuario, String contrasena, int rol){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("correoUsuario", correoUsuario);
        editor.putString("contrasena", contrasena);
        editor.putString("rol", String.valueOf(rol));
        editor.apply();
    }

    //Elimina los datos del sharedPreferences
    public void clearSession(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("correoUsuario");
        editor.remove("contrasena");
        editor.remove("rol");
        editor.apply();
    }

    //verifica si hay datos guardados en el sharedPreferences
    public boolean isSessionSaved(){
        return sharedPreferences.contains("correoUsuario") && sharedPreferences.contains("contrasena") && sharedPreferences.contains("rol");
    }

    //Devuelve los datos del sharedPreferences
    public String[] getSessionData(){
        String[] sessionData = new String[3];
        sessionData[0] = sharedPreferences.getString("correoUsuario", "");
        sessionData[1] = sharedPreferences.getString("contrasena", "");
        sessionData[2] = sharedPreferences.getString("rol", String.valueOf(0));
        return sessionData;
    }
}
