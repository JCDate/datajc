/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.EntregadosM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Entregados_servicio {
   private final String tabla = "entregados";
   
   public void actualizar(Connection conexion, EntregadosM entregados) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET fechaRecibida=?,fechaVencida=?,orden=?, cantidad_reque=? ,cantidadEntregada=?, piezasEntregar=?,  consignatario=?, item_cliente=?, comentario=?, fechaEmbarque=?, factura=?, embarques=? WHERE id = ?");
            consulta.setString(1, entregados.getFechaRecibida());
            consulta.setString(2, entregados.getFechaVencida());
            consulta.setString(3, entregados.getOrden());
            consulta.setString(4, entregados.getCantidad_reque());
            consulta.setInt(5, entregados.getCantidadEntregada());
            consulta.setInt(6, entregados.getPiezasEntregar());
            consulta.setString(7, entregados.getConsignatario());
            consulta.setString(8, entregados.getItem_cliente());
            consulta.setString(9, entregados.getComentario());
            consulta.setString(10, entregados.getFechaEmbarque());
            consulta.setString(11, entregados.getFactura());
            consulta.setString(12, entregados.getEmbarques());
            consulta.setInt(13, entregados.getId());
            
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   
   
   public List<EntregadosM> recuperarTodas(Connection conexion) throws SQLException{
      List<EntregadosM> entregados = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id,fechaRecibida,fechaVencida,orden,cantidad_reque ,cantidadEntregada, piezasEntregar,  consignatario, item_cliente, comentario, fechaEmbarque, factura,embarques FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC ");
         
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            entregados.add(new EntregadosM( resultado.getInt("id"),resultado.getString("fechaRecibida"),resultado.getString("fechaVencida"),resultado.getString("orden"),resultado.getString("cantidad_reque"), resultado.getInt("cantidadEntregada"),resultado.getInt("piezasEntregar"),resultado.getString("consignatario"),resultado.getString("item_cliente"),resultado.getString("comentario"),resultado.getString("fechaEmbarque"),resultado.getString("factura"), resultado.getString("embarques")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return entregados;
   }
}
