/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class DatosListaPreciosM {
   
   private String componente;
   private String cr;
   private String calibre;
   private Double blank_kg;
 
   public  DatosListaPreciosM () {
      
      this.componente = null;
      this.cr = null;
      this.calibre = null;
      this.blank_kg= null;
     
   }
   public  DatosListaPreciosM ( String componente, String cr,String calibre, double blank_kg) {
      
      this.componente = componente;
      this.cr = cr;
      this.calibre = calibre;
      this.blank_kg = blank_kg;
      
   }
   
   public String getComponente() {
      return componente;
   }
   public String getCr() {
      return cr;
   }
   public String getCalibre() {
      return calibre;
   }
   public Double getBlank_kg() {
      return blank_kg;
   }
   
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setCr(String cr) {
      this.cr = cr;
   }
   public void setCalibre(String calibre) {
      this.calibre = calibre;
   }
   public void setBlank_kg(double blank_kg) {
      this.blank_kg = blank_kg;
   }
 
   @Override
   public String toString() {
      return "DatosListaPreciosM{" +  ", componente=" + componente + ", cr=" +cr +",calibre="+calibre+", blank_kg="+blank_kg+'}';
   } 
}