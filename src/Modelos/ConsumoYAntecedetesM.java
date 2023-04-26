/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class ConsumoYAntecedetesM {
   
   private String componente_CA;
   private Float consumo_uni;
   private String desMatePrima;
   private Float anchoTira;
   private Float pesoPzs;
   private String PpapStatus;
   private String comentario_CA;
   private String tipo;
 
   public  ConsumoYAntecedetesM () {
      
      this.componente_CA = null;
      this.consumo_uni = null;
      this.desMatePrima = null;
      this.anchoTira = null;
      this.pesoPzs = null;
      this.PpapStatus = null;
      this.comentario_CA = null;
      this.tipo = null;
     
   }
   public  ConsumoYAntecedetesM ( String componente_CA, float consumo_uni, String desMatePrima, float anchoTira, float pesoPzs, String PpapStatus,String comentario_CA, String tipo) {
      
      this.componente_CA = componente_CA;
      this.consumo_uni = consumo_uni;
      this.desMatePrima = desMatePrima;
      this.anchoTira = anchoTira;
      this.pesoPzs = pesoPzs;
      this.PpapStatus = PpapStatus;
      this.comentario_CA = comentario_CA;
      this.tipo = tipo;
      
   }

    public String getComponente_CA() {
        return componente_CA;
    }

    public void setComponente_CA(String componente_CA) {
        this.componente_CA = componente_CA;
    }

    public Float getConsumo_uni() {
        return consumo_uni;
    }

    public void setConsumo_uni(Float consumo_uni) {
        this.consumo_uni = consumo_uni;
    }

    public String getDesMatePrima() {
        return desMatePrima;
    }

    public void setDesMatePrima(String desMatePrima) {
        this.desMatePrima = desMatePrima;
    }

    public Float getAnchoTira() {
        return anchoTira;
    }

    public void setAnchoTira(Float anchoTira) {
        this.anchoTira = anchoTira;
    }

    public Float getPesoPzs() {
        return pesoPzs;
    }

    public void setPesoPzs(Float pesoPzs) {
        this.pesoPzs = pesoPzs;
    }

    public String getPpapStatus() {
        return PpapStatus;
    }

    public void setPpapStatus(String PpapStatus) {
        this.PpapStatus = PpapStatus;
    }

    public String getComentario_CA() {
        return comentario_CA;
    }

    public void setComentario_CA(String comentario_CA) {
        this.comentario_CA = comentario_CA;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ConsumoYAntecedetesM{" + "componente_CA=" + componente_CA + ", consumo_uni=" + consumo_uni + ", desMatePrima=" + desMatePrima + ", anchoTira=" + anchoTira + ", pesoPzs=" + pesoPzs + ", PpapStatus=" + PpapStatus + ", comentario_CA=" + comentario_CA + ", tipo=" + tipo + '}';
    }
   
}