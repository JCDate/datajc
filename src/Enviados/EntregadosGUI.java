/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enviados;

import OrdenesSolicitadas.OrdenesSolicitadasGUI;
import Modelos.EntregadosM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.Entregados_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class EntregadosGUI extends javax.swing.JFrame {
    private final Entregados_servicio entregados_servicio = new Entregados_servicio();
    private List<EntregadosM> entregados;
    
    private final toExcel excel= new toExcel();
    TableRowSorter trs;
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    
    public EntregadosGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.analisisAtrasos();
        
        jButton4.setVisible(false);
        
         //Boton Exportar Entregados
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);
        

    }
    
    public EntregadosGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.analisisAtrasos();
        
        jButton4.setVisible(false);
        
        //Boton Exportar Entregados
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);
       
        
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
            jButton4.setEnabled(false);          
        }
    }
    
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));
        return retValue;
    }

    private void analisisAtrasos() {
        try{
            this.entregados = this.entregados_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.entregados.size(); i++){
                dtm.addRow(new Object[]{
                    this.entregados.get(i).getId(),
                    this.entregados.get(i).getFechaRecibida(),
                    this.entregados.get(i).getFechaVencida(),
                    this.entregados.get(i).getOrden(),
                    this.entregados.get(i).getCantidad_reque(),
                    this.entregados.get(i).getCantidadEntregada(),
                    this.entregados.get(i).getPiezasEntregar(),
                    this.entregados.get(i).getConsignatario(),
                    this.entregados.get(i).getItem_cliente(),
                    this.entregados.get(i).getComentario(),
                    this.entregados.get(i).getFechaEmbarque(),
                    this.entregados.get(i).getFactura(),
                    this.entregados.get(i).getEmbarques()
                });
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }
    }
    
    public void Filtro(){
        int ColumnaTabla = 3;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(),ColumnaTabla,8,10));
    }

    /**to
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 25)); // NOI18N
        jLabel2.setText("ENTREGADOS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 370, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 140, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/actualizar.png"))); // NOI18N
        jButton2.setText("ACTUALIZAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 30));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 120, 40));

        jtxtfiltro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxtfiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtfiltroActionPerformed(evt);
            }
        });
        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 140, 240, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("TOTAL DE PZS ENTREGADAS:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 600, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 600, 190, 20));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("ACTUALIZAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "<html><center>FECHA<br> RECIBIDA<html>", "<html><center>FECHA<br> VENCIDA</html>", "ORDEN", "<html><center>CANT.<br> SOLICITADA</html>", "<html><center>CANT.<br> ENTREGADA<html>", "<html><center>CANT.<br> A ENTREGAR</html>", "CONSIGNATARIO", "COMPONENTE", "COMENTARIO", "<html><center>FECHA <br>EMBARQUE</html>", "FACTURA", "EMBARQUES"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 170, 1240, 400));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/analisis.png"))); // NOI18N
        jButton5.setText("<html><center>ENVIAR A <br>ORDENES SOLICITADAS</center></html>");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 580, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, 70, 60));

        jLabel7.setText("EXPORTAR");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 70, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1280, 680));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/analisis.png"))); // NOI18N
        jMenu1.setText("ORDENES SOLICITADAS");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
       
        if(fila_seleccionada >= 0 ){
            EntregadosGUI.this.dispose();
            ModificarEntregados modificar = new ModificarEntregados(this.entregados.get(fila_seleccionada),mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

           
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final java.awt.event.KeyEvent ke){
                try {
                    Statement comando=cn.createStatement();
                    jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
                    String cadena= (jtxtfiltro.getText());
                    jtxtfiltro.setText(cadena);

                    //TOTAL DE ATRASOS
                    ResultSet registro = comando.executeQuery("SELECT SUM(cantidadEntregada) AS sumPzs FROM entregados WHERE fechaEmbarque='"+cadena+"'");
                    
                    if(registro.next()==true) {
                        jLabel6.setText(registro.getString("sumPzs"));
                    }
                    Filtro();
                } catch (SQLException ex) {
                    Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
         
        jButton4.setVisible(true);
        jButton2.setVisible(false);
        
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
        if(fila_seleccionada >= 0 ){
            EntregadosGUI.this.dispose();
            ModificarEntregados modificar = new ModificarEntregados(this.entregados.get(fila_seleccionada),mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
        int fila_seleccionada = jTable1.getSelectedRow();
            if(fila_seleccionada != -1){
             
                int id = (int) jTable1.getValueAt(fila_seleccionada, 0);
                String ordenEnv = (String) jTable1.getValueAt(fila_seleccionada, 3);

                //copiar datos de la fila seleccionada  entregados A analisis de atrasos
                PreparedStatement pst = cn.prepareStatement("INSERT INTO analisisatrasos (analisisatrasos.fechaRecibida,analisisatrasos.fechaVencida, analisisatrasos.orden, analisisatrasos.cantidad_reque, analisisatrasos.cantidadEntregada, analisisatrasos.piezasEntregar, analisisatrasos.consignatario, analisisatrasos.item_cliente,analisisatrasos.comentario,analisisatrasos.fechaEmbarque,analisisatrasos.factura, analisisatrasos.embarques) SELECT entregados.fechaRecibida,entregados.fechaVencida, entregados.orden, entregados.cantidad_reque, entregados.CantidadEntregada, entregados.piezasEntregar, entregados.consignatario, entregados.item_cliente, entregados.comentario, entregados.fechaEmbarque, entregados.factura, entregados.embarques FROM entregados WHERE entregados.id='"+id+"'");
                pst.executeUpdate();

                PreparedStatement pst2 = cn.prepareStatement("UPDATE analisisatrasos SET prioridad ='0' WHERE orden='"+ordenEnv+"'");
                pst2.executeUpdate();

                //Eliminar el dato Entregados
                PreparedStatement pst3 = cn.prepareStatement("DELETE FROM entregados WHERE id='"+id+"'");
                pst3.executeUpdate();

                EntregadosGUI.this.dispose();
                
                JOptionPane.showMessageDialog(this, "Orden Enviada a ANALISIS DE ATRASOS");
                 
                EntregadosGUI mp = new EntregadosGUI(mod);
                mp.setVisible(true);
                mp.setLocationRelativeTo(null);
        
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        excel.WriteEntregados();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        try {
            EntregadosGUI.this.dispose();
            OrdenesSolicitadasGUI analisisatrasos = new OrdenesSolicitadasGUI(mod);
            analisisatrasos.setVisible(true);
            analisisatrasos.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EntregadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EntregadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EntregadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EntregadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EntregadosGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(EntregadosGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables

}