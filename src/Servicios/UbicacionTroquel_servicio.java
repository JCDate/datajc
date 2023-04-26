/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.UbicacionTroquelM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UbicacionTroquel_servicio {
  private final String tabla = "ubicacionTroquel";
     
   public void agregarUbTroquel(Connection conexion, UbicacionTroquelM ubTroq) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(troquel, ubicacion) VALUES(?, ?)");
            consulta.setString(1, ubTroq.getTroquel());
            consulta.setString(2, ubTroq.getUbicacion());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificarUbTroquel(Connection conexion, UbicacionTroquelM ubTroq) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  ubicacion = ?  WHERE troquel = ?");
            consulta.setString(1, ubTroq.getUbicacion());
            consulta.setString(2, ubTroq.getTroquel());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public int existeTroquel(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(troquel) FROM ubicaciontroquel WHERE troquel=?";
            
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

   public List<UbicacionTroquelM> recuperarTodas(Connection conexion) throws SQLException{
      List<UbicacionTroquelM> ubT = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT  troquel, ubicacion FROM " + this.tabla+" ORDER BY troquel ASC" );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            ubT.add(new UbicacionTroquelM( resultado.getString("troquel"), resultado.getString("ubicacion")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return ubT;
   } 
}
