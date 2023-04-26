/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.ProveedoresM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Proveedores_servicio {

   private final String tabla = "proveedores";

   public void agregar(Connection conexion, ProveedoresM prov) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "( nombre,colonia,parque,codigoPostal,ciudad,estado,pais,calibre ) VALUES( ?,?,?,?,?,?,?,?)");
            
            consulta.setString(1, prov.getNombre());
            consulta.setString(2, prov.getColonia());
            consulta.setString(3, prov.getParque());
            consulta.setInt(4, prov.getCodigoPostal());
            consulta.setString(5, prov.getCiudad());
            consulta.setString(6, prov.getEstado());
            consulta.setString(7, prov.getPais());
            consulta.setString(8, prov.getCalibre());
   
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
   
   public void modificar(Connection conexion,  ProveedoresM prov) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  nombre=?,colonia=?,parque=?, codigoPostal=?, ciudad=?,estado=?,pais=?, calibre=? WHERE id_prov = ?");
            consulta.setString(1, prov.getNombre());
            consulta.setString(2, prov.getColonia());
            consulta.setString(3, prov.getParque());
            consulta.setInt(4, prov.getCodigoPostal());
            consulta.setString(5, prov.getCiudad());
            consulta.setString(6, prov.getEstado());
            consulta.setString(7, prov.getPais());
            consulta.setString(8, prov.getCalibre());
            consulta.setInt(9, prov.getId_prov());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }

   public List<ProveedoresM> recuperarTodas(Connection conexion) throws SQLException{
      List<ProveedoresM> prov = new ArrayList<>();
      try{
        
        PreparedStatement consulta = conexion.prepareStatement("SELECT  id_prov, nombre,colonia, parque,codigoPostal, ciudad,estado,pais,calibre FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            prov.add(new ProveedoresM( resultado.getInt("id_prov"),resultado.getString("nombre"), resultado.getString("colonia"),resultado.getString("parque"),resultado.getInt("codigoPostal"),resultado.getString("ciudad"),resultado.getString("estado"),resultado.getString("pais"),resultado.getString("calibre")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return prov;
   }
    
}
