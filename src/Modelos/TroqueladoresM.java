/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

public class TroqueladoresM {
    private Integer id_troquelador;
    private String nombre;
    private String puesto;

    public TroqueladoresM() {
        this.id_troquelador = null;
        this.nombre = null;
        this.puesto = null;
    }

    public TroqueladoresM(Integer id_troquelador, String nombre, String puesto) {
        this.id_troquelador = id_troquelador;
        this.nombre = nombre;
        this.puesto = puesto;
    }

    public Integer getId_troquelador() {
        return id_troquelador;
    }

    public void setId_troquelador(Integer id_troquelador) {
        this.id_troquelador = id_troquelador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "TroqueladoresM{" + "id_troquelador=" + id_troquelador + ", nombre=" + nombre + ", puesto=" + puesto + '}';
    }
}
