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
public class OrdenesProdM {
    
    private String orden;
    private String componente;
    private String reparacion;
    private String troquel;

    public OrdenesProdM() {
        this.orden = null;
        this.componente = null;
        this.reparacion = null;
        this.troquel = null;
    }

    public OrdenesProdM(String orden, String componente, String reparacion, String troquel) {
        this.orden = orden;
        this.componente = componente;
        this.reparacion = reparacion;
        this.troquel = troquel;
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

    public String getReparacion() {
        return reparacion;
    }

    public void setReparacion(String reparacion) {
        this.reparacion = reparacion;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        this.troquel = troquel;
    }

    @Override
    public String toString() {
        return "OrdenesProdM{" + "orden=" + orden + ", componente=" + componente + ", reparacion=" + reparacion + ", troquel=" + troquel + '}';
    }

}
