package com.ajsb.tdlist.modelo;

/**
 * Programación Multimedia y de Dispositivos Móviles
 * Antonio J.Sánchez
 * ToDoList
 */

import android.renderscript.Sampler;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Usuario
{
    private String nombre ;
    private String apellidos ;
    private String ciudad ;

    private List<Integer> tareas ;

    public Usuario() { }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Integer> getTareas() {
        return tareas;
    }

    public void setTareas(List<Integer> tareas) {
        this.tareas = tareas;
    }

    /**
     * @param fbdb
     * @param uid
     */
    /*public static void loadInfoUsuario(FirebaseDatabase fbdb, String uid)
    {
        fbdb.getReference("usuarios/" + uid)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }*/
}
