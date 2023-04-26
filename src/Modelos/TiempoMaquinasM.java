/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class TiempoMaquinasM {
    private String maquina;
    private String horas;
    
    public TiempoMaquinasM() {
        this.maquina = null;
        this.horas = null;
    }

    public TiempoMaquinasM(String maquina, String horas) {
        this.maquina = maquina;
        this.horas = horas;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "TiempoMaquinasM{" + "maquina=" + maquina + ", horas=" + horas + '}';
    }

}
