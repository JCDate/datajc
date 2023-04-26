/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.ComponentesRepM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ComponentesRep_servicio {
  private final String tabla = "comparacionordenes";
     
   public void agregarHubTroquel(Connection conexion, ComponentesRepM comR) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(orden, componente) VALUES(?, ?)");
            consulta.setString(1, comR.getOrden());
            consulta.setString(2, comR.getComponente());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   
   public List<ComponentesRepM> recuperarTodas(Connection conexion) throws SQLException{
      List<ComponentesRepM> comR = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  orden, componente FROM " + this.tabla +" ORDER BY componente ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            comR.add(new ComponentesRepM( resultado.getString("orden"), resultado.getString("componente")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return comR;
   }
}
