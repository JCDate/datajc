/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class ProveedoresM {
    private Integer id_prov;
    private String nombre;
    private String colonia;
    private String parque;
    private Integer codigoPostal;
    private String ciudad;
    private String estado;
    private String pais;
    private String calibre;

    public ProveedoresM() {
       this.id_prov = null;
       this.nombre = null;
       this.colonia = null;
       this.parque = null;
       this.codigoPostal = null;
       this.ciudad = null;
       this.estado = null;
       this.calibre = null;
       this.pais = null;
    }

    public ProveedoresM(int id_prov, String nombre, String colonia, String parque, int codigoPostal, String ciudad, String estado, String pais, String calibre) {
        this.id_prov = id_prov;
        this.nombre = nombre;
        this.colonia = colonia;
        this.parque = parque;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.calibre= calibre;
        
    }

    public Integer getId_prov() {
        return id_prov;
    }

    public void setId_prov(Integer id_prov) {
        this.id_prov = id_prov;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getParque() {
        return parque;
    }

    public void setParque(String parque) {
        this.parque = parque;
    }
    
    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    
    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }
    
    @Override
    public String toString() {
        return "ProveedoresM{" + "id_prov=" + id_prov + ", nombre=" + nombre + ", colonia=" + colonia + ", parque=" + parque + ", codigoPostal=" + codigoPostal + ", ciudad=" + ciudad + ", estado=" + estado + ", pais=" + pais + ", calibre=" + calibre + '}';
    }
  
}
