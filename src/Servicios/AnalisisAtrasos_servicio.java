/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.AnalisisAtrasosM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalisisAtrasos_servicio {
   private final String tabla = "analisisatrasos";

   public void agregar(Connection conexion, AnalisisAtrasosM analisisAtrasos) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + " (orden,fechaRecibida,fechaVencida,cantidad_reque ,cantidadEntregada, piezasEntregar,consignatario, item_cliente, comentario, fechaEmbarque, factura, embarques) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setString(1, analisisAtrasos.getOrden());
            consulta.setString(2, analisisAtrasos.getFechaRecibida());
            consulta.setString(3, analisisAtrasos.getFechaVencida());
            consulta.setString(4, analisisAtrasos.getCantidad_reque());
            consulta.setInt(5, analisisAtrasos.getCantidadEntregada());
            consulta.setInt(6, analisisAtrasos.getPiezasEntregar());
            consulta.setString(7, analisisAtrasos.getConsignatario());
            consulta.setString(8, analisisAtrasos.getItem_cliente());
            consulta.setString(9, analisisAtrasos.getComentario());
            consulta.setString(10, analisisAtrasos.getFechaEmbarque());
            consulta.setString(11, analisisAtrasos.getFactura());
            consulta.setString(12, analisisAtrasos.getEmbarques());

         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
   public void actualizar(Connection conexion, AnalisisAtrasosM analisisAtrasos) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  fechaRecibida=?,fechaVencida=?,cantidad_reque=? ,cantidadEntregada=?, piezasEntregar=?,  consignatario=?, item_cliente=?, comentario=?, fechaEmbarque=?, factura=?, embarques=? WHERE orden = ?");
            consulta.setString(1, analisisAtrasos.getFechaRecibida());
            consulta.setString(2, analisisAtrasos.getFechaVencida());
            consulta.setString(3, analisisAtrasos.getCantidad_reque());
            consulta.setInt(4, analisisAtrasos.getCantidadEntregada());
            consulta.setInt(5, analisisAtrasos.getPiezasEntregar());
            consulta.setString(6, analisisAtrasos.getConsignatario());
            consulta.setString(7, analisisAtrasos.getItem_cliente());
            consulta.setString(8, analisisAtrasos.getComentario());
            consulta.setString(9, analisisAtrasos.getFechaEmbarque());
            consulta.setString(10, analisisAtrasos.getFactura());
            consulta.setString(11, analisisAtrasos.getEmbarques());
            consulta.setString(12, analisisAtrasos.getOrden());
            
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
   
   public void eliminar(String orden) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM tiempoestandar WHERE orden= ?";
        ps = con.prepareStatement(consulta);
        ps .setString(1, orden);
        ps .executeUpdate();
        
        PreparedStatement ps2 = null;
        String consulta2= "DELETE FROM analisisatrasos WHERE orden= ?";
        ps2 = con.prepareStatement(consulta2);
        ps2 .setString(1, orden);
        ps2 .executeUpdate();
        
        PreparedStatement ps3 = null;
        String consulta3= "DELETE FROM atrasosproduccion WHERE orden= ?";
        ps3 = con.prepareStatement(consulta3);
        ps3 .setString(1, orden);
        ps3 .executeUpdate();
        
        PreparedStatement ps4 = null;
        String consulta4= "DELETE FROM orden_compra WHERE orden= ?";
        ps4 = con.prepareStatement(consulta4);
        ps4 .setString(1, orden);
        ps4 .executeUpdate();
     
    }

   public List<AnalisisAtrasosM> recuperarTodas(Connection conexion) throws SQLException{
      List<AnalisisAtrasosM> analisisAtrasos = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT prioridad,fechaRecibida,fechaVencida,orden,cantidad_reque ,cantidadEntregada, piezasEntregar,  consignatario, item_cliente, comentario, fechaEmbarque, factura, embarques FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC ");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            analisisAtrasos.add(new AnalisisAtrasosM(resultado.getBoolean("prioridad"), resultado.getString("fechaRecibida"),resultado.getString("fechaVencida"),resultado.getString("orden"),resultado.getString("cantidad_reque"), resultado.getInt("cantidadEntregada"),resultado.getInt("piezasEntregar"),resultado.getString("consignatario"),resultado.getString("item_cliente"), resultado.getString("comentario"),resultado.getString("fechaEmbarque"), resultado.getString("factura"), resultado.getString("embarques")));
         }

      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return analisisAtrasos;
   }
   
   public List<AnalisisAtrasosM> recuperarTodasAnalisis(Connection conexion) throws SQLException{
      List<AnalisisAtrasosM> analisisAtrasos = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT prioridad,fechaRecibida,fechaVencida,orden,cantidad_reque ,cantidadEntregada, piezasEntregar,  consignatario, item_cliente, comentario, fechaEmbarque, factura, embarques FROM analisisatrasos WHERE CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) <= CONVERT(DATE_FORMAT(NOW(), '%Y%m%d'), CHAR) ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            analisisAtrasos.add(new AnalisisAtrasosM(resultado.getBoolean("prioridad"), resultado.getString("fechaRecibida"),resultado.getString("fechaVencida"),resultado.getString("orden"),resultado.getString("cantidad_reque"), resultado.getInt("cantidadEntregada"),resultado.getInt("piezasEntregar"),resultado.getString("consignatario"),resultado.getString("item_cliente"), resultado.getString("comentario"),resultado.getString("fechaEmbarque"), resultado.getString("factura"), resultado.getString("embarques")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return analisisAtrasos;
   }
   
}
