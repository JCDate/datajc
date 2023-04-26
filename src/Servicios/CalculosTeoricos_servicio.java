/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.CalculosTeoricosM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalculosTeoricos_servicio {
  private final String tabla = "calculosteoricos";
     
   public void agregar(Connection conexion, CalculosTeoricosM CT) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, pzas_tira,pzas_tiraEntero,tiras_hojas,tiras_hojasEntero,pzas_hojasEntero,peso_pzasCal,peso_pzasLP ) VALUES(?,?,?,?,?,?,?,?)");
            consulta.setString(1, CT.getComponente());
            consulta.setFloat(2, CT.getPzas_tira());
            consulta.setInt(3, CT.getPzas_tiraEnteros());
            consulta.setFloat(4, CT.getTiras_hojas());
            consulta.setInt(5, CT.getTira_hojasEntero());
            consulta.setInt(6, CT.getPzas_HojasEntero());
            consulta.setDouble(7, CT.getPeso_pzasCal());
            consulta.setDouble(8, CT.getPeso_pzasLP());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion,  CalculosTeoricosM CT) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  pzas_tira=?,pzas_tiraEntero=?,tiras_hojas=?,tiras_hojasEntero=?,pzas_hojasEntero=?,peso_pzasCal=?,peso_pzasLP=?  WHERE componente = ?");           
            consulta.setFloat(1, CT.getPzas_tira());
            consulta.setInt(2, CT.getPzas_tiraEnteros());
            consulta.setFloat(3, CT.getTiras_hojas());
            consulta.setInt(4, CT.getTira_hojasEntero());
            consulta.setInt(5, CT.getPzas_HojasEntero());
            consulta.setDouble(6, CT.getPeso_pzasCal());
            consulta.setDouble(7, CT.getPeso_pzasLP());
            consulta.setString(8, CT.getComponente());

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
            
            String sql= "SELECT COUNT(componente) FROM calculosteoricos WHERE componente=?";
            
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

   public List<CalculosTeoricosM> recuperarTodas(Connection conexion) throws SQLException{
      List<CalculosTeoricosM> CT = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT calculosteoricos.componente,pzas_tira,pzas_tiraEntero,tiras_hojas,tiras_hojasEntero,pzas_hojasEntero,peso_pzasCal,peso_pzasLP FROM calculosteoricos, datostecnicosmp WHERE datostecnicosmp.componente=calculosteoricos.componente ORDER BY calculosteoricos.componente");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            CT.add(new CalculosTeoricosM( resultado.getString("componente"), resultado.getFloat("pzas_tira"),resultado.getInt("pzas_tiraEntero"),resultado.getFloat("tiras_hojas"),resultado.getInt("tiras_hojasEntero"),resultado.getInt("pzas_hojasEntero"),resultado.getDouble("peso_pzasCal"),resultado.getDouble("peso_pzasLP")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return CT;
   }
    
}
