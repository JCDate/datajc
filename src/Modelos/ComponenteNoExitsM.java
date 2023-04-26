/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class ComponenteNoExitsM {
 
  
   private String orden;
   private String item_cliente;
   
   
   public ComponenteNoExitsM() {
      this.orden = null;
      this.item_cliente = null; 
   }
  public  ComponenteNoExitsM(String orden,String item_cliente) {
     
      this.orden = orden;
      this.item_cliente = item_cliente;
     
   }

   public String getOrden() {
      return orden;
   }
 
   public String getItem_cliente() {
      return item_cliente;
   }
  
   
   // aqui inician los set
  
   public void setOrden(String orden) {
      this.orden = orden;
   }
  
   public void setItem_cliente(String item_cliente) {
      this.item_cliente = item_cliente;
   }

    @Override
    public String toString() {
        return "ComponenteNoExitsM{" + "orden=" + orden + ", item_cliente=" + item_cliente + '}';
    }

   
   
}

