/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class DimM {
    private String componente;
    private String cr;
    private String calibre;
    private String diaEx;
    private String diaInt;
    private String altura;

    public DimM() {
        this.componente = null;
        this.cr = null;
        this.calibre = null;
        this.diaEx = null;
        this.diaInt = null;
        this.altura = null;
        
    }

    public DimM(String componente, String cr, String calibre, String diaEx, String diaInt, String altura) {
        this.componente = componente;
        this.cr = cr;
        this.calibre = calibre;
        this.diaEx = diaEx;
        this.diaInt = diaInt;
        this.altura = altura;
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

    public String getDiaEx() {
        return diaEx;
    }

    public void setDiaEx(String diaEx) {
        this.diaEx = diaEx;
    }

    public String getDiaInt() {
        return diaInt;
    }

    public void setDiaInt(String diaInt) {
        this.diaInt = diaInt;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "DimM{" + "componente=" + componente + ", cr=" + cr + ", calibre=" + calibre + ", diaEx=" + diaEx + ", diaInt=" + diaInt + ", altura=" + altura + '}';
    }
  
}
