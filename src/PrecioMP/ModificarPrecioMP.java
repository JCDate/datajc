/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrecioMP;

import Modelos.PrecioMP;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.PrecioMP_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Set;
import javax.swing.JOptionPane;

public class ModificarPrecioMP extends javax.swing.JFrame {

    private final PrecioMP_servicio precioMP_servicio = new PrecioMP_servicio();
    private final PrecioMP precio, precio2;
    Usuarios mod;

    public ModificarPrecioMP() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.precio = new PrecioMP();
        this.precio2 = new PrecioMP();

    }

    public ModificarPrecioMP(PrecioMP p_precio, Usuarios mod) {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.mod = mod;
        this.precio = p_precio;
        this.precio2 = this.precio;
        
        txtCalibre.setText(this.precio.getCalibre());
        txtPU.setText(Float.toString(this.precio.getPesoUnitario()));
        txtMoneda.setText(this.precio.getMoneda());
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCalibre = new javax.swing.JLabel();
        txtCalibre = new javax.swing.JTextField();
        lblPU = new javax.swing.JLabel();
        txtPU = new javax.swing.JTextField();
        btnGuardarPrecio = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblModificar = new javax.swing.JLabel();
        lblPrecioMP = new javax.swing.JLabel();
        lblMoneda = new javax.swing.JLabel();
        txtMoneda = new javax.swing.JTextField();
        lblJCIcono = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibre.setText("CALIBRE:");
        getContentPane().add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, -1, -1));

        txtCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCalibre.setEnabled(false);
        txtCalibre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalibreKeyTyped(evt);
            }
        });
        getContentPane().add(txtCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 190, -1));

        lblPU.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPU.setText("P. U. (KG):");
        getContentPane().add(lblPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        txtPU.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(txtPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 190, -1));

        btnGuardarPrecio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardarPrecio.setText("GUARDAR");
        btnGuardarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPrecioActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardarPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 130, -1));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, -1));

        lblModificar.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        lblModificar.setText("MODIFICAR");
        getContentPane().add(lblModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 250, 50));

        lblPrecioMP.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        lblPrecioMP.setText("PRECIO M. P.");
        getContentPane().add(lblPrecioMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 250, 40));

        lblMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMoneda.setText("MONEDA:");
        getContentPane().add(lblMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, -1, -1));

        txtMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(txtMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 190, -1));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPrecioActionPerformed
        String calibre = txtCalibre.getText();
        String precioUni = txtPU.getText();
        String moneda = txtMoneda.getText();
        if (!calibre.trim().equals("")) {
            
            float f = Float.parseFloat(precioUni);
            this.precio.setCalibre(calibre);
            this.precio.setPesoUnitario(f);
            this.precio.setMoneda(moneda);

            try {
                this.precioMP_servicio.modificar(Conexion.obtener(), this.precio, this.precio2);
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS.");
                ModificarPrecioMP.this.dispose();
                PrecioMPGUI pmp = new PrecioMPGUI(mod);
                pmp.setVisible(true);
                pmp.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "HAY CAMPOS INCOMPLETOS.");
        }
    }//GEN-LAST:event_btnGuardarPrecioActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        ModificarPrecioMP.this.dispose();
        PrecioMPGUI pmp = new PrecioMPGUI(mod);
        pmp.setVisible(true);
        pmp.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCalibreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalibreKeyTyped
    }//GEN-LAST:event_txtCalibreKeyTyped

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
            java.util.logging.Logger.getLogger(ModificarPrecioMP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarPrecioMP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarPrecioMP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarPrecioMP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModificarPrecioMP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarPrecio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblModificar;
    private javax.swing.JLabel lblMoneda;
    private javax.swing.JLabel lblPU;
    private javax.swing.JLabel lblPrecioMP;
    private javax.swing.JTextField txtCalibre;
    private javax.swing.JTextField txtMoneda;
    private javax.swing.JTextField txtPU;
    // End of variables declaration//GEN-END:variables
}
