/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc;

import Calculo.CalculoGUI;
import OrdenesSolicitadas.OrdenesSolicitadasGUI;
import DefectoInspeccion.DefInsGUI;
import Dimensiones.DimensionesGUI;
import Embarque.EmbarqueGUI;
import Enviados.EntregadosGUI;
import Estructura.EstructuraGUI;
import InstPPTroqueldo.InstPPTroqueladoGUI;
import MateriaPrima.MateriaPirmaGUI;
import Modelos.Usuarios;
import OrdenCompra.OrdenCompraGUI;
import RepDiarioProd.RepDiarioProdGUI;
import SIM_CURRENT.SIM_CURRENTGUI;
import TallerMecanico.TallerMecanicoGUI;
import TiemposYRecursos.TiemposYRecursosGUI;
import Troqueladores.TroqueladoresGUI;
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
        btnRepProduccion.setVisible(false);
        btnIIPTroqueles.setVisible(false);
        btnTallerMecanico.setVisible(false);
        
        switch (mod.getId_tipo()) {
            case 1:
                btnAntecedentesFamilia.setEnabled(true);
                btnCRs.setEnabled(true);
                btnUbicacionTroqueles.setEnabled(true);
                btnTroqueles.setEnabled(true);
                btnEstructura.setEnabled(true);
                btnOrdenCompra.setEnabled(true);
                btnCalculo.setEnabled(true);
                btnTiemposRecursos.setEnabled(true);
                btnSimCurrent.setEnabled(true);
                btnOrdenesSolicitadas.setEnabled(true);
                btnEntregados.setEnabled(true);
                btnMateriaPrima.setEnabled(true);
                btnPrecioComponente.setEnabled(true);
                btnDimensiones.setEnabled(true);
                btnEmbarque.setEnabled(true);
                btnVentas.setEnabled(true);
                break;
            case 2:
                btnAntecedentesFamilia.setEnabled(true);
                btnCRs.setEnabled(true);
                btnUbicacionTroqueles.setEnabled(true);
                btnTroqueles.setEnabled(true);
                btnEstructura.setEnabled(true);
                btnOrdenCompra.setEnabled(true);
                btnCalculo.setEnabled(true);
                btnTiemposRecursos.setEnabled(true);
                btnSimCurrent.setEnabled(true);
                btnOrdenesSolicitadas.setEnabled(true);
                btnEntregados.setEnabled(true);
                btnMateriaPrima.setEnabled(true);
                btnPrecioComponente.setEnabled(true);
                btnDimensiones.setEnabled(true);
                btnEmbarque.setEnabled(true);
                btnVentas.setEnabled(true);
                break;
            case 3:
                btnAntecedentesFamilia.setEnabled(false);
                btnCRs.setEnabled(false);
                btnUbicacionTroqueles.setEnabled(false);
                btnTroqueles.setEnabled(false);
                btnEstructura.setEnabled(false);
                btnOrdenCompra.setEnabled(false);
                btnCalculo.setEnabled(false);
                btnTiemposRecursos.setEnabled(false);
                btnSimCurrent.setEnabled(false);
                btnOrdenesSolicitadas.setEnabled(false);
                btnEntregados.setEnabled(false);
                btnTrabajadores.setEnabled(false);
                btnDimensiones.setEnabled(false);
                btnEmbarque.setEnabled(true);
                btnReportes.setEnabled(false);
                btnVentas.setEnabled(false);
                break;
            case 4:
                btnAntecedentesFamilia.setEnabled(true);
                btnCRs.setEnabled(true);
                btnUbicacionTroqueles.setEnabled(true);
                btnTroqueles.setEnabled(true);
                btnEstructura.setEnabled(true);
                btnOrdenCompra.setEnabled(true);
                btnCalculo.setEnabled(true);
                btnTiemposRecursos.setEnabled(true);
                btnSimCurrent.setEnabled(true);
                btnOrdenesSolicitadas.setEnabled(true);
                btnEntregados.setEnabled(true);
                btnMateriaPrima.setEnabled(true);
                btnVentas.setEnabled(true);
                break; 
            default:
                break;
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        btnRepProduccion = new javax.swing.JButton();
        btnIIPTroqueles = new javax.swing.JButton();
        btnTallerMecanico = new javax.swing.JButton();
        btnTroqueles = new javax.swing.JButton();
        btnDimensiones = new javax.swing.JButton();
        btnUbicacionTroqueles = new javax.swing.JButton();
        btnMateriaPrima = new javax.swing.JButton();
        btnEstructura = new javax.swing.JButton();
        btnOrdenCompra = new javax.swing.JButton();
        btnTiemposRecursos = new javax.swing.JButton();
        btnSimCurrent = new javax.swing.JButton();
        btnTrabajadores = new javax.swing.JButton();
        btnOrdenesSolicitadas = new javax.swing.JButton();
        btnAntecedentesFamilia = new javax.swing.JButton();
        btnCRs = new javax.swing.JButton();
        btnCalculo = new javax.swing.JButton();
        btnEntregados = new javax.swing.JButton();
        btnEmbarque = new javax.swing.JButton();
        btnPrecioComponente = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConsumoAntecedentes = new javax.swing.JButton();
        btnEntSalAlmacen = new javax.swing.JButton();
        btnDefectosInspeccion = new javax.swing.JButton();
        lblIconoJC = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(59, 153, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(340, 700));

        jPanel2.setMinimumSize(new java.awt.Dimension(340, 700));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRepProduccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRepProduccion.setText("REP. PRODUCCIÓN");
        btnRepProduccion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRepProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepProduccionActionPerformed(evt);
            }
        });
        jPanel2.add(btnRepProduccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 630, 150, 30));

        btnIIPTroqueles.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIIPTroqueles.setText("I. P. P. TROQUELES");
        btnIIPTroqueles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIIPTroqueles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIIPTroquelesActionPerformed(evt);
            }
        });
        jPanel2.add(btnIIPTroqueles, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 660, 150, -1));

        btnTallerMecanico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTallerMecanico.setText("TALLER MECANICO");
        btnTallerMecanico.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTallerMecanico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTallerMecanicoActionPerformed(evt);
            }
        });
        jPanel2.add(btnTallerMecanico, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 690, 150, -1));

        btnTroqueles.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTroqueles.setText("TROQUELES");
        btnTroqueles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnTroqueles, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 300, 30));

        btnDimensiones.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnDimensiones.setText("DIMENSIONES");
        btnDimensiones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDimensiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDimensionesActionPerformed(evt);
            }
        });
        jPanel2.add(btnDimensiones, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 300, 30));

        btnUbicacionTroqueles.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnUbicacionTroqueles.setText("UBICACIÓN DE LOS TROQUELES ");
        btnUbicacionTroqueles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnUbicacionTroqueles, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 300, 30));

        btnMateriaPrima.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMateriaPrima.setText("MATERIA PRIMA  ");
        btnMateriaPrima.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMateriaPrima.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMateriaPrimaMouseClicked(evt);
            }
        });
        btnMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMateriaPrimaActionPerformed(evt);
            }
        });
        jPanel2.add(btnMateriaPrima, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 300, 30));

        btnEstructura.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEstructura.setText("ESTRUCTURA ");
        btnEstructura.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEstructura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstructuraActionPerformed(evt);
            }
        });
        jPanel2.add(btnEstructura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 300, 30));

        btnOrdenCompra.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnOrdenCompra.setText("ORDEN DE COMPRA  ");
        btnOrdenCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenCompraActionPerformed(evt);
            }
        });
        jPanel2.add(btnOrdenCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 300, 30));

        btnTiemposRecursos.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTiemposRecursos.setText("TIEMPOS Y RECURSOS  ");
        btnTiemposRecursos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTiemposRecursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiemposRecursosActionPerformed(evt);
            }
        });
        jPanel2.add(btnTiemposRecursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 300, 30));

        btnSimCurrent.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSimCurrent.setText("SIM CURRENT ");
        btnSimCurrent.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSimCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimCurrentActionPerformed(evt);
            }
        });
        jPanel2.add(btnSimCurrent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 300, 30));

        btnTrabajadores.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTrabajadores.setText("TRABAJADORES");
        btnTrabajadores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTrabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrabajadoresActionPerformed(evt);
            }
        });
        jPanel2.add(btnTrabajadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 300, 30));

        btnOrdenesSolicitadas.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnOrdenesSolicitadas.setText("ORDENES SOLICITADAS");
        btnOrdenesSolicitadas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnOrdenesSolicitadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenesSolicitadasActionPerformed(evt);
            }
        });
        jPanel2.add(btnOrdenesSolicitadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 300, 30));

        btnAntecedentesFamilia.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnAntecedentesFamilia.setText("ANTECEDENTES POR FAMILIA");
        btnAntecedentesFamilia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnAntecedentesFamilia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 300, 30));

        btnCRs.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnCRs.setText("C/Rs");
        btnCRs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnCRs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 300, 30));

        btnCalculo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnCalculo.setText("CALCULO");
        btnCalculo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCalculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculoActionPerformed(evt);
            }
        });
        jPanel2.add(btnCalculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 300, 30));

        btnEntregados.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEntregados.setText("ENTREGADOS");
        btnEntregados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEntregados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregadosActionPerformed(evt);
            }
        });
        jPanel2.add(btnEntregados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 300, 30));

        btnEmbarque.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEmbarque.setText("EMBARQUE");
        btnEmbarque.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEmbarque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmbarqueActionPerformed(evt);
            }
        });
        jPanel2.add(btnEmbarque, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 300, 30));

        btnPrecioComponente.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnPrecioComponente.setText("PRECIO COMPONENTE");
        btnPrecioComponente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnPrecioComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 300, 30));

        btnReportes.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnReportes.setText("REPORTES");
        btnReportes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        jPanel2.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 300, 30));

        btnVentas.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnVentas.setText("VENTAS");
        btnVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jPanel2.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 300, 30));

        btnConsumoAntecedentes.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnConsumoAntecedentes.setText("CONSUMO Y ANTECEDENTES ");
        btnConsumoAntecedentes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnConsumoAntecedentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 300, 30));

        btnEntSalAlmacen.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnEntSalAlmacen.setText("ENT. Y SAL. ALMACEN (INSP.)");
        btnEntSalAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnEntSalAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 300, -1));

        btnDefectosInspeccion.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnDefectosInspeccion.setText("DEFECTOS INSPECCIÓN");
        btnDefectosInspeccion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDefectosInspeccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDefectosInspeccionMouseClicked(evt);
            }
        });
        btnDefectosInspeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefectosInspeccionActionPerformed(evt);
            }
        });
        jPanel2.add(btnDefectosInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 300, 30));

        lblIconoJC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel2.add(lblIconoJC, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        btnLogOut.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Logout_37127 (2).png"))); // NOI18N
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 40));

        jScrollPane1.setViewportView(jPanel2);

        jPanel1.add(jScrollPane1);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        JC jc= new JC();
        jc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnEstructuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstructuraActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            EstructuraGUI estruc= new EstructuraGUI(mod);
            estruc.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEstructuraActionPerformed

    private void btnOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenCompraActionPerformed
        
         //REPORTES
        btnRepProduccion.setVisible(false);
        btnIIPTroqueles.setVisible(false);
        btnTallerMecanico.setVisible(false);
        
        OrdenCompraGUI ordencompra= new OrdenCompraGUI(mod);
        ordencompra.setVisible(true);
    }//GEN-LAST:event_btnOrdenCompraActionPerformed

    private void btnMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMateriaPrimaActionPerformed
        try {
            
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            MateriaPirmaGUI materiaprima= new MateriaPirmaGUI(mod);
            materiaprima.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMateriaPrimaActionPerformed

    private void btnSimCurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimCurrentActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            SIM_CURRENTGUI sim_current = new SIM_CURRENTGUI(mod);
             sim_current.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSimCurrentActionPerformed

    private void btnOrdenesSolicitadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenesSolicitadasActionPerformed
        try {

             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            OrdenesSolicitadasGUI analisisatrasos = new OrdenesSolicitadasGUI(mod);
            analisisatrasos.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOrdenesSolicitadasActionPerformed

    private void btnTiemposRecursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiemposRecursosActionPerformed
        try {
            
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            TiemposYRecursosGUI tiemposyrecursos = new TiemposYRecursosGUI(mod);
            tiemposyrecursos.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTiemposRecursosActionPerformed

    private void btnCalculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculoActionPerformed

         //REPORTES
        btnRepProduccion.setVisible(false);
        btnIIPTroqueles.setVisible(false);
        btnTallerMecanico.setVisible(false);
        
        CalculoGUI calculo = new CalculoGUI(mod);
        calculo.setVisible(true);
    }//GEN-LAST:event_btnCalculoActionPerformed

    private void btnTrabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrabajadoresActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            TroqueladoresGUI troqueladores = new TroqueladoresGUI(mod);
             troqueladores.setVisible(true);
             
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTrabajadoresActionPerformed

    private void btnDimensionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDimensionesActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
            
            DimensionesGUI dim = new DimensionesGUI(mod);
            dim.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDimensionesActionPerformed

    private void btnEmbarqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmbarqueActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            EmbarqueGUI em= new EmbarqueGUI(mod);
            em.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnEmbarqueActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
         //REPORTES
        btnRepProduccion.setVisible(true);
        btnIIPTroqueles.setVisible(true);
        btnTallerMecanico.setVisible(true);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);
        
            VentasGUI ven= new VentasGUI(mod);
            ven.setVisible(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnMateriaPrimaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMateriaPrimaMouseClicked
    }//GEN-LAST:event_btnMateriaPrimaMouseClicked

    private void btnTallerMecanicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTallerMecanicoActionPerformed
        try {
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);

            TallerMecanicoGUI taller= new TallerMecanicoGUI(mod);
            taller.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTallerMecanicoActionPerformed

    private void btnIIPTroquelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIIPTroquelesActionPerformed
        try {
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);

            InstPPTroqueladoGUI inst= new InstPPTroqueladoGUI(mod);
            inst.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIIPTroquelesActionPerformed

    private void btnRepProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepProduccionActionPerformed
        try {
            //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);

            RepDiarioProdGUI rep= new RepDiarioProdGUI(mod);
            rep.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRepProduccionActionPerformed

    private void btnDefectosInspeccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDefectosInspeccionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDefectosInspeccionMouseClicked

    private void btnDefectosInspeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefectosInspeccionActionPerformed
        try {
            //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);

            DefInsGUI def= new DefInsGUI(mod);
            def.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDefectosInspeccionActionPerformed

    private void btnEntregadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregadosActionPerformed
        try {
             //REPORTES
            btnRepProduccion.setVisible(false);
            btnIIPTroqueles.setVisible(false);
            btnTallerMecanico.setVisible(false);;
        
            EntregadosGUI entregados = new EntregadosGUI(mod);
            entregados.setVisible(true);
 
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    //GEN-LAST
    }//GEN-LAST:event_btnEntregadosActionPerformed

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
    private javax.swing.JButton btnAntecedentesFamilia;
    private javax.swing.JButton btnCRs;
    private javax.swing.JButton btnCalculo;
    private javax.swing.JButton btnConsumoAntecedentes;
    private javax.swing.JButton btnDefectosInspeccion;
    private javax.swing.JButton btnDimensiones;
    private javax.swing.JButton btnEmbarque;
    private javax.swing.JButton btnEntSalAlmacen;
    private javax.swing.JButton btnEntregados;
    private javax.swing.JButton btnEstructura;
    private javax.swing.JButton btnIIPTroqueles;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnMateriaPrima;
    private javax.swing.JButton btnOrdenCompra;
    private javax.swing.JButton btnOrdenesSolicitadas;
    private javax.swing.JButton btnPrecioComponente;
    private javax.swing.JButton btnRepProduccion;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSimCurrent;
    private javax.swing.JButton btnTallerMecanico;
    private javax.swing.JButton btnTiemposRecursos;
    private javax.swing.JButton btnTrabajadores;
    private javax.swing.JButton btnTroqueles;
    private javax.swing.JButton btnUbicacionTroqueles;
    private javax.swing.JButton btnVentas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIconoJC;
    // End of variables declaration//GEN-END:variables
}
