/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.MateriaPrimaM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MateriaPrima_servicio {
  private final String tabla = "materia_prima";
     
   public void agregar(Connection conexion, MateriaPrimaM materiaprima) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(calibre, consumoR, inventarioF, inventarioA,solicitudTransito,kgSolicitar, aprobacion, calibre_orden, calibre_proveedor ) VALUES(?,?, ?,?,?,?,?,?,?)");
            consulta.setString(1, materiaprima.getCalibre());
            consulta.setFloat(2, materiaprima.getConsumoR());
            consulta.setFloat(3, materiaprima.getInventarioF());
            consulta.setFloat(4, materiaprima.getInventarioA());
            consulta.setInt(5, materiaprima.getSolicitudTransito());
            consulta.setFloat(6, materiaprima.getKgSolicitar());
            consulta.setString(7, materiaprima.getAprobacion());
            consulta.setString(8, materiaprima.getCalibre_orden());
            consulta.setString(9, materiaprima.getCalibre_proveedor());
            
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   
    public void modificar(Connection conexion, MateriaPrimaM materiaprima) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET  consumoR=?,inventarioF=?,inventarioA=?,solicitudTransito=?,kgSolicitar=?,aprobacion=?, calibre_orden=?,calibre_proveedor=?  WHERE calibre = ?");
            consulta.setFloat(1, materiaprima.getConsumoR());
            consulta.setFloat(2, materiaprima.getInventarioF());
            consulta.setFloat(3, materiaprima.getInventarioA());
            consulta.setInt(4, materiaprima.getSolicitudTransito());
            consulta.setFloat(5, materiaprima.getKgSolicitar());
            consulta.setString(6, materiaprima.getAprobacion());
            consulta.setString(7, materiaprima.getCalibre_orden());
            consulta.setString(8, materiaprima.getCalibre_proveedor());
            consulta.setString(9, materiaprima.getCalibre());

         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
    public void eliminar(String calibre) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM materia_prima WHERE calibre= ?";
        ps = con.prepareStatement(consulta);
        ps .setString(1, calibre);
        ps .executeUpdate();
    }
    
    public int existeCalibre(String usuarios) throws ClassNotFoundException {
        try{
            PreparedStatement ps = null;
            ResultSet rs= null;
            Connection con= obtener();
            
            String sql= "SELECT COUNT(calibre) FROM materia_prima WHERE calibre=?";
            
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

   public List<MateriaPrimaM> recuperarTodas(Connection conexion) throws SQLException{
      List<MateriaPrimaM> mp = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT  calibre, consumoR, inventarioF, inventarioA,solicitudTransito,kgSolicitar, aprobacion, calibre_orden, calibre_proveedor FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            mp.add(new MateriaPrimaM( resultado.getString("calibre"), resultado.getFloat("consumoR"),resultado.getFloat("inventarioF"),resultado.getFloat("inventarioA"),resultado.getInt("solicitudTransito"),resultado.getFloat("kgSolicitar"),resultado.getString("aprobacion"), resultado.getString("calibre_orden"), resultado.getString("calibre_proveedor")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return mp;
   }
}
