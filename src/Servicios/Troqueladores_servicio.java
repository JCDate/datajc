/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.TroqueladoresM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Troqueladores_servicio {
    private final String tabla = "troqueladores";
    
    public void agregar(Connection conexion, TroqueladoresM troqueladores) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(id_troquelador,nombre,puesto) VALUES( ?, ?, ?)");
            consulta.setInt(1, troqueladores.getId_troquelador());
            consulta.setString(2, troqueladores.getNombre());
            consulta.setString(3, troqueladores.getPuesto());
            
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
   
   public void modificar(Connection conexion, TroqueladoresM troqueladores) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  nombre=?,puesto=?  WHERE id_troquelador = ?");
            
            consulta.setString(1, troqueladores.getNombre());
            consulta.setString(2, troqueladores.getPuesto());
            consulta.setInt(3, troqueladores.getId_troquelador());

         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
   
  
  
    public int existeTroquelador(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(id_troquelador) FROM troqueladores WHERE id_troquelador=?";
            
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
   } public void eliminar(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM troqueladores WHERE id_troquelador= ?";
        ps = con.prepareStatement(consulta);
        ps .setInt(1, id);
        ps .executeUpdate();

   }
   
   
   public List<TroqueladoresM> recuperarTodas(Connection conexion) throws SQLException{
      List<TroqueladoresM> troqueladores= new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id_troquelador,nombre,puesto FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            troqueladores.add(new TroqueladoresM( resultado.getInt("id_troquelador"),resultado.getString("nombre"),resultado.getString("puesto")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return troqueladores;
   }
   
   public List<TroqueladoresM> recuperarInstalador(Connection conexion) throws SQLException{
      List<TroqueladoresM> troqueladores= new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id_troquelador,nombre,puesto FROM troqueladores WHERE puesto='INSTALADOR'" );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            troqueladores.add(new TroqueladoresM( resultado.getInt("id_troquelador"),resultado.getString("nombre"),resultado.getString("puesto")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return troqueladores;
   }
}