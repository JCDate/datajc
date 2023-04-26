/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class UbicacionTroquelM {
   
   private String troquel;
   private String ubicacion;
 
   public  UbicacionTroquelM () {
      this.troquel = null;
      this.ubicacion = null; 
   }
   public  UbicacionTroquelM ( String troquel, String ubicacion) {
      this.troquel = troquel;
      this.ubicacion = ubicacion;
   }
   
   public String getTroquel() {
      return troquel;
   }
   public String getUbicacion() {
      return ubicacion;
   }
   
   public void setTroquel(String troquel) {
      this.troquel = troquel;
   }
   public void setUbicacion(String ubicacion) {
      this.ubicacion = ubicacion;
   }
 
   @Override
   public String toString() {
      return "UbicacionTroquelM{" +  ", troquel=" + troquel + ", ubicacion=" + ubicacion +  '}';
   } 
}