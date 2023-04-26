/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class PrecioComponenteM {
    private String componente;
    private Float precioUnitario;


    public PrecioComponenteM() {
        this.componente = null;
        this.precioUnitario = null;
      
    }
    public PrecioComponenteM(String calibre, Float pesoUnitario) {
        this.componente = calibre;
        this.precioUnitario = pesoUnitario;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "PrecioComponente{" + "componente=" + componente + ", precioUnitario=" + precioUnitario + '}';
    }

   
}
