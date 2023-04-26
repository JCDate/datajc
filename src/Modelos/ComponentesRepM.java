/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class ComponentesRepM {
   
   private String orden;
   private String componente;
 
   public  ComponentesRepM () {
      this.orden = null;
      this.componente = null; 
   }
   
   public  ComponentesRepM ( String orden, String componente) {
      this.orden = orden;
      this.componente = componente; 
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
 
   @Override
   public String toString() {
      return "ComponentesRepM{" +  ", orden=" + orden + ", componente=" + componente +  '}';
   } 
}