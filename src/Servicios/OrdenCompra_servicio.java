/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.OrdenCompraM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrdenCompra_servicio {
  private final String tabla = "orden_compra";

   public void modificar(Connection conexion,  OrdenCompraM ordenCom) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  fechaV=?, componente=?, CR=?, cantidad=?,calibre=?,consumo_kg=?,no_oper=?,no_golpes=?  WHERE orden = ?");
            consulta.setString(1, ordenCom.getFechaV());
            consulta.setString(2, ordenCom.getComponente());
            consulta.setString(3, ordenCom.getCR());
            consulta.setInt(4, ordenCom.getCantidad());
            consulta.setString(5, ordenCom.getCalibre());
            consulta.setFloat(6, ordenCom.getConsumo_kg());
            consulta.setInt(7, ordenCom.getNo_oper());
            consulta.setInt(8, ordenCom.getNo_golpes());
            consulta.setString(9, ordenCom.getOrden());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }

   public List<OrdenCompraM> recuperarTodas(Connection conexion) throws SQLException{
      List<OrdenCompraM> ordenCom = new ArrayList<>();
      try{
        
        PreparedStatement consulta = conexion.prepareStatement("SELECT  fechaV, orden, componente, CR, cantidad,calibre,consumo_kg,no_oper,no_golpes FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            ordenCom.add(new OrdenCompraM( resultado.getString("fechaV"),resultado.getString("orden"),resultado.getString("componente"), resultado.getString("CR"),resultado.getInt("cantidad"),resultado.getString("calibre"),resultado.getFloat("consumo_kg"),resultado.getInt("no_oper"),resultado.getInt("no_golpes")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return ordenCom;
   }
}
