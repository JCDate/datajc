/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MateriaPrimaRec;

import MateriaPrima.MateriaPirmaGUI;
import Modelos.MateriaPrimaRecM;
import Modelos.OrdenCompra;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.MateriaPrimaRec_servicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class MateriaPirmaRecGUI extends javax.swing.JFrame {

    private final MateriaPrimaRec_servicio materiaprimarec_servicio = new MateriaPrimaRec_servicio();
    private List<MateriaPrimaRecM> mpr;

    private final toExcel excel = new toExcel();
    TableRowSorter trs;
    Usuarios mod;
    Conexion cc = new Conexion();
    Connection cn;

    public String direcciomImg = "img\\jc.png";

    //Imagen de firma
    public String direcciomImg2 = "img\\firma.gif";

    public MateriaPirmaRecGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.MateriaPrimaRecGUI();

        jButton4.setVisible(false);

        //Boton Exportar Materia Prima Recibida
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);

        //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
    }

    public MateriaPirmaRecGUI(Usuarios mod) throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setDefaultCloseOperation(0);
        this.mod = mod;
        this.setResizable(false);
        this.MateriaPrimaRecGUI();
        this.jTable1.setDefaultRenderer(Object.class, new MiRenderer());
        jButton4.setVisible(false);

        //Boton Exportar Materia Prima Recibida
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);

        //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        if (mod.getId_tipo() == 4) {
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

    private void MateriaPrimaRecGUI() {
        try {

            this.mpr = this.materiaprimarec_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for (int i = 0; i < this.mpr.size(); i++) {
                dtm.addRow(new Object[]{
                    this.mpr.get(i).getId(),
                    this.mpr.get(i).getOrdenCompra(),
                    this.mpr.get(i).getCalibre(),
                    this.mpr.get(i).getProveedores(),
                    this.mpr.get(i).getKgSolicitados(),
                    this.mpr.get(i).getKgRecibidos(),
                    this.mpr.get(i).getFactura(),
                    this.mpr.get(i).getFechaRecibida(),});
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }
    }

    public void Filtro() {
        int ColumnaTabla = 0;
        trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla, 1, 2, 3));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnActualizarPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "ORDEN COMPRA", "CALIBRE", "PROVEEDOR", "KG./H. SOLICITADOS", "KG./H.  RECIBIDOS", "FACTURA", "FECHA RECIBIDA"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 1140, 430));

        jLabel3.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel3.setText("RECIBIDA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, -1, 40));

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
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 160, 230, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 160, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 120, 60));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 150, 40));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("MODIFICAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 150, 40));

        jLabel4.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel4.setText("MATERIA PRIMA");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, -1, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 10, 60, 60));

        jLabel6.setText("EXPORTAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 70, -1, -1));

        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.setMaximumSize(new java.awt.Dimension(128, 40));
        btnAgregar.setMinimumSize(new java.awt.Dimension(128, 40));
        btnAgregar.setPreferredSize(new java.awt.Dimension(128, 40));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, -1, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnActualizarPDF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/PDF.png"))); // NOI18N
        btnActualizarPDF.setText("<html>Actualizar<br/>Orden</html>");
        btnActualizarPDF.setMaximumSize(new java.awt.Dimension(128, 40));
        btnActualizarPDF.setMinimumSize(new java.awt.Dimension(128, 40));
        btnActualizarPDF.setPreferredSize(new java.awt.Dimension(128, 40));
        btnActualizarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPDFActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizarPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, -1, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            MateriaPirmaGUI mn = new MateriaPirmaGUI(mod);
            mn.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtxtfiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyReleased
    }//GEN-LAST:event_jtxtfiltroKeyReleased

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        jtxtfiltro.addKeyListener(new KeyAdapter() {
            //@Override
            public void keyReleased(final KeyEvent ke) {
                String cadena = (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena);
                Filtro();
            }
        });
        trs = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trs);
        jButton2.setVisible(false);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
        if (fila_seleccionada >= 0) {
            try {
                MateriaPirmaRecGUI.this.dispose();
                ModificarMateriaPrimaRec modificar = new ModificarMateriaPrimaRec(this.mpr.get(fila_seleccionada), mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
            if (fila_seleccionada >= 0) {

                MateriaPirmaRecGUI.this.dispose();
                ModificarMateriaPrimaRec modificar = new ModificarMateriaPrimaRec(this.mpr.get(fila_seleccionada), mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);

            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        excel.WriteExcelMateriaPrimaRecibida();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            int fila_seleccionada = jTable1.getSelectedRow();
            if (fila_seleccionada != -1) {
                int id = (int) jTable1.getValueAt(fila_seleccionada, 0);
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (resp == JOptionPane.YES_NO_OPTION) {
                    this.materiaprimarec_servicio.eliminar(id);

                    MateriaPirmaRecGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    MateriaPirmaRecGUI mp = new MateriaPirmaRecGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);

                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            MateriaPirmaRecGUI.this.dispose();
            AgregarMateriaPrimaRec vista = new AgregarMateriaPrimaRec(mod);
            vista.setVisible(true);
            vista.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPirmaGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MateriaPirmaGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    public void ActualizarOrden(int ordenCompra) throws IOException, SQLException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            String nameFinal = null;
            String moneda = null;
            String moneda2 = null;
            String moneda3 = null;
            String moneda4 = null;
            String moneda5 = null;
            String moneda6 = null;

            //Imagen logo
            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                PDImageXObject pdImage = PDImageXObject.createFromFile(direcciomImg, doc);
                contentStream.drawImage(pdImage, 20, 650, 120, 120);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 30);
                contentStream.showText("Jorge De Jesus Cobian Ordoñez");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 50);
                contentStream.showText("Circuito Norte 1 y 3 dentro del parque  Ind.");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 70);
                contentStream.showText("Zapotlán 2000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 90);
                contentStream.showText("CD Guzmán, Jalisco");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 110);
                contentStream.showText("CP 49000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(280, page.getMediaBox().getHeight() - 130);
                contentStream.showText("Tel (341) 1330-717");
                contentStream.endText();

                contentStream.moveTo(30, 620);
                contentStream.lineTo(590, 620);
                contentStream.stroke();

                //Fecha actual
                LocalDate fechaA = LocalDate.now();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 190);
                contentStream.showText("Fecha: " + fechaA);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(270, page.getMediaBox().getHeight() - 190);
                contentStream.showText("ORDEN DE COMPRA ");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(490, page.getMediaBox().getHeight() - 190);
                contentStream.showText(String.valueOf(ordenCompra));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 230);
                contentStream.showText("Proveedor");
                contentStream.endText();
                java.sql.Statement st = cn.createStatement();
                //Nombre del proveedor
                String sqlOrdenCompra = "SELECT proveedor FROM mprecibida WHERE ordenCompra='" + ordenCompra + "' ";//si falla poner comillas dobles
                ResultSet rs = st.executeQuery(sqlOrdenCompra);
                String nomProv = null;
                while (rs.next()) {
                    nomProv = rs.getString("proveedor");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 13);
                    contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 245);
                    contentStream.showText(nomProv);
                    contentStream.endText();
                }
                //colonia del proovedor
                String sql2 = "SELECT colonia FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rs2 = st.executeQuery(sql2);
                while (rs2.next()) {
                    String ColProv = rs2.getString("colonia");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 13);
                    contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 260);
                    contentStream.showText(ColProv);
                    contentStream.endText();
                }
                //parque 
                //colonia del proovedor
                String sqlPar = "SELECT parque FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rsPar = st.executeQuery(sqlPar);
                while (rsPar.next()) {
                    String parque = rsPar.getString("parque");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 13);
                    contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 275);
                    contentStream.showText(parque);
                    contentStream.endText();
                }
                //cp del proovedor
                String sql3 = "SELECT codigoPostal FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rs3 = st.executeQuery(sql3);
                while (rs3.next()) {
                    String CPprov = rs3.getString("codigoPostal");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 13);
                    contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 290);
                    contentStream.showText("C.P. " + CPprov);
                    contentStream.endText();
                }
                //ciudad, estado, pais del proovedor
                String Cprov = null;
                String Eprov = null;
                String Pprov = null;
                String sqlC = "SELECT ciudad FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rsC = st.executeQuery(sqlC);
                while (rsC.next()) {
                    Cprov = rsC.getString("ciudad");
                }
                String sqlE = "SELECT estado FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rsE = st.executeQuery(sqlE);
                while (rsE.next()) {
                    Eprov = rsE.getString("estado");
                }
                String sqlP = "SELECT pais FROM proveedores WHERE proveedores.nombre='" + nomProv + "'";
                ResultSet rsP = st.executeQuery(sqlP);
                while (rsP.next()) {
                    Pprov = rsP.getString("pais");
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 13);
                contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 305);
                contentStream.showText(Cprov + ", " + Eprov + ", " + Pprov);
                contentStream.endText();

                //Liena inferior X
                contentStream.moveTo(30, 482);
                contentStream.lineTo(590, 482);
                contentStream.stroke();

                //Linea izquierda Y
                contentStream.moveTo(30, 482);
                contentStream.lineTo(30, 620);
                contentStream.stroke();

                //Linea derecha Y
                contentStream.moveTo(590, 482);
                contentStream.lineTo(590, 620);
                contentStream.stroke();

                //Segunda tabla
                //Liena inferior X
                contentStream.moveTo(30, 470);
                contentStream.lineTo(590, 470);
                contentStream.stroke();

                //Descripcion
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 13);
                contentStream.newLineAtOffset(35, page.getMediaBox().getHeight() - 335);
                contentStream.showText("Descripción: ");
                contentStream.endText();

                contentStream.moveTo(30, 450);
                contentStream.lineTo(590, 450);
                contentStream.stroke();

                contentStream.moveTo(30, 180);
                contentStream.lineTo(30, 470);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 370);
                contentStream.showText("Calibre");
                contentStream.endText();

                contentStream.moveTo(90, 300);
                contentStream.lineTo(90, 450);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(160, page.getMediaBox().getHeight() - 370);
                contentStream.showText("Descripción");
                contentStream.endText();

                contentStream.moveTo(310, 300);
                contentStream.lineTo(310, 450);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 370);
                contentStream.showText("P. U.");
                contentStream.endText();

                contentStream.moveTo(380, 300);
                contentStream.lineTo(380, 450);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(382, page.getMediaBox().getHeight() - 370);
                contentStream.showText("Moneda");
                contentStream.endText();

                contentStream.moveTo(430, 300);
                contentStream.lineTo(430, 450);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(438, page.getMediaBox().getHeight() - 370);
                contentStream.showText("No. Calibre");
                contentStream.endText();

                contentStream.moveTo(510, 300);
                contentStream.lineTo(510, 450);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(516, page.getMediaBox().getHeight() - 370);
                contentStream.showText("Cantidad Kg");
                contentStream.endText();

                contentStream.moveTo(590, 180);
                contentStream.lineTo(590, 470);
                contentStream.stroke();

                contentStream.moveTo(30, 418);
                contentStream.lineTo(590, 418);
                contentStream.stroke();

                contentStream.moveTo(30, 400);
                contentStream.lineTo(590, 400);
                contentStream.stroke();

                String sqlCali = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsCali = st.executeQuery(sqlCali);
                String cali1 = null;
                if (rsCali.next() == true) {
                    cali1 = rsCali.getString("calibre");
                }

                String sqlCali2 = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "' AND mprecibida.calibre!='" + cali1 + "'";
                ResultSet rsCali2 = st.executeQuery(sqlCali2);
                String cali2 = null;
                if (rsCali2.next() == true) {
                    cali2 = rsCali2.getString("calibre");
                } else {
                    cali2 = "";
                }

                String sqlCali3 = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "' AND mprecibida.calibre!='" + cali2 + "' AND mprecibida.calibre!='" + cali1 + "' ";
                ResultSet rsCali3 = st.executeQuery(sqlCali3);
                String cali3 = null;
                if (rsCali3.next() == true) {
                    cali3 = rsCali3.getString("calibre");
                } else {
                    cali3 = "";
                }

                String sqlCali4 = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "' AND mprecibida.calibre!='" + cali3 + "' AND mprecibida.calibre!='" + cali2 + "' AND mprecibida.calibre!='" + cali1 + "'";
                ResultSet rsCali4 = st.executeQuery(sqlCali4);
                String cali4 = null;
                if (rsCali4.next() == true) {
                    cali4 = rsCali4.getString("calibre");
                } else {
                    cali4 = "";
                }

                String sqlCali5 = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "' AND mprecibida.calibre!='" + cali4 + "' AND mprecibida.calibre!='" + cali3 + "' AND mprecibida.calibre!='" + cali2 + "' AND mprecibida.calibre!='" + cali1 + "'";
                ResultSet rsCali5 = st.executeQuery(sqlCali5);
                String cali5 = null;
                if (rsCali5.next() == true) {
                    cali5 = rsCali5.getString("calibre");
                } else {
                    cali5 = "";
                }

                String sqlCali6 = "SELECT proveedores.calibre FROM proveedores, mprecibida WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.ordenCompra='" + ordenCompra + "' AND mprecibida.calibre!='" + cali5 + "' AND mprecibida.calibre!='" + cali4 + "' AND mprecibida.calibre!='" + cali3 + "' AND mprecibida.calibre!='" + cali2 + "' AND mprecibida.calibre!='" + cali1 + "'";
                ResultSet rsCali6 = st.executeQuery(sqlCali6);
                String cali6 = null;
                if (rsCali6.next() == true) {
                    cali6 = rsCali6.getString("calibre");
                } else {
                    cali6 = "";
                }
                String sqlD = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali1 + "'";
                ResultSet rsD = st.executeQuery(sqlD);
                String descripcion = null;
                if (rsD.next() == true) {
                    descripcion = rsD.getString("descripcion");
                }

                String sqlD2 = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali2 + "'";
                ResultSet rsD2 = st.executeQuery(sqlD2);
                String descripcion2 = null;
                if (rsD2.next() == true) {
                    descripcion2 = rsD2.getString("descripcion");
                }

                String sqlD3 = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali3 + "'";
                ResultSet rsD3 = st.executeQuery(sqlD3);
                String descripcion3 = null;
                if (rsD3.next() == true) {
                    descripcion3 = rsD3.getString("descripcion");
                }

                String sqlD4 = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali4 + "'";
                ResultSet rsD4 = st.executeQuery(sqlD4);
                String descripcion4 = null;
                if (rsD4.next() == true) {
                    descripcion4 = rsD4.getString("descripcion");
                }

                String sqlD5 = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali5 + "'";
                ResultSet rsD5 = st.executeQuery(sqlD5);
                String descripcion5 = null;
                if (rsD5.next() == true) {
                    descripcion5 = rsD5.getString("descripcion");
                }

                String sqlD6 = "SELECT descripcion FROM inventario, materia_prima, proveedores WHERE inventario.calibre=materia_prima.calibre AND materia_prima.calibre_proveedor= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali6 + "'";
                ResultSet rsD6 = st.executeQuery(sqlD6);
                String descripcion6 = null;
                if (rsD6.next() == true) {
                    descripcion6 = rsD6.getString("descripcion");
                }

                //  String sqlPes = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='"+nomProv+"' AND materia_prima.calibre='"+cali1+"' AND preciomp.moneda='"+moneda+"' ";
                String sqlPes = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali1 + "'  ";
                ResultSet rsPes = st.executeQuery(sqlPes);
                String peso = null;
                if (rsPes.next() == true) {
                    peso = rsPes.getString("pesoUnitario");
                }

                String sqlPes2 = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali2 + "'   ";
                ResultSet rsPes2 = st.executeQuery(sqlPes2);
                String peso2 = null;
                if (rsPes2.next() == true) {
                    peso2 = rsPes2.getString("pesoUnitario");
                }

                String sqlPes3 = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali3 + "'  ";
                ResultSet rsPes3 = st.executeQuery(sqlPes3);
                String peso3 = null;
                if (rsPes3.next() == true) {
                    peso3 = rsPes3.getString("pesoUnitario");
                }

                String sqlPes4 = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali4 + "'  ";
                ResultSet rsPes4 = st.executeQuery(sqlPes4);
                String peso4 = null;
                if (rsPes4.next() == true) {
                    peso4 = rsPes4.getString("pesoUnitario");
                }

                String sqlPes5 = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali5 + "' ";
                ResultSet rsPes5 = st.executeQuery(sqlPes5);
                String peso5 = null;
                if (rsPes5.next() == true) {
                    peso5 = rsPes5.getString("pesoUnitario");
                }
                String sqlPes6 = "SELECT precioMP.pesoUnitario FROM preciomp, proveedores, materia_prima WHERE materia_prima.calibre=proveedores.calibre AND proveedores.calibre= preciomp.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali6 + "' ";
                ResultSet rsPes6 = st.executeQuery(sqlPes6);
                String peso6 = null;
                if (rsPes6.next() == true) {
                    peso6 = rsPes6.getString("pesoUnitario");
                }

                String sqlNCal = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali1 + "'";
                ResultSet rsNCal = st.executeQuery(sqlNCal);
                String numCal = null;
                if (rsNCal.next() == true) {
                    numCal = rsNCal.getString("num_calibre");
                }

                String sqlNCal2 = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali2 + "'";
                ResultSet rsNCal2 = st.executeQuery(sqlNCal2);
                String numCal2 = null;
                if (rsNCal2.next() == true) {
                    numCal2 = rsNCal2.getString("num_calibre");
                }

                String sqlNCal3 = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali3 + "'";
                ResultSet rsNCal3 = st.executeQuery(sqlNCal3);
                String numCal3 = null;
                if (rsNCal3.next() == true) {
                    numCal3 = rsNCal3.getString("num_calibre");
                }

                String sqlNCal4 = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali4 + "'";
                ResultSet rsNCal4 = st.executeQuery(sqlNCal4);
                String numCal4 = null;
                if (rsNCal4.next() == true) {
                    numCal4 = rsNCal4.getString("num_calibre");
                }

                String sqlNCal5 = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali5 + "'";
                ResultSet rsNCal5 = st.executeQuery(sqlNCal5);
                String numCal5 = null;
                if (rsNCal5.next() == true) {
                    numCal5 = rsNCal5.getString("num_calibre");
                }

                String sqlNCal6 = "SELECT num_calibre FROM inventario, materia_prima, proveedores WHERE inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND materia_prima.calibre='" + cali6 + "'";
                ResultSet rsNCal6 = st.executeQuery(sqlNCal6);
                String numCal6 = null;
                if (rsNCal6.next() == true) {
                    numCal6 = rsNCal6.getString("num_calibre");
                }

                String sqlKg = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali1 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg = st.executeQuery(sqlKg);
                String kgSolicitar = null;
                String cantidadK = null;
                if (rsKg.next() == true) {
                    cantidadK = rsKg.getString("kgSolicitados");
                    //cantidadK = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali1+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar+ "\n \n KG A SOLICITAR:" );                 
                } else {
                    cantidadK = "0";
                }

                String sqlKg2 = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali2 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg2 = st.executeQuery(sqlKg2);
                String kgSolicitar2 = null;
                String cantidadK2 = null;
                if (rsKg2.next() == true) {
                    cantidadK2 = rsKg2.getString("kgSolicitados");
                    //cantidadK2 = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali2+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar2+ "\n \n KG A SOLICITAR:" );          
                } else {
                    cantidadK2 = "0";
                }

                String sqlKg3 = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali3 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg3 = st.executeQuery(sqlKg3);
                String kgSolicitar3 = null;
                String cantidadK3 = null;
                if (rsKg3.next() == true) {
                    cantidadK3 = rsKg3.getString("kgSolicitados");
                    // cantidadK3 = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali3+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar3+ "\n \n KG A SOLICITAR:" );          
                } else {
                    cantidadK3 = "0";
                }

                String sqlKg4 = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali4 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg4 = st.executeQuery(sqlKg4);
                String kgSolicitar4 = null;
                String cantidadK4 = null;
                if (rsKg4.next() == true) {
                    cantidadK4 = rsKg4.getString("kgSolicitados");
                    //cantidadK3 = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali3+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar3+ "\n \n KG A SOLICITAR:" );          
                } else {
                    cantidadK4 = "0";
                }

                String sqlKg5 = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali5 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg5 = st.executeQuery(sqlKg5);
                String kgSolicitar5 = null;
                String cantidadK5 = null;
                if (rsKg5.next() == true) {
                    cantidadK5 = rsKg5.getString("kgSolicitados");
                    // cantidadK5 = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali3+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar3+ "\n \n KG A SOLICITAR:" );          
                } else {
                    cantidadK5 = "0";
                }

                String sqlKg6 = "SELECT kgSolicitados FROM mprecibida, proveedores WHERE mprecibida.calibre=proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND mprecibida.calibre='" + cali6 + "' AND mprecibida.ordenCompra='" + ordenCompra + "'";
                ResultSet rsKg6 = st.executeQuery(sqlKg6);
                String kgSolicitar6 = null;
                String cantidadK6 = null;
                if (rsKg6.next() == true) {
                    cantidadK6 = rsKg6.getString("kgSolicitados");
                    // cantidadK6 = JOptionPane.showInputDialog(" CANTIDAD KG \n \nCALIBRE:"+cali3+"\n KG A SOLICITAR ACTUAL: "+ kgSolicitar3+ "\n \n KG A SOLICITAR:" );          
                } else {
                    cantidadK6 = "0";
                }

                if (cali1.equals("")) {
                    descripcion = "";
                    peso = "";
                    moneda = "";
                    numCal = "";
                    cantidadK = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali1.equals("91150")) {
                        moneda = "USD";
                    } else {
                        moneda = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(cali1);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(descripcion);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(peso);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(moneda);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(numCal);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 390);
                    contentStream.showText(cantidadK);
                    contentStream.endText();
                }

                if (cali2.equals("")) {
                    descripcion2 = "";
                    peso2 = "";
                    moneda2 = "";
                    numCal2 = "";
                    cantidadK2 = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali2.equals("91150")) {
                        moneda2 = "USD";
                    } else {
                        moneda2 = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(cali2);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(descripcion2);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(peso2);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(moneda2);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(numCal2);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 410);
                    contentStream.showText(cantidadK2);
                    contentStream.endText();
                }

                if (cali3.equals("")) {
                    descripcion3 = "";
                    peso3 = "";
                    moneda3 = "";
                    numCal3 = "";
                    cantidadK3 = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali3.equals("91150")) {
                        moneda3 = "USD";
                    } else {
                        moneda3 = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(cali3);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(descripcion3);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(peso3);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(moneda3);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(numCal3);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 430);
                    contentStream.showText(cantidadK3);
                    contentStream.endText();
                }

                if (cali4.equals("")) {
                    descripcion4 = "";
                    peso4 = "";
                    moneda4 = "";
                    numCal4 = "";
                    cantidadK4 = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali4.equals("91150")) {
                        moneda4 = "USD";
                    } else {
                        moneda4 = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(cali4);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(descripcion4);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(peso4);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(moneda4);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(numCal4);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 450);
                    contentStream.showText(cantidadK4);
                    contentStream.endText();
                }

                if (cali5.equals("")) {
                    descripcion5 = "";
                    peso5 = "";
                    moneda5 = "";
                    numCal5 = "";
                    cantidadK5 = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali5.equals("91150")) {
                        moneda5 = "USD";
                    } else {
                        moneda5 = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(cali5);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(descripcion5);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(peso5);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(moneda5);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(numCal5);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 470);
                    contentStream.showText(cantidadK5);
                    contentStream.endText();
                }

                if (cali6.equals("")) {
                    descripcion6 = "";
                    peso6 = "";
                    moneda6 = "";
                    numCal6 = "";
                    cantidadK6 = "0";
                } else {
                    if (nomProv.equals("Serviacero Planos S de RL de CV") || nomProv.equals("Brown Metals Co") || cali6.equals("91150")) {
                        moneda6 = "USD";
                    } else {
                        moneda6 = "MXN";
                    }
                    //calibre 1
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    contentStream.newLineAtOffset(31, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(cali6);
                    contentStream.endText();

                    //descripcion
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(92, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(descripcion6);
                    contentStream.endText();

                    //Peso
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(320, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(peso6);
                    contentStream.endText();

                    //Moneda
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(moneda6);
                    contentStream.endText();

                    //num Cali
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(445, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(numCal6);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(518, page.getMediaBox().getHeight() - 490);
                    contentStream.showText(cantidadK6);
                    contentStream.endText();
                }

                contentStream.moveTo(30, 380);
                contentStream.lineTo(590, 380);
                contentStream.stroke();

                contentStream.moveTo(30, 360);
                contentStream.lineTo(590, 360);
                contentStream.stroke();

                contentStream.moveTo(30, 340);
                contentStream.lineTo(590, 340);
                contentStream.stroke();

                contentStream.moveTo(30, 320);
                contentStream.lineTo(590, 320);
                contentStream.stroke();

                contentStream.moveTo(30, 300);
                contentStream.lineTo(590, 300);
                contentStream.stroke();

                if (nomProv.equals("B.F STEEL DE MEXICO S.A DE C.V")) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 510);
                    contentStream.showText("Presentación:");
                    contentStream.endText();

                    String sqlPres = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "'";
                    ResultSet rsPres = st.executeQuery(sqlPres);
                    String pres = null;
                    if (rsPres.next() == true) {
                        pres = rsPres.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 510);
                        contentStream.showText(pres);
                        contentStream.endText();
                    }
                    String sqlPres2 = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND  materia_prima.calibre!='" + cali1 + "' AND inventario.unidad!='" + pres + "'";
                    ResultSet rsPres2 = st.executeQuery(sqlPres2);
                    if (rsPres2.next() == true) {
                        String pres2 = rsPres2.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 520);
                        contentStream.showText(pres2);
                        contentStream.endText();
                    }
                    contentStream.setNonStrokingColor(Color.RED);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 550);
                    contentStream.showText("Nota: Dureza máxima 48 HRB");
                    contentStream.endText();

                    contentStream.setNonStrokingColor(Color.BLACK);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 580);
                    contentStream.showText("Cantidad Total:");
                    contentStream.endText();

                    int cant = Integer.parseInt(cantidadK);
                    int cant2 = Integer.parseInt(cantidadK2);
                    int cant3 = Integer.parseInt(cantidadK3);
                    int cant4 = Integer.parseInt(cantidadK4);
                    int cant5 = Integer.parseInt(cantidadK5);
                    int cant6 = Integer.parseInt(cantidadK6);
                    int cantTotal = cant + cant2 + cant3 + cant4 + cant5 + cant6;

                    String totalCadena = String.valueOf(cantTotal);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 580);
                    contentStream.showText(totalCadena + "  kgs");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 600);
                    contentStream.showText("Crédito: 30 días");
                    contentStream.endText();

                    contentStream.moveTo(30, 180);
                    contentStream.lineTo(590, 180);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 650);
                    contentStream.showText("PLANEACIÓN");
                    contentStream.endText();

                    contentStream.moveTo(400, 90);
                    contentStream.lineTo(590, 90);
                    contentStream.stroke();

                    PDImageXObject pdImageFirma = PDImageXObject.createFromFile(direcciomImg2, doc);

                    contentStream.drawImage(pdImageFirma, 412, 55, 130, 120);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(410, page.getMediaBox().getHeight() - 715);
                    contentStream.showText("SILVIA MONROY CHAGOLLA");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.newLineAtOffset(530, page.getMediaBox().getHeight() - 780);
                    contentStream.showText("F - CO 02 REV 01");
                    contentStream.endText();

                } else if (nomProv.equals("Aceros Especiales Magaña")) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 510);
                    contentStream.showText("Presentación:");
                    contentStream.endText();

                    String sqlPres = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "'";
                    ResultSet rsPres = st.executeQuery(sqlPres);
                    String pres = null;
                    if (rsPres.next() == true) {
                        pres = rsPres.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 510);
                        contentStream.showText(pres);
                        contentStream.endText();
                    }

                    String sqlPres2 = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' AND  materia_prima.calibre!='" + cali1 + "' AND inventario.unidad!='" + pres + "'";
                    ResultSet rsPres2 = st.executeQuery(sqlPres2);
                    if (rsPres2.next() == true) {
                        String pres2 = rsPres2.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 550);
                        contentStream.showText(pres2);
                        contentStream.endText();
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 550);
                    contentStream.setNonStrokingColor(Color.red);
                    contentStream.showText("Nota: Este material se Recoge en Hojas.");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.setNonStrokingColor(Color.black);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 580);
                    contentStream.showText("Cantidad Total:");
                    contentStream.endText();

                    int cant = Integer.parseInt(cantidadK);
                    int cant2 = Integer.parseInt(cantidadK2);
                    int cant3 = Integer.parseInt(cantidadK3);
                    int cant4 = Integer.parseInt(cantidadK4);
                    int cant5 = Integer.parseInt(cantidadK5);
                    int cant6 = Integer.parseInt(cantidadK6);
                    int cantTotal = cant + cant2 + cant3 + cant4 + cant5 + cant6;

                    String totalCadena = String.valueOf(cantTotal);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 580);
                    contentStream.showText(totalCadena + "  Hojas");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 600);
                    contentStream.showText("Crédito: 30 días");
                    contentStream.endText();

                    contentStream.moveTo(30, 180);
                    contentStream.lineTo(590, 180);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 650);
                    contentStream.showText("PLANEACIÓN");
                    contentStream.endText();

                    contentStream.moveTo(400, 90);
                    contentStream.lineTo(590, 90);
                    contentStream.stroke();

                    PDImageXObject pdImageFirma = PDImageXObject.createFromFile(direcciomImg2, doc);

                    contentStream.drawImage(pdImageFirma, 412, 55, 130, 120);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(410, page.getMediaBox().getHeight() - 715);
                    contentStream.showText("SILVIA MONROY CHAGOLLA");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.newLineAtOffset(530, page.getMediaBox().getHeight() - 780);
                    contentStream.showText("F - CO 02 REV 01");
                    contentStream.endText();
                } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 530);
                    contentStream.showText("Presentación:");
                    contentStream.endText();

                    String sqlPres = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='" + nomProv + "' ";
                    ResultSet rsPres = st.executeQuery(sqlPres);
                    String pres = null;
                    if (rsPres.next() == true) {
                        pres = rsPres.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 530);
                        contentStream.showText(pres);
                        contentStream.endText();
                    }

                    String sqlPres2 = "SELECT inventario.unidad FROM inventario, materia_prima, proveedores WHERE materia_prima.aprobacion='SOLICITAR' AND inventario.calibre= materia_prima.calibre AND inventario.calibre= proveedores.calibre AND proveedores.nombre='Serviacero Planos S de RL de CV' AND  materia_prima.calibre!='" + cali1 + "' AND inventario.unidad!='" + pres + "'";
                    ResultSet rsPres2 = st.executeQuery(sqlPres2);
                    if (rsPres2.next() == true) {
                        String pres2 = rsPres2.getString("unidad");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 550);
                        contentStream.showText(pres2);
                        contentStream.endText();
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 580);
                    contentStream.showText("Cantidad Total:");
                    contentStream.endText();

                    int cant = Integer.parseInt(cantidadK);
                    int cant2 = Integer.parseInt(cantidadK2);
                    int cant3 = Integer.parseInt(cantidadK3);
                    int cant4 = Integer.parseInt(cantidadK4);
                    int cant5 = Integer.parseInt(cantidadK5);
                    int cant6 = Integer.parseInt(cantidadK6);
                    int cantTotal = cant + cant2 + cant3 + cant4 + cant5 + cant6;

                    String totalCadena = String.valueOf(cantTotal);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 580);
                    contentStream.showText(totalCadena + "  kgs");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 600);
                    contentStream.showText("Crédito: 30 días");
                    contentStream.endText();

                    contentStream.moveTo(30, 180);
                    contentStream.lineTo(590, 180);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 650);
                    contentStream.showText("PLANEACIÓN");
                    contentStream.endText();

                    contentStream.moveTo(400, 90);
                    contentStream.lineTo(590, 90);
                    contentStream.stroke();

                    //SERVIDOR
                    PDImageXObject pdImageFirma = PDImageXObject.createFromFile(direcciomImg2, doc);

                    contentStream.drawImage(pdImageFirma, 412, 55, 130, 120);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(410, page.getMediaBox().getHeight() - 715);
                    contentStream.showText("SILVIA MONROY CHAGOLLA");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                    contentStream.newLineAtOffset(530, page.getMediaBox().getHeight() - 780);
                    contentStream.showText("F - CO 02 REV 01");
                    contentStream.endText();
                }
                nameFinal = nomProv;
            }
            JOptionPane.showMessageDialog(this,"Orden Actualizada");
            doc.save(ordenCompra + "-" + nameFinal + ".pdf");
        }
    }

    private void btnActualizarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPDFActionPerformed
        int ordenCompra = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la orden de Compra"));
        try {
            this.ActualizarOrden(ordenCompra);
        } catch (IOException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarPDFActionPerformed

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
            java.util.logging.Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MateriaPirmaRecGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MateriaPirmaRecGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarPDF;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables
}
