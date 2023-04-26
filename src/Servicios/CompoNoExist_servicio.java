/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.ComponenteNoExitsM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompoNoExist_servicio {

   public List<ComponenteNoExitsM> recuperarTodas(Connection conexion) throws SQLException{
      List<ComponenteNoExitsM> ComponenteNoExits= new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("select orden, item_cliente from analisisatrasos A Where Not exists (select componente from tiemposyrecursos B Where A.item_cliente = B.componente)");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            ComponenteNoExits.add(new ComponenteNoExitsM( resultado.getString("orden"),resultado.getString("item_cliente")));
         }

      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return ComponenteNoExits;
   }
}
