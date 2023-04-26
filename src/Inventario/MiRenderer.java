/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
public class MiRenderer extends DefaultTableCellRenderer {
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus, 
                                                   int row, 
                                                   int column) {
 
        float numero = (float) table.getValueAt(row, 6);
 
        if (numero >= 1000) {
            Color colorVerde=new Color(46, 204, 113);
            setBackground(colorVerde);
            setForeground(Color.BLACK);
        }else if(numero  >= 1 && numero <1000) {
            Color colorVerde=new Color(243, 240, 32);
            setBackground(colorVerde);
            setForeground(Color.BLACK);
        }else {
            Color colorRojo=new Color(231, 76, 60);
            setBackground(colorRojo);
            setForeground(Color.BLACK);
        }
 
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
}