/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.AntecedentesFamiliaM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AntecedentesFamilia_servicio {
  private final String tabla = "antecedentesfamilia";
     
   public void agregar(Connection conexion, AntecedentesFamiliaM antFam) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, familia) VALUES(?, ?)");
            consulta.setString(1, antFam.getComponente());
            consulta.setString(2, antFam.getFamilia());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
   
   public void modificar(Connection conexion, AntecedentesFamiliaM antFam) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  familia = ?  WHERE componente = ?");
            
            consulta.setString(1, antFam.getFamilia());
            consulta.setString(2, antFam.getComponente());

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
            
            String sql= "SELECT COUNT(componente) FROM antecedentesfamilia WHERE componente=?";
            
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
   
   
   public List<AntecedentesFamiliaM> recuperarTodas(Connection conexion) throws SQLException{
      List<AntecedentesFamiliaM> hubT = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  componente, familia FROM " + this.tabla+" ORDER BY componente ASC" );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            hubT.add(new AntecedentesFamiliaM( resultado.getString("componente"), resultado.getString("familia")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return hubT;
   }
    
}
