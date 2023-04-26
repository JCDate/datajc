/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class CalculosTeoricosM {
   
   private String componente;
   private Float pzas_tira;
   private Integer pzas_tiraEnteros;
   private Float tiras_hojas;
   private Integer tira_hojasEntero;
   private Integer pzas_HojasEntero;
   private Double peso_pzasCal;
   private Double peso_pzasLP;
 
   public  CalculosTeoricosM () {
      
      this.componente = null;
      this.pzas_tira = null;
      this.pzas_tiraEnteros = null;
      this.tiras_hojas= null;
      this.tira_hojasEntero= null;
      this.pzas_HojasEntero= null;
      this.peso_pzasCal= null;
      this.peso_pzasLP= null;
     
   }
   public  CalculosTeoricosM ( String componente, float pzas_tira,Integer pzas_tiraEnteros,float tiras_hojas,int tira_hojasEntero, int pzas_HojasEntero, double peso_pzasCal, double peso_pzasLP) {
      
      this.componente = componente;
      this.pzas_tira = pzas_tira;
      this.pzas_tiraEnteros = pzas_tiraEnteros;
      this.tiras_hojas = tiras_hojas;
      this.tira_hojasEntero = tira_hojasEntero;
      this.pzas_HojasEntero = pzas_HojasEntero;
      this.peso_pzasCal = peso_pzasCal;
      this.peso_pzasLP = peso_pzasLP;      
   }
   
   public String getComponente() {
      return componente;
   }
   public float getPzas_tira() {
      return pzas_tira;
   }
   public int getPzas_tiraEnteros() {
      return pzas_tiraEnteros;
   }
   public Float getTiras_hojas() {
      return tiras_hojas;
   }
   public int getTira_hojasEntero() {
      return tira_hojasEntero;
   }
   public int getPzas_HojasEntero() {
      return pzas_HojasEntero;
   }
   public double getPeso_pzasCal() {
      return peso_pzasCal;
   }
   public double getPeso_pzasLP() {
      return peso_pzasLP;
   }
   
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setPzas_tira(float pzas_tira) {
      this.pzas_tira = pzas_tira;
   }
   public void setPzasTiraEntero(int pzas_tiraEnteros) {
      this.pzas_tiraEnteros = pzas_tiraEnteros;
   }
   public void setTiras_hojas(float tiras_hojas) {
      this.tiras_hojas = tiras_hojas;
   }
   public void setTira_hojasEntero(int tira_hojasEntero) {
      this.tira_hojasEntero = tira_hojasEntero;
   }
   public void setPzas_HojasEntero(int pzas_HojasEntero) {
      this.pzas_HojasEntero = pzas_HojasEntero;
   }
    public void setPeso_pzasCal(double peso_pzasCal) {
      this.peso_pzasCal = peso_pzasCal;
   }
   public void setPeso_pzasLP(double peso_pzasLP) {
      this.peso_pzasLP = peso_pzasLP;
   }

   @Override
   public String toString() {
      return "CalculosTecnicosM{" +  ", componente=" + componente + ", pzas_tira=" +pzas_tira +",pzas_tiraEnteros="+pzas_tiraEnteros+", tiras_hojas="+tiras_hojas+",tira_hojasEntero= "+tira_hojasEntero+",pzas_HojasEntero="+pzas_HojasEntero+",peso_pzasCal="+peso_pzasCal+", peso_pzasLP="+peso_pzasLP +'}';
   } 
}