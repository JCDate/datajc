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
public class OrdenCompra {
    String calibre;
    String descripcion;
    Float pu;
    String Moneda;
    String numCalibre;
    Float kgSolicitar;

    public OrdenCompra(String calibre, String descripcion, Float pu, String Moneda, String numCalibre, Float kgSolicitar) {
        this.calibre = calibre;
        this.descripcion = descripcion;
        this.pu = pu;
        this.Moneda = Moneda;
        this.numCalibre = numCalibre;
        this.kgSolicitar = kgSolicitar;
    }

    
    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPu() {
        return pu;
    }

    public void setPu(Float pu) {
        this.pu = pu;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public String getNumCalibre() {
        return numCalibre;
    }

    public void setNumCalibre(String numCalibre) {
        this.numCalibre = numCalibre;
    }

    public Float getKgSolicitar() {
        return kgSolicitar;
    }

    public void setKgSolicitar(Float kgSolicitar) {
        this.kgSolicitar = kgSolicitar;
    }
    
    
    
    
    
}
