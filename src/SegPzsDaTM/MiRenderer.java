/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegPzsDaTM;
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
 
        String numero = (String) table.getValueAt(row, 13);
 
        if (!numero.equals("")) {
            Color colorVerde=new Color(46, 204, 113);
            setBackground(colorVerde);
            setForeground(Color.BLACK);
        }else {
            Color colorVerde=new Color(242, 243, 244);
            setBackground(colorVerde);
            setForeground(Color.BLACK);
        }
 
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
}