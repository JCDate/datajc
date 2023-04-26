 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculo;

import Modelos.CalculosTeoricosM;
import Modelos.DatosListaPreciosM;
import Modelos.DatosaTecnicosM;
import Modelos.Usuarios;
import Servicios.CalculosTeoricos_servicio;
import Servicios.Conexion;
import Servicios.DatosListaPrecios_servicio;
import Servicios.DatosTecnicos_servicio;
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


public class CalculoGUI extends javax.swing.JFrame {
    private final DatosListaPrecios_servicio DLP_servicio = new DatosListaPrecios_servicio();
    private List<DatosListaPreciosM> DLP;
    private final DatosTecnicos_servicio DT_servicio = new DatosTecnicos_servicio();
    private List<DatosaTecnicosM> DT;
    private final CalculosTeoricos_servicio CT_servicio = new CalculosTeoricos_servicio();
    private List<CalculosTeoricosM> CT;
    
    private final toExcel excel= new toExcel();
    TableRowSorter trs;
    TableRowSorter trs2;
    TableRowSorter trs3;
    Usuarios mod;
   

    public CalculoGUI() {
        initComponents();
        this.setResizable(false);
        this.CalculoGUI();
        this.setDefaultCloseOperation(0);
        jButton4.setVisible(false);
        
         //Boton Exportar Calculo
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
        //jButton5.setContentAreaFilled(false);
        
    }
    public CalculoGUI(Usuarios mod){
        initComponents();
        this.setDefaultCloseOperation(0);
        this.mod = mod;
        this.setResizable(false);
        this.CalculoGUI();
        jButton4.setVisible(false);
        
         //Boton Exportar Calculo
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
        //jButton5.setContentAreaFilled(false);
       
        
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
            jButton4.setEnabled(false);
            jButton1.setEnabled(false);           
        }
    }
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    private void CalculoGUI(){
        try{
            //Tabla Datos de la lista de precios
            this.DLP = this.DLP_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.DLP.size(); i++){
                dtm.addRow(new Object[]{
                    this.DLP.get(i).getComponente(),
                    this.DLP.get(i).getCr(), 
                    this.DLP.get(i).getCalibre(),
                    this.DLP.get(i).getBlank_kg()
                });
            }
            
            //Tabla datos tecnicos de M.P.
            this.DT = this.DT_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm2 = (DefaultTableModel) jTable2.getModel();
            dtm2.setRowCount(0);
            for(int i = 0; i < this.DT.size(); i++){
                dtm2.addRow(new Object[]{
                    this.DT.get(i).getComponente(),
                    this.DT.get(i).getDimHojaW(), 
                    this.DT.get(i).getDimHojaL(),
                    this.DT.get(i).getPesoH_kg(),
                    this.DT.get(i).getDimRollo_p(),
                    this.DT.get(i).getPesoRollo_kgs(),
                    this.DT.get(i).getAnchoTira_p()
                });
            }

             //Tabla calculos Teoricos
            this.CT = this.CT_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm3 = (DefaultTableModel) jTable3.getModel();
            dtm3.setRowCount(0);
            for(int i = 0; i < this.CT.size(); i++){
                dtm3.addRow(new Object[]{
                    this.CT.get(i).getComponente(),
                    this.CT.get(i).getPzas_tira(), 
                    this.CT.get(i).getPzas_tiraEnteros(),
                    this.CT.get(i).getTiras_hojas(),
                    this.CT.get(i).getTira_hojasEntero(),
                    this.CT.get(i).getPzas_HojasEntero(),
                    this.CT.get(i).getPeso_pzasCal(),
                    this.CT.get(i).getPeso_pzasLP()
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
        int ColumnaTabla = 0;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla));
    }
    public void Filtro2(){
       int ColumnaTabla = 0;
      trs2.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla));
   }
    public void Filtro3(){
      int ColumnaTabla = 0;
     trs3.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla));
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        jButton1.setText("AGREGAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 150, 40));

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel3.setText("DATOS DE LA LISTA DE PRECIOS");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, -1, -1));

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
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 100, 220, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 100, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 639, 120, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 150, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "COMPONENTE", "C/R", "CALIBRE", "KG/BLANK"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 480, 230));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "COMPONENTE", "DIMS W", "DIMS L", "PESO/HOJA (KG)", "<html><center>DIM ROLLO<br> (PULGADAS)</center></html>", "PESO/ROLLO (KG)", "<html>ANCHO TIRA<br> (PULGADAS)</html>"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 750, 230));

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel4.setText("DATOS TECNICOS DE M. P.");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 140, -1, -1));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "COMPONENTE", "PZAS/TIRA", "PZAS/TIRA (ENTERO)", "TIRAS/HOJAS", "<html>TIRAS/HOJAS<br> (ENTERO)</html>", "<html>PZAS/HOJAS <br>(ENTERO)</html>", "<html><center>PESO/PZAS <br>CALCULADO</center></html>", "PESO/PZS (L DE P)"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 1100, 230));

        jLabel6.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        jLabel6.setText("CALCULOS TEORICOS");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 410, -1, -1));

        jLabel7.setFont(new java.awt.Font("Wide Latin", 1, 36)); // NOI18N
        jLabel7.setText("CALCULO");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 400, 50));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("MODIFICAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 150, 40));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 0, 60, 60));

        jLabel8.setText("EXPORTAR");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 60, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CalculoGUI.this.dispose();
        AgregarCalculo vista;
        try {
            vista = new AgregarCalculo(mod);
            vista.setVisible(true);
            vista.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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

        //Tabla datos de la lista de precios
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        
        jtxtfiltro.addKeyListener(new KeyAdapter(){
           //@Override
           public void keyReleased(final KeyEvent ke){
               String cadena= (jtxtfiltro.getText());
               jtxtfiltro.setText(cadena);
               Filtro2(); 
           }
        });
        
        //Tabla datos tecnicos de M. P.
        trs2 = new TableRowSorter(jTable2.getModel());
        jTable2.setRowSorter(trs2);
        
        jtxtfiltro.addKeyListener(new KeyAdapter(){
           //@Override
           public void keyReleased(final KeyEvent ke){
               String cadena= (jtxtfiltro.getText());
               jtxtfiltro.setText(cadena);
               Filtro3(); 
           }
        });
        
        //Tabla caluclos teoricos
        trs3 = new TableRowSorter(jTable3.getModel());
        jTable3.setRowSorter(trs3);
        
        jButton2.setVisible(false);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();

        if(fila_seleccionada >= 0){
            try {
                CalculoGUI.this.dispose();
                ModificarCalculo modificar = new ModificarCalculo(this.DLP.get(fila_seleccionada), this.DT.get(fila_seleccionada), this.CT.get(fila_seleccionada),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());

        if(fila_seleccionada >= 0){
            try {
                CalculoGUI.this.dispose();
                ModificarCalculo modificar = new ModificarCalculo(this.DLP.get(fila_seleccionada), this.DT.get(fila_seleccionada), this.CT.get(fila_seleccionada), mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CalculoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        excel.WriteExcelCalculo();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_jButton5ActionPerformed

    
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
            java.util.logging.Logger.getLogger(CalculoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculoGUI().setVisible(true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
