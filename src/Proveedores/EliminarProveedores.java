/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proveedores;

import Modelos.ProveedoresM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.Proveedores_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author JC
 */
public class EliminarProveedores extends javax.swing.JFrame {
    private final Proveedores_servicio proveedores_servicio = new Proveedores_servicio();
    private final ProveedoresM prov;
    Usuarios mod;
    public EliminarProveedores() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.prov = new ProveedoresM();
    }
    
    public EliminarProveedores(ProveedoresM p_prov,Usuarios mod){
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.prov = p_prov;
        this.mod=mod;

        txtNombre.setText(this.prov.getNombre());
        txtColonia.setText(this.prov.getColonia());
        txtParque.setText(this.prov.getParque());
        txtCP.setText(String.valueOf(this.prov.getCodigoPostal()));
        txtCiudad.setText(this.prov.getCiudad());
        txtEstado.setText(this.prov.getEstado());
        txtPais.setText(this.prov.getPais());
        txtCalibre.setText(this.prov.getCalibre());                
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png"));
        return retValue;
    }
    
    private void eliminar(){
        int respuesta = JOptionPane.showConfirmDialog(null,"La información del proveedor se eliminará ¿Estás de acuerdo?","ALERTA",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
         //  JOptionPane.showConfirmDialog(null,"¿Estas Completamente de acuerdo?, la información ya no podrá recuperarse");
            String nombre = txtNombre.getText();
            String colonia = txtColonia.getText();
            String parque = txtParque.getText();
            String codigoPostal = txtCP.getText();
            String ciudad = txtCiudad.getText();        
            String estado = txtEstado.getText();
            String pais = txtPais.getText();
            String calibre= txtCalibre.getText();
        
            if(!nombre.trim().equals("")){
                int  codigoPostalInt = Integer.parseInt(codigoPostal);
                this.prov.setNombre(nombre);
                this.prov.setColonia(colonia);
                this.prov.setParque(parque);
                this.prov.setCodigoPostal(codigoPostalInt);
                this.prov.setCiudad(ciudad);            
                this.prov.setEstado(estado);
                this.prov.setPais(pais);
                this.prov.setCalibre(calibre);
                try{
                    this.proveedores_servicio.eliminar(Conexion.obtener(), this.prov);
                    JOptionPane.showMessageDialog(this, "Registro Eliminado Correctamente.");
                    EliminarProveedores.this.dispose();
                    ProveedoresGUI ca = new ProveedoresGUI(mod);
                    ca.setVisible(true);
                    ca.setLocationRelativeTo(null);
                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido eliminar el registro.");
                }catch(ClassNotFoundException ex){
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido eliminar el registro.");
                }
            }else{
                JOptionPane.showMessageDialog(this, "INGRESAR COMPONENTE.");
            }   
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtEstado = new javax.swing.JTextField();
        txtCalibre = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        lblCalibre = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        txtCiudad = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtCP = new javax.swing.JTextField();
        txtParque = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        lblCP = new javax.swing.JLabel();
        lblParque = new javax.swing.JLabel();
        lblEliminar = new javax.swing.JLabel();
        lblProveedores = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblColonia = new javax.swing.JLabel();
        lblCiudad = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPais = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(830, 490));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtEstado.setEnabled(false);
        txtEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoActionPerformed(evt);
            }
        });
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });
        getContentPane().add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 190, -1));

        txtCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCalibre.setEnabled(false);
        txtCalibre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalibreActionPerformed(evt);
            }
        });
        txtCalibre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalibreKeyTyped(evt);
            }
        });
        getContentPane().add(txtCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 190, -1));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, -1, -1));

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibre.setText("CALIBRE:");
        getContentPane().add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 120, -1));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, -1, -1));

        txtCiudad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCiudad.setEnabled(false);
        txtCiudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiudadKeyTyped(evt);
            }
        });
        getContentPane().add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 190, -1));

        lblPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPais.setText("PAÍS:");
        getContentPane().add(lblPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 120, -1));

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstado.setText("ESTADO:");
        getContentPane().add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, 120, -1));

        txtCP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCP.setEnabled(false);
        txtCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPActionPerformed(evt);
            }
        });
        txtCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCPKeyTyped(evt);
            }
        });
        getContentPane().add(txtCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 190, -1));

        txtParque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtParque.setEnabled(false);
        txtParque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtParqueActionPerformed(evt);
            }
        });
        txtParque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtParqueKeyTyped(evt);
            }
        });
        getContentPane().add(txtParque, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 190, -1));

        txtColonia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtColonia.setEnabled(false);
        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColoniaKeyTyped(evt);
            }
        });
        getContentPane().add(txtColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 190, -1));

        lblCP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCP.setText("C.P.");
        getContentPane().add(lblCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, -1, -1));

        lblParque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblParque.setText("PARQUE:");
        getContentPane().add(lblParque, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, -1));

        lblEliminar.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblEliminar.setText("ELIMINAR");
        lblEliminar.setToolTipText("");
        getContentPane().add(lblEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 320, 50));

        lblProveedores.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblProveedores.setText("PROVEEDORES");
        getContentPane().add(lblProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 390, 50));

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("NOMBRE:");
        getContentPane().add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 80, -1));

        lblColonia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblColonia.setText("COLONIA:");
        getContentPane().add(lblColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, -1, -1));

        lblCiudad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCiudad.setText("CIUDAD:");
        getContentPane().add(lblCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 100, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setEnabled(false);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 190, -1));

        txtPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPais.setEnabled(false);
        txtPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaisActionPerformed(evt);
            }
        });
        txtPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaisKeyTyped(evt);
            }
        });
        getContentPane().add(txtPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, 190, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped

    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtColoniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyTyped

    }//GEN-LAST:event_txtColoniaKeyTyped

    private void txtParqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtParqueActionPerformed

    }//GEN-LAST:event_txtParqueActionPerformed

    private void txtParqueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParqueKeyTyped

    }//GEN-LAST:event_txtParqueKeyTyped

    private void txtCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPActionPerformed

    }//GEN-LAST:event_txtCPActionPerformed

    private void txtCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPKeyTyped
        
    }//GEN-LAST:event_txtCPKeyTyped

    private void txtCiudadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiudadKeyTyped

    }//GEN-LAST:event_txtCiudadKeyTyped

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed

    }//GEN-LAST:event_txtEstadoActionPerformed

    private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped

    }//GEN-LAST:event_txtEstadoKeyTyped

    private void txtPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaisActionPerformed

    }//GEN-LAST:event_txtPaisActionPerformed

    private void txtPaisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaisKeyTyped

    }//GEN-LAST:event_txtPaisKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        EliminarProveedores.this.dispose();
        ProveedoresGUI ca = new ProveedoresGUI(mod);
        ca.setVisible(true);
        ca.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCalibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalibreActionPerformed

    }//GEN-LAST:event_txtCalibreActionPerformed

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
            java.util.logging.Logger.getLogger(EliminarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EliminarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EliminarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EliminarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EliminarProveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCP;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblColonia;
    private javax.swing.JLabel lblEliminar;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblParque;
    private javax.swing.JLabel lblProveedores;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtCalibre;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtParque;
    // End of variables declaration//GEN-END:variables
}
