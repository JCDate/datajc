/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.CambiosM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cambios_servicio {
   
   public List<CambiosM> recuperarTodas(Connection conexion) throws SQLException{
      List<CambiosM> cambios = new ArrayList<>();
      try{
        PreparedStatement consulta = conexion.prepareStatement("SELECT orden, fechaV, atrasosproduccion.componente, crs.cr, cantidad, comentario FROM atrasosproduccion,crs WHERE atrasosproduccion.componente=crs.componente AND comentario LIKE '%CAMBIO%' AND  comentario NOT LIKE '%TALLER MECANICO%' OR atrasosproduccion.componente=crs.componente AND comentario LIKE '%ESPERA DE TROQUEL%' AND  comentario NOT LIKE '%TALLER MECANICO%' ORDER BY CONCAT(SUBSTRING_INDEX(fechaV , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaV , '/', 2), '/', -1),SUBSTRING_INDEX(fechaV, '/', 1)) ASC");
        ResultSet resultado = consulta.executeQuery();
        while(resultado.next()){
           cambios.add(new CambiosM( resultado.getString("orden"),resultado.getString("fechaV"),resultado.getString("atrasosproduccion.componente"),resultado.getString("crs.cr"), resultado.getInt("cantidad"),resultado.getString("comentario")));
        }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return cambios;
   }
   
   
}
