/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.PrecioMP;
import static Servicios.Conexion.obtener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrecioMP_servicio {

    private final String tabla = "preciomp";

    public void agregar(Connection conexion, PrecioMP precioMP) throws SQLException {
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "( calibre, pesoUnitario, moneda ) VALUES( ?,?,?)");
            consulta.setString(1, precioMP.getCalibre());
            consulta.setFloat(2, precioMP.getPesoUnitario());
            consulta.setString(3, precioMP.getMoneda());

            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void modificar(Connection conexion, PrecioMP precioMP, PrecioMP precioMP2) throws SQLException {
        try {
            PreparedStatement consulta;
            java.sql.Statement st = conexion.createStatement();
            Float p2min = precioMP2.getPesoUnitario(); 
            String sqlId = "SELECT idCalibre FROM preciomp WHERE calibre=? AND pesoUnitario BETWEEN ? AND ? AND moneda=?";
            PreparedStatement statement = conexion.prepareStatement(sqlId);
            statement.setString(1, precioMP2.getCalibre());
            statement.setFloat(2,precioMP2.getPesoUnitario()-100);
            statement.setFloat(3,precioMP2.getPesoUnitario()+100);
            statement.setString(4, precioMP2.getMoneda());
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()) {
                    id = rs.getInt("idCalibre");
            }
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET pesoUnitario=?, moneda=? WHERE idCalibre = ?");
            consulta.setFloat(1, precioMP.getPesoUnitario());
            consulta.setString(2, precioMP.getMoneda());
            consulta.setInt(3, id);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public int existeCalibre(String usuarios) throws ClassNotFoundException {
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = obtener();

            String sql = "SELECT COUNT(calibre) FROM preciomp WHERE calibre=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, usuarios);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public List<PrecioMP> recuperarTodas(Connection conexion) throws SQLException {
        List<PrecioMP> precioMP = new ArrayList<>();
        try {

            PreparedStatement consulta = conexion.prepareStatement("SELECT  calibre, pesoUnitario, moneda FROM " + this.tabla);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                precioMP.add(new PrecioMP(resultado.getString("calibre"), resultado.getFloat("pesoUnitario"), resultado.getString("moneda")));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return precioMP;
    }
}
