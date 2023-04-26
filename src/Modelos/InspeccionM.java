/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class InspeccionM {
   
   private String componente;
   private Integer existencia;
   private Integer entrada;
   private String fechaEntrada;
   private Integer salida;
   

   public  InspeccionM () { 
      this.componente = null;
      this.existencia = null;   
      this.entrada = null;   
      this.fechaEntrada = null;   
      this.salida = null;   
   }

    public InspeccionM(String componente, Integer existencia, Integer entrada, String fechaEntrada, Integer salida) {
        this.componente = componente;
        this.existencia = existencia;
        this.entrada = entrada;
        this.fechaEntrada = fechaEntrada;
        this.salida = salida;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public Integer getEntrada() {
        return entrada;
    }

    public void setEntrada(Integer entrada) {
        this.entrada = entrada;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    @Override
    public String toString() {
        return "InspeccionM{" + "componente=" + componente + ", existencia=" + existencia + ", entrada=" + entrada + ", fechaEntrada=" + fechaEntrada + ", salida=" + salida + '}';
    }

}