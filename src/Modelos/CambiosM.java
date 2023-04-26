/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class CambiosM {

   private String orden;   
   private String fechaV;
   private String componente;
   private String cr;
   private Integer cantidad;
   private String comentario;
   
   public CambiosM() {
      this.orden = null;
      this.fechaV = null;
      this.componente = null;
      this.cr = null;
      this.cantidad = null;
      this.comentario = null;
      
   }
  public  CambiosM( String orden, String fechaV, String componente, String cr,Integer cantidad,String comentario) {
      this.orden = orden;
      this.fechaV = fechaV;
      this.orden = orden;
      this.componente = componente;
      this.cr = cr;
      this.cantidad = cantidad;
      this.comentario = comentario;
   }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getFechaV() {
        return fechaV;
    }

    public void setFechaV(String fechaV) {
        this.fechaV = fechaV;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "CambiosM{" + "orden=" + orden + ", fechaV=" + fechaV + ", componente=" + componente + ", cr=" + cr + ", cantidad=" + cantidad + ", comentario=" + comentario + '}';
    } 
}

