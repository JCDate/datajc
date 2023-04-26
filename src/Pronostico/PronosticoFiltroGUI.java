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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JC
 */
public class PronosticoFiltroGUI extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    TableRowSorter trs;
    private final Pronostico_servicio pronostico_servicio = new Pronostico_servicio();
    private List<PronosticoM> pronostico;
    /**
     * Creates new form PronosticoGUI
     */
    public PronosticoFiltroGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.PronosticoGUI();
        
    }
    
    public PronosticoFiltroGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.setDefaultCloseOperation(0);
        this.mod = mod;
        this.setResizable(false);
        this.PronosticoGUI();

    }
    public void Filtro(){
        int ColumnaTabla = 0;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla, 2));
    }
    
    private void PronosticoGUI()  {
 
        try {
            //Conectar base de datos
            Statement comando=cn.createStatement();
            
            JTableHeader tableHeader = jTable1.getTableHeader();
            TableColumnModel tableColumnModel = tableHeader.getColumnModel();
            
            TableColumn tableColumn = tableColumnModel.getColumn(0);
            tableColumn.setHeaderValue( "SUPPLIER" );
            tableHeader.repaint();
            
            TableColumn tableColumn2 = tableColumnModel.getColumn(1);
            tableColumn2.setHeaderValue( "COMPONENTE" );
            tableHeader.repaint();
            
            TableColumn tableColumn3 = tableColumnModel.getColumn(2);
            tableColumn3.setHeaderValue( "CALIBRE" );
            tableHeader.repaint();
            
            TableColumn tableColumn4 = tableColumnModel.getColumn(4);
            tableColumn4.setHeaderValue( "CONSUMO U." );
            tableHeader.repaint();
            
            TableColumn tableColumn5 = tableColumnModel.getColumn(5);
            tableColumn5.setHeaderValue( "TOTAL KG" );
            tableHeader.repaint();
            
            TableColumn tableColumn6 = tableColumnModel.getColumn(6);
            tableColumn6.setHeaderValue( "PESO U." );
            tableHeader.repaint();
            
            TableColumn tableColumn7 = tableColumnModel.getColumn(7);
            tableColumn7.setHeaderValue( "MONEDA" );
            tableHeader.repaint();
            
            ResultSet registro = comando.executeQuery("SET lc_time_names = 'es_MX';" );
            ResultSet registroApp2 = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%M') AS mesA" );
            
            choice1.add("MES");
            
            String val2 = null;
            if (registroApp2.next()==true) {
                val2 = registroApp2.getString("mesA");   
                
                ResultSet registroAnio = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%Y') AS anio" );
                String val = null;
                String anio1 = null;
                if (registroAnio.next()==true) {
                    val = registroAnio.getString("anio");
                    
                    ResultSet registroAnio1 = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%Y')+1 AS anio" );
                    if(registroAnio1.next() == true){
                        anio1 = registroAnio1.getString("anio");

                        if(val2.equals("enero")){                            
                            choice1.add("FEBRERO "+ val);
                            choice1.add("MARZO "+ val);
                            choice1.add("ABRIL "+ val);
                            choice1.add("MAYO "+ val); 
  
                        }
                        if(val2.equals("febrero")){                            
                            choice1.add("MARZO "+ val);
                            choice1.add("ABRIL "+ val);
                            choice1.add("MAYO "+ val);
                            choice1.add("JUNIO "+ val);
  
                        }
                        if(val2.equals("marzo")){
                            choice1.add("ABRIL "+ val);
                            choice1.add("MAYO "+ val);
                            choice1.add("JUNIO "+ val);
                            choice1.add("JULIO "+ val);

                        }
                        if(val2.equals("abril")){                           
                            choice1.add("MAYO "+ val);
                            choice1.add("JUNIO "+ val);
                            choice1.add("JULIO "+ val);
                            choice1.add("AGOSTO "+ val);;
                        }
                        if(val2.equals("mayo")){
                            choice1.add("JUNIO "+ val);
                            choice1.add("JULIO "+ val);
                            choice1.add("AGOSTO "+ val);
                            choice1.add("SEPTIEMBRE "+ val);

                        }
                        if(val2.equals("junio")){
                            choice1.add("JULIO "+ val);
                            choice1.add("AGOSTO "+ val);
                            choice1.add("SEPTIEMBRE "+ val);
                            choice1.add("OCTUBRE "+ val);

                        }
                        if(val2.equals("julio")){
                            choice1.add("AGOSTO "+ val);
                            choice1.add("SEPTIEMBRE "+ val);
                            choice1.add("OCTUBRE "+ val);
                            choice1.add("NOVIEMBRE "+ val);
                        }
                        if(val2.equals("agosto")){
                            choice1.add("SEPTIEMBRE "+ val);
                            choice1.add("OCTUBRE "+ val);
                            choice1.add("NOVIEMBRE "+ val);
                            choice1.add("DICIEMBRE "+ val);
                        }
                        if(val2.equals("septiembre")){
                            choice1.add("OCTUBRE "+ val);
                            choice1.add("NOVIEMBRE "+ val);
                            choice1.add("DICIEMBRE "+ val);
                            choice1.add("ENERO "+ anio1);
                        }
                        if(val2.equals("octubre")){
                            choice1.add("NOVIEMBRE "+ val);
                            choice1.add("DICIEMBRE "+ val);
                            choice1.add("ENERO "+ anio1);
                            choice1.add("FEBRERO "+ anio1);
                        }
                        if(val2.equals("noviembre")){
                            choice1.add("DICIEMBRE "+ val);
                            choice1.add("ENERO "+ anio1);
                            choice1.add("FEBRERO "+ anio1);
                            choice1.add("MARZO "+ anio1);
                        }
                        if(val2.equals("diciembre")){
                            choice1.add("ENERO "+ anio1);
                            choice1.add("FEBRERO "+ anio1);
                            choice1.add("MARZO "+ anio1);
                            choice1.add("ABRIL "+ anio1);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        choice1 = new java.awt.Choice();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("FILTRAR PRONOSTICO / MES");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", "", ""
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 1150, 460));

        choice1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choice1MouseClicked(evt);
            }
        });
        choice1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice1ItemStateChanged(evt);
            }
        });
        getContentPane().add(choice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 150, 20));

        jtxtfiltro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 140, 190, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, 30, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 640, 120, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 640, 110, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 640, 90, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 640, 130, 20));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/limpiar.png"))); // NOI18N
        jButton1.setText("LIMPIAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 0, 140, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/filtro1.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choice1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choice1MouseClicked
         
    }//GEN-LAST:event_choice1MouseClicked

    private void choice1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choice1ItemStateChanged
        try {
            //Conectar base de datos
            Statement comando=cn.createStatement();
           
            //Columna principal de la tabla
            JTableHeader tableHeader = jTable1.getTableHeader();
            TableColumnModel tableColumnModel = tableHeader.getColumnModel();
            
            //Seleccion de CHOICE
            String data =  choice1.getItem(choice1.getSelectedIndex());
      
            
                       
            ResultSet registro = comando.executeQuery("SET lc_time_names = 'es_MX';" ); //Mes en español
            ResultSet registroApp2 = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%M') AS mesA" );//Mes actual
            
            String val2 = null;
            if (registroApp2.next()==true) {
                val2 = registroApp2.getString("mesA");   
                
                ResultSet registroAnio = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%Y') AS anio" ); // Año actual
                String val = null;
                String anio1 = null;
                if (registroAnio.next()==true) {
                    val = registroAnio.getString("anio");
                    
                    ResultSet registroAnio1 = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%Y')+1 AS anio" );//año actual +1
                    if(registroAnio1.next() == true){
                        anio1 = registroAnio1.getString("anio");

                        if(val2.equals("enero")){                            
                            if(data.equals("FEBRERO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");                         
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MARZO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");                       
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("ABRIL "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MAYO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("febrero")){                            
                            
                            if(data.equals("MARZO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                } 

                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("ABRIL "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");                        
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MAYO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                               ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                               if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JUNIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");                        
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }                            
                        }
                        if(val2.equals("marzo")){                            
                            
                            if(data.equals("ABRIL "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                 //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1  * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MAYO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JUNIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JULIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                               if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                        }
                        if(val2.equals("abril")){                            
                            
                            if(data.equals("MAYO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JUNIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JULIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("AGOSTO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        
                        if(val2.equals("mayo")){                          
                            
                            if(data.equals("JUNIO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                } 
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("JULIO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("AGOSTO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("SEPTIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            } 
                        }
                        if(val2.equals("junio")){                            
                            
                            if(data.equals("JULIO "+val)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("AGOSTO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("SEPTIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("OCTUBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("julio")){                            
                            
                            if(data.equals("AGOSTO "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("SEPTIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("OCTUBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("NOVIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("agosto")){                            

                            if(data.equals("SEPTIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("OCTUBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("NOVIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("DICIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                 //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("septiembre")){                            

                            if(data.equals("OCTUBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("NOVIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("DICIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("ENERO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("octubre")){                            

                            if(data.equals("NOVIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("DICIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("ENERO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("FEBRERO "+anio1)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                } 
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                        }
                        if(val2.equals("noviembre")){                            

                            if(data.equals("DICIEMBRE "+val)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("ENERO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("FEBRERO "+anio1)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                } 
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MARZO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }                            
                        }
                        if(val2.equals("diciembre")){                            

                            if(data.equals("ENERO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes1 = '', '0', mes1 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes1(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes1(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes1) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("FEBRERO "+anio1)){
                                //ESCRIBE NOMBRE DE LA COLUMNA 
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes2 = '', '0', mes2 * consumoU))");
                                pst.executeUpdate();

                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes2(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes2(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                } 
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes2) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            if(data.equals("MARZO "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes3 = '', '0', mes3 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes3(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes3(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes3) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                            
                            if(data.equals("ABRIL "+anio1)){
                                TableColumn tableColumn2 = tableColumnModel.getColumn(3);
                                tableColumn2.setHeaderValue(data);
                                tableHeader.repaint();
                                
                                //Total kgs
                                PreparedStatement pst = cn.prepareStatement("UPDATE pronostico SET pronostico.totalkg = (SELECT if(mes4 = '', '0', mes4 * consumoU))");
                                pst.executeUpdate();
                                
                                //Imprimir tabla
                                this.pronostico = this.pronostico_servicio.recuperarMes4(Conexion.obtener());
                                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                dtm.setRowCount(0);
                                
                                for(int i = 0; i < this.pronostico.size(); i++ ){
                                    dtm.addRow(new Object[]{
                                        this.pronostico.get(i).getSupplier(),
                                        this.pronostico.get(i).getComponente(),
                                        this.pronostico.get(i).getCalibre(),
                                        this.pronostico.get(i).getMes4(),
                                        this.pronostico.get(i).getConsumoU(),
                                        this.pronostico.get(i).getTotalkg(),
                                        this.pronostico.get(i).getPesoUnitario(),
                                        this.pronostico.get(i).getMoneda()
                                    });
                                }
                                ResultSet registroS1 = comando.executeQuery("SELECT SUM(mes4) AS total FROM pronostico WHERE 1");
                                if (registroS1.next()==true) {
                                        jLabel3.setText("TOTAL PZS:");
                                        jLabel5.setText(registroS1.getString("total"));
                                }
                                //KGs
                                ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico");
                                if (registroS2.next()==true) {
                                        jLabel6.setText("TOTAL KGS:");
                                        jLabel7.setText(registroS2.getString("total"));
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }//GEN-LAST:event_choice1ItemStateChanged

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        try {
            jtxtfiltro.addKeyListener(new KeyAdapter(){
                //Conectar base de datos
                Statement comando=cn.createStatement();
                //@Override
                public void keyReleased(final KeyEvent ke){
                    try {
                        
                        String cadena= (jtxtfiltro.getText());
                        jtxtfiltro.setText(cadena);
                        Filtro();
                        jLabel7.setText("");
                        //KGs
                        ResultSet registroS2 = comando.executeQuery("SELECT TRUNCATE(SUM(totalkg),2) AS total FROM pronostico WHERE calibre='"+cadena+"'");
                        
                        if (registroS2.next()==true) {
                            jLabel6.setText("TOTAL KGS:");
                            jLabel7.setText(registroS2.getString("total"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            trs = new TableRowSorter(jTable1.getModel());
            jTable1.setRowSorter(trs);
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            
            //Total kgs
            PreparedStatement pst = cn.prepareStatement("DELETE FROM pronostico WHERE 1");
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "LIMPIEZA EXITOSA");
            
            PronosticoFiltroGUI.this.dispose();
            MateriaPirmaGUI mn= new MateriaPirmaGUI(mod);
            mn.setVisible(true);
            mn.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PronosticoFiltroGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PronosticoFiltroGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
