/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class MateriaPrimaRecM {
    private final Integer id;
    private Integer ordenCompra;
    private String calibre;
    private String proveedores;
    private Float kgSolicitados;
    private Float kgRecibidos;
    private String factura;
    private String fechaRecibida;

    public MateriaPrimaRecM() {
        this.id = null;
        this.ordenCompra = null;
        this.calibre = null;
        this.proveedores = null;
        this.kgSolicitados = null;
        this.kgRecibidos = null;
        this.factura = null;
        this.fechaRecibida = null;
    }

    public MateriaPrimaRecM( Integer id, Integer ordenCompra, String calibre, String proveedores, Float kgSolicitados, Float kgRecibidos, String factura, String fechaRecibida) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.calibre = calibre;
        this.proveedores = proveedores;
        this.kgSolicitados = kgSolicitados;
        this.kgRecibidos = kgRecibidos;
        this.factura = factura;
        this.fechaRecibida = fechaRecibida;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(Integer ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getProveedores() {
        return proveedores;
    }

    public void setProveedores(String proveedores) {
        this.proveedores = proveedores;
    }

    public Float getKgSolicitados() {
        return kgSolicitados;
    }

    public void setKgSolicitados(Float kgSolicitados) {
        this.kgSolicitados = kgSolicitados;
    }

    public Float getKgRecibidos() {
        return kgRecibidos;
    }

    public void setKgRecibidos(Float kgRecibidos) {
        this.kgRecibidos = kgRecibidos;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(String fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    @Override
    public String toString() {
        return "MateriaPrimaRecM{" + "id=" + id + ", ordenCompra=" + ordenCompra + ", calibre=" + calibre + ", proveedores=" + proveedores + ", kgSolicitados=" + kgSolicitados + ", kgRecibidos=" + kgRecibidos + ", factura=" + factura + ", fechaRecibida=" + fechaRecibida + '}';
    }
}
