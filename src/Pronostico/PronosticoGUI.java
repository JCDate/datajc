/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pronostico;


import MateriaPrima.MateriaPirmaGUI;
import Modelos.PronosticoM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.Pronostico_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JC
 */
public class PronosticoGUI extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    
    private final Pronostico_servicio pronostico_servicio = new Pronostico_servicio();
    private List<PronosticoM> pronostico;
    private final PronosticoM prono= new PronosticoM();
    /**
     * Creates new form PronosticoGUI
     */
    public PronosticoGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.PronosticoGUI();
        
        //Boton GENERAR
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        
        //Boton Regresar
        jButton2.setFocusPainted(false);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
    }
    
    public PronosticoGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.PronosticoGUI();
        
       
        
        //Boton Regresar
        jButton2.setFocusPainted(false);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
    }
    
    private void PronosticoGUI() throws SQLException, ClassNotFoundException {
       pronosticoXlsx dropXlsx = new pronosticoXlsx();
       dropXlsx.setJtable(jTable1); 
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("CARGAR PRONOSTICO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", ""
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 470, 390));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Generate-tables_37132.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 110, 100));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 120, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("GENERAR");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         

        try {
            for(int i=0; i< jTable1.getRowCount(); i++)
            {
                
                    PreparedStatement pst = cn.prepareStatement("INSERT INTO pronostico(supplier, componente, mes1, mes2, mes3, mes4) VALUES(?,?,?,?,?,?)");
                    pst.setString(1, jTable1.getValueAt(i,0).toString());
                    pst.setString(2, jTable1.getValueAt(i,1).toString());
                    pst.setString(3, jTable1.getValueAt(i,2).toString());
                    pst.setString(4, jTable1.getValueAt(i,3).toString());
                    pst.setString(5, jTable1.getValueAt(i,4).toString());
                    pst.setString(6, jTable1.getValueAt(i,5).toString());
                    
                    pst.executeUpdate();
                
            }
            
            
            //Borrar datos vacios
            for(int i=0; i< jTable1.getRowCount(); i++)
            {
            PreparedStatement pst2 = cn.prepareStatement("DELETE FROM pronostico WHERE mes1='' and mes2='' and mes3='' and mes4=''");
            pst2.executeUpdate();
            }
            
            // eliminar los ceros de mas
            PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET componente =(if(componente LIKE '1%' OR componente LIKE '2%' AND CHAR_LENGTH(componente)>6 , SUBSTRING(componente, 1, CHAR_LENGTH(componente) - 2), componente) )");
            pst.executeUpdate();
            
            //Calibre
            PreparedStatement pst3 = cn.prepareStatement("UPDATE pronostico JOIN estructura ON estructura.componente = pronostico.componente SET pronostico.calibre = estructura.calibre");
            pst3.executeUpdate();
            
            //Consumo Unitario
            PreparedStatement pst4 = cn.prepareStatement("UPDATE pronostico JOIN estructura ON estructura.componente = pronostico.componente SET pronostico.consumoU = estructura.peso_estamp");
            pst4.executeUpdate();
            
            
            //Precio Unitacio
            PreparedStatement pst5 = cn.prepareStatement("UPDATE pronostico JOIN preciomp ON preciomp.calibre = pronostico.calibre SET pronostico.pesoUnitario = preciomp.pesoUnitario");
            pst5.executeUpdate();
            
            //Precio Unitacio
            PreparedStatement pst6 = cn.prepareStatement("UPDATE pronostico JOIN preciomp ON preciomp.calibre = pronostico.calibre SET pronostico.moneda = preciomp.moneda");
            pst6.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "DATOS CARGADOS EXITOSAMENTE");
            
            PronosticoGUI.this.dispose();
            PronosticoGeneradoGUI pronostico = new PronosticoGeneradoGUI(mod);
            pronostico.setVisible(true);
            pronostico.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            PronosticoGUI.this.dispose();
            MateriaPirmaGUI mn= new MateriaPirmaGUI(mod);
            mn.setVisible(true);
            mn.setLocationRelativeTo(null);
            

        } catch (SQLException ex) {
            Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(PronosticoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PronosticoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PronosticoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PronosticoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PronosticoGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PronosticoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}