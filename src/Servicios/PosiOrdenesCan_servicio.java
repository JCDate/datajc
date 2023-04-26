/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.PosiOrdenesCanM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PosiOrdenesCan_servicio {
  private final String tabla = "posiordcancelacion";
  
   

   public List<PosiOrdenesCanM> recuperarTodas(Connection conexion) throws SQLException{
      List<PosiOrdenesCanM> poOrC = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            poOrC.add(new PosiOrdenesCanM(resultado.getString("orden"), resultado.getString("componente")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return poOrC;
   }
    
}
