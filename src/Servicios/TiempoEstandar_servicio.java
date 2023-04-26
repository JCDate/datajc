/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.TiempoEstandarM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiempoEstandar_servicio {
   private final String tabla = "tiempoestandar";

   public List<TiempoEstandarM> recuperarTodas(Connection conexion) throws SQLException{
      List<TiempoEstandarM> tiempoEstandar = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT fechaVencida,orden,componente,inst_desm,tiempo_troquelado,tiempo_total FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC ");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            tiempoEstandar.add(new TiempoEstandarM(resultado.getString("fechaVencida"), resultado.getString("orden"), resultado.getString("componente"),resultado.getString("inst_desm"),resultado.getString("tiempo_troquelado"),resultado.getString("tiempo_total")));
         }

      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return tiempoEstandar;
   }
}
