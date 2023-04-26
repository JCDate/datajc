/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class RepDiarioProdM {
   private Integer id;
   private String orden;
   private String componente;
   private String cr;
   private Integer contS;
   private String fecha;
   private String operador;
   private String noMaquina;
   private String actividad;
   private String operacion;
   private Integer tOp;
   private String inicio;
   private String fin;
   private Integer cantProd;
   private Integer cantParcial;
   private String comentario;
   
   public RepDiarioProdM() {
      this.id = null;
      this.orden = null;
      this.componente = null;
      this.cr = null;
      this.contS = null;
      this.fecha = null;
      this.operador = null;
      this.noMaquina = null;
      this.actividad = null;
      this.operacion = null;
      this.tOp = null;
      this.inicio = null;
      this.fin = null;
      this.cantProd = null;
      this.cantParcial = null;
      this.comentario = null;
   }
  public  RepDiarioProdM(int id, String orden, String componente, String cr, int contS,String fecha,String operador,  String noMaquina,String actividad, String operacion,int tOp, String inicio, String fin, int cantProd, int cantParcial, String comentario) {
      this.id = id;
      this.orden = orden;
      this.componente = componente;
      this.cr = cr;
      this.contS = contS;
      this.fecha = fecha;
      this.operador = operador;
      this.noMaquina = noMaquina;
      this.actividad = actividad;
      this.operacion = operacion;
      this.tOp = tOp;
      this.inicio = inicio;
      this.fin = fin;
      this.cantProd = cantProd;
      this.cantParcial = cantParcial;
      this.comentario = comentario;
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
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

    public Integer getContS() {
        return contS;
    }

    public void setContS(Integer contS) {
        this.contS = contS;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getNoMaquina() {
        return noMaquina;
    }

    public void setNoMaquina(String noMaquina) {
        this.noMaquina = noMaquina;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Integer getTOp() {
        return tOp;
    }

    public void setTOp(Integer tOp) {
        this.tOp = tOp;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public Integer getCantProd() {
        return cantProd;
    }

    public void setCantProd(Integer cantProd) {
        this.cantProd = cantProd;
    }

    public Integer getCantParcial() {
        return cantParcial;
    }

    public void setCantParcial(Integer cantParcial) {
        this.cantParcial = cantParcial;
    }
    

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "RepDiarioProdM{" + "id=" + id + ", orden=" + orden + ", componente=" + componente + ", cr=" + cr + ", contS=" + contS + ", fecha=" + fecha + ", operador=" + operador + ", noMaquina=" + noMaquina + ", actividad=" + actividad + ", operacion=" + operacion + ", tOp=" + tOp + ", inicio=" + inicio + ", fin=" + fin + ", cantProd=" + cantProd + ", cantParcial=" + cantParcial + ", comentario=" + comentario + '}';
    }
}

