/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class InstPPTroqueladoM {
   
   private String componente;
   private String cr;
   private String calibre;
   private String fecha;
   private Integer operacion;
   private String troquel;
   private String maquina;
   private String operador;
   private String libs;
   private String causaR;
 
   public  InstPPTroqueladoM () { 
      this.componente = null;
      this.cr = null;    
      this.calibre = null;
      this.fecha = null;
      this.operacion = null;
      this.troquel = null;
      this.maquina = null;
      this.operador = null;
      this.libs = null;
      this.causaR = null;
   }
   
   public  InstPPTroqueladoM ( String componente, String cr, String calibre, String fecha,int operacion,String troquel,String maquina,String operador,String libs, String causaR) {
      this.componente = componente;
      this.cr = cr;   
      this.calibre = calibre;
      this.fecha = fecha;
      this.operacion = operacion;
      this.troquel = troquel;
      this.maquina = maquina;
      this.operador = operador;
      this.libs = libs;
      this.causaR = causaR;
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

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getOperacion() {
        return operacion;
    }

    public void setOperacion(Integer operacion) {
        this.operacion = operacion;
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

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getLibs() {
        return libs;
    }

    public void setLibs(String libs) {
        this.libs = libs;
    }

    public String getCausaR() {
        return causaR;
    }

    public void setCausaR(String causaR) {
        this.causaR = causaR;
    }

    @Override
    public String toString() {
        return "InstPPTroqueladoM{" + "componente=" + componente + ", cr=" + cr + ", calibre=" + calibre + ", fecha=" + fecha + ", operacion=" + operacion + ", troquel=" + troquel + ", maquina=" + maquina + ", operador=" + operador + ", libs=" + libs + ", causaR=" + causaR + '}';
    }
}