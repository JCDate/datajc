/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegPzsDaTM;

import Modelos.CotizacionTMPDM;
import Modelos.OrdenesProdM;
import Modelos.SolicitudTMPDM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.CotizacionTM_servicio;
import Servicios.OrdenProd_servicio;
import Servicios.SolicitudTM_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class CotizacionAgregar extends javax.swing.JFrame {
    private final OrdenProd_servicio op_servicio = new OrdenProd_servicio();
    private final OrdenesProdM op;
    
    private final CotizacionTM_servicio co_servicio = new CotizacionTM_servicio();
    private final CotizacionTMPDM co;
    
    private final SolicitudTM_servicio sol_servicio = new SolicitudTM_servicio();
    private final SolicitudTMPDM sol;

    
    Usuarios mod;
    Conexion cc = new Conexion();
    Connection cn;
   
    public CotizacionAgregar() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.op = new OrdenesProdM();
        this.co = new CotizacionTMPDM();
        this.sol = new SolicitudTMPDM();
        this.setDefaultCloseOperation(0);
     
    }
    
    public CotizacionAgregar(Usuarios mod) throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.op = new OrdenesProdM();
        this.co = new CotizacionTMPDM();
        this.sol = new SolicitudTMPDM();
        this.mod = mod;
        
        this.agregarCot();
    }

    public void agregarCot(){
        try {
            String componente=JOptionPane.showInputDialog("INGRESAR COMPONENTE");
            
            jTextField2.setText(componente);
            
            PreparedStatement consulta = cn.prepareStatement("SELECT troquel FROM troqueles WHERE componente ='"+componente+"'");
            ResultSet resultado = consulta.executeQuery();
            choice1.add("-------");
            while(resultado.next()){
                choice1.add(resultado.getString("troquel"));
            } 
        } catch (SQLException ex) {
            Logger.getLogger(CotizacionAgregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        choice1 = new java.awt.Choice();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("COTIZACIÓN:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 190, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("COMPONENTE:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 140, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, -1, 40));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel6.setText("COTIZACIÓN");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 300, 50));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField2.setEnabled(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 190, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("PZS. DAÑADAS:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 190, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("TROQUEL:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));
        getContentPane().add(choice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 190, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            String cotizacion = jTextField1.getText();
            String componente = jTextField2.getText();
            String pzsD = jTextField3.getText();
            String troquel = choice1.getSelectedItem();
            
            this.co.setCotizacion(cotizacion);
            this.co.setComponente(componente);
            this.co.setPzaDañada(pzsD);
            this.co.setTroquel(troquel);
            
            this.sol.setCotizacion(cotizacion);
            this.sol.setComponente(componente);
            

            try{
                if(this.co_servicio.existeComponente(jTextField1.getText())== 0 && this.sol_servicio.existeC(jTextField1.getText())== 0)
                {
                    this.co_servicio. agregar(Conexion.obtener(), this.co);
                    //Actualizar cr de cotizacion
                    PreparedStatement pst = cn.prepareStatement("UPDATE cotizaciontmpd SET cotizaciontmpd.cr =(SELECT crs.cr FROM crs WHERE cotizaciontmpd.componente= crs.componente)");
                    pst.executeUpdate();

                    this.sol_servicio. agregar(Conexion.obtener(), this.sol);
                    
                    PreparedStatement pst2 = cn.prepareStatement("UPDATE solicitudtmpd SET fechaSol='',envioCot='',enviadoSKF='',aprobadoSKF='',solicitudM='',fechaMaq='',temple='',ajuste='',produccion='',pzFacturadaSKF='' WHERE  fechaSol IS NULL");
                    pst2.executeUpdate();

                    CotizacionAgregar.this.dispose();
                    SegPzsDaTMGUI ant = new SegPzsDaTMGUI(mod);
                    ant.setVisible(true);
                    ant.setLocationRelativeTo(null);
                }else{
                    JOptionPane.showMessageDialog(this, "Cotización existente.");
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }catch(ClassNotFoundException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            CotizacionAgregar.this.dispose();
            SegPzsDaTMGUI tr = new SegPzsDaTMGUI(mod);
            tr.setVisible(true);
            tr.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(CotizacionAgregar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CotizacionAgregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyTyped

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
            java.util.logging.Logger.getLogger(CotizacionAgregar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CotizacionAgregar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CotizacionAgregar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CotizacionAgregar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CotizacionAgregar().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(CotizacionAgregar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CotizacionAgregar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
