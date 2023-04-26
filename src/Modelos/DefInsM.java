/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class DefInsM {
   
   private Integer id;
   private String componente;
   private String defecto;
   private Integer operacion;

 
   public  DefInsM () { 
      this.id = null;
      this.componente = null;
      this.defecto = null;   
      this.operacion = null;
   }
   
   public  DefInsM ( Integer id, String componente, String defecto, Integer operacion) {
      this.id = id;
      this.componente = componente;
      this.defecto = defecto;   
      this.operacion = operacion;

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

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getDefecto() {
        return defecto;
    }

    public void setDefecto(String defecto) {
        this.defecto = defecto;
    }

    public Integer getOperacion() {
        return operacion;
    }

    public void setOperacion(Integer operacion) {
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "DefInsM{" + "id=" + id + ", componente=" + componente + ", defecto=" + defecto + ", operacion=" + operacion + '}';
    }
   
}