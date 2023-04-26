/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.InspeccionM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inspeccion_servicio {
  private final String tabla = "almaceninspeccion";

   public void modificar(Connection conexion, InspeccionM crs) throws SQLException {
       try{
         PreparedStatement consulta;

         consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  existencia = ?, entrada=?, fechaEntrada=?, salida=? WHERE componente = ?");
            
            consulta.setInt(1, crs.getExistencia());
            consulta.setInt(2, crs.getEntrada());
            consulta.setString(3, crs.getFechaEntrada());
            consulta.setInt(4, crs.getSalida());
            consulta.setString(5, crs.getComponente());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }


   public List<InspeccionM> recuperarTodas(Connection conexion) throws SQLException{
      List<InspeccionM> ins = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT  componente, existencia,entrada,fechaEntrada,salida FROM " + this.tabla +" ORDER BY componente ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            ins.add(new InspeccionM( resultado.getString("componente"), resultado.getInt("existencia"), resultado.getInt("entrada"), resultado.getString("fechaEntrada"), resultado.getInt("salida")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return ins;
   }
    
}
