/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TallerMecanicoFALLAM {
   private Integer id;
   private String troquel;
   private String componente;
   private String fechaEntrada;
   private String descripcion;

    public TallerMecanicoFALLAM() {
        this.id = null;
        this.troquel = null;
        this.componente = null;
        this.fechaEntrada = null;
        this.descripcion = null;
    }

    public TallerMecanicoFALLAM(int id, String troquel, String componente, String fechaEntrada, String descripcion) {
        this.id = id;
        this.troquel = troquel;
        this.componente = componente;
        this.fechaEntrada = fechaEntrada;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        this.troquel = troquel;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TallerMecanicoFALLAM{" + "id=" + id + ", troquel=" + troquel + ", componente=" + componente + ", fechaEntrada=" + fechaEntrada + ", descripcion=" + descripcion + '}';
    }

}