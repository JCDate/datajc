/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.SIM_CURRENT_M;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SIMCURRENT_servicio {
  private final String tabla = "sim_current";

    public List<SIM_CURRENT_M> recuperarTodas(Connection conexion) throws SQLException{
      List<SIM_CURRENT_M> simcurrent = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT  fechaRecibida, consignatario, item_cliente, orden, fechaVencida, cantidad_reque FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            simcurrent.add(new SIM_CURRENT_M(resultado.getString("fechaRecibida"), resultado.getString("consignatario"),resultado.getString("item_cliente"),resultado.getString("orden"),resultado.getString("fechaVencida"),resultado.getInt("cantidad_reque")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return simcurrent;
    }
}
