/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class CRsM {
   
   private String componente;
   private String cr;

 
   public  CRsM () { 
      this.componente = null;
      this.cr = null;   
   }
   
   public  CRsM ( String componente, String cr) {
      this.componente = componente;
      this.cr = cr;   

   }
   
   public String getComponente() {
      return componente;
   }
   public String getCr() {
      return cr;
   }  
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setCr(String cr) {
      this.cr = cr;
   }


    @Override
    public String toString() {
        return "CRsM{" + "componente=" + componente + ", cr=" + cr +  '}';
    }
 
   
}