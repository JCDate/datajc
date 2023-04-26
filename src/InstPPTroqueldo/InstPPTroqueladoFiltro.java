/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstPPTroqueldo;

import Modelos.RepDiarioProdM;
import Modelos.TroqueladoresM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.RepDiarioProd_servicio;
import Servicios.Troqueladores_servicio;
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
public class InstPPTroqueladoFiltro extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    TableRowSorter trs;
    private final toExcel excel= new toExcel(); //Generar Excel
    
    private final RepDiarioProd_servicio repDi_servicio = new RepDiarioProd_servicio();
    private List<RepDiarioProdM> repDi;
    
    private final Troqueladores_servicio troq_servicio = new Troqueladores_servicio();
    private List <TroqueladoresM> troq;
    

    /**
     * Creates new form PronosticoGUI
     */
    public InstPPTroqueladoFiltro() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.InstPPTroqueladoFiltro();

    }
    
    public InstPPTroqueladoFiltro(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.InstPPTroqueladoFiltro();

    }
    
    private void InstPPTroqueladoFiltro() throws SQLException, ClassNotFoundException {
        String fecha=JOptionPane.showInputDialog("INGRESAR FECHA \n (DD/MM/YYYY)");
         
        this.troq = this.troq_servicio.recuperarInstalador(Conexion.obtener());
        int Size = this.troq.size();
        
        String options[] =new String[Size];
        for(int i = 0; i < this.troq.size(); i++){
        options[i]  =   
                this.troq.get(i).getNombre();   
        }
        Object opera = null;
        if(Size==1){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0]},"Seleccione"
            );
        }
        if(Size==2){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1]},"Seleccione"
            );
        }
        if(Size==3){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2]},"Seleccione"
            );
        }
        if(Size==4){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3]},"Seleccione"
            );
        }
        if(Size==5){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4]},"Seleccione"
            );
        }
        if(Size==6){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5]},"Seleccione"
            );
        }
        if(Size==7){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6]},"Seleccione"
            );
        }
        if(Size==8){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7]},"Seleccione"
            );
        }
        if(Size==9){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8]},"Seleccione"
            );
        }
        if(Size==10){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9]},"Seleccione"
            );
        }
        if(Size==11){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10]},"Seleccione"
            );
        }
        if(Size==12){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11]},"Seleccione"
            );
        }
        if(Size==13){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12]},"Seleccione"
            );
        }
        if(Size==14){
            opera = JOptionPane.showInputDialog(null,"Seleccionar Instalador", "INSTALADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13]},"Seleccione"
            );
        }
       try{
            
            PreparedStatement consulta;
            consulta = cn.prepareStatement("SELECT * FROM instpptroquelado WHERE operador='"+opera+"' AND fecha='"+fecha+"'");
            ResultSet resultado = consulta.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            while(resultado.next()){
               dtm.addRow(new Object[]{ 
                   resultado.getString("componente"),
                   resultado.getString("cr"), 
                   resultado.getString("calibre"), 
                   resultado.getString("fecha"), 
                   resultado.getInt("operacion"), 
                   resultado.getString("troquel"), 
                   resultado.getString("maquina"), 
                   resultado.getString("operador"), 
                   resultado.getString("libs"), 
                   resultado.getString("causaR")
               });                                                                                                                                                                                                                                                                                            
            }
           
            Statement comando=cn.createStatement();
            
            //TOTAL DE estampas terminadas
            ResultSet registro = comando.executeQuery("SELECT COUNT(*) AS cont FROM instpptroquelado WHERE operador='"+opera+"' AND fecha='"+fecha+"'");
            if(registro.next()==true) {
                jLabel6.setText(registro.getString("cont"));                
            }else{
                jLabel6.setText("0");
            }
            
  
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
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
        int ColumnaTabla = 0;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla,3,7));
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
        jButton2 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("FILTRO POR FECHA E INSTALADOR");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CERRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 587, 120, -1));

        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 130, 210, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 140, -1, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("TOTAL INSTALADAS:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 570, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 570, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "COMPONENTE", "C/R", "CALIBRE", "FECHA", "OPERACION", "TROQUEL", "NO. MAQUINA", "INSTALADOR", "LBS", "CAUSA DE REP."
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 1260, 390));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        InstPPTroqueladoFiltro.this.dispose();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
       jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                String cadena= (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);
                Filtro(); 
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        
        
    }//GEN-LAST:event_jtxtfiltroKeyTyped

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
            java.util.logging.Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new InstPPTroqueladoFiltro().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InstPPTroqueladoFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
