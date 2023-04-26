/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.ConsumoYAntecedetesM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConsumoyAntecedentes_servicio {
  private final String tabla = "consumoyantecedentes";
     
   public void agregarCA(Connection conexion, ConsumoYAntecedetesM CA) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente_CA, consumo_uni, desMatePrima, anchoTira,PpapStatus,comentario_CA, tipo ) VALUES(?, ?,?,?,?,?,?)");
            consulta.setString(1, CA.getComponente_CA());
            consulta.setFloat(2, CA.getConsumo_uni());
            consulta.setString(3, CA.getDesMatePrima());
            consulta.setFloat(4, CA.getAnchoTira());
            consulta.setString(5, CA.getPpapStatus());
            consulta.setString(6, CA.getComentario_CA());
            consulta.setString(7, CA.getTipo());
            
         
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
     public void modificar(Connection conexion, ConsumoYAntecedetesM CA) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  consumo_uni=?, desMatePrima=?, anchoTira=?,PpapStatus=?,comentario_CA=?, tipo=?  WHERE componente_CA = ?");
            consulta.setFloat(1, CA.getConsumo_uni());
            consulta.setString(2, CA.getDesMatePrima());
            consulta.setFloat(3, CA.getAnchoTira());
            consulta.setString(4, CA.getPpapStatus());
            consulta.setString(5, CA.getComentario_CA());
            consulta.setString(6, CA.getTipo());
            consulta.setString(7, CA.getComponente_CA());
            
         
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
            
            String sql= "SELECT COUNT(componente_CA) FROM consumoyantecedentes WHERE componente_CA=?";
            
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
   
   public List<ConsumoYAntecedetesM> recuperarTodas(Connection conexion) throws SQLException{
      List<ConsumoYAntecedetesM> CA = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  componente_CA, consumo_uni, desMatePrima, anchoTira,pesoPzs,PpapStatus,comentario_CA, tipo FROM " + this.tabla+" ORDER BY componente_CA ASC" );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            CA.add(new ConsumoYAntecedetesM( resultado.getString("componente_CA"), resultado.getFloat("consumo_uni"),resultado.getString("desMatePrima"),resultado.getFloat("anchoTira"),resultado.getFloat("pesoPzs"), resultado.getString("PpapStatus"),resultado.getString("comentario_CA"), resultado.getString("tipo")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return CA;
   }
    
}
