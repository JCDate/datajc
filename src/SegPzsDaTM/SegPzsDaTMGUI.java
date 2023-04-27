/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegPzsDaTM;

import Modelos.CotizacionTMPDM;
import Modelos.SolicitudTMPDM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.CotizacionTM_servicio;
import Servicios.SolicitudTM_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class SegPzsDaTMGUI extends javax.swing.JFrame {
    private final CotizacionTM_servicio CotizacionTM_servicio = new CotizacionTM_servicio();
    private List<CotizacionTMPDM> SegPzsDaTM;
    
    private final SolicitudTM_servicio sol_servicio = new SolicitudTM_servicio();
    private List<SolicitudTMPDM> sol;
    
    public toExcel excel=new toExcel();

    Usuarios mod;
    TableRowSorter trs;
    TableRowSorter trs2;
    Conexion cc = new Conexion();
    Connection cn;
 
    public SegPzsDaTMGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.Seg();
        this.setDefaultCloseOperation(0);
        jButton4.setVisible(false);
 
        //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
    }
    
    public SegPzsDaTMGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.Seg();
        this.jTable2.setDefaultRenderer(Object.class, new SegPzsDaTM.MiRenderer());
        
         //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);       
        
        jButton4.setVisible(false);
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
            jButton4.setEnabled(false);           
        }
        }
    
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    private void Seg(){
        try{
            this.SegPzsDaTM = this.CotizacionTM_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.SegPzsDaTM.size(); i++){
                dtm.addRow(new Object[]{
                    this.SegPzsDaTM.get(i).getCotizacion(),
                    this.SegPzsDaTM.get(i).getComponente(),
                    this.SegPzsDaTM.get(i).getCr(),
                    this.SegPzsDaTM.get(i).getTroquel(),
                    this.SegPzsDaTM.get(i).getPzaDañada()
                });
            }
            
            this.sol = this.sol_servicio.recuperarTodas(Conexion.obtener());
            
            DefaultTableModel dtm2 = (DefaultTableModel) jTable2.getModel();
            dtm2.setRowCount(0);
            for(int i = 0; i < this.sol.size(); i++){
                dtm2.addRow(new Object[]{
                    this.sol.get(i).getCotizacion(),
                    this.sol.get(i).getComponente(),
                    this.sol.get(i).getFechaSol(),
                    this.sol.get(i).getEnvioCot(),
                    this.sol.get(i).getPrecioC(),
                    this.sol.get(i).getEnviadoSKF(),
                    this.sol.get(i).getAprobadoSKF(),
                    this.sol.get(i).getSolicitudM(),
                    this.sol.get(i).getCostoM(),
                    this.sol.get(i).getFechaMaq(),
                    this.sol.get(i).getTemple(),
                    this.sol.get(i).getAjuste(),
                    this.sol.get(i).getProduccion(),
                    this.sol.get(i).getPzFacturadaSKF()
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
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla, 1,3));
    }
    
     public void Filtro2(){
        int ColumnaTabla = 0;
       trs2.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla, 1));
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
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel3.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel3.setText("<html><center>SEGUIMIENTO PIEZAS DAÑADAS<br> DEL TALLER MECANICO</center></html>");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, 70));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("ACTUALIZAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 660, 160, 40));

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
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 110, 180, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 110, 30, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 130, 50));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "COTIZACION", "COMPONENTE", "C/R", "TROQUEL", "PZS. DAÑADAS"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1260, 240));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("ACTUALIZAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 660, 160, 40));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "COTIZACIÓN", "COMPONENTE", "<html><center>FECHA DE<br> SOLICITUD</center></html>", "<html><center>ENVIO DE <br>COTIZACIÓN (J.C)</center></html>", "<html><center>PRECIO DE<br> COTIZACIÓN</center></html>", "<html><center>ENVIADO A <br>SKF</center></html>", "<html><center>APROBADO <br>POR SKF</center></html>", "<html><center>SOLICITUD DE<br> MATERIAL</center></html>", "<html><center>COSTO DE<br> MATERIAL</center></html>", "<html><center>FECHA DE<br> MAQUINADO</center></html>", "TEMPLE", "AJUSTE", "PRODUCCIÓN ", "FACTURA"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 1260, 260));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 0, 70, -1));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        jButton5.setText("AGREGAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 160, 30));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Factura.png"))); // NOI18N
        jButton6.setText("ENVIAR A FACTURACIÓN");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 230, 30));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
        int fila_seleccionada2 = jTable2.getSelectedRow();
        if(fila_seleccionada >= 0 ){
            try {
                SegPzsDaTMGUI.this.dispose();
                Solicitud modificar = new Solicitud(this.sol.get(fila_seleccionada),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(fila_seleccionada2 >= 0 ){
            try {
                SegPzsDaTMGUI.this.dispose();
                Solicitud modificar = new Solicitud(this.sol.get(fila_seleccionada2),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            CompProdMayorGUI mn = new CompProdMayorGUI(mod);
            mn.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                String cadena = (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);
                Filtro(); 
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                String cadena = (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);
                Filtro2(); 
            }
        });
        trs2 = new TableRowSorter(jTable2.getModel());
        jTable2.setRowSorter(trs2);
        
        jButton4.setVisible(true);
        jButton2.setVisible(false);

    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jtxtfiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyReleased
    }//GEN-LAST:event_jtxtfiltroKeyReleased

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila_seleccionada = trs.convertRowIndexToModel(jTable2.getSelectedRow());

        if(fila_seleccionada >= 0){
            try {
                SegPzsDaTMGUI.this.dispose();
                Solicitud modificar = new Solicitud(this.sol.get(fila_seleccionada),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        } 
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       excel.WriteExcel();
       JOptionPane.showMessageDialog(this, "¡DESCARGA EXITOSA!");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            SegPzsDaTMGUI.this.dispose();
            CotizacionAgregar agregar = new CotizacionAgregar(mod);
            agregar.setVisible(true);
            agregar.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            int fila_seleccionada = jTable1.getSelectedRow();
            int fila_seleccionada2 = jTable2.getSelectedRow();
            if(fila_seleccionada >= 0){
              
               // String orden=JOptionPane.showInputDialog("INGRESAR ORDEN");

                SegPzsDaTMGUI.this.dispose();
                String orden = JOptionPane.showInputDialog("INGRESAR ORDEN");

                //El usuario digito algo y NO le dio al boton cancelar
                if(orden != null){

                    orden = (String)orden;
                    //El usuario coloco algo que no sea solo espacios
                    if(!orden.trim().equals("")){
                        //Aqui deberias seguir tu codigo al validar que todo es correcto.
                        if(this.sol_servicio.existeOrdenF(orden)== 0)
                        {
                            String contacto=JOptionPane.showInputDialog("INGRESAR CONTACTO");
                            String cotizacion = (String) jTable1.getValueAt(fila_seleccionada, 0);

                            //SELECT pzaDañada FROM cotizaciontmpd WHERE cotizacion LIKE '%13/2022%';

                            //SELECT GROUP_CONCAT(pzaDañada SEPARATOR ' y ') AS ids_alumnos FROM cotizaciontmpd WHERE cotizacion LIKE '%13/2022%';

                            //
                            Date date= new Date();
                            DateFormat fechaHoraEnvi = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String fecha = fechaHoraEnvi.format(date).toString();
                            PreparedStatement pst1 = cn.prepareStatement("INSERT INTO facturacionherramental (orden,cotizacion,componente,troquel, contacto,fechaHoraEnvi) SELECT '"+orden+"',cotizacion, componente,cotizaciontmpd.troquel, '"+contacto+"',"+fecha+" FROM cotizaciontmpd WHERE cotizacion='"+cotizacion+"'");
                            pst1.executeUpdate();

                            //piezas
                            PreparedStatement pst2 = cn.prepareStatement("UPDATE facturacionherramental SET pieza = (SELECT GROUP_CONCAT(pzaDañada SEPARATOR ', ') AS pzs FROM cotizaciontmpd WHERE cotizacion LIKE '%"+cotizacion+"%') WHERE facturacionherramental.orden='"+orden+"'");
                            pst2.executeUpdate();

                            //precio cotizacion
                            PreparedStatement pst3 = cn.prepareStatement("UPDATE facturacionherramental SET pu = (SELECT SUM(precioC) FROM solicitudtmpd WHERE cotizacion LIKE '%"+cotizacion+"%') WHERE facturacionherramental.orden='"+orden+"'");
                            pst3.executeUpdate();
                            
                            //modificar orden
                            /*PreparedStatement pst4 = cn.prepareStatement("UPDATE facturacionherramental SET orden = '"+orden+"' WHERE EXISTS (SELECT cotizacion WHERE cotizacion='"+cotizacion+"')");
                            pst4.executeUpdate();*/

                            JOptionPane.showMessageDialog(this, "ORDEN ENVIADA A FACTURACIÓN");
                            SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                            embarque.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(this, "ESTA ORDEN YA FUE ENVIADA A FACTURACIÓN.");
                            SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                            embarque.setVisible(true);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "SE INGRESÓ SOLO ESPACIOS EN BLANCO O TABULACIONES");
                        //El usuario coloco solo espacios en blanco o tabulaciones.
                        SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                        embarque.setVisible(true);
                    }
                }else{
                    //El usuario le dio al boton cancelar.
                    JOptionPane.showMessageDialog(this, "CANCELADO.");
                    SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                    embarque.setVisible(true);
                }
                }else if(fila_seleccionada2 >= 0){
                  SegPzsDaTMGUI.this.dispose(); 
                 String orden=JOptionPane.showInputDialog("INGRESAR ORDEN");
                  //El usuario digito algo y NO le dio al boton cancelar
                  if(orden != null){

                    orden = (String)orden;
                    //El usuario coloco algo que no sea solo espacios
                    if(!orden.trim().equals("")){
                       //Aqui deberias seguir tu codigo al validar que todo es correcto.
                
                        if(this.sol_servicio.existeOrdenF(orden)== 0)
                        {
                            String contacto=JOptionPane.showInputDialog("INGRESAR CONTACTO");
                            String cotizacion = (String) jTable1.getValueAt(fila_seleccionada2, 0);

                            //SELECT pzaDañada FROM cotizaciontmpd WHERE cotizacion LIKE '%13/2022%';

                            //SELECT GROUP_CONCAT(pzaDañada SEPARATOR ' y ') AS ids_alumnos FROM cotizaciontmpd WHERE cotizacion LIKE '%13/2022%';

                            //
                            PreparedStatement pst1 = cn.prepareStatement("INSERT INTO facturacionherramental (orden,cotizacion,componente,troquel, contacto) SELECT '"+orden+"',cotizacion, componente,cotizaciontmpd.troquel, '"+contacto+"' FROM cotizaciontmpd WHERE cotizacion='"+cotizacion+"'");
                            pst1.executeUpdate();

                            //piezas
                            PreparedStatement pst2 = cn.prepareStatement("UPDATE facturacionherramental SET pieza = (SELECT GROUP_CONCAT(pzaDañada SEPARATOR ', ') AS ids_alumnos FROM cotizaciontmpd WHERE cotizacion LIKE '%"+cotizacion+"%') WHERE facturacionherramental.orden='"+orden+"'");
                            pst2.executeUpdate();

                            //precio cotizacion
                            PreparedStatement pst3 = cn.prepareStatement("UPDATE facturacionherramental SET pu = (SELECT SUM(precioC) FROM solicitudtmpd WHERE cotizacion LIKE '%"+cotizacion+"%') WHERE facturacionherramental.orden='"+orden+"'");
                            pst3.executeUpdate();
                            
                             //modificar orden
                            /*PreparedStatement pst4 = cn.prepareStatement("UPDATE facturacionherramental SET orden = '"+orden+"' WHERE EXISTS (SELECT cotizacion WHERE cotizacion='"+cotizacion+"')");
                            pst4.executeUpdate();*/

                            JOptionPane.showMessageDialog(this, "ORDEN ENVIADA A FACTURACIÓN");
                            SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                            embarque.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(this, "ESTA ORDEN YA FUE ENVIADA A FACTURACIÓN.");
                            SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                            embarque.setVisible(true);
                        }
               }else{
                        JOptionPane.showMessageDialog(this, "SE INGRESÓ SOLO ESPACIOS EN BLANCO O TABULACIONES");
                        //El usuario coloco solo espacios en blanco o tabulaciones.
                        SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                        embarque.setVisible(true);
                    }
                }else{
                    //El usuario le dio al boton cancelar.
                    JOptionPane.showMessageDialog(this, "CANCELADO.");
                    SegPzsDaTMGUI embarque= new SegPzsDaTMGUI(mod);
                    embarque.setVisible(true);
                }
            }else{
                JOptionPane.showMessageDialog(this, "POR FAVOR SELECCIONE UNA FILA.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){
                try {
                    new SegPzsDaTMGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SegPzsDaTMGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
