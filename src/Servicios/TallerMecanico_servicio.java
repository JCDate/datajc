/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.TallerMecanicoFALLAM;
import Modelos.TallerMecanicoREPM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TallerMecanico_servicio {

   public List<TallerMecanicoFALLAM> recuperarTodasFalla(Connection conexion) throws SQLException{
      List<TallerMecanicoFALLAM> falla = new ArrayList<>();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT tallermecacicofalla.id,tallermecacicofalla.troquel,tallermecacicofalla.componente,fechaEntrada,descripcion FROM tallermecacicofalla, tallermecanicorep WHERE tallermecanicorep.id = tallermecacicofalla.id ORDER BY tallermecanicorep.reparadaP!='' DESC,troquel DESC");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            falla.add(new TallerMecanicoFALLAM(resultado.getInt("id"), resultado.getString("troquel"), resultado.getString("componente"), resultado.getString("fechaEntrada"), resultado.getString("descripcion")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return falla;
   }
   
    public void eliminarFalla(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        
        PreparedStatement ps = null;
        String consulta= "DELETE FROM tallermecacicofalla WHERE id= ?";
        ps = con.prepareStatement(consulta);
        ps .setInt(1, id);
        ps .executeUpdate();
    }
    public void eliminarRep(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();

        PreparedStatement ps2 = null;
        String consulta2= "DELETE FROM tallermecanicorep WHERE id_troquel= ?";
        ps2 = con.prepareStatement(consulta2);
        ps2 .setInt(1, id);
        ps2 .executeUpdate();
    }
   
   public List<TallerMecanicoREPM> recuperarTodasRep(Connection conexion) throws SQLException{
      List<TallerMecanicoREPM> reparacion = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT tallermecanicorep.id_troquel,tallermecanicorep.troquel,tallermecanicorep.componente,fechaEnt,reparadaP,turno,solucion,fabricada,reparada,eficaz FROM tallermecanicorep, tallermecacicofalla WHERE tallermecanicorep.id = tallermecacicofalla.id ORDER BY tallermecanicorep.reparadaP!='' DESC,troquel DESC ");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
             reparacion.add(new TallerMecanicoREPM(resultado.getInt("id_troquel"), resultado.getString("troquel"), resultado.getString("componente"), resultado.getString("fechaEnt"), resultado.getString("reparadaP"), resultado.getInt("turno"), resultado.getString("solucion"), resultado.getString("fabricada"), resultado.getString("reparada"), resultado.getString("eficaz")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return reparacion;
   } 
}
