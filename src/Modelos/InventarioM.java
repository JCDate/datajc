
package Modelos;


public class InventarioM {
   private String calibre;
   private String descripcion;
   private String num_calibre;
   private String unidad;
   private String milesimas;
   private String existencia;
   private Float kgs;
   private String tolerancia;
   private Float peso_hoja;
   private String observaciones;
 
   public  InventarioM () {
      this.calibre = null;
      this.descripcion = null;
      this.num_calibre = null;
      this.unidad = null;
      this.milesimas = null;
      this.existencia = null;
      this.kgs = null;
      this.tolerancia = null;
      this.peso_hoja = null;
      this.observaciones = null;
     
   }
   public  InventarioM ( String calibre, String descripcion, String num_calibre, String unidad, String milesimas ,String existencia,float kgs,String tolerancia,float peso_hoja,String observaciones) {
      this.calibre = calibre;
      this.descripcion = descripcion;
      this.num_calibre = num_calibre;
      this.unidad = unidad;
      this.milesimas = milesimas;
      this.existencia = existencia;
      this.kgs = kgs;
      this.tolerancia = tolerancia;
      this.peso_hoja = peso_hoja;
      this.observaciones = observaciones;
      
   }
    public String  getCalibre() {
      return calibre;
   }
   public String getDescripcion() {
      return descripcion;
   }
   public String getNum_calibre() {
      return num_calibre;
   }
   public String getUnidad() {
      return unidad;
   }
   public String getMilesimas() {
      return milesimas;
   }
   public String getExistencia() {
      return existencia;
   }
   public Float getKgs() {
      return kgs;
   }
   public String getTolerancia() {
      return tolerancia;
   }
    public Float getPeso_hoja() {
      return peso_hoja;
   }
     public String getObservaciones() {
      return observaciones;
   }
   
   //SET
   public void setCalibre(String calibre) {
      this.calibre = calibre;
   }
   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }
   public void setNum_calibre(String num_calibre) {
      this.num_calibre = num_calibre;
   }
   public void setUnidad(String unidad) {
      this.unidad = unidad;
   }
   public void setMilesimas(String milesimas) {
      this.milesimas = milesimas;
   }
    public void setExistencia(String existencia) {
      this.existencia = existencia;
   }
   public void setKgs(Float kgs) {
      this.kgs = kgs;
   }
   public void setTolerancia(String tolerancia) {
      this.tolerancia = tolerancia;
   }
   public void setPeso_hoja(Float peso_hoja) {
      this.peso_hoja = peso_hoja;
   }
   public void setObservaciones(String observaciones) {
      this.observaciones = observaciones;
   }
 
   @Override
   public String toString() {
      return "InventarioM{" + ", calibre="+ calibre+ ", descripcion=" + descripcion + ", num_calibre=" +num_calibre +", unidad="+unidad+", milesimas= "+ milesimas+", existencia="+existencia +",kgs="+kgs+", tolerancia="+tolerancia+", peso_hoja="+peso_hoja+", observaciones="+ observaciones+'}';
   } 
}