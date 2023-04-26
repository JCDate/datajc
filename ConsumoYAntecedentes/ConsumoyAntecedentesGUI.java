/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsumoYAntecedentes;

import CRs.CRsGUI;
import Modelos.ConsumoYAntecedetesM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ConsumoyAntecedentes_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ConsumoyAntecedentesGUI extends javax.swing.JFrame {
    private final ConsumoyAntecedentes_servicio consumoyAntecedentes_servicio = new ConsumoyAntecedentes_servicio();
    private List<ConsumoYAntecedetesM> CA;
    
    private final toExcel excel = new toExcel();
    TableRowSorter trs;
    Usuarios mod;
    Conexion cc = new Conexion();
    Connection cn;
 
    public ConsumoyAntecedentesGUI() {
        initComponents();
        this.setResizable(false);
        this.ConsumoyAntecedentesGUI();
        this.setDefaultCloseOperation(0);

        //Boton Exportar Consumo y antecedentes
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
        //jButton5.setContentAreaFilled(false);
        
        //Boton Regresar
        /*jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);*/
    }
    
    public ConsumoyAntecedentesGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.ConsumoyAntecedentesGUI();
        
        
        //Boton Exportar Consumo y antecedentes
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
        //jButton5.setContentAreaFilled(false);
        
        //Boton Regresar
       /* jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);*/
        
        try {
            //cr
            PreparedStatement pst = cn.prepareStatement("INSERT INTO crs (crs.componente) SELECT consumoyantecedentes.componente_CA FROM consumoyantecedentes WHERE NOT EXISTS ( SELECT crs.componente FROM crs WHERE crs.componente = consumoyantecedentes.componente_CA)");
            pst.executeUpdate();
            
            //Ancho de tira
            PreparedStatement pst2 = cn.prepareStatement("UPDATE datostecnicosmp JOIN consumoyantecedentes ON consumoyantecedentes.componente_CA = datostecnicosmp.componente SET datostecnicosmp.anchoTira_p = consumoyantecedentes.anchoTira");
            pst2.executeUpdate();
            
            //Precio componente
            PreparedStatement pst3 = cn.prepareStatement("INSERT INTO preciocomponente (preciocomponente.componente) SELECT consumoyantecedentes.componente_CA FROM consumoyantecedentes WHERE NOT EXISTS ( SELECT preciocomponente.componente FROM preciocomponente WHERE preciocomponente.componente = consumoyantecedentes.componente_CA)");
            pst3.executeUpdate();
            
            //Estructura componente
            PreparedStatement pst4 = cn.prepareStatement("INSERT INTO estructura (estructura.componente) SELECT consumoyantecedentes.componente_CA FROM consumoyantecedentes WHERE NOT EXISTS ( SELECT estructura.componente FROM estructura WHERE estructura.componente = consumoyantecedentes.componente_CA)");
            pst4.executeUpdate();
            
            //Estructura consumo unitario
            PreparedStatement pst5 = cn.prepareStatement("UPDATE estructura JOIN consumoyantecedentes ON consumoyantecedentes.componente_CA = estructura.componente SET estructura.peso_estamp = consumoyantecedentes.consumo_uni");
            pst5.executeUpdate();
            
            //Calibre dimensiones
            PreparedStatement pst6 = cn.prepareStatement("UPDATE dim JOIN consumoyantecedentes ON consumoyantecedentes.componente_CA = dim.componente SET dim.calibre = consumoyantecedentes.desMatePrima");
            pst6.executeUpdate();
            
            //Inspeccion Almacen
            PreparedStatement pst7 = cn.prepareStatement("INSERT INTO almaceninspeccion (componente) SELECT consumoyantecedentes.componente_CA FROM consumoyantecedentes WHERE consumoyantecedentes.tipo='ALTO VOLUMEN' AND NOT EXISTS ( SELECT almaceninspeccion.componente FROM almaceninspeccion WHERE almaceninspeccion.componente = consumoyantecedentes.componente_CA)");
            pst7.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CRsGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jButton4.setVisible(false);
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

    private void ConsumoyAntecedentesGUI(){
        try{
            this.CA = this.consumoyAntecedentes_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.CA.size(); i++){
                dtm.addRow(new Object[]{
                    this.CA.get(i).getComponente_CA(),
                    this.CA.get(i).getConsumo_uni(),
                    this.CA.get(i).getDesMatePrima(),
                    this.CA.get(i).getAnchoTira(),
                    this.CA.get(i).getPesoPzs(),
                    this.CA.get(i).getPpapStatus(),
                    this.CA.get(i).getComentario_CA(),
                    this.CA.get(i).getTipo()
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
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
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
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 40));

        jLabel3.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("CONSUMO Y");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("ANTECEDENTES ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 150, 40));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("MODIFICAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 150, 40));

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
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 120, 220, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 120, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 120, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "COMPONENTE", "CONSUMO UNITARIO", "<html><center>DESCRIPCIÓN DE LA <br>MATERIA PRIMA</center></html>", "ANCHO DE TIRA", "PESO POR PZS", "PPAP STATUS ", "COMENTARIOS", "TIPO"
            }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1250, 460));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, 60, 60));

        jLabel6.setText("EXPORTAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 70, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ConsumoyAntecedentesGUI.this.dispose();
        AgregarConsumoYAntecedentes vista = new AgregarConsumoYAntecedentes(mod);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);   
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
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        jButton2.setVisible(false);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
        if(fila_seleccionada >= 0){
            try {
                ConsumoyAntecedentesGUI.this.dispose();
                ModificarConsumoYAntecedentes modificar = new ModificarConsumoYAntecedentes(this.CA.get(fila_seleccionada),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
        if(fila_seleccionada >= 0){
            try {
                ConsumoyAntecedentesGUI.this.dispose();
                ModificarConsumoYAntecedentes modificar = new ModificarConsumoYAntecedentes(this.CA.get(fila_seleccionada),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        excel.WriteExceConsumoYAntecedentes();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if(evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER){
            try {
                String componente = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
                String consumo_uni = jTable1.getValueAt(jTable1.getSelectedRow(),1).toString();
                String desMatePrima = jTable1.getValueAt(jTable1.getSelectedRow(),2).toString();
                String anchoTira = jTable1.getValueAt(jTable1.getSelectedRow(),3).toString();
                String pesoPzs = jTable1.getValueAt(jTable1.getSelectedRow(),4).toString();
                String PpapStatus = jTable1.getValueAt(jTable1.getSelectedRow(),5).toString();
                String comentario_CA = jTable1.getValueAt(jTable1.getSelectedRow(),6).toString();
                String tipo = jTable1.getValueAt(jTable1.getSelectedRow(),7).toString();
                
                PreparedStatement consulta;
                consulta = cn.prepareStatement("UPDATE consumoyantecedentes SET consumo_uni='"+consumo_uni+"',desMatePrima='"+desMatePrima+"',anchoTira='"+anchoTira+"',pesoPzs='"+pesoPzs+"',PpapStatus='"+PpapStatus+"',comentario_CA='"+comentario_CA+"',tipo='"+tipo+"' WHERE componente_CA='"+componente+"'");
                consulta.executeUpdate();
                JOptionPane.showMessageDialog(this, "MODIFICACIÓN EXITOSA");
            } catch (SQLException ex) {
                Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1KeyReleased
   
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
            java.util.logging.Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsumoyAntecedentesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsumoyAntecedentesGUI().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
