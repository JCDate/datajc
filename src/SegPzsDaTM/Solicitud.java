/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegPzsDaTM;

import Modelos.SolicitudTMPDM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.SolicitudTM_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Solicitud extends javax.swing.JFrame {
    private final SolicitudTM_servicio op_servicio = new SolicitudTM_servicio();
    private final SolicitudTMPDM op;
    
    
    Usuarios mod;
    Conexion cc = new Conexion();
    Connection cn;
   
    public Solicitud() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.op = new SolicitudTMPDM();;
        this.setDefaultCloseOperation(0);
    }
    
    public Solicitud(SolicitudTMPDM p_op,Usuarios mod) throws SQLException, ClassNotFoundException, ParseException{
        this.cn = Conexion.obtener();
        initComponents();
        this.op = p_op;;
        this.setDefaultCloseOperation(0);
        this.mod=mod;
        
        jTextField1.setText(this.op.getCotizacion());
        jTextField2.setText(this.op.getComponente());

        String sDate1= this.op.getFechaSol(); 
        if(!sDate1.equals(""))
        {
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
            jDateChooser1.setDate(date1);
        }
        
        String sDate2= this.op.getEnvioCot(); 
        if(!sDate2.equals(""))
        {
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);  
            jDateChooser2.setDate(date2);
        }
        
        jTextField5.setText(this.op.getPrecioC());
        
        String sDate3= this.op.getEnviadoSKF();
        if(!sDate3.equals(""))
        {
            Date date3=new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);  
            jDateChooser3.setDate(date3);
        }
        
        String sDate4= this.op.getAprobadoSKF(); 
        if(!sDate4.equals(""))
        {
            Date date4=new SimpleDateFormat("dd/MM/yyyy").parse(sDate4);  
            jDateChooser4.setDate(date4);
        }
        
        String sDate5= this.op.getSolicitudM(); 
        if(!sDate5.equals(""))
        {
            Date date5=new SimpleDateFormat("dd/MM/yyyy").parse(sDate5);  
            jDateChooser5.setDate(date5);
        }
        
        jTextField9.setText(this.op.getCostoM());
        
        String sDate6= this.op.getFechaMaq(); 
        if(!sDate6.equals(""))
        {
            Date date6=new SimpleDateFormat("dd/MM/yyyy").parse(sDate6);  
            jDateChooser6.setDate(date6);
        }
        
        String sDate7= this.op.getTemple(); 
        if(!sDate7.equals(""))
        {
            Date date7=new SimpleDateFormat("dd/MM/yyyy").parse(sDate7);  
            jDateChooser7.setDate(date7);
        }
        
        String sDate8= this.op.getAjuste();
        if(!sDate8.equals(""))
        {
            Date date8=new SimpleDateFormat("dd/MM/yyyy").parse(sDate8);  
            jDateChooser8.setDate(date8);
        }
        
        String sDate9= this.op.getProduccion();  
        if(!sDate9.equals(""))
        {
            Date date9=new SimpleDateFormat("dd/MM/yyyy").parse(sDate9);  
            jDateChooser9.setDate(date9);
        }
        
        jTextField14.setText(this.op.getPzFacturadaSKF());

        
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jDateChooser7 = new com.toedter.calendar.JDateChooser();
        jDateChooser8 = new com.toedter.calendar.JDateChooser();
        jDateChooser9 = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("COTIZACIÓN:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setEnabled(false);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 190, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("COMPONENTE:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 220, 140, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 320, -1, 40));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel6.setText("SOLICITUD");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 300, 50));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField2.setEnabled(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 100, 190, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("<HTML>ENVIO DE <BR>COTIZACIÓN (J.C):</HTML>");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("FECHA DE SOLICITUD:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("<HTML>PRECIO DE<BR> COTIZACIÓN:</HTML>");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 190, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("ENVIADO A SKF:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("APROBADO POR SKF:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("<HTML>SOLICITUD DE<BR> MATERIAL:</HTML>");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, 40));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("COSTO DE MATERIAL:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, -1, 40));

        jTextField9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField9KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, 190, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("FECHA DE MAQUINADO:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, -1, 40));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("TEMPLE :");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("AJUSTE:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 330, -1, 40));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("PRODUCCIÓN:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 380, -1, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("<HTML>PIEZA FACTURADA A <BR>SKF:</HTML>");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, -1, 40));

        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 190, -1));

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 190, -1));

        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 190, -1));

        jDateChooser3.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 190, -1));

        jDateChooser4.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 190, -1));

        jDateChooser5.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 190, -1));

        jDateChooser6.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 190, -1));

        jDateChooser7.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser7, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 190, -1));

        jDateChooser8.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, 190, -1));

        jDateChooser9.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDateChooser9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 390, 190, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String cotizacion = jTextField1.getText();
        String componente = jTextField2.getText();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        
        Date sol = jDateChooser1.getDate();
        String strSol = null;
        if(sol != null)
        {
            strSol = dateFormat.format(sol);  
        }else{
            strSol = "";
        }
        
        Date enC = jDateChooser2.getDate();
        String strEnC = null;
        if(enC != null)
        {
            strEnC = dateFormat.format(enC); 
        }else{
            strEnC = "";
        }
        
        String preC = jTextField5.getText();
        
        Date envSKF = jDateChooser3.getDate();
        String strenvSKF = null;
        if(envSKF != null)
        {
            strenvSKF = dateFormat.format(envSKF); 
        }else{
            strenvSKF = "";
        }
        
        Date aprSKF = jDateChooser4.getDate();
        String straprSKF = null;
        if(aprSKF != null)
        {
           straprSKF = dateFormat.format(aprSKF); 
        }else{
            straprSKF = "";
        }
        
        Date solM = jDateChooser5.getDate();
        String strsolM = null;
        if(solM != null)
        {
           strsolM = dateFormat.format(solM);
        }else{
            strsolM = "";
        }
        
        String cosM = jTextField9.getText();
        
        Date FMaqui = jDateChooser6.getDate();
        String strFMaqui = null;
        if(FMaqui != null)
        {
            strFMaqui= dateFormat.format(FMaqui);
        }else{
            strFMaqui = "";
        }
        
        Date temple = jDateChooser7.getDate();
        String strtemple= null;
        if(temple != null)
        {
            strtemple= dateFormat.format(temple);
        }else{
            strtemple = "";
        }
        
        Date ajuste = jDateChooser8.getDate();
        String strajuste = null;
        if(ajuste != null)
        {
            strajuste = dateFormat.format(ajuste);
        }else{
            strajuste = "";
        }
        
        Date prod = jDateChooser9.getDate();
        String strprod = null;
        if(prod != null)
        {
            strprod = dateFormat.format(prod);
        }else{
            strprod = "";
        }
        
        String pfac = jTextField14.getText();

        
           
        try{

            this.op.setCotizacion(cotizacion);
            this.op.setComponente(componente);
            this.op.setFechaSol(strSol);
            this.op.setEnvioCot(strEnC);
            this.op.setPrecioC(preC);
            this.op.setEnviadoSKF(strenvSKF);
            this.op.setAprobadoSKF(straprSKF);
            this.op.setSolicitudM(strsolM);
            this.op.setCostoM(cosM);
            this.op.setFechaMaq(strFMaqui);
            this.op.setTemple(strtemple);
            this.op.setAjuste(strajuste);
            this.op.setProduccion(strprod);
            this.op.setPzFacturadaSKF(pfac);
            
            
            this.op_servicio. modificar(Conexion.obtener(), this.op);
            
            Solicitud.this.dispose();
            SegPzsDaTMGUI ant = new SegPzsDaTMGUI(mod);
            ant.setVisible(true);
            ant.setLocationRelativeTo(null);
            
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
            Solicitud.this.dispose();
            SegPzsDaTMGUI tr = new SegPzsDaTMGUI(mod);
            tr.setVisible(true);
            tr.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14KeyTyped

    private void jTextField9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped

    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyTyped

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
            java.util.logging.Logger.getLogger(Solicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Solicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Solicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Solicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Solicitud().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Solicitud.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private com.toedter.calendar.JDateChooser jDateChooser7;
    private com.toedter.calendar.JDateChooser jDateChooser8;
    private com.toedter.calendar.JDateChooser jDateChooser9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField9;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
