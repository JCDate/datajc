/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc;

import AlmacenInspeccion.InspeccionGUI;
import AntecedentesFamilia.AntecedentesFamiliaGUI;
import CRs.CRsGUI;
import Calculo.CalculoGUI;
import OrdenesSolicitadas.OrdenesSolicitadasGUI;
import ConsumoYAntecedentes.ConsumoyAntecedentesGUI;
import DefectoInspeccion.DefInsGUI;
import Dimensiones.DimensionesGUI;
import Embarque.EmbarqueGUI;
import Enviados.EntregadosGUI;
import Estructura.EstructuraGUI;
import InstPPTroqueldo.InstPPTroqueladoGUI;
import MateriaPrima.MateriaPirmaGUI;
import Modelos.Usuarios;
import OrdenCompra.OrdenCompraGUI;
import PrecioComponente.PrecoComponenteGUI;
import RepDiarioProd.RepDiarioProdGUI;
import SIM_CURRENT.SIM_CURRENTGUI;
import TallerMecanico.TallerMecanicoGUI;
import TiemposYRecursos.TiemposYRecursosGUI;
import Troqueladores.TroqueladoresGUI;
import Troqueles.troqueles;
import UbicacionTroqueles.UbicacionTroquel;
import Ventas.VentasGUI;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class menu extends javax.swing.JFrame {

    Usuarios mod;

    public menu() {
        initComponents(); 
        
        this.setResizable(false);
        this.setDefaultCloseOperation(0);   
    }
    
    public menu(Usuarios mod){
        initComponents();
        setLocationRelativeTo(null);
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
       
        //REPORTES
        jButton26.setVisible(false);
        jButton27.setVisible(false);
        jButton28.setVisible(false);
        
        if(mod.getId_tipo()== 1)
        {
            jButton12.setEnabled(true);
            jButton13.setEnabled(true);
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);
            jButton5.setEnabled(true);
            jButton6.setEnabled(true);
            jButton14.setEnabled(true);
            jButton7.setEnabled(true);
            jButton8.setEnabled(true);
            jButton11.setEnabled(true);
            jButton15.setEnabled(true);
            jButton3.setEnabled(true);
            jButton21.setEnabled(true);
            jButton19.setEnabled(true);
            jButton20.setEnabled(true);
            jButton10.setEnabled(true);
            
        }else if(mod.getId_tipo()== 2)
        {
            jButton12.setEnabled(true);
            jButton13.setEnabled(true);
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);
            jButton5.setEnabled(true);
            jButton6.setEnabled(true);
            jButton14.setEnabled(true);
            jButton7.setEnabled(true);
            jButton8.setEnabled(true);
            jButton11.setEnabled(true);
            jButton15.setEnabled(true);
            jButton3.setEnabled(true);
            jButton21.setEnabled(true);
            jButton19.setEnabled(true);
            jButton20.setEnabled(true);
            jButton10.setEnabled(true);
     
        }else if(mod.getId_tipo()== 3){
            jButton12.setEnabled(false);
            jButton13.setEnabled(false);
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
            jButton14.setEnabled(false);
            jButton7.setEnabled(false);
            jButton8.setEnabled(false);
            jButton11.setEnabled(false);
            jButton15.setEnabled(false);
            jButton18.setEnabled(false);
            jButton19.setEnabled(false);
            jButton20.setEnabled(true);
            jButton17.setEnabled(false);
            jButton10.setEnabled(false);
        }else if(mod.getId_tipo()== 4){
            jButton12.setEnabled(true);
            jButton13.setEnabled(true);
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);
            jButton5.setEnabled(true);
            jButton6.setEnabled(false);
            jButton14.setEnabled(true);
            jButton7.setEnabled(true);
            jButton8.setEnabled(false);
            jButton11.setEnabled(true);
            jButton15.setEnabled(true);
            jButton3.setEnabled(false);
            jButton10.setEnabled(false);
        } 
    }
   
    
    public void restaurarBackUp(){
        try{
            JFileChooser Dialogo = new JFileChooser();
            //Creamos el filtro
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.sql", "sql");
            
            //Le indicamos el filtro
            Dialogo.setFileFilter(filtro);
            Dialogo.setSelectedFile(new File("jc_mysql.sql"));
            int x=Dialogo.showOpenDialog(this);
            if(x==0)
            {
                File F = Dialogo.getSelectedFile();
                String rutaFile = F.getAbsolutePath();
                String rutaMySql = "C:\\xampp2\\mysql\\bin\\mysql.exe";
                String Clave = "";
                String Usuario = "root";
                String db = "jc_mysql";

                String cad = "\"" + rutaMySql + "\" --password=" + Clave + " --user=" + Usuario + " " + db + " < \"" + rutaFile +"\"\n";

                File fcopi = new File("copia_seguridad.bat");
                FileWriter fw = new FileWriter(fcopi);
                fw.write(cad, 0, cad.length());
                fw.close();
                Runtime.getRuntime().exec("copia_Seguridad.bat");
                JOptionPane.showMessageDialog(this, "RESTAURACION EXITOSA");
            }else{
              JOptionPane.showMessageDialog(this, "Restauración Cancelada Por El Usuario");
            }
         }catch(Exception ex){
             ex.printStackTrace();
         }
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

        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton26.setText("REP. PRODUCCIÓN");
        jButton26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 630, 150, 30));

        jButton27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton27.setText("I. P. P. TROQUELES");
        jButton27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 660, 150, -1));

        jButton28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton28.setText("TALLER MECANICO");
        jButton28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 690, 150, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton1.setText("TROQUELES");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 300, 30));

        jButton19.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton19.setText("DIMENSIONES");
        jButton19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 300, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton2.setText("UBICACIÓN DE LOS TROQUELES ");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 300, 30));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("MATERIA PRIMA  ");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 300, 30));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton5.setText("ESTRUCTURA ");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 300, 30));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton6.setText("ORDEN DE COMPRA  ");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 300, 30));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton7.setText("TIEMPOS Y RECURSOS  ");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 300, 30));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton8.setText("SIM CURRENT ");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 300, 30));

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Logout_37127 (2).png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 40));

        jButton18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton18.setText("TRABAJADORES");
        jButton18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 300, 30));

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton11.setText("ORDENES SOLICITADAS");
        jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 300, 30));

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton12.setText("ANTECEDENTES POR FAMILIA");
        jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 300, 30));

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton13.setText("C/Rs");
        jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 300, 30));

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton14.setText("CALCULO");
        jButton14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 300, 30));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton15.setText("ENTREGADOS");
        jButton15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 300, 30));

        jButton20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton20.setText("EMBARQUE");
        jButton20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 300, 30));

        jButton21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton21.setText("PRECIO COMPONENTE");
        jButton21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 300, 30));

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton17.setText("REPORTES");
        jButton17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 300, 30));

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton10.setText("VENTAS");
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 300, 30));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton4.setText("CONSUMO Y ANTECEDENTES ");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 300, 30));

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton16.setText("ENT. Y SAL. ALMACEN (INSP.)");
        jButton16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 300, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        jButton22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton22.setText("DEFECTOS INSPECCIÓN");
        jButton22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton22MouseClicked(evt);
            }
        });
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 300, 30));

        jPanel1.setBackground(new java.awt.Color(59, 153, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
            
            troqueles tro= new troqueles(mod);
            tro.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        JC jc= new JC();
        jc.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            UbicacionTroquel ubT= new UbicacionTroquel(mod);
            ubT.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            EstructuraGUI estruc= new EstructuraGUI(mod);
            estruc.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
         //REPORTES
        jButton26.setVisible(false);
        jButton27.setVisible(false);
        jButton28.setVisible(false);
        
        OrdenCompraGUI ordencompra= new OrdenCompraGUI(mod);
        ordencompra.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            MateriaPirmaGUI materiaprima= new MateriaPirmaGUI(mod);
            materiaprima.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            SIM_CURRENTGUI sim_current = new SIM_CURRENTGUI(mod);
             sim_current.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {

             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            OrdenesSolicitadasGUI analisisatrasos = new OrdenesSolicitadasGUI(mod);
            analisisatrasos.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            TiemposYRecursosGUI tiemposyrecursos = new TiemposYRecursosGUI(mod);
            tiemposyrecursos.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
            
            AntecedentesFamiliaGUI antFam = new AntecedentesFamiliaGUI(mod);
            antFam.setVisible(true);

            /*PanelPrincipal.removeAll();
            PanelPrincipal.add(antFam, BorderLayout.CENTER);
            PanelPrincipal.revalidate();
            PanelPrincipal.repaint();*/
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
            
            CRsGUI crs = new CRsGUI(mod);
            crs.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed

         //REPORTES
        jButton26.setVisible(false);
        jButton27.setVisible(false);
        jButton28.setVisible(false);
        
        CalculoGUI calculo = new CalculoGUI(mod);
        calculo.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            EntregadosGUI entregados = new EntregadosGUI(mod);
            entregados.setVisible(true);
 
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            TroqueladoresGUI troqueladores = new TroqueladoresGUI(mod);
             troqueladores.setVisible(true);
             
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
            
            DimensionesGUI dim = new DimensionesGUI(mod);
            dim.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            EmbarqueGUI em= new EmbarqueGUI(mod);
            em.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
           PrecoComponenteGUI preCom = new PrecoComponenteGUI(mod);
           preCom.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
         //REPORTES
        jButton26.setVisible(true);
        jButton27.setVisible(true);
        jButton28.setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            VentasGUI ven= new VentasGUI(mod);
            ven.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
             //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
        
            ConsumoyAntecedentesGUI cons= new ConsumoyAntecedentesGUI(mod);
            cons.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        try {
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);

            TallerMecanicoGUI taller= new TallerMecanicoGUI(mod);
            taller.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        try {
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);

            InstPPTroqueladoGUI inst= new InstPPTroqueladoGUI(mod);
            inst.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        try {
            //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);

            RepDiarioProdGUI rep= new RepDiarioProdGUI(mod);
            rep.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        try {
            //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);
            
            InspeccionGUI ins= new InspeccionGUI(mod);
            ins.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22MouseClicked

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        try {
            //REPORTES
            jButton26.setVisible(false);
            jButton27.setVisible(false);
            jButton28.setVisible(false);

            DefInsGUI def= new DefInsGUI(mod);
            def.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

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
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
