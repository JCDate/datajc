/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.DimM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dim_servicio {
     private final String tabla = "dim";
     
   public void agregar(Connection conexion, DimM dim) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, cr, calibre, diaEx,diaInt,altura ) VALUES(?, ?,?,?,?,?)");
            consulta.setString(1, dim.getComponente());
            consulta.setString(2, dim.getCr());
            consulta.setString(3, dim.getCalibre());
            consulta.setString(4, dim.getDiaEx());
            consulta.setString(5, dim.getDiaInt());
            consulta.setString(6, dim.getAltura());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
     public void modificar(Connection conexion, DimM dim) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  cr=?, calibre=?, diaEx=?,diaInt=?,altura=?  WHERE componente = ?");
            consulta.setString(1, dim.getCr());
            consulta.setString(2, dim.getCalibre());
            consulta.setString(3, dim.getDiaEx());
            consulta.setString(4, dim.getDiaInt());
            consulta.setString(5, dim.getAltura());
            consulta.setString(6, dim.getComponente());

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
            
            String sql= "SELECT COUNT(componente) FROM dim WHERE componente=?";
            
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
   
   public List<DimM> recuperarTodas(Connection conexion) throws SQLException{
      List<DimM> dim = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT componente,cr,calibre,diaEx ,diaInt,altura FROM " + this.tabla +" ORDER BY  diaEx!='null', diaInt!='null', altura!='null'");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            dim.add(new DimM( resultado.getString("componente"),resultado.getString("cr"),resultado.getString("calibre"),resultado.getString("diaEx"), resultado.getString("diaInt"),resultado.getString("altura")));
          
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return dim;
   }
}
