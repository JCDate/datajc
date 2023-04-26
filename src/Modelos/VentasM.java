/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class VentasM {
   private Integer id;
   private String componente;
   private String cr;
   private String pzs;
   private String fechaEmb;

 
   public  VentasM () { 
      this.id = null;
      this.componente = null;
      this.cr = null; 
      this.pzs = null;
      this.fechaEmb = null;
   }
    public  VentasM (int id, String componente, String cr, String pzs, String fechaEmb) {
      this.id = id;
      this.componente = componente;
      this.cr = cr;   
      this.pzs = pzs;
      this.fechaEmb =  fechaEmb;
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
   public String getComponente() {
      return componente;
   }
   public String getCr() {
      return cr;
   }  
   public void setComponente(String componente) {
      this.componente = componente;
   }
   public void setCr(String cr) {
      this.cr = cr;
   }

    public String getPzs() {
        return pzs;
    }

    public void setPzs(String pzs) {
        this.pzs = pzs;
    }

    public String getFechaEmb() {
        return fechaEmb;
    }

    public void setFechaEm(String fechaEmb) {
        this.fechaEmb = fechaEmb;
    }

    @Override
    public String toString() {
        return "VentasM{" + "id=" + id + ", componente=" + componente + ", cr=" + cr + ", pzs=" + pzs + ", fechaEmb=" + fechaEmb +  '}';
    }
}