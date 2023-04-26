/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.DatosaTecnicosM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatosTecnicos_servicio {
  private final String tabla = "datostecnicosmp";
     
   public void agregar(Connection conexion, DatosaTecnicosM DT) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente, dimHojaW,dimHojaL,pesoH_kg,dimRollo_p,pesoRollo_kgs,anchoTira_p) VALUES(?,?,?,?,?,?,?)");
            consulta.setString(1, DT.getComponente());
            consulta.setInt(2, DT.getDimHojaW());
            consulta.setInt(3, DT.getDimHojaL());
            consulta.setFloat(4, DT.getPesoH_kg());
            consulta.setFloat(5, DT.getDimRollo_p());
            consulta.setFloat(6, DT.getPesoRollo_kgs());
            consulta.setFloat(7, DT.getAnchoTira_p());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }  
   }
   
   public void modificar(Connection conexion,  DatosaTecnicosM DT) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  dimHojaW=?,dimHojaL=?,pesoH_kg=?,dimRollo_p=?,pesoRollo_kgs=?,anchoTira_p=?  WHERE componente = ?");           
            consulta.setInt(1, DT.getDimHojaW());
            consulta.setInt(2, DT.getDimHojaL());
            consulta.setFloat(3, DT.getPesoH_kg());
            consulta.setFloat(4, DT.getDimRollo_p());
            consulta.setFloat(5, DT.getPesoRollo_kgs());
            consulta.setFloat(6, DT.getAnchoTira_p());
            consulta.setString(7, DT.getComponente());

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
            
            String sql= "SELECT COUNT(componente) FROM datostecnicosmp WHERE componente=?";
            
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
   

   public List<DatosaTecnicosM> recuperarTodas(Connection conexion) throws SQLException{
      List<DatosaTecnicosM> DT = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT datostecnicosmp.componente, dimHojaW,dimHojaL,pesoH_kg,dimRollo_p,pesoRollo_kgs,anchoTira_p FROM datostecnicosmp,datoslistaprecios WHERE datostecnicosmp.componente=datoslistaprecios.componente ORDER by datostecnicosmp.componente" );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            DT.add(new DatosaTecnicosM( resultado.getString("componente"), resultado.getInt("dimHojaW"),resultado.getInt("dimHojaL"),resultado.getFloat("pesoH_kg"),resultado.getFloat("dimRollo_p"),resultado.getFloat("pesoRollo_kgs"),resultado.getFloat("anchoTira_p")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return DT;
   }
    
}
