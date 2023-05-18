package com.example.firebase;

public class Usuario {
    public String nombre;
    public String apellido;
    public String email;
    public String password;
    public String identificacion;
    public String latitud;
    public String longitud;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String nombre, String apellido, String email, String password,
                   String identificacion, String latitud, String longitud) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.identificacion = identificacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }
    public String getCorreo(){
        return email;
    }

    public boolean isDisponible() {
        return true;
    }
}
