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
public class CotizacionTMPDM {

    private String cotizacion;
    private String componente;
    private String cr;
    private String troquel;
    private String pzaDañada;

    public CotizacionTMPDM() {
        cotizacion = null;
        componente = null;
        cr = null;
        troquel = null;
        pzaDañada = null;
    }

    public CotizacionTMPDM(String cotizacion, String componente, String cr, String troquel, String pzaDañada) {
        this.cotizacion = cotizacion;
        this.componente = componente;
        this.cr = cr;
        this.troquel = troquel;
        this.pzaDañada = pzaDañada;
    }

    public String getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(String cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        this.troquel = troquel;
    }

    public String getPzaDañada() {
        return pzaDañada;
    }

    public void setPzaDañada(String pzaDañada) {
        this.pzaDañada = pzaDañada;
    }

    @Override
    public String toString() {
        return "CotizacionTMPD{" + "cotizacion=" + cotizacion + ", componente=" + componente + ", cr=" + cr + ", troquel=" + troquel + ", pzaDa\u00f1ada=" + pzaDañada + '}';
    }

}
