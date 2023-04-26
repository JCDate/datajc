/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.MateriaPrimaRecM;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaRec_servicio {
    private final String tabla = "mprecibida";
    
    public void agregar(Connection conexion,  MateriaPrimaRecM MPR) throws SQLException {
       try{
         PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "( ordenCompra,calibre, proveedor,kgSolicitados)  VALUES(?,?,?,?)");
            consulta.setInt(1, MPR.getOrdenCompra());
            consulta.setString(2, MPR.getCalibre());
            consulta.setString(3, MPR.getProveedores());
            consulta.setFloat(4, MPR.getKgSolicitados());
            
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
       
   }
     public void modificar(Connection conexion, MateriaPrimaRecM mpr) throws SQLException {
       try{
         PreparedStatement consulta;
         
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET ordenCompra = ?,calibre=?, proveedor=?,kgSolicitados=?, kgRecibidos=?, factura=?, fechaRecibida=? WHERE id = ?");
            consulta.setInt(1, mpr.getOrdenCompra());
            consulta.setString(2, mpr.getCalibre());
            consulta.setString(3, mpr.getProveedores());
            consulta.setFloat(4, mpr.getKgSolicitados());
            consulta.setFloat(5, mpr.getKgRecibidos());
            consulta.setString(6, mpr.getFactura());
            consulta.setString(7, mpr.getFechaRecibida());
            consulta.setInt(8, mpr.getId());
       
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      } 
   }
    public void eliminar(int id) throws SQLException, ClassNotFoundException{
        Connection con= obtener();
        PreparedStatement ps = null;
        String consulta= "DELETE FROM mprecibida WHERE id= ?";
        ps = con.prepareStatement(consulta);
        ps .setInt(1, id);
        ps .executeUpdate();

    }

   public List<MateriaPrimaRecM> recuperarTodas(Connection conexion) throws SQLException{
      List<MateriaPrimaRecM> mpr = new ArrayList<>();
      try{
          
         PreparedStatement consulta = conexion.prepareStatement("SELECT id,ordenCompra,calibre, proveedor,kgSolicitados, kgRecibidos, factura, fechaRecibida FROM " + this.tabla );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            mpr.add(new MateriaPrimaRecM( resultado.getInt("id"), resultado.getInt("ordenCompra"), resultado.getString("calibre"),resultado.getString("proveedor"),resultado.getFloat("kgSolicitados"), resultado.getFloat("kgRecibidos"), resultado.getString("factura"), resultado.getString("fechaRecibida")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return mpr;
   }
}
