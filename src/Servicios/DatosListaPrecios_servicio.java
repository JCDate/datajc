/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.DatosListaPreciosM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatosListaPrecios_servicio {
  private final String tabla = "datoslistaprecios";
     
   public void agregar(Connection conexion, DatosListaPreciosM DLP) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, cr,calibre,blank_kg ) VALUES(?,?,?,?)");
            consulta.setString(1, DLP.getComponente());
            consulta.setString(2, DLP.getCr());
            consulta.setString(3, DLP.getCalibre());
            consulta.setDouble(4, DLP.getBlank_kg());
            
         
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion,  DatosListaPreciosM DLP) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  cr=?,calibre=?, blank_kg=?  WHERE componente = ?");
            consulta.setString(1, DLP.getCr());
            consulta.setString(2, DLP.getCalibre());
            consulta.setDouble(3, DLP.getBlank_kg());
            consulta.setString(4, DLP.getComponente());

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
            
            String sql= "SELECT COUNT(componente) FROM datoslistaprecios WHERE componente=?";
            
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

   public List<DatosListaPreciosM> recuperarTodas(Connection conexion) throws SQLException{
      List<DatosListaPreciosM> DLP = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT datoslistaprecios.componente, cr,calibre,blank_kg FROM datoslistaprecios,datostecnicosmp WHERE datostecnicosmp.componente= datoslistaprecios.componente ORDER BY datoslistaprecios.componente");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            DLP.add(new DatosListaPreciosM( resultado.getString("componente"), resultado.getString("cr"),resultado.getString("calibre"),resultado.getDouble("blank_kg")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return DLP;
   }
}
