/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class NuevasOrdenesM {
   
   private boolean ordengenerada;
   private String orden;
   private String componente;
   private String fechaV;
   private Integer cantidad;
   private String loteeconomico;
 
   public  NuevasOrdenesM () {
      boolean ordengenerada1 = this.ordengenerada;
      this.orden = null;
      this.componente = null;
      this.fechaV = null;
      this.cantidad = null;
      this.loteeconomico = null;
   }
   
   public  NuevasOrdenesM ( Boolean ordengenerada, String orden, String componente, String fechaV, int cantidad, String loteeconomico) {
      this.ordengenerada = ordengenerada;
      this.orden = orden;
      this.componente = componente;
      this.fechaV = fechaV;
      this.cantidad = cantidad;
      this.loteeconomico = loteeconomico;
   }

    public boolean isOrdengenerada() {
        return ordengenerada;
    }

    public void setOrdengenerada(boolean ordengenerada) {
        this.ordengenerada = ordengenerada;
    }
   
   public String getOrden() {
      return orden;
   }
   public String getComponente() {
      return componente;
   }
   
   public void setOrden(String orden) {
      this.orden = orden;
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

    public String getLoteeconomico() {
        return loteeconomico;
    }

    public void setLoteeconomico(String loteeconomico) {
        this.loteeconomico = loteeconomico;
    }

    @Override
    public String toString() {
        return "NuevasOrdenesM{" + "ordengenerada=" + ordengenerada + ", orden=" + orden + ", componente=" + componente + ", fechaV=" + fechaV + ", cantidad=" + cantidad + ", loteeconomico=" + loteeconomico + '}';
    }
   
}