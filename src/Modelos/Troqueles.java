/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class Troqueles {
   private final Integer id_troquel;

   private String componente;
   private Integer numOperaciones;
   private String caja;
   private String troquel;
   private String opciones;
   private String maquinaOp;
   
   public Troqueles() {
      this.id_troquel = null;
      this.componente = null;
      this.numOperaciones = null;
      this.caja = null;
      this.troquel = null;
      this.opciones = null;
      this.maquinaOp = null;
      
   }
  public  Troqueles( Integer id_troquel, String componente, Integer numOperaciones, String caja, String troquel, String opciones, String maquinaOp) {
      this.id_troquel = id_troquel;
      this.componente = componente;
      this.numOperaciones = numOperaciones;
      this.caja = caja;
      this.troquel = troquel;
      this.opciones = opciones;
      this.maquinaOp = maquinaOp;
      
   }
    public Integer getId_troquel() {
      return id_troquel;
   }
   public String getComponente() {
      return componente;
   }
   public Integer getNumOperaciones() {
      return numOperaciones;
   }
    public String getCaja() {
      return caja;
   }
   public String getTroquel() {
      return troquel;
   }

    public String getOpciones() {
      return opciones;
   }
   public String getMaquinaOp() {
      return maquinaOp;
   }
   
   // aqui inician los set
  public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setNumOperaciones(Integer numOperaciones) {
      this.numOperaciones = numOperaciones;
   }
   public void setCaja(String caja) {
      this.caja = caja;
   }
   public void setTroquel(String troquel) {
      this.troquel = troquel;
   }

    public void setOpciones(String opciones) {
      this.opciones = opciones;
   }
   public void setMaquinaOp(String maquinaOp) {
      this.maquinaOp = maquinaOp;
   }

   public String toString() {
      return "Troqueles{" +"componente=" + componente + ", numOperaciones=" + numOperaciones +  ", caja=" + caja +", troquel=" + troquel +", opciones=" + opciones +", maquinaOp=" + maquinaOp + '}'; 
   }  
}

