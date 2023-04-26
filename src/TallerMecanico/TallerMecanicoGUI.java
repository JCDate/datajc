/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TallerMecanico;

import Modelos.TallerMecanicoFALLAM;
import Modelos.TallerMecanicoREPM;
import Modelos.Usuarios;
import SegPzsDaTM.CompProdMayorGUI;
import Servicios.Conexion;
import Servicios.TallerMecanico_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
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

/**
 *
 * @author JC
 */
public class TallerMecanicoGUI extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    TableRowSorter trs;
    TableRowSorter trs2;
    
    private final TallerMecanico_servicio tallerMecanico_servicio = new TallerMecanico_servicio();
    private List<TallerMecanicoFALLAM> falla;
    private List<TallerMecanicoREPM> reparacion;
    
    private final toExcel excel = new toExcel();
    

    public TallerMecanicoGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.FallaRepGUI();
        
    }
    
    public TallerMecanicoGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.FallaRepGUI();       

    }
    
    private void FallaRepGUI() throws SQLException, ClassNotFoundException {
       try{
            Statement comando=cn.createStatement();
            this.falla = this.tallerMecanico_servicio.recuperarTodasFalla(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTableFalla.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.falla.size(); i++){
                dtm.addRow(new Object[]{
                    this.falla.get(i).getId(),
                    this.falla.get(i).getTroquel(),
                    this.falla.get(i).getComponente(),
                    this.falla.get(i).getFechaEntrada(),
                    this.falla.get(i).getDescripcion()
                }); 
            }
            
            this.reparacion = this.tallerMecanico_servicio.recuperarTodasRep(Conexion.obtener());
            DefaultTableModel dtm2 = (DefaultTableModel) jTableReparacion.getModel();
            dtm2.setRowCount(0);
            for(int i = 0; i < this.reparacion.size(); i++){
                dtm2.addRow(new Object[]{
                    this.reparacion.get(i).getId(),
                    this.reparacion.get(i).getTroquel(),
                    this.reparacion.get(i).getComponente(),
                    this.reparacion.get(i).getFechaEntregada(),
                    this.reparacion.get(i).getReparadaP(),
                    this.reparacion.get(i).getTurno(),
                    this.reparacion.get(i).getSolucion(),
                    this.reparacion.get(i).getFabricada(),
                    this.reparacion.get(i).getReparada(),
                    this.reparacion.get(i).getEficaz()
                }); 
            }
            
        //TOTAL DE LINEAS
            ResultSet registroL = comando.executeQuery("SELECT COUNT(*) AS cont FROM tallermecanicorep WHERE solucion IS NOT NULL");
            if(registroL.next()==true) {
                jLabel10.setText(registroL.getString("cont"));                
            }
  
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }  
    }
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    public void Filtro(){
        int ColumnaTabla = 0;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla,1));
    }
    
    public void Filtro2(){
       int ColumnaTabla = 0;
      trs2.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla,1,2,3));
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFalla = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableReparacion = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setText("TALLER MECANICO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CERRAR");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 670, 120, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 100, 210, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 110, -1, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("REPARADAS:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 660, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 660, -1, -1));

        jTableFalla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "TROQUEL", "COMPONENTE", "FECHA ENTRADA", "DESCRIPCIÓN"
            }
        ));
        jTableFalla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTableFalla);
        if (jTableFalla.getColumnModel().getColumnCount() > 0) {
            jTableFalla.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableFalla.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableFalla.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTableFalla.getColumnModel().getColumn(4).setPreferredWidth(860);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 1230, 240));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("FALLA");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, -1, -1));

        jTableReparacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "TROQUEL", "COMPONENTE", "<html><center>FECHA <br>ENTREGADA</center></html>", "REPARADA POR", "TURNO", "SOLUCIÓN", "FAB.", "REP.", "<html><center>AJUSTE <br>EFICAZ A LA 1a.?</center></html>"
            }
        ));
        jTableReparacion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableReparacion);
        if (jTableReparacion.getColumnModel().getColumnCount() > 0) {
            jTableReparacion.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableReparacion.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableReparacion.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTableReparacion.getColumnModel().getColumn(4).setPreferredWidth(220);
            jTableReparacion.getColumnModel().getColumn(5).setPreferredWidth(70);
            jTableReparacion.getColumnModel().getColumn(6).setPreferredWidth(400);
            jTableReparacion.getColumnModel().getColumn(7).setPreferredWidth(50);
            jTableReparacion.getColumnModel().getColumn(8).setPreferredWidth(50);
            jTableReparacion.getColumnModel().getColumn(9).setPreferredWidth(125);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 1230, 240));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("REPARACIÓN");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 380, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        jButton1.setText("ELIMINAR REPARACIÓN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 200, 30));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        jButton3.setText("ELIMINAR FALLA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 200, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, 70, 60));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 710));

        jMenu1.setText("PIEZAS DAÑADAS DEL TALLER MECANICO");
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
        TallerMecanicoGUI.this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
      
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                try {
                    Statement comando=cn.createStatement();
                    jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
                    String cadena= (jtxtfiltro.getText());
                    jtxtfiltro.setText(cadena);
                    
                     //TOTAL DELIENAS
                    ResultSet registroL = comando.executeQuery("SELECT COUNT(*) AS cont FROM tallermecanicorep WHERE fechaEnt='"+cadena+"' OR reparadaP='"+cadena+"'");
                    if(registroL.next()==true) {
                        jLabel10.setText(registroL.getString("cont"));
                    }
                    
                    Filtro2();
                    
                if(cadena.equals("")){
                    
                    //TOTAL DELIENAS
                    ResultSet registroLI = comando.executeQuery("SELECT COUNT(*) AS cont FROM tallermecanicorep WHERE solucion IS NOT NULL");
                    if(registroLI.next()==true) {
                        jLabel10.setText(registroLI.getString("cont"));                
                    }
                }
  
                } catch (SQLException ex) {
                    Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        trs2 = new TableRowSorter(jTableReparacion.getModel());
        jTableReparacion.setRowSorter(trs2);
        
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                try {
                    Statement comando=cn.createStatement();
                    jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
                    String cadena= (jtxtfiltro.getText());
                    jtxtfiltro.setText(cadena);

                    Filtro();
                    
                if(cadena.equals("")){
                    
                    //TOTAL DELIENAS
                    ResultSet registroLI = comando.executeQuery("SELECT COUNT(*) AS cont FROM tallermecanicorep WHERE 1");
                    if(registroLI.next()==true) {
                        jLabel10.setText(registroLI.getString("cont"));                
                    }
                }
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        trs = new TableRowSorter(jTableFalla.getModel());
        jTableFalla.setRowSorter(trs);
       
        
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        try {
            CompProdMayorGUI cm= new CompProdMayorGUI(mod);
            cm.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            int fila_seleccionada = jTableReparacion.getSelectedRow();
            if(fila_seleccionada != -1){
                int id = (int) jTableReparacion.getValueAt(fila_seleccionada, 0);
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    this.tallerMecanico_servicio.eliminarRep(id);

                    TallerMecanicoGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    TallerMecanicoGUI mp = new TallerMecanicoGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);
                    
                }
        
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            int fila_seleccionada = jTableFalla.getSelectedRow();
            if(fila_seleccionada != -1){
                int id = (int) jTableFalla.getValueAt(fila_seleccionada, 0);
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    this.tallerMecanico_servicio.eliminarFalla(id);

                    TallerMecanicoGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    TallerMecanicoGUI mp = new TallerMecanicoGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);
                    
                }
        
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       excel.WriteExcel();
       JOptionPane.showMessageDialog(this, "Datos Exportados");
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(TallerMecanicoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TallerMecanicoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TallerMecanicoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TallerMecanicoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TallerMecanicoGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TallerMecanicoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFalla;
    private javax.swing.JTable jTableReparacion;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
