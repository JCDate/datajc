/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.OrdenActM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrdeneAct_servicio {
  private final String tabla = "ordenact";

   public List<OrdenActM> recuperarTodas(Connection conexion) throws SQLException{
      List<OrdenActM> OrAc = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            OrAc.add(new OrdenActM(resultado.getString("orden"), resultado.getString("componente"), resultado.getString("fechaVencida"), resultado.getInt("cantidad_reque"), resultado.getString("actualizo")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return OrAc;
   }
    
}
