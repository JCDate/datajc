/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.TiempoMaquinasM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiempoMaquinas_servicio {
    private final String tabla = "tiempomaquinas";
    
    public void agregar(Connection conexion, TiempoMaquinasM maq) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(maquina) VALUES(?)");
            consulta.setString(1, maq.getMaquina());


         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
    }
    
    public int existe(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(maquina) FROM tiempomaquinas WHERE maquina=?";
            
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
    
    public void eliminar(String id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM tiempomaquinas WHERE maquina= ?";
        ps = con.prepareStatement(consulta);
        ps .setString(1, id);
        ps .executeUpdate();

    }
    
     public List<TiempoMaquinasM> recuperarTodas(Connection conexion) throws SQLException{
      List<TiempoMaquinasM> mq = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT maquina,horas FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            mq.add(new TiempoMaquinasM(resultado.getString("maquina"), resultado.getString("horas")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return mq;
   }
}
