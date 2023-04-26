/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class PrecioMP {
    private String calibre;
    private Float pesoUnitario;
    private String moneda;

    public PrecioMP() {
        this.calibre = null;
        this.pesoUnitario = null;
        this.moneda = null;
    }
    public PrecioMP(String calibre, Float pesoUnitario, String moneda) {
        this.calibre = calibre;
        this.pesoUnitario = pesoUnitario;
        this.moneda = moneda;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public Float getPesoUnitario() {
        return pesoUnitario;
    }

    public void setPesoUnitario(Float pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return "PrecioMP{" + "calibre=" + calibre + ", pesoUnitario=" + pesoUnitario + ", moneda=" + moneda + '}';
    }  
}
