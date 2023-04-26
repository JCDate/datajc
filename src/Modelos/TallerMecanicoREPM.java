/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TallerMecanicoREPM {
   private Integer id;
   private String troquel;
   private String componente;
   private String fechaEntregada;
   private String reparadaP;
   private Integer turno;
   private String solucion;
   private String fabricada;
   private String reparada;
   private String eficaz;

 
   
    public TallerMecanicoREPM() {
        this.id = null;
        this.troquel = null;
        this.componente = null;
        this.fechaEntregada = null;
        this.reparadaP = null;
        this.turno = null;
        this.solucion = null;
        this.fabricada = null;
        this.reparada = null;
        this.eficaz = null;
    }

    public TallerMecanicoREPM(int id, String troquel, String componente, String fechaEntregada, String reparadaP, int turno, String solucion, String fabricada, String reparada, String eficaz) {
        this.id = id;
        this.troquel = troquel;
        this.componente = componente;
        this.fechaEntregada = fechaEntregada;
        this.reparadaP = reparadaP;
        this.turno = turno;
        this.solucion = solucion;
        this.fabricada = fabricada;
        this.reparada = reparada;
        this.eficaz = eficaz;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTroquel() {
        return troquel;
    }

    public void setTroquel(String troquel) {
        this.troquel = troquel;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getFechaEntregada() {
        return fechaEntregada;
    }

    public void setFechaEntregada(String fechaEntregada) {
        this.fechaEntregada = fechaEntregada;
    }

    public String getReparadaP() {
        return reparadaP;
    }

    public void setReparadaP(String reparadaP) {
        this.reparadaP = reparadaP;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getFabricada() {
        return fabricada;
    }

    public void setFabricada(String fabricada) {
        this.fabricada = fabricada;
    }

    public String getReparada() {
        return reparada;
    }

    public void setReparada(String reparada) {
        this.reparada = reparada;
    }

    public String getEficaz() {
        return eficaz;
    }

    public void setEficaz(String eficaz) {
        this.eficaz = eficaz;
    }

    @Override
    public String toString() {
        return "TallerMecanicoREPM{" + "id=" + id + ", troquel=" + troquel + ", componente=" + componente + ", fechaEntregada=" + fechaEntregada + ", reparadaP=" + reparadaP + ", turno=" + turno + ", solucion=" + solucion + ", fabricada=" + fabricada + ", reparada=" + reparada + ", eficaz=" + eficaz + '}';
    }

  

}