/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.SolicitudTMPDM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SolicitudTM_servicio {
  private final String tabla = "solicitudtmpd";
     
   public void agregar(Connection conexion, SolicitudTMPDM sol) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(cotizacion,componente, fechaSol,envioCot,precioC,enviadoSKF,aprobadoSKF,solicitudM,costoM,fechaMaq,temple,ajuste,produccion,pzFacturadaSKF) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            consulta.setString(1, sol.getCotizacion());
            consulta.setString(2, sol.getComponente());
            consulta.setString(3, sol.getFechaSol());
            consulta.setString(4, sol.getEnvioCot());
            consulta.setString(5, sol.getPrecioC());
            consulta.setString(6, sol.getEnviadoSKF());
            consulta.setString(7, sol.getAprobadoSKF());
            consulta.setString(8, sol.getSolicitudM());
            consulta.setString(9, sol.getCostoM());
            consulta.setString(10,sol.getFechaMaq());
            consulta.setString(11,sol.getTemple());
            consulta.setString(12,sol.getAjuste());
            consulta.setString(13,sol.getProduccion());
            consulta.setString(14,sol.getPzFacturadaSKF());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion, SolicitudTMPDM sol) throws SQLException {
       try{
        PreparedStatement consulta;

        consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  componente=?, fechaSol=?,envioCot=?,precioC=?,enviadoSKF=?,aprobadoSKF=?,solicitudM=?,costoM=?,fechaMaq=?,temple=?,ajuste=?,produccion=?,pzFacturadaSKF=? WHERE cotizacion = ?");

            consulta.setString(1, sol.getComponente());
            consulta.setString(2, sol.getFechaSol());
            consulta.setString(3, sol.getEnvioCot());
            consulta.setString(4, sol.getPrecioC());
            consulta.setString(5, sol.getEnviadoSKF());
            consulta.setString(6, sol.getAprobadoSKF());
            consulta.setString(7, sol.getSolicitudM());
            consulta.setString(8, sol.getCostoM());
            consulta.setString(9, sol.getFechaMaq());
            consulta.setString(10,sol.getTemple());
            consulta.setString(11,sol.getAjuste());
            consulta.setString(12,sol.getProduccion());
            consulta.setString(13,sol.getPzFacturadaSKF());
            consulta.setString(14,sol.getCotizacion());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }

   public int existeC(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(cotizacion) FROM solicitudtmpd WHERE cotizacion=?";
            
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
   
   public int existeOrdenF(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(orden) FROM facturacionherramental WHERE orden=?";
            
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

   public List<SolicitudTMPDM> recuperarTodas(Connection conexion) throws SQLException{
      List<SolicitudTMPDM> sol = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT  cotizacion,componente, fechaSol,envioCot,precioC,enviadoSKF,aprobadoSKF,solicitudM,costoM,fechaMaq,temple,ajuste,produccion,pzFacturadaSKF FROM " + this.tabla +" ORDER BY CONCAT(SUBSTRING_INDEX(cotizacion , '/', -1), SUBSTRING_INDEX(SUBSTRING_INDEX(cotizacion , '/', 2), '/', -1),SUBSTRING_INDEX(cotizacion ,'_', 1)) ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            sol.add(new SolicitudTMPDM( resultado.getString("cotizacion"), resultado.getString("componente"),resultado.getString("fechaSol"), resultado.getString("envioCot"), resultado.getString("precioC"),resultado.getString("enviadoSKF"),resultado.getString("aprobadoSKF"),resultado.getString("solicitudM"),resultado.getString("costoM"),resultado.getString("fechaMaq"),resultado.getString("temple"),resultado.getString("ajuste"),resultado.getString("produccion"),resultado.getString("pzFacturadaSKF")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return sol;
   }
    
}
