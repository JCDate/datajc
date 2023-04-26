/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

public class AntecedentesFamiliaM {
   
   private String componente;
   private String familia;
 
   public  AntecedentesFamiliaM () {
      
      this.componente = null;
      this.familia = null;
     
   }
   public  AntecedentesFamiliaM ( String componente, String familia) {
      
      this.componente = componente;
      this.familia = familia;
      
   }
   
   public String getComponente() {
      return componente;
   }
   public String getFamilia() {
      return familia;
   }
   
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setFamilia(String familia) {
      this.familia = familia;
   }
 
   @Override
   public String toString() {
      return "AntecedentesFamiliaM{" +  ", componente=" + componente + ", familia=" + familia +  '}';
   } 
}