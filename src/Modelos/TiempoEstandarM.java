/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TiempoEstandarM {
   private String fechaVencida;
   private String orden;
   private String componente;
   private String inst_desm;
   private String tiempo_troquelado;
   private String tiempo_total;

   public TiempoEstandarM() {
      this.orden = null;
      this.componente = null;
      this.inst_desm = null;
      this.tiempo_troquelado = null;
      this.tiempo_total = null; 
   }
   
  public  TiempoEstandarM( String fechaVencida,String orden, String componente, String inst_desm, String tiempo_troquelado, String tiempo_total) {
      this.fechaVencida = fechaVencida;
      this.orden = orden;
      this.componente =componente;
      this.inst_desm = inst_desm;
      this.tiempo_troquelado = tiempo_troquelado;
      this.tiempo_total = tiempo_total;
 
   }
  public String getFechaVencida() {
      return fechaVencida;
   }
   public String getOrden() {
      return orden;
   }
    public String getComponente() {
      return componente;
   }
   public String getInst_desm() {
      return inst_desm;
   }
   public String getTiempo_troquelado() {
      return tiempo_troquelado;
   }
    public String getTiempo_total() {
      return tiempo_total;
   }
   
   // aqui inician los set
  public void setFecahVencida(String fechaVencida) {
      this.fechaVencida = fechaVencida;
   }
  public void setOrden(String orden) {
      this.orden = orden;
   }
  public void setComponente(String componente) {
      this.componente = componente;
   }
  public void setInst_desm(String inst_desm) {
      this.inst_desm = inst_desm;
   }
   public void setTiempo_troquelado(String tiempo_troquelado) {
      this.tiempo_troquelado = tiempo_troquelado;
   }
   public void setTiempo_total(String tiempo_total) {
      this.tiempo_total = tiempo_total;
   }

   public String toString() {
      return "TiempoEstandar{"+"fechaVencida="+fechaVencida +",orden="+orden+",componente="+componente+",inst_desm=" + inst_desm + ", tiempo_troquelado=" + tiempo_troquelado +  ", tiempo_total=" + tiempo_total + '}';   
   }
}

