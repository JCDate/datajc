/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;

import MateriaPrima.MateriaPirmaGUI;
import Modelos.InventarioM;
import Modelos.Usuarios;
import Proveedores.ProveedoresGUI;
import Servicios.Conexion;
import Servicios.Inventario_servicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class InventarioGUI extends javax.swing.JFrame {
    private final Inventario_servicio inventario_servicio = new Inventario_servicio();
    private List<InventarioM> inventario;
    
    Conexion cc = new Conexion();
    Connection cn;
    
    private final toExcel excel = new toExcel();
    TableRowSorter trs;
    Usuarios mod;
    //Servidor
    public String direcciomImg="img\\jc.png";

                

    public InventarioGUI() throws SQLException, ClassNotFoundException {
        initComponents();
        this.cn = Conexion.obtener();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.inventarioGUI();
        jButton4.setVisible(false);
        
        //Boton Exportar Inventario
        jButton6.setFocusPainted(false);
        jButton6.setBorderPainted(false);
        //jButton6.setContentAreaFilled(false);
        
        //Boton PDF
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
        //jButton5.setContentAreaFilled(false);
        
        //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
    }
    public InventarioGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        initComponents();
        this.cn = Conexion.obtener();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.inventarioGUI();
        this.suma();
        this.jTable1.setDefaultRenderer(Object.class, new MiRenderer());
        jButton4.setVisible(false);
        
        //Boton Exportar Inventario
        jButton6.setFocusPainted(false);
        jButton6.setBorderPainted(false);
        //jButton6.setContentAreaFilled(false);
        
        //Boton PDF
        jButton5.setFocusPainted(false);
        jButton5.setBorderPainted(false);
       // jButton5.setContentAreaFilled(false);
        
        //Boton Regresar
        jButton3.setFocusPainted(false);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
            jButton4.setEnabled(false);
            jButton1.setEnabled(false);           
        }
        if(mod.getId_tipo()== 3){
            jButton1.setEnabled(false);           
        }
    }
    
     @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
     
    private void inventarioGUI() {
        try{
            this.inventario = this.inventario_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.inventario.size(); i++){
                dtm.addRow(new Object[]{
                    this.inventario.get(i).getCalibre(),
                    this.inventario.get(i).getDescripcion(),
                    this.inventario.get(i).getNum_calibre(),
                    this.inventario.get(i).getUnidad(),
                    this.inventario.get(i).getMilesimas(),
                    this.inventario.get(i).getExistencia(),
                    this.inventario.get(i).getKgs(), 
                    this.inventario.get(i).getTolerancia(),
                    this.inventario.get(i).getPeso_hoja(),
                    this.inventario.get(i).getObservaciones()
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
    
     public void suma(){
         
        try {
            jLabel6.setText("");

            Statement comando=cn.createStatement();
           
            //Suma de KGS del Inventario
            ResultSet registro = comando.executeQuery("SELECT TRUNCATE(SUM(kgs),3) AS suma FROM inventario ");
            if(registro.next()==true) {
                jLabel6.setText(registro.getString("suma"));                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
      public void inventarioPDF() throws IOException, SQLException{
        try (PDDocument doc = new PDDocument()) {
            float POINTS_PER_INCH = 72;
            float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
            
            PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
            doc.addPage(page);

            //Imagen logo

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
            //Conectar base de datos
            Statement comando=cn.createStatement();
            
            
            String semana= JOptionPane.showInputDialog("Semana:" );
            String del= JOptionPane.showInputDialog("Del:" );


            PDImageXObject pdImage = PDImageXObject. createFromFile (direcciomImg, doc);
            
            contentStream.drawImage(pdImage, 60,  page.getMediaBox().getHeight() - 90,70,70);
            // Text
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.newLineAtOffset( 700, page.getMediaBox().getHeight() - 30);
            contentStream.showText("PLANTA CD. GUZMÁN, JALISCO ");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset( 250, page.getMediaBox().getHeight() - 50);
            contentStream.showText("SEMANA: "+ semana);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset( 420, page.getMediaBox().getHeight() - 50);
            contentStream.showText("FECHA: ");
            contentStream.endText();
            
           ResultSet registro = comando.executeQuery("SELECT NOW() AS fechaH");
            if (registro.next()==true) {
                    String val1 = registro.getString("fechaH");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 50);
                    contentStream.showText(val1);
                    contentStream.endText();
            }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset( 280, page.getMediaBox().getHeight() - 70);
            contentStream.showText("DEL: "+ del);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset( 510, page.getMediaBox().getHeight() - 70);
            contentStream.showText("ELABORÓ:  SAÚL ARZATE");
            contentStream.endText();
            
            contentStream.setNonStrokingColor(Color.RED);
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 100);
            contentStream.showText("INVENTARIO FÍSICO SEMANAL DE MATERIA PRIMA");
            contentStream.endText();
            
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 110);
            contentStream.stroke();
            
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(30,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.setNonStrokingColor(Color.BLUE);
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset( 40, page.getMediaBox().getHeight() - 120);
            contentStream.showText("CALIBRE");
            contentStream.endText();
            
            
            contentStream.moveTo(100,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(100,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(150, page.getMediaBox().getHeight() - 120);
            contentStream.showText("DESCRIPCIÓN");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(110, page.getMediaBox().getHeight() - 130);
            contentStream.showText("(ESPESOR O CALIBRE, ANCHO Y LARGO)");
            contentStream.endText();
            
            contentStream.moveTo(280,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(280,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(285, page.getMediaBox().getHeight() - 120);
            contentStream.showText("NO. CALIBRE");
            contentStream.endText();
            
            contentStream.moveTo(340,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(340,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(345, page.getMediaBox().getHeight() - 120);
            contentStream.showText("UNIDAD");
            contentStream.endText();
            
            contentStream.moveTo(380,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(380,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(385, page.getMediaBox().getHeight() - 120);
            contentStream.showText("MILÉSIMAS");
            contentStream.endText();
            
            contentStream.moveTo(430,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(430,  page.getMediaBox().getHeight() - 555);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(433, page.getMediaBox().getHeight() - 120);
            contentStream.showText("EXISTENCIA EN");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(433, page.getMediaBox().getHeight() - 130);
            contentStream.showText("HOJA Y ROLLO");
            contentStream.endText();
            
            contentStream.moveTo(473,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(473,  page.getMediaBox().getHeight() - 555);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(485, page.getMediaBox().getHeight() - 120);
            contentStream.showText("KGS");
            contentStream.endText();
            
            contentStream.moveTo(520,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(520,  page.getMediaBox().getHeight() - 555);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(523, page.getMediaBox().getHeight() - 120);
            contentStream.showText("TOLERANCIA");
            contentStream.endText();
            
            contentStream.moveTo(577,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(577,  page.getMediaBox().getHeight() - 555);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 120);
            contentStream.showText("PESO POR");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(590, page.getMediaBox().getHeight() - 130);
            contentStream.showText("HOJA");
            contentStream.endText();
            
            contentStream.moveTo(625,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(625,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(690, page.getMediaBox().getHeight() - 120);
            contentStream.showText("OBSERVACIONES");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(626, page.getMediaBox().getHeight() - 130);
            contentStream.showText("(NO. ROLLOS/CERTIFICADO DEL PROVEEDOR)");
            contentStream.endText();
            
            contentStream.setNonStrokingColor(Color.BLACK);
            
            contentStream.moveTo(810,  page.getMediaBox().getHeight() - 110);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 135);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 135);
            contentStream.stroke();
            
            String calibre1=null;
            String calibre2=null;
            String calibre3=null;
            String calibre4=null;
            String calibre5=null;
            String calibre6=null;
            String calibre7=null;
            String calibre8=null;
            String calibre9=null;
            String calibre10=null;
            String calibre11=null;
            String calibre12=null;
            String calibre13=null;
            String calibre14=null;
            String calibre15=null;
            String calibre16=null;
            String calibre17=null;
            String calibre18=null;
            String calibre19=null;
            String calibre20=null;
            String calibre21=null;
            String calibre22=null;
            String calibre23=null;
            String calibre24=null;
            String calibre25=null;
            String calibre26=null;
            String calibre27=null;
            
            ResultSet registroO = comando.executeQuery("SELECT calibre FROM inventario ");
                if (registroO.next()==true) {
                        calibre1 = registroO.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(calibre1);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre1+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre1+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre1+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre1+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre1+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre1+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre1+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre1+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre1+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 150);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 150);
            contentStream.stroke();
                
                ResultSet registroCal2 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"'");
                if (registroCal2.next()==true) {
                        calibre2 = registroCal2.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 160);
                        contentStream.showText(calibre2);
                        contentStream.endText();                       
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre2+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre2+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre2+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre2+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre2+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre2+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre2+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre2+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre2+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 160);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 165);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 165);
            contentStream.stroke();
                
                ResultSet registroCal3 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"'");
                if (registroCal3.next()==true) {
                        calibre3 = registroCal3.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 175);
                        contentStream.showText(calibre3);
                        contentStream.endText(); 
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre3+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre3+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre3+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre3+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre3+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre3+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre3+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre3+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre3+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 175);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 180);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 180);
            contentStream.stroke();
                
                ResultSet registroCal4 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"'");
                if (registroCal4.next()==true) {
                        calibre4 = registroCal4.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 190);
                        contentStream.showText(calibre4);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre4+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre4+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre4+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre4+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre4+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre4+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre4+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre4+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre4+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 195);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 195);
            contentStream.stroke();
                
                ResultSet registroCal5 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"'");
                if (registroCal5.next()==true) {
                        calibre5 = registroCal5.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 205);
                        contentStream.showText(calibre5);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre5+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre5+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre5+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre5+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre5+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre5+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre5+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre5+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre5+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 205);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 210);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 210);
            contentStream.stroke();
                
                ResultSet registroCal6 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"'");
                if (registroCal6.next()==true) {
                        calibre6 = registroCal6.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 220);
                        contentStream.showText(calibre6);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre6+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre6+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre6+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre6+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre6+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre6+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre6+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre6+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre6+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 220);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 225);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 225);
            contentStream.stroke();
               
                ResultSet registroCal7 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"'");
                if (registroCal7.next()==true) {
                        calibre7 = registroCal7.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 235);
                        contentStream.showText(calibre7);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre7+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre7+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre7+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre7+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre7+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre7+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre7+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre7+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre7+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 235);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 240);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 240);
            contentStream.stroke();
                
                ResultSet registroCal8 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"'");
                if (registroCal8.next()==true) {
                        calibre8 = registroCal8.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 250);
                        contentStream.showText(calibre8);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre8+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre8+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre8+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre8+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre8+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre8+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre8+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre8+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre8+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 255);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 255);
            contentStream.stroke();
                
                ResultSet registroCal9 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"'");
                if (registroCal9.next()==true) {
                        calibre9 = registroCal9.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 265);
                        contentStream.showText(calibre9);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre9+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre9+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre9+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre9+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre9+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre9+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre9+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre9+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre9+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 265);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 270);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 270);
            contentStream.stroke();
                
                ResultSet registroCal10 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"'");
                if (registroCal10.next()==true) {
                        calibre10 = registroCal10.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 280);
                        contentStream.showText(calibre10);
                        contentStream.endText();                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre10+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre10+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre10+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre10+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre10+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre10+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre10+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre10+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre10+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 280);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 285);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 285);
            contentStream.stroke();
                
                ResultSet registroCal11 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"'");
                if (registroCal11.next()==true) {
                        calibre11 = registroCal11.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 295);
                        contentStream.showText(calibre11);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre11+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre11+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre11+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre11+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre11+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre11+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre11+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre11+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre11+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 295);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 300);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 300);
            contentStream.stroke();
                
                ResultSet registroCal12 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"'");
                if (registroCal12.next()==true) {
                        calibre12 = registroCal12.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 310);
                        contentStream.showText(calibre12);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre12+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre12+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre12+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre12+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre12+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre12+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre12+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre12+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre12+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 315);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 315);
            contentStream.stroke();
                
                ResultSet registroCal13 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"'");
                if (registroCal13.next()==true) {
                        calibre13 = registroCal13.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 325);
                        contentStream.showText(calibre13);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre13+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre13+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre13+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre13+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre13+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre13+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre13+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre13+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre13+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 325);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 330);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 330);
            contentStream.stroke();
                
                ResultSet registroCal14 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"'");
                if (registroCal14.next()==true) {
                        calibre14 = registroCal14.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 340);
                        contentStream.showText(calibre14);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre14+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre14+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre14+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre14+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre14+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre14+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre14+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre14+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre14+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 340);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 345);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 345);
            contentStream.stroke();
                
                ResultSet registroCal15 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"'");
                if (registroCal15.next()==true) {
                        calibre15 = registroCal15.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 355);
                        contentStream.showText(calibre15);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre15+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre15+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre15+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre15+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre15+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre15+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre15+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre15+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre15+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 355);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 360);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 360);
            contentStream.stroke();
                
                ResultSet registroCal16 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"'");
                if (registroCal16.next()==true) {
                        calibre16 = registroCal16.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 370);
                        contentStream.showText(calibre16);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre16+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre16+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre16+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre16+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre16+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre16+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre16+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre16+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre16+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 375);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 375);
            contentStream.stroke();
                
                ResultSet registroCal17 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"'");
                if (registroCal17.next()==true) {
                        calibre17 = registroCal17.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 385);
                        contentStream.showText(calibre17);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre17+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre17+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre17+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre17+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre17+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre17+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre17+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre17+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre17+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 385);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 390);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 390);
            contentStream.stroke();
                
                ResultSet registroCal18 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"'");
                if (registroCal18.next()==true) {
                        calibre18 = registroCal18.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 400);
                        contentStream.showText(calibre18);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre18+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre18+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre18+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre18+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre18+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre18+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre18+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre18+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre18+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 400);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 405);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 405);
            contentStream.stroke();
                
                ResultSet registroCal19 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"'");
                if (registroCal19.next()==true) {
                        calibre19 = registroCal19.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 415);
                        contentStream.showText(calibre19);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre19+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre19+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre19+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre19+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre19+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre19+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre19+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre19+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre19+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 415);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 420);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 420);
            contentStream.stroke();
                
                ResultSet registroCal20 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"'");
                if (registroCal20.next()==true) {
                        calibre20 = registroCal20.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 430);
                        contentStream.showText(calibre20);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre20+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre20+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre20+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre20+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre20+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre20+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre20+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre20+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre20+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 435);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 435);
            contentStream.stroke();
                
                ResultSet registroCal21 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'");
                if (registroCal21.next()==true) {
                        calibre21 = registroCal21.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 445);
                        contentStream.showText(calibre21);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre21+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre21+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre21+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre21+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre21+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre21+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre21+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre21+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre21+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 445);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 450);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 450);
            contentStream.stroke();
                
                ResultSet registroCal22 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"'");
                if (registroCal22.next()==true) {
                        calibre22 = registroCal22.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 460);
                        contentStream.showText(calibre22);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre22+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre22+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre22+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre22+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre22+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre22+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre22+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre22+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre22+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 460);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 465);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 465);
            contentStream.stroke();
                
                ResultSet registroCal23 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"' AND calibre!='"+calibre22+"'");
                if (registroCal23.next()==true) {
                        calibre23 = registroCal23.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 475);
                        contentStream.showText(calibre23);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre23+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre23+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre23+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre23+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre23+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre23+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre23+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre23+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre23+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 475);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 480);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 480);
            contentStream.stroke();
            
            ResultSet registroCal24 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"' AND calibre!='"+calibre22+"' AND calibre!='"+calibre23+"'");
                if (registroCal24.next()==true) {
                        calibre24 = registroCal24.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 490);
                        contentStream.showText(calibre24);
                        contentStream.endText();
                        
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre24+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre24+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre24+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre24+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre24+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre24+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre24+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre24+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre24+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 495);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 495);
            contentStream.stroke();
                
                ResultSet registroCal25 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"' AND calibre!='"+calibre22+"' AND calibre!='"+calibre23+"' AND calibre!='"+calibre24+"'");
                if (registroCal25.next()==true) {
                        calibre25 = registroCal25.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 505);
                        contentStream.showText(calibre25);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre25+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre25+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre25+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre25+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre25+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre25+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre25+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre25+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre25+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 505);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 510);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 510);
            contentStream.stroke();
                
                ResultSet registroCal26 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"' AND calibre!='"+calibre22+"' AND calibre!='"+calibre23+"' AND calibre!='"+calibre24+"' AND calibre!='"+calibre25+"'");
                if (registroCal26.next()==true) {
                        calibre26 = registroCal26.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 520);
                        contentStream.showText(calibre26);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre26+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre26+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre26+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre26+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre26+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre26+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre26+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre26+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre26+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 520);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 525);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 525);
            contentStream.stroke();
                
                ResultSet registroCal27 = comando.executeQuery("SELECT calibre FROM inventario WHERE calibre!='"+calibre1+"' AND calibre!='"+calibre2+"' AND calibre!='"+calibre3+"' AND calibre!='"+calibre4+"' AND calibre!='"+calibre5+"' AND calibre!='"+calibre6+"' AND calibre!='"+calibre7+"' AND calibre!='"+calibre8+"' AND calibre!='"+calibre9+"' AND calibre!='"+calibre10+"' AND calibre!='"+calibre11+"' AND calibre!='"+calibre12+"' AND calibre!='"+calibre13+"' AND calibre!='"+calibre14+"' AND calibre!='"+calibre15+"' AND calibre!='"+calibre16+"' AND calibre!='"+calibre17+"' AND calibre!='"+calibre18+"' AND calibre!='"+calibre19+"' AND calibre!='"+calibre20+"'  AND calibre!='"+calibre21+"' AND calibre!='"+calibre22+"' AND calibre!='"+calibre23+"' AND calibre!='"+calibre24+"' AND calibre!='"+calibre25+"' AND calibre!='"+calibre26+"'");
                if (registroCal27.next()==true) {
                        calibre27 = registroCal27.getString("calibre");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 535);
                        contentStream.showText(calibre27);
                        contentStream.endText();
                        
                        ResultSet registroD = comando.executeQuery("SELECT descripcion FROM inventario WHERE calibre= '"+calibre27+"'");
                        if (registroD.next()==true) {
                                String descripcion = registroD.getString("descripcion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(101, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(descripcion);
                                contentStream.endText();
                        }
                        
                        ResultSet registroNumC = comando.executeQuery("SELECT num_calibre FROM inventario WHERE calibre= '"+calibre27+"'");
                        if (registroNumC.next()==true) {
                                String numC = registroNumC.getString("num_calibre");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(295, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(numC);
                                contentStream.endText();
                        }
                        
                        ResultSet registroU = comando.executeQuery("SELECT unidad FROM inventario WHERE calibre= '"+calibre27+"'");
                        if (registroU.next()==true) {
                                String unidad = registroU.getString("unidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(unidad);
                                contentStream.endText();
                        }
                        
                        ResultSet registroM = comando.executeQuery("SELECT milesimas FROM inventario WHERE calibre= '"+calibre27+"'");
                        if (registroM.next()==true) {
                                String mil = registroM.getString("milesimas");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(390, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(mil);
                                contentStream.endText();
                        }
                        
                        ResultSet registroE = comando.executeQuery("SELECT existencia FROM inventario WHERE calibre='"+calibre27+"'");
                        if (registroE.next()==true) {
                                String exist = registroE.getString("existencia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(exist);
                                contentStream.endText();
                        }
                        
                        ResultSet registroK = comando.executeQuery("SELECT kgs FROM inventario WHERE calibre='"+calibre27+"'");
                        if (registroK.next()==true) {
                                String kgs = registroK.getString("kgs");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(kgs);
                                contentStream.endText();
                        }
                        
                        ResultSet registroTol = comando.executeQuery("SELECT tolerancia FROM inventario WHERE calibre='"+calibre27+"'");
                        if (registroTol.next()==true) {
                                String tolerancia = registroTol.getString("tolerancia");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(522, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(tolerancia);
                                contentStream.endText();
                        } 
                        
                        ResultSet registroPesoH = comando.executeQuery("SELECT peso_hoja FROM inventario WHERE calibre='"+calibre27+"'");
                        if (registroPesoH.next()==true) {
                                String pesoHoja = registroPesoH.getString("peso_hoja");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(580, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(pesoHoja);
                                contentStream.endText();
                        }
                        
                        ResultSet registroOb = comando.executeQuery("SELECT observaciones FROM inventario WHERE calibre='"+calibre27+"'");
                        if (registroOb.next()==true) {
                                String observaciones = registroOb.getString("observaciones");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                                contentStream.newLineAtOffset(628, page.getMediaBox().getHeight() - 535);
                                contentStream.showText(observaciones);
                                contentStream.endText();
                        }
                    }
                
            contentStream.moveTo(30,  page.getMediaBox().getHeight() - 540);
            contentStream.lineTo(810,  page.getMediaBox().getHeight() - 540);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.newLineAtOffset(440, page.getMediaBox().getHeight() - 550);
            contentStream.showText("Total");
            contentStream.endText();
            
                    ResultSet registroTotal = comando.executeQuery("SELECT TRUNCATE(SUM(kgs),2) AS total FROM inventario");
                        if (registroTotal.next()==true) {
                                String total = registroTotal.getString("total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
                                contentStream.newLineAtOffset(475, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(total);
                                contentStream.endText();
                        }
                        
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
            contentStream.newLineAtOffset(527, page.getMediaBox().getHeight() - 550);
            contentStream.showText("Kilos");
            contentStream.endText(); 
            
            contentStream.moveTo(430,  page.getMediaBox().getHeight() - 555);
            contentStream.lineTo(577,  page.getMediaBox().getHeight() - 555);
            contentStream.stroke();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
            contentStream.newLineAtOffset(690, page.getMediaBox().getHeight() - 555);
            contentStream.showText("F-AL 04 Rev 00");
            contentStream.endText(); 

            contentStream.close();
            }
            doc.save("Inventario.pdf");
            JOptionPane.showMessageDialog(this, "Lista Generada");
        }
    }
    
    public void Filtro(){
        int ColumnaTabla = 0;
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla));
    }
    

    /**to
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 36)); // NOI18N
        jLabel2.setText("INVENTARIO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 530, 50));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 130, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 130, 40));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 130, 50));

        jtxtfiltro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 120, 160, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 120, -1, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("Modificar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 130, 40));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/file_pdf_download_icon-icons.com_68954.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 80, 90));

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
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CALIBRE", "<html><center>DESCRIPCIÓN <br>(ESPESOR O CALIBRE, ANCHO Y LARGO)</html>", "NO. CALIBRE", "UNIDAD", "MILÉSIMAS", "<html>EXISTENCIA EN <br>HOJA Y ROLLO</html>", "KGS", "TOLERANCIA", "PESO POR HOJA", "<html>OBSERVACIONES <br> (NO. ROLLOS/CERTIFICADO DEL PROVEEDOR)</html>"
            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(51, 51, 255));
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 1120, 410));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("TOTAL:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 560, -1, -1));

        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 560, 90, 20));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 0, 60, 60));

        jLabel7.setText("EXPORTAR");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 60, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTable1.getSelectedRow();
        if(fila_seleccionada >= 0){
            InventarioGUI.this.dispose();
            ModificarInventario modificar = new ModificarInventario(this.inventario.get(fila_seleccionada), mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

           
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         try {
            MateriaPirmaGUI mn= new MateriaPirmaGUI(mod);
            mn.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProveedoresGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InventarioGUI.this.dispose();
        AgregarInventario vista = new AgregarInventario(mod);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

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
        jButton2.setVisible(false);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
        if(fila_seleccionada >= 0){
            InventarioGUI.this.dispose();
            ModificarInventario modificar = new ModificarInventario(this.inventario.get(fila_seleccionada),mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            inventarioPDF();
        } catch (IOException ex) {
            Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        excel.WriteExcelInventario();
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
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
            java.util.logging.Logger.getLogger(InventarioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventarioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventarioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventarioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new InventarioGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables

}