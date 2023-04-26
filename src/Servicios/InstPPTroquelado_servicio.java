/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.InstPPTroqueladoM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InstPPTroquelado_servicio {
  private final String tabla = "instpptroquelado";
     
  
   public List<InstPPTroqueladoM> recuperarTodas(Connection conexion) throws SQLException{
      List<InstPPTroqueladoM> instPPT = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT componente,cr,calibre,fecha,operacion,troquel,maquina,operador,libs,causaR FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fecha , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fecha , '/', 2), '/', -1),SUBSTRING_INDEX(fecha, '/', 1)) DESC ");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            instPPT.add(new InstPPTroqueladoM( resultado.getString("componente"), resultado.getString("cr"), resultado.getString("calibre"), resultado.getString("fecha"), resultado.getInt("operacion"), resultado.getString("troquel"),resultado.getString("maquina"),resultado.getString("operador"), resultado.getString("libs"), resultado.getString("causaR")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return instPPT;
   }
    
}
