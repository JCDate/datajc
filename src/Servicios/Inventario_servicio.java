/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.InventarioM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Inventario_servicio {
  private final String tabla = "inventario";

    public void agregar(Connection conexion,  InventarioM inventario) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "( calibre,descripcion, num_calibre,unidad,milesimas ,existencia,kgs,tolerancia,peso_hoja,observaciones)  VALUES(?,?,?,?,?,?,?,?,?,?)");
            consulta.setString(1, inventario.getCalibre());
            consulta.setString(2, inventario.getDescripcion());
            consulta.setString(3, inventario.getNum_calibre());
            consulta.setString(4, inventario.getUnidad());
            consulta.setString(5, inventario.getMilesimas());
            consulta.setString(6, inventario.getExistencia());
            consulta.setFloat(7, inventario.getKgs());
            consulta.setString(8, inventario.getTolerancia());
            consulta.setFloat(9, inventario.getPeso_hoja());
            consulta.setString(10, inventario.getObservaciones());
            
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
    
   
   public void modificar(Connection conexion,  InventarioM inventario) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  descripcion=?, num_calibre=?,unidad=?,milesimas=? ,existencia=?,kgs=?,tolerancia=?,peso_hoja=?,observaciones=?  WHERE calibre = ?");
            consulta.setString(1, inventario.getDescripcion());
            consulta.setString(2, inventario.getNum_calibre());
            consulta.setString(3, inventario.getUnidad());
            consulta.setString(4, inventario.getMilesimas());
            consulta.setString(5, inventario.getExistencia());
            consulta.setFloat(6, inventario.getKgs());
            consulta.setString(7, inventario.getTolerancia());
            consulta.setFloat(8, inventario.getPeso_hoja());
            consulta.setString(9, inventario.getObservaciones());
            consulta.setString(10, inventario.getCalibre());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
   public int existeCalibre(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(calibre) FROM inventario WHERE calibre=?";
            
                ps= con.prepareStatement(sql);
                ps.setString(1,usuarios);
        
                rs= ps.executeQuery();
                
                if(rs.next()){
                    return rs.getInt(1);
                }
                return 1;
           
        }catch(SQLException ex){
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } 
   }

   public List<InventarioM> recuperarTodas(Connection conexion) throws SQLException{
      List<InventarioM> inventario = new ArrayList<>();
      try{
        
        PreparedStatement consulta = conexion.prepareStatement("SELECT  calibre,descripcion, num_calibre,unidad,milesimas ,existencia,kgs,tolerancia,peso_hoja,observaciones FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            inventario.add(new InventarioM( resultado.getString("calibre"),resultado.getString("descripcion"), resultado.getString("num_calibre"),resultado.getString("unidad"),resultado.getString("milesimas"),resultado.getString("existencia"),resultado.getFloat("kgs"),resultado.getString("tolerancia"),resultado.getFloat("peso_hoja"),resultado.getString("observaciones")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return inventario;
   }
}
