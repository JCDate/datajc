/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class OrdenCompraM {
   private String fechaV;
   private String orden;
   private String componente;
   private String CR;
   private Integer cantidad;
   private String calibre;
   private Float consumo_kg;
   private Integer no_oper;
   private Integer no_golpes;
 
   public  OrdenCompraM () {
      this.fechaV = null;
      this.orden = null;
      this.componente = null;
      this.CR = null;
      this.cantidad = null;
      this.calibre = null;
      this.consumo_kg = null;
      this.no_oper = null;
      this.no_golpes = null;
   }
   
   public  OrdenCompraM ( String fechaV, String orden, String componente, String CR, Integer cantidad, String calibre ,float consumo_kg,Integer no_oper,Integer no_golpes) {
      this.fechaV = fechaV;
      this.orden = orden;
      this.componente = componente;
      this.CR = CR;
      this.cantidad = cantidad;
      this.calibre = calibre;
      this.consumo_kg = consumo_kg;
      this.no_oper = no_oper;
      this.no_golpes = no_golpes;
      
   }

    public String getFechaV() {
        return fechaV;
    }

    public void setFechaV(String fechaV) {
        this.fechaV = fechaV;
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

    public String getCR() {
        return CR;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public Float getConsumo_kg() {
        return consumo_kg;
    }

    public void setConsumo_kg(Float consumo_kg) {
        this.consumo_kg = consumo_kg;
    }

    public Integer getNo_oper() {
        return no_oper;
    }

    public void setNo_oper(Integer no_oper) {
        this.no_oper = no_oper;
    }

    public Integer getNo_golpes() {
        return no_golpes;
    }

    public void setNo_golpes(Integer no_golpes) {
        this.no_golpes = no_golpes;
    }

    @Override
    public String toString() {
        return "OrdenCompraM{" + "fechaV=" + fechaV + ", orden=" + orden + ", componente=" + componente + ", CR=" + CR + ", cantidad=" + cantidad + ", calibre=" + calibre + ", consumo_kg=" + consumo_kg + ", no_oper=" + no_oper + ", no_golpes=" + no_golpes + '}';
    }
}