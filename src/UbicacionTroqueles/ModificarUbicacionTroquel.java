/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UbicacionTroqueles;

import Modelos.UbicacionTroquelM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.UbicacionTroquel_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ModificarUbicacionTroquel extends javax.swing.JFrame {
    private final UbicacionTroquel_servicio ubicacionTroquel_servicio = new UbicacionTroquel_servicio();
    private final UbicacionTroquelM ubT;
    Usuarios mod;

    public ModificarUbicacionTroquel() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.ubT = new UbicacionTroquelM();
        
    }
    public ModificarUbicacionTroquel(UbicacionTroquelM p_ubT,Usuarios mod){
        initComponents();
        this.ubT = p_ubT;
        this.mod=mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        
        jTextField1.setText(this.ubT.getTroquel());
        jTextField2.setText(this.ubT.getUbicacion());
        
    }
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    private boolean validar(){
        boolean validado = true;
        String troquel = jTextField1.getText();
        String ubicacion = jTextField2.getText();
        
        if(!troquel.trim().equals("") && !ubicacion.trim().equals("")){
            JOptionPane.showMessageDialog(null, "DATOS GUARDADOS.");
        }else{
            validado = false;
        }
        return validado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("TROQUEL:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setEnabled(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 190, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("UBICACIÓN:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 190, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 140, -1));

        jLabel5.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        jLabel5.setText("TROQUEL");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 200, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, -1));

        jLabel6.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        jLabel6.setText("MODIFICAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 240, 50));

        jLabel7.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        jLabel7.setText("UBICACIÓN");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 250, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(this.validar()){        
            String troquel = jTextField1.getText();
            String ubicacion = jTextField2.getText();

            this.ubT.setTroquel(troquel);
            this.ubT.setUbicacion(ubicacion);

            try{
                this.ubicacionTroquel_servicio. modificarUbTroquel(Conexion.obtener(), this.ubT);
                ModificarUbicacionTroquel.this.dispose();
                UbicacionTroquel ub = new UbicacionTroquel(mod);
                ub.setVisible(true);
                ub.setLocationRelativeTo(null);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }catch(ClassNotFoundException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "HAY CAMPOS INCOMPLETOS.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            ModificarUbicacionTroquel.this.dispose();
            UbicacionTroquel ub = new UbicacionTroquel(mod);
            ub.setVisible(true);
            ub.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarUbicacionTroquel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModificarUbicacionTroquel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(AgregarUbicacionTroquel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarUbicacionTroquel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarUbicacionTroquel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarUbicacionTroquel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarUbicacionTroquel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}
