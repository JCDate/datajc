/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class AnalisisAtrasosM {
 
   private boolean prioridad;
   private String fechaRecibida;
   private String fechaVencida;
   private String orden;
   private String cantidad_reque;
   private Integer cantidadEntregada;
   private Integer piezasEntregar;
   private String consignatario;
   private String item_cliente;
   private String comentario;
   private String fechaEmbarque;
   private String factura;
   private String embarques;
   
   public AnalisisAtrasosM() {
      boolean prioridad = this.prioridad;
      this.fechaRecibida = null;
      this.fechaVencida = null;
      this.orden = null;
      this.cantidad_reque = null;
      this.cantidadEntregada = null;
      this.piezasEntregar = null;
      this.consignatario = null;
      this.item_cliente = null;
      this.comentario = null;
      this.fechaEmbarque = null;
      this.factura = null;
      this.embarques = null;
      
   }
  public  AnalisisAtrasosM(boolean prioridad, String fechaRecibida, String fechaVencida, String orden, String cantidad_reque,Integer cantidadEntregada,Integer piezasEntregar,  String consignatario,String item_cliente, String comentario, String fechaEmbarque, String factura, String embarques) {
      this.prioridad = prioridad;
      this.fechaRecibida = fechaRecibida;
      this.fechaVencida = fechaVencida;
      this.orden = orden;
      this.cantidad_reque = cantidad_reque;
      this.cantidadEntregada = cantidadEntregada;
      this.piezasEntregar = piezasEntregar;
      this.consignatario = consignatario;
      this.item_cliente = item_cliente;
      this.comentario = comentario;
      this.fechaEmbarque = fechaEmbarque;
      this.factura = factura;
      this.embarques = embarques;
   }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }

    public String getEmbarques() {
        return embarques;
    }

    public void setEmbarques(String embarques) {
        this.embarques = embarques;
    }
  
   public String getFechaRecibida() {
      return fechaRecibida;
   }
   public String getFechaVencida() {
      return fechaVencida;
   }
   public String getOrden() {
      return orden;
   }
   public String getCantidad_reque() {
      return cantidad_reque;
   }
   public Integer getCantidadEntregada() {
      return cantidadEntregada;
   }
   public Integer getPiezasEntregar() {
      return piezasEntregar;
   }
   public String getConsignatario() {
      return consignatario;
   }
   public String getItem_cliente() {
      return item_cliente;
   }
   public String getComentario() {
      return comentario;
   }

    public String getFechaEmbarque() {
        return fechaEmbarque;
    }

    public void setFechaEmbarque(String fechaEmbarque) {
        this.fechaEmbarque = fechaEmbarque;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
   
   
   // aqui inician los set
  public void setFechaRecibida(String fechaRecibida) {
      this.fechaRecibida = fechaRecibida;
   }
   public void setFechaVencida(String fechaVencida) {
      this.fechaVencida = fechaVencida;
   }
   public void setOrden(String orden) {
      this.orden = orden;
   }
   public void setCantidad_reque(String cantidad_reque) {
      this.cantidad_reque = cantidad_reque;
   }
    public void setCantidadEntregada(Integer cantidadEntregada) {
      this.cantidadEntregada = cantidadEntregada;
   }
   public void setPiezasEntregar(Integer piezasEntregar) {
      this.piezasEntregar = piezasEntregar;
   }
   public void setConsignatario(String consignatario) {
      this.consignatario = consignatario;
   }
   public void setItem_cliente(String item_cliente) {
      this.item_cliente = item_cliente;
   }
   public void setComentario(String comentario) {
      this.comentario = comentario;
   }

    @Override
    public String toString() {
        return "AnalisisAtrasosM{" + "prioridad=" + prioridad + ", fechaRecibida=" + fechaRecibida + ", fechaVencida=" + fechaVencida + ", orden=" + orden + ", cantidad_reque=" + cantidad_reque + ", cantidadEntregada=" + cantidadEntregada + ", piezasEntregar=" + piezasEntregar + ", consignatario=" + consignatario + ", item_cliente=" + item_cliente + ", comentario=" + comentario + ", fechaEmbarque=" + fechaEmbarque + ", factura=" + factura + ", embarques=" + embarques + '}';
    }

}

