/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class DatosaTecnicosM {
   
   private String componente;
   private Integer dimHojaW;
   private Integer dimHojaL;
   private Float pesoH_kg;
   private Float dimRollo_p;
   private Float pesoRollo_kgs;
   private Float anchoTira_p;
 
   public  DatosaTecnicosM () {
      
      this.componente = null;
      this.dimHojaW = null;
      this.dimHojaL = null;
      this.pesoH_kg= null;
      this.dimRollo_p= null;
      this.pesoRollo_kgs= null;
      this.anchoTira_p= null;
     
   }
   public  DatosaTecnicosM ( String componente, Integer dimHojaW,Integer dimHojaL,float pesoH_kg,float dimRollo_p, float pesoRollo_kgs, float anchoTira_p ) {
      
      this.componente = componente;
      this.dimHojaW = dimHojaW ;
      this.dimHojaL = dimHojaL;
      this.pesoH_kg = pesoH_kg;
      this.dimRollo_p = dimRollo_p;
      this.pesoRollo_kgs = pesoRollo_kgs;
      this.anchoTira_p = anchoTira_p;
      
   }
   
   public String getComponente() {
      return componente;
   }
   public int getDimHojaW() {
      return dimHojaW;
   }
   public int getDimHojaL() {
      return dimHojaL;
   }
   public Float getPesoH_kg() {
      return pesoH_kg;
   }
   public Float getDimRollo_p() {
      return dimRollo_p;
   }
   public Float getPesoRollo_kgs() {
      return pesoRollo_kgs;
   }
    public Float getAnchoTira_p() {
      return anchoTira_p;
   }
   
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setDimHojaW(int dimHojaW) {
      this.dimHojaW = dimHojaW;
   }
   public void setDimHojaL(int dimHojaL) {
      this.dimHojaL = dimHojaL;
   }
   public void setPesoH_kg(float pesoH_kg) {
      this.pesoH_kg = pesoH_kg;
   }
   public void setDimRollo_p(float dimRollo_p) {
      this.dimRollo_p = dimRollo_p;
   }
     public void setPesoRollo_kgs(float pesoRollo_kgs) {
      this.pesoRollo_kgs = pesoRollo_kgs;
   }
      public void setAnchoTira_p(float anchoTira_p) {
      this.anchoTira_p = anchoTira_p;
   }
 
   @Override
   public String toString() {
      return "DatosaTecnicosM{" +  ", componente=" + componente + ", dimHojaW=" +dimHojaW +",dimHojaL="+dimHojaL+", pesoH_kg="+pesoH_kg+",dimRollo_p= "+dimRollo_p+",pesoRollo_kgs="+pesoRollo_kgs+",anchoTira_p="+anchoTira_p+'}';
   } 
}