/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class EstructuraM {
   
   private String componente;
   private String um;
   private Integer OP;
   private String CR;
   private String calibre;
   private Float peso_estamp;
 
   public  EstructuraM () {
      
      this.componente = null;
      this.um = null;
      this.OP = null;
      this.CR = null;
      this.calibre = null;
      this.peso_estamp = null;
     
   }
   public  EstructuraM ( String componente, String um ,Integer OP,String CR,String calibre, Float peso_estamp) {
      this.componente = componente;
      this.um = um;
      this.OP = OP;
      this.CR = CR;
      this.calibre = calibre;
      this.peso_estamp = peso_estamp;
   }
   
   public String getComponente() {
      return componente;
   }

   public String getUm() {
      return um;
   }
   public Integer getOp() {
      return OP;
   }
   public String getCR() {
      return CR;
   }
   public String getCalibre() {
      return calibre;
   }
   public Float getPeso_estamp() {
      return peso_estamp;
   }
   
   public void setComponente(String componente) {
      this.componente = componente;
   }

   public void setUm(String um) {
      this.um = um;
   }
   public void setOp(Integer OP) {
      this.OP = OP;
   }
    public void setCR(String CR) {
      this.CR = CR;
   }
   public void setCalibre(String calibre) {
      this.calibre = calibre;
   }
   public void setPeso_estamp(float peso_estamp) {
      this.peso_estamp = peso_estamp;
   }
 
   @Override
   public String toString() {
      return "EstructuraM{" +  ", componente=" + componente + ", um="+um+", OP= "+ OP+", CR="+CR +",calibre="+calibre+", peso_estamp="+peso_estamp+'}';
   } 
}