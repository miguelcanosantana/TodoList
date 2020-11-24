package com.ajsb.tdlist.modelo;

public class Tarea
{
    private String tarea ;
    private Integer lista ;
    private Boolean completa ;

    public Tarea() { }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Integer getLista() {
        return lista;
    }

    public void setLista(Integer lista) {
        this.lista = lista;
    }

    public Boolean getCompleta() {
        return completa;
    }

    public void setCompleta(Boolean completa) {
        this.completa = completa;
    }
}
