/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TiempoAtrasosM {
    private String orden;
    private Integer cantidad_reque;
    private String componente;
    private Integer numOperaciones;
    private String troquel;
    private String maquina;
    private String inst_desm;
    private String tiempo_troquelado;
    private String tiempo_total;
    private String fechaVencida;
    private String componente_TR;
    private String componente_T;
    private String componente_A;

    public TiempoAtrasosM() {
      this.orden = null;
      this.cantidad_reque = null;
      this.componente = null;
      this.numOperaciones = null;
      this.troquel = null;
      this.maquina = null;
      this.inst_desm = null;
      this.tiempo_troquelado = null;
      this.tiempo_total = null;
      this.fechaVencida = null;
      this.componente_TR = null;
      this.componente_TR = null;
      this.componente_A = null;
 
   }

    public TiempoAtrasosM(String orden, Integer cantidad_reque, String componente, Integer numOperaciones, String troquel, String maquina, String inst_desm, String tiempo_troquelado, String tiempo_total, String fechaVencida, String componente_TR, String componente_T, String componente_A) {
        this.orden = orden;
        this.cantidad_reque = cantidad_reque;
        this.componente = componente;
        this.numOperaciones = numOperaciones;
        this.troquel = troquel;
        this.maquina = maquina;
        this.inst_desm = inst_desm;
        this.tiempo_troquelado = tiempo_troquelado;
        this.tiempo_total = tiempo_total;
        this.fechaVencida = fechaVencida;
        this.componente_TR = componente_TR;
        this.componente_T = componente_T;
        this.componente_A = componente_A;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Integer getCantidad_reque() {
        return cantidad_reque;
    }

    public void setCantidad_reque(Integer cantidad_reque) {
        this.cantidad_reque = cantidad_reque;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Integer getNumOperaciones() {
        return numOperaciones;
    }

    public void setNumOperaciones(Integer numOperaciones) {
        this.numOperaciones = numOperaciones;
    }

    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        this.troquel = troquel;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getInst_desm() {
        return inst_desm;
    }

    public void setInst_desm(String inst_desm) {
        this.inst_desm = inst_desm;
    }

    public String getTiempo_troquelado() {
        return tiempo_troquelado;
    }

    public void setTiempo_troquelado(String tiempo_troquelado) {
        this.tiempo_troquelado = tiempo_troquelado;
    }

    public String getTiempo_total() {
        return tiempo_total;
    }

    public void setTiempo_total(String tiempo_total) {
        this.tiempo_total = tiempo_total;
    }

    public String getFechaVencida() {
        return fechaVencida;
    }

    public void setFechaVencida(String fechaVencida) {
        this.fechaVencida = fechaVencida;
    }

    public String getComponente_TR() {
        return componente_TR;
    }

    public void setComponente_TR(String componente_TR) {
        this.componente_TR = componente_TR;
    }

    public String getComponente_T() {
        return componente_T;
    }

    public void setComponente_T(String componente_T) {
        this.componente_T = componente_T;
    }

    public String getComponente_A() {
        return componente_A;
    }

    public void setComponente_A(String componente_A) {
        this.componente_A = componente_A;
    }

    @Override
    public String toString() {
        return "TiempoAtrasosM{" + "orden=" + orden + ", cantidad_reque=" + cantidad_reque + ", componente=" + componente + ", numOperaciones=" + numOperaciones + ", troquel=" + troquel + ", maquina=" + maquina + ", inst_desm=" + inst_desm + ", tiempo_troquelado=" + tiempo_troquelado + ", tiempo_total=" + tiempo_total + ", fechaVencida=" + fechaVencida + ", componente_TR=" + componente_TR + ", componente_T=" + componente_T + ", componente_A=" + componente_A + '}';
    }
   
}
