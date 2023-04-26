/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.RepDiarioProdM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepDiarioProd_servicio {
  private final String tabla = "repdiprod";
  
    public void modificar(Connection conexion,  RepDiarioProdM rep) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET cantProd=?,cantParcial=?, noMaquina=? WHERE id = ?");
            consulta.setInt(1, rep.getCantProd());
            consulta.setInt(2, rep.getCantParcial());
            consulta.setString(3, rep.getNoMaquina());
            consulta.setInt(4, rep.getId());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
  
  public void eliminar(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM repdiarot WHERE id= ?";
        ps = con.prepareStatement(consulta);
        ps.setInt(1, id);
        ps.executeUpdate();
        
        PreparedStatement ps2 = null;
        String consulta2= "DELETE FROM repdiprod WHERE id= ?";
        ps2 = con.prepareStatement(consulta2);
        ps2.setInt(1, id);
        ps2.executeUpdate();
    }
   
  public List<RepDiarioProdM> recuperarTodas(Connection conexion) throws SQLException{
      List<RepDiarioProdM> rep = new ArrayList<>();
      try{
        PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(fecha , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fecha , '/', 2), '/', -1),SUBSTRING_INDEX(fecha, '/', 1)) DESC, componente ASC" );
        ResultSet resultado = consulta.executeQuery();
        while(resultado.next()){
           rep.add(new RepDiarioProdM(resultado.getInt("id"), resultado.getString("orden"), resultado.getString("componente"), resultado.getString("cr"), resultado.getInt("contS"), resultado.getString("fecha"), resultado.getString("operador"),resultado.getString("noMaquina"), resultado.getString("actividad"), resultado.getString("operacion"), resultado.getInt("tOp"), resultado.getString("inicio"),resultado.getString("fin"),resultado.getInt("cantProd"), resultado.getInt("cantParcial"),resultado.getString("comentario")));                                                                                                                                                                                                                                                                                            
        }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return rep;
   }
}
