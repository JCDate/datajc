/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author JC
 */
public class PosiOrdenesCanM {
   private String orden;
   private String componente;

    public PosiOrdenesCanM(){
       this.orden = null;
       this.componente = null;

    }
    public PosiOrdenesCanM(String orden, String componente) {
        this.orden = orden;
        this.componente = componente;

    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

   

    @Override
    public String toString() {
        return "OrdenActM{" + "orden=" + orden + ", componente=" + componente + '}';
    }
}
