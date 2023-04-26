/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.PrecioComponenteM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrecioComponente_servicio {
     private final String tabla = "precioComponente";

   public void agregar(Connection conexion, PrecioComponenteM precioComponente) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "( componente, precioU ) VALUES( ?,?)");
            consulta.setString(1, precioComponente.getComponente());
            consulta.setFloat(2, precioComponente.getPrecioUnitario());
            
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion,  PrecioComponenteM precioComponente) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET   precioU=? WHERE componente = ?");
            consulta.setFloat(1, precioComponente.getPrecioUnitario());
            consulta.setString(2, precioComponente.getComponente());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public int existeCalibre(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(componente) FROM precioComponente WHERE componente=?";
            
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

   public List<PrecioComponenteM> recuperarTodas(Connection conexion) throws SQLException{
      List<PrecioComponenteM> precioComponente = new ArrayList<>();
      try{
        
        PreparedStatement consulta = conexion.prepareStatement("SELECT  componente, precioU FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            precioComponente.add(new PrecioComponenteM( resultado.getString("componente"),resultado.getFloat("precioU")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return precioComponente;
   }
}
