/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SqlUsuarios extends Conexion{
    
   public boolean login(Usuarios usr) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT u.id, u.usuario,u.password, u.nombre, u.id_tipo, t.nombre FROM usuarios AS u INNER JOIN tipo_usuario AS t ON u.id_tipo=t.id_tipo WHERE usuario=?";

                ps= con.prepareStatement(sql);
                ps.setString(1,usr.getUsuario());
        
                rs= ps.executeQuery();
                
                if(rs.next()){
                    if(usr.getPassword().equals(rs.getString(3))){
                        String sqlUpdate= "UPDATE usuarios SET last_session=? WHERE id= ? ";
                        ps= con.prepareStatement(sqlUpdate);
                        ps.setString(1, usr.getLast_session());
                        ps.setInt(2, rs.getInt(1));
                        ps.execute();
                        
                        usr.setId(rs.getInt(1));
                        usr.setNombre(rs.getString(4));
                        usr.setId_tipo(rs.getInt(5));
                        usr.setNombre_tipo(rs.getString(6));
                        
                        return true;
                    }else{
                        return false;
                    }
                    
                }
                return false;
           
        }catch(SQLException ex){
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
       
    }
    public boolean registrar(Usuarios usr) {
        try{
            PreparedStatement ps = null;
            Connection con= obtener();
            
            String sql= "INSERT INTO usuarios (usuario, password, nombre, id_tipo) VALUES(?,?,?,?)";

                ps= con.prepareStatement(sql);
                ps.setString(1, usr.getUsuario());
                ps.setString(2, usr.getPassword());
                ps.setString(3, usr.getNombre());
                ps.setInt(4, usr.getId_tipo());
                
                ps.execute();
                
                return true;
           
        }catch(SQLException ex){
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    public int existeUsuario(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(id) FROM usuarios WHERE usuario=?";
            
            
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
}
