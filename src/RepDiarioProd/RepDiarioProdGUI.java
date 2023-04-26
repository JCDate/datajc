/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepDiarioProd;

import Modelos.RepDiarioProdM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.RepDiarioProd_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JC
 */
public class RepDiarioProdGUI extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    TableRowSorter trs;

    private final RepDiarioProd_servicio repDi_servicio = new RepDiarioProd_servicio();
    private List<RepDiarioProdM> repDi;
    private final toExcel excel = new toExcel();

    /**
     * Creates new form PronosticoGUI
     */
    public RepDiarioProdGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.RepDiarioProdGUI();
        this.operaciones();
        
        jButton6.setVisible(false);
        

    }
    
    public RepDiarioProdGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.RepDiarioProdGUI();
        this.operaciones();
        
        jButton6.setVisible(false);

    }
    
    private void RepDiarioProdGUI() throws SQLException, ClassNotFoundException {
       try{
            this.repDi = this.repDi_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.repDi.size(); i++){
                dtm.addRow(new Object[]{
                    this.repDi.get(i).getId(),
                    this.repDi.get(i).getFecha(),
                    this.repDi.get(i).getOperador(),
                    this.repDi.get(i).getOrden(),
                    this.repDi.get(i).getComponente(),
                    this.repDi.get(i).getCr(),
                    this.repDi.get(i).getContS(),
                    this.repDi.get(i).getNoMaquina(),
                    this.repDi.get(i).getActividad(),
                    this.repDi.get(i).getOperacion(),
                    this.repDi.get(i).getTOp(),
                    this.repDi.get(i).getInicio(),
                    this.repDi.get(i).getFin(),
                    this.repDi.get(i).getCantProd(),
                    this.repDi.get(i).getCantParcial(),
                    this.repDi.get(i).getComentario()
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
    
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    public void Filtro(){
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), 1,2,3,4));
    }
    
     public void operaciones(){
        try {
            Statement comando=cn.createStatement();

            //TOTAL DE estampas terminadas
            ResultSet registro1 = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura WHERE estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND actividad LIKE '%PROCESO%'");
            if(registro1.next()==true) {
                jLabel6.setText(registro1.getString("suma"));
            }

            //TOTAL DE estampas terminadas BAJO VOLUMEN
            ResultSet registroB1 = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
            if(registroB1.next()==true) {
                jLabel14.setText(registroB1.getString("suma"));
            }

            //TOTAL DE estampas terminadas ALTO VOLUMEN
            ResultSet registroA1 = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
            if(registroA1.next()==true) {
                jLabel12.setText(registroA1.getString("suma"));
            }

            //TOTAL DE Golpes
            ResultSet registroG1 = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod WHERE 1");
            if(registroG1.next()==true) {
                jLabel8.setText(registroG1.getString("total"));
            }

            //TOTAL DE Golpes BAJO VOLUMAN
            ResultSet registroGB1 = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
            if(registroGB1.next()==true) {
                jLabel18.setText(registroGB1.getString("total"));
            }

            //TOTAL DE Golpes ALTO VOLUMAN
            ResultSet registroGA1 = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
            if(registroGA1.next()==true) {
                jLabel16.setText(registroGA1.getString("total"));
            }

            //TOTAL DELIENAS
            ResultSet registroS1 = comando.executeQuery("SELECT COUNT(operacion) AS total FROM repdiarot, estructura WHERE repdiarot.operacion=estructura.OP AND repdiarot.componente= estructura.componente");    
            if(registroS1.next()==true) {
                jLabel10.setText(registroS1.getString("total"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void filtrarOF(String cadena){
        try {
            Statement comando=cn.createStatement();
            
            //TOTAL DE estampas terminadas
            ResultSet registro = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura WHERE repdiprod.fecha='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND actividad LIKE '%PROCESO%' OR repdiprod.operador ='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND actividad LIKE '%PROCESO%'");
            if(registro.next()==true) {
                jLabel6.setText(registro.getString("suma"));
            }
            
            //TOTAL DE estampas terminadas BAJO VOLUMEN
            ResultSet registroB = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE repdiprod.fecha='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='BAJO VOLUMEN' OR repdiprod.operador ='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
            if(registroB.next()==true) {
                jLabel14.setText(registroB.getString("suma"));
            }
            
            //TOTAL DE estampas terminadas ALTO VOLUMEN
            ResultSet registroA = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE repdiprod.fecha='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='ALTO VOLUMEN' OR repdiprod.operador ='"+cadena+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
            if(registroA.next()==true) {
                jLabel12.setText(registroA.getString("suma"));
            }

            //TOTAL DE Golpes
            ResultSet registroG = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod WHERE fecha='"+cadena+"' OR operador='"+cadena+"'");
            if(registroG.next()==true) {
                jLabel8.setText(registroG.getString("total"));
            }
            
            //TOTAL DE Golpes BAJO VOLUMAN
            ResultSet registroGB = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE fecha='"+cadena+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='BAJO VOLUMEN' OR operador='"+cadena+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
            if(registroGB.next()==true) {
                jLabel18.setText(registroGB.getString("total"));
            }
            
            //TOTAL DE Golpes ALTO VOLUMAN
            ResultSet registroGA = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE fecha='"+cadena+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='ALTO VOLUMEN' OR operador='"+cadena+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
            if(registroGA.next()==true) {
                jLabel16.setText(registroGA.getString("total"));
            }
            
            //TOTAL DELIENAS
            ResultSet registroS = comando.executeQuery("SELECT COUNT( DISTINCT orden, repdiarot.componente, operacion) AS total FROM repdiarot, estructura WHERE fecha='"+cadena+"' AND repdiarot.operacion=estructura.OP AND repdiarot.componente= estructura.componente OR operador='"+cadena+"' AND repdiarot.operacion=estructura.OP AND repdiarot.componente= estructura.componente");
            if(registroS.next()==true) {
                jLabel10.setText(registroS.getString("total"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setText("REPORTE DIARIO DE PRODUCCIÓN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "FECHA", "OPERADOR", "ORDEN", "COMPONENTE", "C/R", "<html><center>CANT. <br>SOLICITADA</center></html>", "NO. MAQUINA", "ACTIVIDAD", "OPERACION", "TOTAL OP", "INICIO", "FIN", "<html><center>CANT.<p> PRODUCIDA</p></center></html>", "<html><center>CANT. <p>PARCIAL</p></center></html>", "COMENTARIO"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 1260, 390));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CERRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 120, 40));

        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 130, 210, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 140, -1, 20));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/filtro.png"))); // NOI18N
        jButton1.setText("FILTTRAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1111, 560, 150, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 0, 60, 60));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 560, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("EST. TERMINADA:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 560, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("GOLPES:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 560, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 560, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL DE LINEAS TERMINADAS:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 570, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 570, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        jButton4.setText("BORRAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 120, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("EST. TERMINADA B.V:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 590, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("jLabel12");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 620, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("EST. TERMINADA A.V:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 620, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("jLabel14");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 590, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("GOLPES A.V:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 620, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("jLabel16");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 620, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("GOLPES B.V:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 590, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("jLabel18");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 590, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton5.setText("MODIFICAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, 30));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton6.setText("MODIFICAR");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, 30));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/actualizar.png"))); // NOI18N
        jButton7.setText("ACTUALIZAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 610, -1, 30));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        RepDiarioProdGUI.this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
      
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
                String cadena= (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);

                Filtro();
                
                filtrarOF(cadena);
                
                if(cadena.equals("")){
                     operaciones();
                }
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        
        jButton6.setVisible(true);
        jButton5.setVisible(false);
   
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            RepDiarioProdFiltro mn= new RepDiarioProdFiltro(mod);
            mn.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        excel.WriteExcelRep();
        JOptionPane.showMessageDialog(this, "¡DESCARGA EXITOSA!");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            Statement comando=cn.createStatement();
            int fila_seleccionada = jTable1.getSelectedRow();
            if(fila_seleccionada != -1){
                
                String orden = (String) jTable1.getValueAt(fila_seleccionada, 3);
                String opera = (String) jTable1.getValueAt(fila_seleccionada, 9);
                
                int id = (int) jTable1.getValueAt(fila_seleccionada, 0);
                
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    this.repDi_servicio.eliminar(id);

                    RepDiarioProdGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    ResultSet registroS = comando.executeQuery("SELECT @suma := SUM(cantProd) FROM repdiprod WHERE orden = '"+orden+"' AND operacion = '"+opera+"' AND id=id");
                    if(registroS.next()==true) {
                        PreparedStatement pst = cn.prepareStatement("UPDATE repdiprod SET cantParcial = @suma  WHERE orden = '"+orden+"' AND operacion = '"+opera+"' AND id=id");
                        pst.executeUpdate();
                    }
                    
                    RepDiarioProdGUI mp = new RepDiarioProdGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error."); 
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       int fila_seleccionada = jTable1.getSelectedRow();
        if(fila_seleccionada >= 0){
           try {
               RepDiarioProdGUI.this.dispose();
               ModificarRepDiarioPrdo modificar = new ModificarRepDiarioPrdo(this.repDi.get(fila_seleccionada), mod);
               modificar.setVisible(true);
               modificar.setLocationRelativeTo(null);
           } catch (SQLException ex) {
               Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       int fila_seleccionada2 = trs.convertRowIndexToModel(jTable1.getSelectedRow());
        if(fila_seleccionada2 >= 0){
           try {
               RepDiarioProdGUI.this.dispose();
               ModificarRepDiarioPrdo modificar = new ModificarRepDiarioPrdo(this.repDi.get(fila_seleccionada2),mod);
               modificar.setVisible(true);
               modificar.setLocationRelativeTo(null);
           } catch (SQLException ex) {
               Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            RepDiarioProdGUI.this.dispose();
            
            PreparedStatement pstD = cn.prepareStatement("DELETE FROM repdiarot WHERE 1");
            pstD.executeUpdate();
            
            PreparedStatement pst3 = cn.prepareStatement("UPDATE repdiprod JOIN estructura ON estructura.componente = repdiprod.componente SET repdiprod.tOp = estructura.OP");
            pst3.executeUpdate();
            
            PreparedStatement pst = cn.prepareStatement("INSERT INTO repdiarot (repdiarot.id, repdiarot.orden,repdiarot.componente, repdiarot.contS, repdiarot.operacion, repdiarot.cantProd, repdiarot.cantParcial, repdiarot.fecha, repdiarot.operador) SELECT repdiprod.id, repdiprod.orden, repdiprod.componente, repdiprod.contS, repdiprod.operacion, repdiprod.cantProd, repdiprod.cantParcial, repdiprod.fecha, repdiprod.operador FROM repdiprod, estructura WHERE NOT EXISTS ( SELECT repdiarot.orden FROM repdiarot, estructura WHERE repdiarot.id = repdiprod.id AND repdiarot.orden = repdiprod.orden) AND repdiprod.operacion=estructura.OP AND repdiprod.cantParcial >= repdiprod.contS AND estructura.componente= repdiprod.componente");
            pst.executeUpdate();
            
            PreparedStatement pst2 = cn.prepareStatement("INSERT INTO repdiarot (repdiarot.id,repdiarot.orden, repdiarot.componente, repdiarot.contS, repdiarot.operacion, repdiarot.cantProd, repdiarot.cantParcial, repdiarot.fecha, repdiarot.operador) SELECT repdiprod.id, repdiprod.orden, repdiprod.componente, repdiprod.contS,repdiprod.operacion, repdiprod.cantProd, repdiprod.cantParcial, repdiprod.fecha, repdiprod.operador FROM repdiprod, repdiarot WHERE repdiprod.orden=repdiarot.orden AND NOT EXISTS ( SELECT repdiarot.id FROM repdiarot WHERE repdiarot.id=repdiprod.id AND repdiprod.orden= repdiarot.orden)");
            pst2.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS");

            RepDiarioProdGUI mp = new RepDiarioProdGUI(mod);
            mp.setVisible(true);
            mp.setLocationRelativeTo(null);
            
        } catch (SQLException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(RepDiarioProdGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RepDiarioProdGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RepDiarioProdGUI.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
