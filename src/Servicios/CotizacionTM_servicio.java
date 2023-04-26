/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.CotizacionTMPDM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CotizacionTM_servicio {
  private final String tabla = "cotizaciontmpd";
     
   public void agregar(Connection conexion, CotizacionTMPDM co) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(cotizacion,componente,troquel,pzaDañada) VALUES(?,?,?,?)");
            consulta.setString(1, co.getCotizacion());
            consulta.setString(2, co.getComponente());
            consulta.setString(3, co.getTroquel());
            consulta.setString(4, co.getPzaDañada());
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }

   public int existeComponente(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(cotizacion) FROM cotizaciontmpd WHERE cotizacion=?";
            
            ps= con.prepareStatement(sql);
            ps.setString(1,usuarios);
            rs= ps.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
            return 1;
        }catch(SQLException ex){
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }  
    }
   
   public List<CotizacionTMPDM> recuperarTodas(Connection conexion) throws SQLException{
      List<CotizacionTMPDM> co = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT  cotizacion,componente,cr, troquel,pzaDañada FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(cotizacion , '/', -1), SUBSTRING_INDEX(SUBSTRING_INDEX(cotizacion , '/', 2), '/', -1),SUBSTRING_INDEX(cotizacion ,'_', 1)) ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            co.add(new CotizacionTMPDM( resultado.getString("cotizacion"), resultado.getString("componente"),resultado.getString("cr"), resultado.getString("troquel"),resultado.getString("pzaDañada")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return co;
   }
}
