/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.PronosticoM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Pronostico_servicio {
  private final String tabla = "pronostico";
     
  
   
  public List<PronosticoM> recuperarTodas(Connection conexion) throws SQLException{
      List<PronosticoM> prono = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prono.add(new PronosticoM( resultado.getString("supplier"), resultado.getString("componente"), resultado.getString("mes1"), resultado.getString("mes2"), resultado.getString("mes3"), resultado.getString("mes4"),resultado.getString("calibre"), resultado.getString("consumoU"), resultado.getFloat("totalkg"), resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prono;
   }
  
  public List<PronosticoM> recuperarMes1(Connection conexion) throws SQLException{
      List<PronosticoM> prono = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla +" WHERE mes1!='' " );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prono.add(new PronosticoM( resultado.getString("supplier"), resultado.getString("componente"), resultado.getString("mes1"), resultado.getString("mes2"), resultado.getString("mes3"), resultado.getString("mes4"),resultado.getString("calibre"), resultado.getString("consumoU"), resultado.getFloat("totalkg"), resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prono;
   }
  
  public List<PronosticoM> recuperarMes2(Connection conexion) throws SQLException{
      List<PronosticoM> prono = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla +" WHERE mes2!='' " );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prono.add(new PronosticoM( resultado.getString("supplier"), resultado.getString("componente"), resultado.getString("mes1"), resultado.getString("mes2"), resultado.getString("mes3"), resultado.getString("mes4"),resultado.getString("calibre"), resultado.getString("consumoU"), resultado.getFloat("totalkg"), resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prono;
   }
    public List<PronosticoM> recuperarMes3(Connection conexion) throws SQLException{
      List<PronosticoM> prono = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla +" WHERE mes3!='' " );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prono.add(new PronosticoM( resultado.getString("supplier"), resultado.getString("componente"), resultado.getString("mes1"), resultado.getString("mes2"), resultado.getString("mes3"), resultado.getString("mes4"),resultado.getString("calibre"), resultado.getString("consumoU"), resultado.getFloat("totalkg"),  resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prono;
   }
    public List<PronosticoM> recuperarMes4(Connection conexion) throws SQLException{
      List<PronosticoM> prono = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  * FROM " + this.tabla +" WHERE mes4!='' " );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prono.add(new PronosticoM( resultado.getString("supplier"), resultado.getString("componente"), resultado.getString("mes1"), resultado.getString("mes2"), resultado.getString("mes3"), resultado.getString("mes4"),resultado.getString("calibre"), resultado.getString("consumoU"), resultado.getFloat("totalkg"), resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prono;
   }
    
}
