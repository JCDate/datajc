/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.NuevasOrdenesM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NuevasOrdenes_servicio {
  private final String tabla = "nuevasordenes";
  
   public int existeOrden(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(orden) FROM atrasosproduccion WHERE orden=?";
            
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
   

   public List<NuevasOrdenesM> recuperarTodas(Connection conexion) throws SQLException{
      List<NuevasOrdenesM> nueOr = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla + " ORDER BY componente ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            nueOr.add(new NuevasOrdenesM(resultado.getBoolean("ordengenerada"), resultado.getString("orden"), resultado.getString("componente"), resultado.getString("fechaV"), resultado.getInt("cantidadR"), resultado.getString("loteeconomico")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return nueOr;
   }
    
}
