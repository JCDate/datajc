/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import Modelos.DefInsM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DefIns_servicio {
  private final String tabla = "defectoins";
     
   public void agregar(Connection conexion, DefInsM crs) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, defecto,operacion) VALUES(?, ?, ?)");
            consulta.setString(1, crs.getComponente());
            consulta.setString(2, crs.getDefecto());
            consulta.setInt(3, crs.getOperacion());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion, DefInsM crs) throws SQLException {
       try{
         PreparedStatement consulta;

         consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  componente = ?, defecto = ?, operacion = ? WHERE id = ?");
            consulta.setString(1, crs.getComponente());
            consulta.setString(2, crs.getDefecto());
            consulta.setInt(3, crs.getOperacion());
            consulta.setInt(4, crs.getId());
           
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }


   public List<DefInsM> recuperarTodas(Connection conexion) throws SQLException{
      List<DefInsM> crs = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  *  FROM " + this.tabla +" ORDER BY componente ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            crs.add(new DefInsM(resultado.getInt("id"), resultado.getString("componente"), resultado.getString("defecto"),resultado.getInt("operacion")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return crs;
   }
    
}
