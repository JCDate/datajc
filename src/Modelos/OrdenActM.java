/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author JC
 */
public class OrdenActM {
   private String orden;
   private String componente;
   private String fechaV;
   private Integer cantidad;
   private String actualizo;

    public OrdenActM(){
       this.orden = null;
       this.componente = null;
       this.fechaV = null;
       this.cantidad = null;
       this.actualizo = null;
    }
    public OrdenActM(String orden, String componente, String fechaV, Integer cantidad, String actualizo) {
        this.orden = orden;
        this.componente = componente;
        this.fechaV = fechaV;
        this.cantidad = cantidad;
        this.actualizo = actualizo;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getFechaV() {
        return fechaV;
    }

    public void setFechaV(String fechaV) {
        this.fechaV = fechaV;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getActualizo() {
        return actualizo;
    }

    public void setActualizo(String actualizo) {
        this.actualizo = actualizo;
    }

    @Override
    public String toString() {
        return "OrdenActM{" + "orden=" + orden + ", componente=" + componente + ", fechaV=" + fechaV + ", cantidad=" + cantidad + ", actualizo=" + actualizo + '}';
    }
    
}
