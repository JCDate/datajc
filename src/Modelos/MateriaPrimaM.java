/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class MateriaPrimaM {
   
   private String calibre;
   private Float consumoR;
   private Float inventarioF;
   private Float inventarioA;
   private Integer solicitudTransito;
   private Float kgSolicitar;
   private String aprobacion;
   private String calibre_orden;
   private String calibre_proveedor;
 
   public  MateriaPrimaM () {
      this.calibre = null;
      this.consumoR = null;
      this.inventarioF = null;
      this.inventarioA = null;
      this.solicitudTransito = null;
      this.kgSolicitar = null;
      this.aprobacion = null;
      this.calibre_orden = null;
      this.calibre_proveedor = null;
   }
   public  MateriaPrimaM (String calibre, float consumoR, float inventarioF, float inventarioA,Integer solicitudTransito, float kgSolicitar, String aprobacion, String calibre_orden, String calibre_proveedor) {
      
      this.calibre = calibre;
      this.consumoR = consumoR;
      this.inventarioF = inventarioF;
      this.inventarioA = inventarioA;
      this.solicitudTransito = solicitudTransito;
      this.kgSolicitar = kgSolicitar;
      this.aprobacion = aprobacion;
      this.calibre_orden = calibre_orden;
      this.calibre_proveedor = calibre_proveedor;
   }

    public String getCalibre_proveedor() {
        return calibre_proveedor;
    }

    public void setCalibre_proveedor(String calibre_proveedor) {
        this.calibre_proveedor = calibre_proveedor;
    }

    public String getCalibre_orden() {
        return calibre_orden;
    }

    public void setCalibre_orden(String calibre_orden) {
        this.calibre_orden = calibre_orden;
    }
   
   public String getCalibre() {
      return calibre;
   }
   public Float getConsumoR() {
      return consumoR;
   }
   public Float getInventarioF() {
      return inventarioF;
   }
   public Float getInventarioA() {
      return inventarioA;
   }
   public Integer getSolicitudTransito() {
      return solicitudTransito;
   }
   public Float getKgSolicitar() {
      return kgSolicitar;
   }
   public String getAprobacion() {
      return aprobacion;
   }
   
   //SET
   public void setCalibre(String calibre) {
      this.calibre = calibre;
   }
   public void setConsumoR(float consumoR) {
      this.consumoR = consumoR;
   }
   public void setInventarioF(float inventarioF) {
      this.inventarioF = inventarioF;
   }
   public void setInventarioA(float inventarioA) {
      this.inventarioA = inventarioA;
   }
   public void setSolicitudTransito(Integer solicitudTransito) {
      this.solicitudTransito = solicitudTransito;
   }
   public void setKgSolicitar(float kgSolicitar) {
      this.kgSolicitar= kgSolicitar;
   }
   public void setAprobacion(String aprobacion) {
      this.aprobacion = aprobacion;
   }

    @Override
    public String toString() {
        return "MateriaPrimaM{" + "calibre=" + calibre + ", consumoR=" + consumoR + ", inventarioF=" + inventarioF + ", inventarioA=" + inventarioA + ", solicitudTransito=" + solicitudTransito + ", kgSolicitar=" + kgSolicitar + ", aprobacion=" + aprobacion + ", calibre_orden=" + calibre_orden + ", calibre_proveedor=" + calibre_proveedor + '}';
    }
   
}