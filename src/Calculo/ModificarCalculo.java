/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculo;

import Modelos.CalculosTeoricosM;
import Modelos.DatosListaPreciosM;
import Modelos.DatosaTecnicosM;
import Modelos.Usuarios;
import Servicios.CalculosTeoricos_servicio;
import Servicios.Conexion;
import Servicios.DatosListaPrecios_servicio;
import Servicios.DatosTecnicos_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ModificarCalculo extends javax.swing.JFrame {
    private final DatosListaPrecios_servicio DLP_servicio = new DatosListaPrecios_servicio();
    private final DatosListaPreciosM DLP;
    
    private final DatosTecnicos_servicio DT_servicio = new DatosTecnicos_servicio();
    private final DatosaTecnicosM DT;
    
    private final CalculosTeoricos_servicio CT_servicio = new CalculosTeoricos_servicio();
    private final CalculosTeoricosM CT;
    
    Conexion cc = new Conexion();
    Connection cn;
   
    Usuarios mod;
    public ModificarCalculo() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.cn = Conexion.obtener();
        this.DLP = new DatosListaPreciosM();
        this.DT = new DatosaTecnicosM();
        this.CT = new CalculosTeoricosM();
        this.setDefaultCloseOperation(0);
        
    }
    
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    public ModificarCalculo(DatosListaPreciosM p_DatosListaPrecios,DatosaTecnicosM p_DatosTecnicos,CalculosTeoricosM p_CalculosTeoricos,Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.DLP = p_DatosListaPrecios;
        this.DT = p_DatosTecnicos;
        this.CT = p_CalculosTeoricos;
        this.mod = mod;
        this.setDefaultCloseOperation(0);       
        
        //Tabla datos de la lista de precios
        jTextField1.setText(this.DLP.getComponente());
        jTextField2.setText(this.DLP.getCr());
        jTextField3.setText(this.DLP.getCalibre());
        jTextField4.setText(String.valueOf(this.DLP.getBlank_kg()));
        
        //Tabla datos tecnicos de M. P.
        jTextField5.setText(String.valueOf(this.DT.getDimHojaW()));
        jTextField6.setText(String.valueOf(this.DT.getDimHojaL()));
        jTextField7.setText(String.valueOf(this.DT.getPesoH_kg()));
        jTextField8.setText(String.valueOf(this.DT.getDimRollo_p()));
        jTextField9.setText(String.valueOf(this.DT.getPesoRollo_kgs()));
        jTextField10.setText(String.valueOf(this.DT.getAnchoTira_p()));
        
        //Tabla calculos teoricos
        jTextField11.setText(String.valueOf(this.CT.getPzas_tira()));
        jTextField12.setText(String.valueOf(this.CT.getPzas_tiraEnteros()));
        jTextField13.setText(String.valueOf(this.CT.getTiras_hojas()));
        jTextField14.setText(String.valueOf(this.CT.getTira_hojasEntero()));
        jTextField15.setText(String.valueOf(this.CT.getPzas_HojasEntero()));
        jTextField16.setText(String.valueOf(this.CT.getPeso_pzasCal()));
        jTextField17.setText(String.valueOf(this.CT.getPeso_pzasLP()));      
    }

    private void guardarDLP(){
        String componente = jTextField1.getText();
        String cr = jTextField2.getText();
        String calibre = jTextField3.getText();
        String blank_kg = jTextField4.getText();
        
        if(!componente.trim().equals("")){

            double blank_kgD = Double.parseDouble(blank_kg); 
            
            this.DLP.setComponente(componente);
            this.DLP.setCr(cr);
            this.DLP.setCalibre(calibre);
            this.DLP.setBlank_kg(blank_kgD);
            
            try{    
                this.DLP_servicio.modificar(Conexion.obtener(), this.DLP);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }catch(ClassNotFoundException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "INGRESAR COMPONENTE.");
        }
        
    }
    private void guardarDT(){
        String componente = jTextField1.getText();
        String dimHojaW = jTextField5.getText();
        String dimHojaL = jTextField6.getText();
        String pesoH_kg = jTextField7.getText();
        String dimRollo_p = jTextField8.getText();
        String pesoRollo_kgs = jTextField9.getText();
        String anchoTira_p = jTextField10.getText();

            int    dimHojaWInt = Integer.parseInt(dimHojaW);
            int    dimHojaLInt = Integer.parseInt(dimHojaL);
            float pesoH_kgFloat = Float.parseFloat(pesoH_kg);
            float dimRollo_pFloat = Float.parseFloat(dimRollo_p);
            float pesoRollo_kgsFloat = Float.parseFloat(pesoRollo_kgs);
            float anchoTira_pFloat = Float.parseFloat(anchoTira_p);
            
            this.DT.setComponente(componente);
            this.DT.setDimHojaW(dimHojaWInt);
            this.DT.setDimHojaL(dimHojaLInt);
            this.DT.setPesoH_kg(pesoH_kgFloat);
            this.DT.setDimRollo_p(dimRollo_pFloat);
            this.DT.setPesoRollo_kgs(pesoRollo_kgsFloat);
            this.DT.setAnchoTira_p(anchoTira_pFloat);

            try{
                this.DT_servicio.modificar(Conexion.obtener(), this.DT);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }catch(ClassNotFoundException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }        
    }
    private void guardarCT(){
        String componente = jTextField1.getText();
        String pzas_tira = jTextField11.getText();
        String pzas_tiraEntero = jTextField12.getText();
        String tiras_hojas = jTextField13.getText();
        String tiras_hojasEntero = jTextField14.getText();
        String pzas_hojasEntero = jTextField15.getText();
        String peso_pzasCal = jTextField16.getText();
        String peso_pzasLP = jTextField17.getText();

            float pzas_tiraFloat = Float.parseFloat(pzas_tira);
            int   pzas_tiraEnteroInt = Integer.parseInt(pzas_tiraEntero);
            float tiras_hojasdimRollo_pFloat = Float.parseFloat(tiras_hojas);
            int   tiras_hojasEnteroInt = Integer.parseInt(tiras_hojasEntero);
            int   pzas_hojasEnteroInt = Integer.parseInt(pzas_hojasEntero);
            double peso_pzasCalDouble = Double.parseDouble(peso_pzasCal);
            double peso_pzasLPDouble = Double.parseDouble(peso_pzasLP);
            
            this.CT.setComponente(componente);
            this.CT.setPzas_tira(pzas_tiraFloat);
            this.CT.setPzasTiraEntero(pzas_tiraEnteroInt);
            this.CT.setTiras_hojas(tiras_hojasdimRollo_pFloat);
            this.CT.setTira_hojasEntero(tiras_hojasEnteroInt);
            this.CT.setPzas_HojasEntero(pzas_hojasEnteroInt);
            this.CT.setPeso_pzasCal(peso_pzasCalDouble);
            this.CT.setPeso_pzasLP(peso_pzasLPDouble);
            try{
                this.CT_servicio.modificar(Conexion.obtener(), this.CT);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }catch(ClassNotFoundException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
            }        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("COMPONENTE:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 190, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 480, 140, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 540, -1, 40));

        jLabel6.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel6.setText("MODIFICAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 300, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("C/R:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 100, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("BLANK KG/PZA:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 120, -1));

        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField4.setEnabled(false);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 190, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("CALIBRE:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 120, -1));

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 190, -1));

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 190, 30));

        jLabel10.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel10.setText("DATOS TECNICOS DE M. P.");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel12.setText("---------------------------------------------------------CALCULOS TEORICOS---------------------------------------------------------");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("DIMS HOJAS W:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField2.setEnabled(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 190, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("DIMS HOJAS L:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 120, -1));

        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, 190, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("PESO/HOJAS (KG):");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 140, -1));

        jTextField8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField8KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 190, 190, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("(PULGADAS):");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 300, 120, -1));

        jTextField9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField9KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 240, 190, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("DIM ROLLO");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, 120, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("PESO/ROLLO (KG):");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 250, 140, -1));

        jTextField10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField10KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 290, 190, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("ANCHO TIRA ");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 280, 140, -1));

        jTextField11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField11.setEnabled(false);
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField11KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 190, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("(PULGADAS):");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, 120, -1));

        jLabel20.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel20.setText("DATOS DE LA LISTA DE PRECIOS");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("PZAS/TIRA:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField3.setEnabled(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 190, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("(ENTERO):");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, -1, -1));

        jTextField12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField12.setEnabled(false);
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField12KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 190, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("PZAS/TIRA");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        jTextField13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField13.setEnabled(false);
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField13KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, 190, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("TIRAS/HOJAS:");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("TIRAS/HOJAS");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 410, -1, -1));

        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField14.setEnabled(false);
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField14KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 190, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("(ENTERO):");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("PZAS/HOJAS");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 470, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("(ENTERO):");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, -1, -1));

        jTextField15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField15.setEnabled(false);
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 190, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("PESO/PZAS");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("(CALCULADO):");
        getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 540, -1, -1));

        jTextField16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField16.setEnabled(false);
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 530, 190, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("PESO/PZAS");
        getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 410, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("(L DE P):");
        getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, -1, -1));

        jTextField17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField17.setEnabled(false);
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField17KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 420, 190, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("*");
        getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 290, 10, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         this.guardarDLP();  
         this.guardarDT();
         this.guardarCT();
         ModificarCalculo.this.dispose(); 
         CalculoGUI ca = new CalculoGUI(mod);
        ca.setVisible(true);
        ca.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ModificarCalculo.this.dispose();
        CalculoGUI ca = new CalculoGUI(mod);
        ca.setVisible(true);
        ca.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        jTextField2.setText("");
        jTextField3.setText("");
        String componente = jTextField1.getText();
        if(!componente.trim().equals("")){
        try {
                Statement comando=cn.createStatement();
                
                ResultSet registro = comando.executeQuery("SELECT cr FROM crs WHERE componente='"+jTextField1.getText()+"'");
                
                if (registro.next()==true) {
                        jTextField2.setText(registro.getString("cr"));
                } else {
                        JOptionPane.showMessageDialog(this, "No existe el componente.");
                }
                ResultSet registro2 = comando.executeQuery("SELECT calibre FROM estructura WHERE componente='"+jTextField1.getText()+"'");
                
                if (registro2.next()==true) {
                        jTextField3.setText(registro2.getString("calibre"));
                } else {
                        JOptionPane.showMessageDialog(this, "No existe el componente");
                }

        } catch(SQLException ex){
                setTitle(ex.toString());
        }
      }else{
            JOptionPane.showMessageDialog(this, "Favor de ingerar el componente.");
           }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
    }//GEN-LAST:event_jTextField8KeyTyped

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyTyped
    }//GEN-LAST:event_jTextField9KeyTyped

    private void jTextField10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyTyped
    }//GEN-LAST:event_jTextField10KeyTyped

    private void jTextField11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyTyped
    }//GEN-LAST:event_jTextField11KeyTyped

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyTyped
    }//GEN-LAST:event_jTextField12KeyTyped

    private void jTextField13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyTyped
    }//GEN-LAST:event_jTextField13KeyTyped

    private void jTextField14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyTyped
    }//GEN-LAST:event_jTextField14KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped
    }//GEN-LAST:event_jTextField15KeyTyped

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
    }//GEN-LAST:event_jTextField16KeyTyped

    private void jTextField17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyTyped
    }//GEN-LAST:event_jTextField17KeyTyped

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        String val1 = jTextField6.getText().trim();
        String val2 = jTextField10.getText().trim();
        
        //piezas/tira decimal
        float num1 = Float.parseFloat(val1);
        float num2 = Float.parseFloat(val2);
        jTextField11.setText((num1 / num2)+"");
        
        String val3 = jTextField11.getText().trim();
        
        //Piezas/ tira entero
        float num3 = Float.parseFloat(val3);
        int entero = (int)num3;
        jTextField12.setText((entero)+"");
        
        //Tiras/hoja
        String val4 = jTextField5.getText().trim();
        
        //tiras/hojas decimal
        float num1Tira = Float.parseFloat(val4);
        float num2Tira = Float.parseFloat(val2);
        jTextField13.setText((num1Tira / num2Tira)+"");
        
        String val5 = jTextField13.getText().trim();
        
        //Piezas/ tira entero
        float num3Tira = Float.parseFloat(val5);
        int enteroTira = (int)num3Tira;
        jTextField14.setText((enteroTira)+"");
        
        //Calcular PZA/HOJA
        String pzaTiraEn = jTextField12.getText().trim();
        String tiraHojaEn = jTextField14.getText().trim();
        
        int    a = Integer.parseInt(pzaTiraEn);
        int    b = Integer.parseInt(tiraHojaEn);
        
        jTextField15.setText((a * b)+"");
        
        //Agregar peso/ pza caulculado
        String pesoHoja = jTextField7.getText().trim();
        String pzasHojaEn = jTextField15.getText().trim();
        
        double pesoHojaF = Double.parseDouble(pesoHoja);
        double pzasHojaEnF = Double.parseDouble(pzasHojaEn);
        
       jTextField16.setText((pesoHojaF / pzasHojaEnF)+"");
       
       //Peso l de P
        String bank = jTextField4.getText().trim();
        double pesoPieza = Double.parseDouble(bank);
        jTextField17.setText((pesoPieza)+"");
        
    }//GEN-LAST:event_jTextField10ActionPerformed

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
            java.util.logging.Logger.getLogger(ModificarCalculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModificarCalculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModificarCalculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModificarCalculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ModificarCalculo().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ModificarCalculo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ModificarCalculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
