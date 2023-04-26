/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.VentasM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ventas_servicio {
  private final String tabla = "ventas";
     
   public void agregar(Connection conexion, VentasM ventas) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, cr, pzs, fechaEmb) VALUES(?,?,?,?)");
            consulta.setString(1, ventas.getComponente());
            consulta.setString(2, ventas.getCr());
            consulta.setString(3, ventas.getPzs());
            consulta.setString(4, ventas.getFechaEmb());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion, VentasM ventas) throws SQLException {
       try{
         PreparedStatement consulta;

         consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET componente = ?, cr = ?, pzs=?, fechaEmb=? WHERE id = ?");
            consulta.setString(1, ventas.getComponente());
            consulta.setString(2, ventas.getCr());
            consulta.setString(3, ventas.getPzs());
            consulta.setString(4, ventas.getFechaEmb());
            consulta.setInt(5, ventas.getId());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
   
   public void eliminar(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM ventas WHERE id= ?";
        ps = con.prepareStatement(consulta);
        ps .setInt(1, id);
        ps .executeUpdate();
    }

   public List<VentasM> recuperarTodas(Connection conexion) throws SQLException{
      List<VentasM> ventas = new ArrayList<>();
      try{ 
         PreparedStatement consulta = conexion.prepareStatement("SELECT  id,componente, cr, pzs, fechaEmb FROM " + this.tabla +" ORDER BY pzs DESC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            ventas.add(new VentasM( resultado.getInt("id"),resultado.getString("componente"), resultado.getString("cr"), resultado.getString("pzs"), resultado.getString("fechaEmb")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return ventas;
   }
   
    
}
