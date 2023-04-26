/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pronostico;


import Modelos.PronosticoM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.Pronostico_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author JC
 */
public class PronosticoGeneradoGUI extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    
    private final Pronostico_servicio pronostico_servicio = new Pronostico_servicio();
    private List<PronosticoM> pronostico;
    /**
     * Creates new form PronosticoGUI
     */
    public PronosticoGeneradoGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.ColumnasTabla();
    }
    
    public PronosticoGeneradoGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.ColumnasTabla();
    }
    
    public void ColumnasTabla() throws ClassNotFoundException{
                             
        try {
             //Conectar base de datos
            Connection conexion=DriverManager.getConnection("jdbc:mysql://LAPTOP-PIT7Q5KF:3306/jc_mysql", "root", "");
            Statement comando=conexion.createStatement();
            
            JTableHeader tableHeader = jTable1.getTableHeader();
            TableColumnModel tableColumnModel = tableHeader.getColumnModel();
            
            TableColumn tableColumn = tableColumnModel.getColumn(0);
            tableColumn.setHeaderValue( "SUPPLIER" );
            tableHeader.repaint();
            
            TableColumn tableColumn2 = tableColumnModel.getColumn(1);
            tableColumn2.setHeaderValue( "COMPONENTE" );
            tableHeader.repaint();
            
            ResultSet registro = comando.executeQuery("SET lc_time_names = 'es_MX';" );
            ResultSet registroApp2 = comando.executeQuery("SELECT DATE_FORMAT(NOW(),'%M') AS mesA" );
            
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

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("FEBRERO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("MARZO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("ABRIL " + val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("MAYO " + val );

                        }
                        if(val2.equals("febrero")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("MARZO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("ABRIL "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("MAYO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("JUNIO "+ val );
                            tableHeader.repaint();

                        }
                        if(val2.equals("marzo")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("ABRIL "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("MAYO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("JUNIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("JULIO "+ val );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("abril")){
                            
                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("MAYO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("JUNIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("JULIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("AGOSTO "+ val );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("mayo")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("JUNIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("JULIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("AGOSTO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("SEPTIEMBRE "+ val );
                            tableHeader.repaint();
                        }
                        if(val2.equals("junio")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("JULIO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("AGOSTO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("SEPTIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("OCTUBRE "+ val );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("julio")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("AGOSTO "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("SEPTIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("OCTUBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("NOVIEMBRE "+ val );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("agosto")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("SEPTIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("OCTUBRE "+val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("NOVIEMBRE "+val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("DICIEMBRE "+ val );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("septiembre")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("OCTUBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("NOVIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("DICIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("ENERO "+ anio1 );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("octubre")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("NOVIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("DICIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("ENERO "+ anio1);
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("FEBRERO "+ anio1 );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("noviembre")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("DICIEMBRE "+ val );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("ENERO "+ anio1 );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("FEBRERO "+ anio1);
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("MARZO "+ anio1 );
                            tableHeader.repaint();
                        }
                        
                        if(val2.equals("diciembre")){

                            TableColumn tableColumn4 = tableColumnModel.getColumn(2);
                            tableColumn4.setHeaderValue("ENERO "+ anio1 );
                            tableHeader.repaint();

                            TableColumn tableColumn5 = tableColumnModel.getColumn(3);
                            tableColumn5.setHeaderValue("FEBRERO "+ anio1 );
                            tableHeader.repaint();

                            TableColumn tableColumn6 = tableColumnModel.getColumn(4);
                            tableColumn6.setHeaderValue("MARZO "+ anio1);
                            tableHeader.repaint();

                            TableColumn tableColumn7 = tableColumnModel.getColumn(5);
                            tableColumn7.setHeaderValue("ABRIL "+ anio1 );
                            tableHeader.repaint();
                        }
                    }
                }
            }
            
            this.pronostico = this.pronostico_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            
            for(int i = 0; i < this.pronostico.size(); i++){
                dtm.addRow(new Object[]{
                    this.pronostico.get(i).getSupplier(),
                    this.pronostico.get(i).getComponente(),
                    this.pronostico.get(i).getMes1(), 
                    this.pronostico.get(i).getMes2(), 
                    this.pronostico.get(i).getMes3(), 
                    this.pronostico.get(i).getMes4()
                });
            } 
            
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("PRONOSTICO GENERADO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

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
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 1220, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/filtro.png"))); // NOI18N
        jButton1.setText("FILTRAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 117, 150, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            PronosticoGeneradoGUI.this.dispose();
            PronosticoFiltroGUI pronostico = new PronosticoFiltroGUI(mod);
            pronostico.setVisible(true);
            pronostico.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PronosticoGeneradoGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PronosticoGeneradoGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
