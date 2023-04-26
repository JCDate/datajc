/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.Date;

/**
 *
 * @author JC
 */
public class SolicitudTMPDM {
    
    private String cotizacion;
    private String componente;
    private String fechaSol;
    private String envioCot;
    private String precioC;
    private String enviadoSKF;
    private String aprobadoSKF;
    private String solicitudM;
    private String costoM;
    private String fechaMaq;
    private String temple;
    private String ajuste;
    private String produccion;
    private String pzFacturadaSKF;

    public SolicitudTMPDM() {
        cotizacion = null;
        componente = null;
        fechaSol = null;
        envioCot = null;
        precioC = null;
        enviadoSKF = null;
        aprobadoSKF = null;
        solicitudM = null;
        costoM = null;
        fechaMaq = null;
        temple = null;
        ajuste = null;
        produccion = null;
        pzFacturadaSKF = null;
    }

    public SolicitudTMPDM(String cotizacion, String componente, String fechaSol, String envioCot, String precioC, String enviadoSKF, String aprobadoSKF, String solicitudM, String costoM, String fechaMaq, String temple, String ajuste, String produccion, String pzFacturadaSKF) {
        this.cotizacion = cotizacion;
        this.componente = componente;
        this.fechaSol = fechaSol;
        this.envioCot = envioCot;
        this.precioC = precioC;
        this.enviadoSKF = enviadoSKF;
        this.aprobadoSKF = aprobadoSKF;
        this.solicitudM = solicitudM;
        this.costoM = costoM;
        this.fechaMaq = fechaMaq;
        this.temple = temple;
        this.ajuste = ajuste;
        this.produccion = produccion;
        this.pzFacturadaSKF = pzFacturadaSKF;
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

    public String getFechaSol() {
        return fechaSol;
    }

    public void setFechaSol(String fechaSol) {
        this.fechaSol = fechaSol;
    }

    public String getEnvioCot() {
        return envioCot;
    }

    public void setEnvioCot(String envioCot) {
        this.envioCot = envioCot;
    }

    public String getPrecioC() {
        return precioC;
    }

    public void setPrecioC(String precioC) {
        this.precioC = precioC;
    }

    public String getEnviadoSKF() {
        return enviadoSKF;
    }

    public void setEnviadoSKF(String enviadoSKF) {
        this.enviadoSKF = enviadoSKF;
    }

    public String getAprobadoSKF() {
        return aprobadoSKF;
    }

    public void setAprobadoSKF(String aprobadoSKF) {
        this.aprobadoSKF = aprobadoSKF;
    }

    public String getSolicitudM() {
        return solicitudM;
    }

    public void setSolicitudM(String solicitudM) {
        this.solicitudM = solicitudM;
    }

    public String getCostoM() {
        return costoM;
    }

    public void setCostoM(String costoM) {
        this.costoM = costoM;
    }

    public String getFechaMaq() {
        return fechaMaq;
    }

    public void setFechaMaq(String fechaMaq) {
        this.fechaMaq = fechaMaq;
    }

    public String getTemple() {
        return temple;
    }

    public void setTemple(String temple) {
        this.temple = temple;
    }

    public String getAjuste() {
        return ajuste;
    }

    public void setAjuste(String ajuste) {
        this.ajuste = ajuste;
    }

    public String getProduccion() {
        return produccion;
    }

    public void setProduccion(String produccion) {
        this.produccion = produccion;
    }

    public String getPzFacturadaSKF() {
        return pzFacturadaSKF;
    }

    public void setPzFacturadaSKF(String pzFacturadaSKF) {
        this.pzFacturadaSKF = pzFacturadaSKF;
    }



    @Override
    public String toString() {
        return "SolicitudTMPDM{" + "cotizacion=" + cotizacion + ", componente=" + componente + ", fechaSol=" + fechaSol + ", envioCot=" + envioCot + ", precioC=" + precioC + ", enviadoSKF=" + enviadoSKF + ", aprobadoSKF=" + aprobadoSKF + ", solicitudM=" + solicitudM + ", costoM=" + costoM + ", fechaMaq=" + fechaMaq + ", temple=" + temple + ", ajuste=" + ajuste + ", produccion=" + produccion + ", pzFacturadaSKF=" + pzFacturadaSKF + '}';
    }
    
}
