
package Modelos;

public class EmbarqueM {
    private String orden;
    private String componente;
    private String cr;
    private Integer cantidad;
    private String noCuñetes;
    private String noSim; 
    private Float pesoNeto;
    private String factura;
    private boolean facturacion;

    public EmbarqueM() {
        this.orden = null;
        this.componente = null;
        this.cr = null;
        this.cantidad = null;
        this.noCuñetes = null;
        this.noSim = null;  
        this.pesoNeto = null;
        this.factura = null;
        boolean facturacion1 = this.facturacion;
    }

    public EmbarqueM(String orden, String componente,String cr, Integer cantidad, String noCuñetes, String noSim,float pesoNeto, String factura, Boolean facturacion) {
        this.orden = orden;
        this.componente = componente;
        this.cr = cr;
        this.cantidad = cantidad;
        this.noCuñetes = noCuñetes;
        this.noSim = noSim;
        this.pesoNeto = pesoNeto;
        this.factura = factura;
        this.facturacion = facturacion;
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

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNoCuñetes() {
        return noCuñetes;
    }

    public void setNoCuñetes(String noCuñetes) {
        this.noCuñetes = noCuñetes;
    }

    public String getNoSim() {
        return noSim;
    }

    public void setNoSim(String noSim) {
        this.noSim = noSim;
    }

    public Float getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(Float pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public boolean isFacturacion() {
        return facturacion;
    }

    public void setFacturacion(boolean facturacion) {
        this.facturacion = facturacion;
    }


}
