/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.TiempoAtrasosM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TiempoAtrasos_servicio {
    private final String tabla = "tiempoatrasos";
    
     public void modificar(Connection conexion, TiempoAtrasosM tiempoAtrasos) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  maquina=?  WHERE orden = ? AND numOperaciones=?");
            consulta.setString(1, tiempoAtrasos.getMaquina());
            consulta.setString(2, tiempoAtrasos.getOrden());
            consulta.setInt(3,tiempoAtrasos.getNumOperaciones());

         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
    
    
    public List<TiempoAtrasosM> recuperarTodas(Connection conexion) throws SQLException{
      List<TiempoAtrasosM> entregados = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT orden,cantidad_reque,componente,numOperaciones,troquel,maquina,inst_desm,tiempo_troquelado,tiempo_total,fechaVencida, componente_TR, componente_T, componente_A FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC, numOperaciones ASC ");
         
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            entregados.add(new TiempoAtrasosM( resultado.getString("orden"),resultado.getInt("cantidad_reque"),resultado.getString("componente"),resultado.getInt("numOperaciones"), resultado.getString("troquel"),resultado.getString("maquina"),resultado.getString("inst_desm"),resultado.getString("tiempo_troquelado"),resultado.getString("tiempo_total"), resultado.getString("fechaVencida"), resultado.getString("componente_TR"), resultado.getString("componente_T"), resultado.getString("componente_A")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return entregados;
   }
}
