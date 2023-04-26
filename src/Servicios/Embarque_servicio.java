/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.EmbarqueM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Embarque_servicio {
    private final String tabla = "embarque";
    
   public void modificar(Connection conexion, EmbarqueM embarque) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  componente=?,cr=?,cantidad=?,noCuñetes=?,NoSim=?,pesoNeto=?, factura=?  WHERE orden = ?");
            
            consulta.setString(1, embarque.getComponente());
            consulta.setString(2, embarque.getCr());
            consulta.setInt(3, embarque.getCantidad());
            consulta.setString(4, embarque.getNoCuñetes());
            consulta.setString(5, embarque.getNoSim());
            consulta.setFloat(6, embarque.getPesoNeto());
            consulta.setString(7, embarque.getFactura());
            consulta.setString(8, embarque.getOrden());
            
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
            
            String sql= "SELECT COUNT(orden) FROM facturacion WHERE orden=?";
            
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
    
    public void eliminar(String orden) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM embarque WHERE orden= ?";
        ps = con.prepareStatement(consulta);
        ps .setString(1, orden);
        ps .executeUpdate();
     
    }
   
   
   public List<EmbarqueM> recuperarTodas(Connection conexion) throws SQLException{
      List<EmbarqueM> embarque= new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM embarque WHERE señalEmb=1 ORDER BY factura !='', orden ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            embarque.add(new EmbarqueM( resultado.getString("orden"),resultado.getString("componente"),resultado.getString("cr"),resultado.getInt("cantidad"),resultado.getString("noCuñetes"),resultado.getString("NoSim"),resultado.getFloat("pesoNeto"),resultado.getString("factura"), resultado.getBoolean("facturado")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return embarque;
   }
}