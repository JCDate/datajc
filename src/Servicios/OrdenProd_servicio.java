/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.OrdenesProdM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrdenProd_servicio {
  private final String tabla = "atrasosproduccion";

   public List<OrdenesProdM> recuperarTodas(Connection conexion) throws SQLException{
      List<OrdenesProdM> op = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  orden, componente, reparacion, troquel FROM " + this.tabla +" WHERE reparacion='MAYOR' ORDER BY componente ASC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            op.add(new OrdenesProdM( resultado.getString("orden"), resultado.getString("componente"), resultado.getString("reparacion"), resultado.getString("troquel")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return op;
   }
    
}
