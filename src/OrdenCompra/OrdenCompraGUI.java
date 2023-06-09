/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenCompra;

import Modelos.OrdenCompraM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.OrdenCompra_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class OrdenCompraGUI extends javax.swing.JFrame {
    private final OrdenCompra_servicio ordenCompra_servicio = new OrdenCompra_servicio();
    private List<OrdenCompraM> ordenCom;
    
    private final toExcel excel= new toExcel();
    Usuarios mod;
    TableRowSorter trs;
  
    public OrdenCompraGUI() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.OrdenCompraGUI();
        
        //Boton Exportar Orden Compra
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);
        

        
    }
    public OrdenCompraGUI(Usuarios mod){
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.OrdenCompraGUI();
        
        //Boton Exportar Orden Compra
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);

        
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
                      
        }
    } 
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    private void OrdenCompraGUI() {
        try{
            this.ordenCom = this.ordenCompra_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.ordenCom.size(); i++){
                dtm.addRow(new Object[]{
                    this.ordenCom.get(i).getFechaV(),
                    this.ordenCom.get(i).getOrden(),
                    this.ordenCom.get(i).getComponente(),
                    this.ordenCom.get(i).getCR(),
                    this.ordenCom.get(i).getCantidad(),
                    this.ordenCom.get(i).getCalibre(),
                    this.ordenCom.get(i).getConsumo_kg(),
                    this.ordenCom.get(i).getNo_oper(), 
                    this.ordenCom.get(i).getNo_golpes()
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
        int ColumnaTabla = 1;
        int ColumnaTabla2 = 2;
        int ColumnaTabla3 = 5;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla, ColumnaTabla2,ColumnaTabla3 ));
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
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 36)); // NOI18N
        jLabel2.setText("ORDEN DE COMPRA");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 800, 50));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 40));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 120, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "<html><center>FECHA <br>VENCIMIENTO</html>", "ORDEN", "COMPONENTE", "C/R", "CANTIDAD", "CALIBRE", "CONSUMO (KGS)", "NO. OPERADOR", "NO. GOLPES"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 1250, 420));

        jtxtfiltro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtfiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtfiltroActionPerformed(evt);
            }
        });
        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 160, 230, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 160, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 0, 60, 60));

        jLabel3.setText("EXPORTAR");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 60, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
         
        if(fila_seleccionada >= 0){
             try {
                 OrdenCompraGUI.this.dispose();
                 ModificarOrdenCompra modificar = new ModificarOrdenCompra(this.ordenCom.get(fila_seleccionada),mod);
                 modificar.setVisible(true);
                 modificar.setLocationRelativeTo(null);
             } catch (SQLException ex) {
                 Logger.getLogger(OrdenCompraGUI.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(OrdenCompraGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

           
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed

    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jtxtfiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyReleased

    }//GEN-LAST:event_jtxtfiltroKeyReleased

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                String cadena= (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);
                Filtro();
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        jButton2.setVisible(false);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
        if(fila_seleccionada >= 0){
             try {
                 OrdenCompraGUI.this.dispose();
                 ModificarOrdenCompra modificar = new ModificarOrdenCompra(this.ordenCom.get(fila_seleccionada),mod);
                 modificar.setVisible(true);
                 modificar.setLocationRelativeTo(null);
             } catch (SQLException ex) {
                 Logger.getLogger(OrdenCompraGUI.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(OrdenCompraGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        excel.WriteExcelOrdenCompra();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(OrdenCompraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenCompraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenCompraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenCompraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrdenCompraGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables

}