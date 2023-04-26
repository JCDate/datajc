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
public class PronosticoM {
    String supplier;
    String componente;
    String mes1;
    String mes2;
    String mes3;
    String mes4;
    String calibre;
    String consumoU;
    Float totalkg;
    Float pesoUnitario;
    String moneda;
    
    public PronosticoM(){
        this.supplier = null;
        this.componente = null;
        this.mes1 = null;
        this.mes2 = null;
        this.mes3 = null;
        this.mes4 = null;
        this.calibre = null;
        this.consumoU = null;
        this.totalkg = null;
        this.pesoUnitario = null;
        this.moneda = null; 
    }
    
    public PronosticoM(String supplier, String componente, String mes1, String mes2, String mes3, String mes4, String calibre, String consumoU,float totalkg, float pesoUnitario, String moneda){
        this.supplier = supplier;
        this.componente = componente;
        this.mes1 = mes1;
        this.mes2 = mes2;
        this.mes3 = mes3;
        this.mes4 = mes4;
        this.calibre = calibre;
        this.consumoU = consumoU;
        this.totalkg = totalkg;
        this.pesoUnitario = pesoUnitario;
        this.moneda = moneda;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getMes1() {
        return mes1;
    }

    public void setMes1(String mes1) {
        this.mes1 = mes1;
    }

    public String getMes2() {
        return mes2;
    }

    public void setMes2(String mes2) {
        this.mes2 = mes2;
    }

    public String getMes3() {
        return mes3;
    }

    public void setMes3(String mes3) {
        this.mes3 = mes3;
    }

    public String getMes4() {
        return mes4;
    }

    public void setMes4(String mes4) {
        this.mes4 = mes4;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getConsumoU() {
        return consumoU;
    }

    public void setConsumoU(String consumoU) {
        this.consumoU = consumoU;
    }

    public Float getTotalkg() {
        return totalkg;
    }

    public void setTotalkg(Float totalkg) {
        this.totalkg = totalkg;
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
        return "PronosticoM{" + "supplier=" + supplier + ", componente=" + componente + ", mes1=" + mes1 + ", mes2=" + mes2 + ", mes3=" + mes3 + ", mes4=" + mes4 + ", calibre=" + calibre + ", consumoU=" + consumoU + ", totalkg=" + totalkg + ", pesoUnitario=" + pesoUnitario + ", moneda=" + moneda + '}';
    }
}
