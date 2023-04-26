/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelos.Troqueles;

public class troqueles_servicio {
   private final String tabla = "troqueles";

   public void agregar(Connection conexion, Troqueles troqueles) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(componente,numOperaciones,caja,troquel ,opciones, maquinaOp ) VALUES( ?, ?, ?, ?, ?, ?)");
            consulta.setString(1, troqueles.getComponente());
            consulta.setInt(2, troqueles.getNumOperaciones());
            consulta.setString(3, troqueles.getCaja());
            consulta.setString(4, troqueles.getTroquel());
            consulta.setString(5, troqueles.getOpciones());
            consulta.setString(6, troqueles.getMaquinaOp());

         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }
   
   public void modificar(Connection conexion, Troqueles troqueles) throws SQLException{
      try{
        
         PreparedStatement consulta;  
            
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  componente=?, numOperaciones=?, caja=?, troquel = ?, opciones=?, maquinaOp=?  WHERE id_troquel = ?");
            consulta.setString(1, troqueles.getComponente());
            consulta.setInt(2, troqueles.getNumOperaciones());
            consulta.setString(3, troqueles.getCaja());
            consulta.setString(4, troqueles.getTroquel());
            consulta.setString(5, troqueles.getOpciones());
            consulta.setString(6, troqueles.getMaquinaOp());
            consulta.setInt(7, troqueles.getId_troquel());
         
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
         
      }
   }

   
   public List<Troqueles> recuperarTodas(Connection conexion) throws SQLException{
      List<Troqueles> troquel = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id_troquel,componente,numOperaciones,caja,troquel ,opciones, maquinaOp FROM " + this.tabla +" ORDER BY componente ASC, numOperaciones ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            troquel.add(new Troqueles( resultado.getInt("id_troquel"),resultado.getString("componente"),resultado.getInt("numOperaciones"),resultado.getString("caja"), resultado.getString("troquel"),resultado.getString("opciones"),resultado.getString("maquinaOp")));
         }

      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return troquel;
   }
   
   public List<Troqueles> recuperarTodasDef(Connection conexion, String componente) throws SQLException{
      List<Troqueles> troquel = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT id_troquel,componente,numOperaciones,caja,troquel ,opciones, maquinaOp FROM " + this.tabla +" WHERE componente='"+componente+"' ORDER BY componente ASC, numOperaciones ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            troquel.add(new Troqueles( resultado.getInt("id_troquel"),resultado.getString("componente"),resultado.getInt("numOperaciones"),resultado.getString("caja"), resultado.getString("troquel"),resultado.getString("opciones"),resultado.getString("maquinaOp")));
         }

      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return troquel;
   }
}
