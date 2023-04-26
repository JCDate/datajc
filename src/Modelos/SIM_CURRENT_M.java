/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class SIM_CURRENT_M {
   
   private String fechaRecibida;
   private String consignatario;
   private String item_cliente;
   private String orden;
   private String fechaVencida;
   private Integer cantidad_reque;
 
   public  SIM_CURRENT_M () {
      
      this.fechaRecibida = null;
      this.consignatario = null;
      this.item_cliente = null;
      this.orden = null;
      this.fechaVencida = null;
      this.cantidad_reque = null;
     
   }
   public  SIM_CURRENT_M (  String fechaRecibida,String consignatario, String item_cliente,String orden, String fechaVencida, int cantidad_reque) {
      this.fechaRecibida = fechaRecibida;
      this.consignatario = consignatario;
      this.item_cliente = item_cliente;
      this.orden = orden;
      this.fechaVencida = fechaVencida;
      this.cantidad_reque = cantidad_reque; 
   }
   
   public String getFechaRecibida() {
      return fechaRecibida;
   }
   public String getConsignatario() {
      return consignatario;
   }
   public String getItem_cliente() {
      return item_cliente;
   }
   public String getOrden() {
      return orden;
   }
   public String getFechaVencida() {
      return fechaVencida;
   }
    public Integer getCantidad_reque() {
      return cantidad_reque;
   }
   
   public void setFechaRecibida(String fechaRecibida) {
      this.fechaRecibida = fechaRecibida;
   }
   public void setConsignatario(String consignatario) {
      this.consignatario = consignatario;
   }
   public void setItem_cliente(String item_cliente) {
      this.item_cliente = item_cliente;
   }
   public void setOrden(String orden) {
      this.orden = orden;
   }
   public void setFechaVencida(String fechaVencida) {
      this.fechaVencida = fechaVencida;
   }
   public void setCantidad_reque(int cantidad_reque) {
      this.cantidad_reque = cantidad_reque;
   }
 
   @Override
   public String toString() {
      return "SIM_CURRENT_M{" + ", fechaRecibida=" + fechaRecibida + ", consignatario=" +consignatario +", item_cliente="+item_cliente+", orden="+orden+", fechaVencida= "+fechaVencida+ ", cantidad_reque="+cantidad_reque+ '}';
   } 
}