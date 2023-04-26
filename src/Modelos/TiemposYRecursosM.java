/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TiemposYRecursosM {
   private final Integer id_tiemposRecursos;

   private String componente;
   private Integer numOperaciones;
   private Integer inst;
   private Float pzsMin;
   private Integer desm;
   private Integer inst_desm;
   
   public TiemposYRecursosM() {
      this.id_tiemposRecursos = null;
      this.componente = null;
      this.numOperaciones = null;
      this.inst = null;
      this.pzsMin = null;
      this.desm = null;
      this.inst_desm = null;  
   }
   
  public  TiemposYRecursosM( Integer id_tiemposRecursos, String componente, Integer numOperaciones, Integer inst, float pzsMin, Integer desm, Integer inst_desm) {
      this.id_tiemposRecursos = id_tiemposRecursos;
      this.componente = componente;
      this.numOperaciones = numOperaciones;
      this.inst = inst;
      this.pzsMin = pzsMin;
      this.desm = desm;
      this.inst_desm = inst_desm;
      
   }
   public Integer getId_tiemposRecursos() {
      return id_tiemposRecursos;
   }
   public String getComponente() {
      return componente;
   }
   public Integer getNumOperaciones() {
      return numOperaciones;
   }
    public Integer getInst() {
      return inst;
   }
   public float getPzsMin() {
      return pzsMin;
   }
   public Integer getDesm() {
      return desm;
   }
    public Integer getInst_desm() {
      return inst_desm;
   }
   
   // aqui inician los set
  public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setNumOperaciones(Integer numOperaciones) {
      this.numOperaciones = numOperaciones;
   }
   public void setInst(Integer inst) {
      this.inst = inst;
   }
   public void setPzsMin(float pzsMin) {
      this.pzsMin = pzsMin;
   }
   public void setDesm(Integer desm) {
      this.desm = desm;
   }
    public void setInst_desm(Integer inst_desm) {
      this.inst_desm = inst_desm;
   }

   public String toString() {
      return "TiemposYRecursos{" +"componente=" + componente + ", numOperaciones=" + numOperaciones +  ", inst=" + inst +", pzsMin=" + pzsMin +", desm=" + desm +", inst_desm=" + inst_desm + '}';
       
   }
   
}

