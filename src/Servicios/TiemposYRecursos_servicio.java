/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import Modelos.TiemposYRecursosM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiemposYRecursos_servicio {
   private final String tabla = "tiemposyrecursos";

   public void agregar(Connection conexion, TiemposYRecursosM tiemposRecursos) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente,numeroOper,inst,pzsMin ,desm,inst_desm) VALUES( ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, tiemposRecursos.getComponente());
            consulta.setInt(2, tiemposRecursos.getNumOperaciones());
            consulta.setInt(3, tiemposRecursos.getInst());
            consulta.setFloat(4, tiemposRecursos.getPzsMin());
            consulta.setInt(5, tiemposRecursos.getDesm());
            consulta.setInt(6, tiemposRecursos.getInst_desm());
            
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex); 
      }
   }
   
   public void modificar(Connection conexion, TiemposYRecursosM tiemposRecursos) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  componente=?,numeroOper=?,inst=?,pzsMin=? ,desm=?,inst_desm=?  WHERE id_tiemposRecurso = ?");
            consulta.setString(1, tiemposRecursos.getComponente());
            consulta.setInt(2, tiemposRecursos.getNumOperaciones());
            consulta.setInt(3, tiemposRecursos.getInst());
            consulta.setFloat(4, tiemposRecursos.getPzsMin());
            consulta.setInt(5, tiemposRecursos.getDesm());
            consulta.setInt(6, tiemposRecursos.getInst_desm());
            consulta.setInt(7, tiemposRecursos.getId_tiemposRecursos());

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
            
            String sql= "SELECT COUNT(componente) FROM tiemposyrecursos WHERE componente=?";
            
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
   
   public List<TiemposYRecursosM> recuperarTodas(Connection conexion) throws SQLException{
      List<TiemposYRecursosM> tiemposRecursos = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id_tiemposRecurso,componente,numeroOper,inst,pzsMin ,desm,inst_desm FROM " + this.tabla +" ORDER BY componente ASC, numeroOper ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            tiemposRecursos.add(new TiemposYRecursosM( resultado.getInt("id_tiemposRecurso"),resultado.getString("componente"),resultado.getInt("numeroOper"),resultado.getInt("inst"), resultado.getFloat("pzsMin"),resultado.getInt("desm"),resultado.getInt("inst_desm")));  
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return tiemposRecursos;
   }
}
