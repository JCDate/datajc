/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Embarque;

import Modelos.EmbarqueM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.Embarque_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

public class EmbarqueGUI extends javax.swing.JFrame {
    private final Embarque_servicio embarque_servicio = new Embarque_servicio();
    private List<EmbarqueM> embarque;

    public String direcciomImg="img\\jc.png";

    TableRowSorter trs;
    Usuarios mod;
    Conexion cc = new Conexion();
    Connection cn;

    public EmbarqueGUI() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.Embarque();
        
        jButton4.setVisible(false);

    }
    
    public EmbarqueGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.Embarque();
        
        jButton4.setVisible(false);

    }
    
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    private void Embarque() {
        try{
            this.embarque = this.embarque_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            
            for(int i = 0; i < this.embarque.size(); i++){
                dtm.addRow(new Object[]{
                    this.embarque.get(i).getOrden(),
                    this.embarque.get(i).getComponente(),
                    this.embarque.get(i).getCr(), 
                    this.embarque.get(i).getCantidad(), 
                    this.embarque.get(i).getNoCuñetes(), 
                    this.embarque.get(i).getNoSim(),
                    this.embarque.get(i).getPesoNeto(),
                    this.embarque.get(i).getFactura(),
                    this.embarque.get(i).isFacturacion()
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
       trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla,1));
    }
    
     public void ListaEmbarquePDF() throws IOException, SQLException{
   
       try (PDDocument doc = new PDDocument()){
            PDPage page = new PDPage();
            doc.addPage(page);
            
            //Imagen logo
            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                //Conectar base de datos
                Statement comando=cn.createStatement();

                PDImageXObject pdImage = PDImageXObject. createFromFile (direcciomImg, doc);
           
                contentStream.drawImage(pdImage, 50,  page.getMediaBox().getHeight() - 45,40,40);  
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(150, page.getMediaBox().getHeight() - 30);
                contentStream.showText("LISTA DE EMBARQUE");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 30);
                contentStream.showText("F-EN 01 REV 02");
                contentStream.endText();
                
                //Linea superios horizontal
                contentStream.moveTo(360,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(500,  page.getMediaBox().getHeight() - 40);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 48);
                contentStream.showText("FECHA");
                contentStream.endText();
                
                //Fecha actual
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaA = localDate.format(formatter);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(430, page.getMediaBox().getHeight() - 48);
                contentStream.showText(fechaA);
                contentStream.endText();
                
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 50);
                contentStream.stroke();
                
                //Lineas verticales
                //linea  1
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(20,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 60);
                contentStream.showText("PT");
                contentStream.endText();
                
                //linea  2
                contentStream.moveTo(35,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(35,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(45, page.getMediaBox().getHeight() - 60);
                contentStream.showText("O. C.");
                contentStream.endText();
                
                //linea  3
                contentStream.moveTo(100,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(100,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                contentStream.newLineAtOffset(105, page.getMediaBox().getHeight() - 60);
                contentStream.showText("COMPONENTE");
                contentStream.endText();
                
                //linea  4
                contentStream.moveTo(165,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(165,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 60);
                contentStream.showText("C/R");
                contentStream.endText();
                
                //linea  5
                contentStream.moveTo(230,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(230,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 60);
                contentStream.showText("PZ. ENT.");
                contentStream.endText();
                
                //linea  6
                contentStream.moveTo(295,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(295,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(310, page.getMediaBox().getHeight() - 60);
                contentStream.showText("P.U. ($)");
                contentStream.endText();
                
                //linea  7
                contentStream.moveTo(360,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(360,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 60);
                contentStream.showText("TOTAL");
                contentStream.endText();
                
                //linea  8
                contentStream.moveTo(425,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(425,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(430, page.getMediaBox().getHeight() - 60);
                contentStream.showText("NO. CUÑETES");
                contentStream.endText();
                
                //linea  9
                contentStream.moveTo(500,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(500,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 60);
                contentStream.showText("NO. SIM");
                contentStream.endText();
                
                //linea  10
                contentStream.moveTo(560,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                //Lineas Horizontal
                //linea 
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 62);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 62);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 70);
                contentStream.showText("1");
                contentStream.endText();
                
                String orden1 = null;
                String orden2 = null;
                String orden3 = null;
                String orden4 = null;
                String orden5 = null;
                String orden6 = null;
                String orden7 = null;
                String orden8 = null;
                String orden9 = null;
                String orden10 = null;
                String orden11 = null;
                String orden12 = null;
                String orden13 = null;
                String orden14 = null;
                String orden15 = null;
                String orden16 = null;
                String orden17 = null;
                String orden18 = null;
                String orden19 = null;
                String orden20 = null;
                String orden21 = null;
                String orden22 = null;
                String orden23 = null;
                String orden24 = null;
                String orden25 = null;
                String orden26 = null;
                String orden27 = null;
                String orden28 = null;
                String orden29 = null;
                String orden30 = null;
                String orden31 = null;
                String orden32 = null;
                String orden33 = null;
                String orden34 = null;
                String orden35 = null;
                String orden36 = null;
                String orden37 = null;
                String orden38 = null;
                String orden39 = null;
                String orden40 = null;
                String orden41 = null;
                String orden42 = null;
                String orden43 = null;
                String orden44 = null;
                String orden45 = null;
                String orden46 = null;
                String orden47 = null;
                String orden48 = null; 
                String orden49 = null;
                String orden50 = null;
                String orden51 = null;
                String orden52 = null;
    
                ResultSet registro = comando.executeQuery("SELECT orden FROM embarque WHERE facturado='1'");
                if (registro.next()==true) {
                        orden1 = registro.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 70);
                        contentStream.showText(orden1);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden1+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 70);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden1+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden1+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }                  
                }
                
                //linea 2
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 74);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 74);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 82);
                contentStream.showText("2");
                contentStream.endText();
                
                ResultSet registro2 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND facturado='1'");
                if (registro2.next()==true) {
                        orden2 = registro2.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 82);
                        contentStream.showText(orden2);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden2+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 82);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden2+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden2+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }  
                }
                
                //linea 3
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 86);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 86);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 94);
                contentStream.showText("3");
                contentStream.endText();
                
                ResultSet registro3 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND facturado='1'");
                if (registro3.next()==true) {
                        orden3 = registro3.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 94);
                        contentStream.showText(orden3);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden3+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 94);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden3+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden3+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }    
                }
                
                //linea 4
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 98);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 98);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 106);
                contentStream.showText("4");
                contentStream.endText();
                
                ResultSet registro4 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND facturado='1'");
                if (registro4.next()==true) {
                        orden4 = registro4.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 106);
                        contentStream.showText(orden4);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden4+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 106);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden4+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden4+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 5
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 110);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 110);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 118);
                contentStream.showText("5");
                contentStream.endText();
                
                ResultSet registro5 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND facturado='1'");
                if (registro5.next()==true) {
                        orden5 = registro5.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 118);
                        contentStream.showText(orden5);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden5+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 118);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden5+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden5+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 6
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 122);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 122);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 130);
                contentStream.showText("6");
                contentStream.endText();
                
                ResultSet registro6 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND facturado='1'");
                if (registro6.next()==true) {
                        orden6 = registro6.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(orden6);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden6+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 130);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden6+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden6+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 7
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 134);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 134);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 142);
                contentStream.showText("7");
                contentStream.endText();
                
                ResultSet registro7 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND facturado='1'");
                if (registro7.next()==true) {
                        orden7 = registro7.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 142);
                        contentStream.showText(orden7);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden7+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 142);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden7+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden7+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 8
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 146);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 146);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 154);
                contentStream.showText("8");
                contentStream.endText();
                
                ResultSet registro8 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND facturado='1'");
                if (registro8.next()==true) {
                        orden8 = registro8.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 154);
                        contentStream.showText(orden8);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden8+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 154);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden8+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD,10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden8+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 9
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 158);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 158);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 166);
                contentStream.showText("9");
                contentStream.endText();
                
                ResultSet registro9 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"'AND orden !='"+orden8+"' AND facturado='1'");
                if (registro9.next()==true) {
                        orden9 = registro9.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 166);
                        contentStream.showText(orden9);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden9+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 166);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden9+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden9+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 10
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 170);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 170);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 178);
                contentStream.showText("10");
                contentStream.endText();
                
                ResultSet registro10 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND facturado='1'");
                if (registro10.next()==true) {
                        orden10 = registro10.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 178);
                        contentStream.showText(orden10);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden10+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 178);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden10+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden10+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 11
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 182);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 182);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 190);
                contentStream.showText("11");
                contentStream.endText();
                
                ResultSet registro11 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND facturado='1'");
                if (registro11.next()==true) {
                        orden11 = registro11.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 190);
                        contentStream.showText(orden11);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden11+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 190);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden11+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden11+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 12
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 194);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 194);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 202);
                contentStream.showText("12");
                contentStream.endText();
                
                ResultSet registro12 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND facturado='1'");
                if (registro12.next()==true) {
                        orden12 = registro12.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 202);
                        contentStream.showText(orden12);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden12+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 202);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden12+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden12+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 13
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 206);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 206);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 214);
                contentStream.showText("13");
                contentStream.endText();
                
                ResultSet registro13 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND facturado='1'");
                if (registro13.next()==true) {
                        orden13 = registro13.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 214);
                        contentStream.showText(orden13);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden13+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 214);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden13+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden13+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 14
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 218);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 218);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 226);
                contentStream.showText("14");
                contentStream.endText();
                
                ResultSet registro14 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND facturado='1'");
                if (registro14.next()==true) {
                        orden14 = registro14.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 226);
                        contentStream.showText(orden14);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden14+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 226);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden14+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden14+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 15
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 230);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 230);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 238);
                contentStream.showText("15");
                contentStream.endText();
                
                ResultSet registro15 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND facturado='1'");
                if (registro15.next()==true) {
                        orden15 = registro15.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 238);
                        contentStream.showText(orden15);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden15+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 238);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden15+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden15+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 16
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 242);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 242);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 250);
                contentStream.showText("16");
                contentStream.endText();
                
                ResultSet registro16 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND facturado='1'");
                if (registro16.next()==true) {
                        orden16 = registro16.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 250);
                        contentStream.showText(orden16);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden16+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 250);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden16+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden16+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 17
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 254);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 254);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 262);
                contentStream.showText("17");
                contentStream.endText();
                
                ResultSet registro17 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND facturado='1'");
                if (registro17.next()==true) {
                        orden17 = registro17.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 262);
                        contentStream.showText(orden17);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden17+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 262);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden17+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden17+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 18
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 266);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 266);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 274);
                contentStream.showText("18");
                contentStream.endText();
                
                ResultSet registro18 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND facturado='1'");
                if (registro18.next()==true) {
                        orden18 = registro18.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 274);
                        contentStream.showText(orden18);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden18+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 274);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden18+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden18+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 19
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 278);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 278);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 286);
                contentStream.showText("19");
                contentStream.endText();
                
                ResultSet registro19 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND facturado='1'");
                if (registro19.next()==true) {
                        orden19 = registro19.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 286);
                        contentStream.showText(orden19);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden19+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 286);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden19+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden19+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 20
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 290);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 290);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 298);
                contentStream.showText("20");
                contentStream.endText();
                
                ResultSet registro20 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND facturado='1'");
                if (registro20.next()==true) {
                        orden20 = registro20.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 298);
                        contentStream.showText(orden20);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden20+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 298);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden20+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden20+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 21
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 302);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 302);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 310);
                contentStream.showText("21");
                contentStream.endText();
                
                ResultSet registro21 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND facturado='1'");
                if (registro21.next()==true) {
                        orden21 = registro21.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 310);
                        contentStream.showText(orden21);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden21+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 310);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden21+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden21+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                 //linea 22
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 314);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 314);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 322);
                contentStream.showText("22");
                contentStream.endText();
                
                ResultSet registro22 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND facturado='1'");
                if (registro22.next()==true) {
                        orden22 = registro22.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 322);
                        contentStream.showText(orden22);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden22+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 322);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden22+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden22+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 23
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 326);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 326);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 334);
                contentStream.showText("23");
                contentStream.endText();
                
                ResultSet registro23 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND facturado='1'");
                if (registro23.next()==true) {
                        orden23 = registro23.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 334);
                        contentStream.showText(orden23);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden23+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 334);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden23+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden23+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 24
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 338);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 338);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 346);
                contentStream.showText("24");
                contentStream.endText();
                
                ResultSet registro24 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND facturado='1'");
                if (registro24.next()==true) {
                        orden24 = registro24.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 346);
                        contentStream.showText(orden24);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden24+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 346);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden24+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden24+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 25
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 350);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 350);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 358);
                contentStream.showText("25");
                contentStream.endText();
                
                ResultSet registro25 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND facturado='1'");
                if (registro25.next()==true) {
                        orden25 = registro25.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 358);
                        contentStream.showText(orden25);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden25+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 358);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden25+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden25+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 26
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 362);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 362);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 370);
                contentStream.showText("26");
                contentStream.endText();
                
                ResultSet registro26 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND facturado='1'");
                if (registro26.next()==true) {
                        orden26 = registro26.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 370);
                        contentStream.showText(orden26);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden26+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 370);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden26+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden26+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 27
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 374);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 374);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 382);
                contentStream.showText("27");
                contentStream.endText();
                
                ResultSet registro27 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND facturado='1'");
                if (registro27.next()==true) {
                        orden27 = registro27.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 382);
                        contentStream.showText(orden27);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden27+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 382);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden27+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden27+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 28
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 386);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 386);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 394);
                contentStream.showText("28");
                contentStream.endText();
                
                ResultSet registro28 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND facturado='1'");
                if (registro28.next()==true) {
                        orden28 = registro28.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 394);
                        contentStream.showText(orden28);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden28+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 394);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden28+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden28+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 29
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 398);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 398);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 406);
                contentStream.showText("29");
                contentStream.endText();
                
                ResultSet registro29 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND facturado='1'");
                if (registro29.next()==true) {
                        orden29 = registro29.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 406);
                        contentStream.showText(orden29);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden29+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 406);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden29+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden29+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 30
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 410);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 410);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 418);
                contentStream.showText("30");
                contentStream.endText();
                
                ResultSet registro30 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND facturado='1'");
                if (registro30.next()==true) {
                        orden30 = registro30.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 418);
                        contentStream.showText(orden30);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden30+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 418);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden30+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden30+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 31
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 422);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 422);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 430);
                contentStream.showText("31");
                contentStream.endText();
                
                ResultSet registro31 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND facturado='1'");
                if (registro31.next()==true) {
                        orden31 = registro31.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 430);
                        contentStream.showText(orden31);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden31+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 430);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden31+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden31+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 32
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 434);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 434);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 442);
                contentStream.showText("32");
                contentStream.endText();
                
                ResultSet registro32 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND facturado='1'");
                if (registro32.next()==true) {
                        orden32 = registro32.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 442);
                        contentStream.showText(orden32);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden32+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 442);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden32+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden32+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 33
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 446);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 446);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 454);
                contentStream.showText("33");
                contentStream.endText();
                
                ResultSet registro33 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND facturado='1'");
                if (registro33.next()==true) {
                        orden33 = registro33.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 454);
                        contentStream.showText(orden33);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden33+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 454);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden33+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden33+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 34
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 458);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 458);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 466);
                contentStream.showText("34");
                contentStream.endText();
                
                ResultSet registro34 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND facturado='1'");
                if (registro34.next()==true) {
                        orden34 = registro34.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 466);
                        contentStream.showText(orden34);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroC.next()==true) { 
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden34+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 466);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden34+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden34+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 35
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 470);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 470);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 478);
                contentStream.showText("35");
                contentStream.endText();
                
                ResultSet registro35 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND facturado='1'");
                if (registro35.next()==true) {
                        orden35 = registro35.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 478);
                        contentStream.showText(orden35);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden35+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 478);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden35+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden35+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 36
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 482);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 482);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 490);
                contentStream.showText("36");
                contentStream.endText();
                
                ResultSet registro36 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND facturado='1'");
                if (registro36.next()==true) {
                        orden36 = registro36.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 490);
                        contentStream.showText(orden36);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden36+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 490);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden36+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden36+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 37
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 494);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 494);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 502);
                contentStream.showText("37");
                contentStream.endText();
                
                ResultSet registro37 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND facturado='1'");
                if (registro37.next()==true) {
                        orden37 = registro37.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 502);
                        contentStream.showText(orden37);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden37+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 502);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden37+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden37+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 38
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 506);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 506);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 514);
                contentStream.showText("38");
                contentStream.endText();
                
                ResultSet registro38 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND facturado='1'");
                if (registro38.next()==true) {
                        orden38 = registro38.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 514);
                        contentStream.showText(orden38);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden38+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 514);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden38+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden38+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 39
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 518);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 518);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 526);
                contentStream.showText("39");
                contentStream.endText();
                
                ResultSet registro39 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND facturado='1'");
                if (registro39.next()==true) {
                        orden39 = registro39.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 526);
                        contentStream.showText(orden39);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden39+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 526);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden39+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden39+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 40
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 530);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 530);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 538);
                contentStream.showText("40");
                contentStream.endText();
                
                ResultSet registro40 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND facturado='1'");
                if (registro40.next()==true) {
                        orden40 = registro40.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 538);
                        contentStream.showText(orden40);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden40+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 538);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden40+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden40+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 41
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 542);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 542);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 550);
                contentStream.showText("41");
                contentStream.endText();
                
                ResultSet registro41 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND facturado='1'");
                if (registro41.next()==true) {
                        orden41 = registro41.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 550);
                        contentStream.showText(orden41);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden41+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 550);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden41+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden41+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 42
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 554);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 554);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 562);
                contentStream.showText("42");
                contentStream.endText();
                
                ResultSet registro42 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND facturado='1'");
                if (registro42.next()==true) {
                        orden42 = registro42.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 562);
                        contentStream.showText(orden42);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden42+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 562);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden42+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden42+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 43
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 566);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 566);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 574);
                contentStream.showText("43");
                contentStream.endText();
                
                ResultSet registro43 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND facturado='1'");
                if (registro43.next()==true) {
                        orden43 = registro43.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 574);
                        contentStream.showText(orden43);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden43+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 574);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden43+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden43+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 44
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 578);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 578);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 586);
                contentStream.showText("44");
                contentStream.endText();
                
                ResultSet registro44 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND facturado='1'");
                if (registro44.next()==true) {
                        orden44 = registro44.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 586);
                        contentStream.showText(orden44);
                        contentStream.endText();
                       
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden44+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 586);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden44+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden44+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 45
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 590);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 590);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 598);
                contentStream.showText("45");
                contentStream.endText();
                
                ResultSet registro45 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND facturado='1'");
                if (registro45.next()==true) {
                        orden45 = registro45.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 598);
                        contentStream.showText(orden45);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden45+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 598);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden45+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden45+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 46
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 602);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 602);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 610);
                contentStream.showText("46");
                contentStream.endText();
                
                ResultSet registro46 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND facturado='1'");
                if (registro46.next()==true) {
                        orden46 = registro46.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 610);
                        contentStream.showText(orden46);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden46+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 610);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden46+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden46+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 47
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 614);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 614);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 622);
                contentStream.showText("47");
                contentStream.endText();
                
                ResultSet registro47 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND facturado='1'");
                if (registro47.next()==true) {
                        orden47 = registro47.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 622);
                        contentStream.showText(orden47);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden47+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 622);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden47+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden47+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 48
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 626);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 626);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 634);
                contentStream.showText("48");
                contentStream.endText();
                
                ResultSet registro48 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND orden !='"+orden47+"' AND facturado='1'");
                if (registro48.next()==true) {
                        orden48 = registro48.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 634);
                        contentStream.showText(orden48);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden48+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 634);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden48+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden48+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 49
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 638);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 638);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 646);
                contentStream.showText("49");
                contentStream.endText();
                
                ResultSet registro49 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND orden !='"+orden47+"' AND orden !='"+orden48+"' AND facturado='1'");
                if (registro49.next()==true) {
                        orden49 = registro49.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 646);
                        contentStream.showText(orden49);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden49+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 646);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden49+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden49+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 50
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 650);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 650);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 658);
                contentStream.showText("50");
                contentStream.endText();
                
                 ResultSet registro50 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND orden !='"+orden47+"' AND orden !='"+orden48+"' AND orden !='"+orden49+"' AND facturado='1'");
                if (registro50.next()==true) {
                        orden50 = registro50.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 658);
                        contentStream.showText(orden50);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden50+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 658);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden50+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden50+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 51
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 662);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 662);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 670);
                contentStream.showText("51");
                contentStream.endText();
                
                ResultSet registro51 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND orden !='"+orden47+"' AND orden !='"+orden48+"' AND orden !='"+orden49+"' AND orden !='"+orden50+"' AND facturado='1'");
                if (registro51.next()==true) {
                        orden51 = registro51.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 670);
                        contentStream.showText(orden51);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden51+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 670);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden51+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden51+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 52
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 674);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 674);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 682);
                contentStream.showText("52");
                contentStream.endText();
                
                ResultSet registro52 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND orden !='"+orden47+"' AND orden !='"+orden48+"' AND orden !='"+orden49+"' AND orden !='"+orden50+"' AND orden !='"+orden51+"' AND facturado='1'");
                if (registro52.next()==true) {
                        orden52 = registro52.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 682);
                        contentStream.showText(orden52);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden52+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(115, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden52+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden52+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroPU = comando.executeQuery("SELECT precioU FROM preciocomponente WHERE componente= '"+componente+"'");
                        if (registroPU.next()==true) {
                                String pu = registroPU.getString("precioU");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(315, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(pu);
                                contentStream.endText();
                        }
                        
                        ResultSet registroT = comando.executeQuery("SELECT TRUNCATE((embarque.cantidad*preciocomponente.precioU),2) AS Total FROM embarque, preciocomponente WHERE embarque.componente='"+componente+"' AND preciocomponente.componente='"+componente+"' AND embarque.orden='"+orden52+"'");
                        if (registroT.next()==true) {
                                String total = registroT.getString("Total");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 682);
                                contentStream.showText("$" + total);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden52+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden52+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(505, page.getMediaBox().getHeight() - 682);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 53
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 686);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 686);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 694);
                contentStream.showText("53");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 694);
                contentStream.showText("ELABORÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(125, page.getMediaBox().getHeight() - 694);
                contentStream.showText("Saúl");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 694);
                contentStream.showText("Arzate");
                contentStream.endText();
                
                //linea 54
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 698);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 698);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 706);
                contentStream.showText("54");
                contentStream.endText();
                
                //linea 55
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 710);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 710);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 718);
                contentStream.showText("55");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() -718 );
                contentStream.showText("REVISÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(125, page.getMediaBox().getHeight() - 718);
                contentStream.showText("Silvia");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 718);
                contentStream.showText("Monroy");
                contentStream.endText();
                
                //linea 56
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 722);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 722);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 730);
                contentStream.showText("56");
                contentStream.endText();
                
                //linea 57
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 734);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 734);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(23, page.getMediaBox().getHeight() - 742);
                contentStream.showText("57");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - 742);
                contentStream.showText("APROBÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(125, page.getMediaBox().getHeight() - 742);
                contentStream.showText("Arturo");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 742);
                contentStream.showText("Kehoe");
                contentStream.endText();
                
                 //linea 58
                contentStream.moveTo(20,  page.getMediaBox().getHeight() - 746);
                contentStream.lineTo(560,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke(); 
            }
            doc.save("Lista de Embarque.pdf");
        }
    }
     
     //Lista de Embarque cliente
      public void ListaEmbarqueClientePDF() throws IOException, SQLException{
   
       try (PDDocument doc = new PDDocument()){
            PDPage page = new PDPage();
            doc.addPage(page);
            
            //Imagen logo

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                 //Conectar base de datos
                Statement comando=cn.createStatement();
                
                PDImageXObject pdImage = PDImageXObject. createFromFile (direcciomImg, doc);
 
                contentStream.drawImage(pdImage, 50,  page.getMediaBox().getHeight() - 45,40,40);  
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(150, page.getMediaBox().getHeight() - 30);
                contentStream.showText("LISTA DE EMBARQUE");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(450, page.getMediaBox().getHeight() - 30);
                contentStream.showText("F-EN 01 REV 02");
                contentStream.endText();
                
                //Linea superios horizontal
                
                contentStream.moveTo(350,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 40);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 48);
                contentStream.showText("FECHA");
                contentStream.endText();
                
                 //Fecha actual
                
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaA = localDate.format(formatter);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(460, page.getMediaBox().getHeight() - 48);
                contentStream.showText(fechaA);
                contentStream.endText();
                
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 50);
                contentStream.stroke();
                
                //Lineas verticales
                //linea  1
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(40,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 60);
                contentStream.showText("PT");
                contentStream.endText();
                
                 //linea  2
                contentStream.moveTo(55,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(55,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(75, page.getMediaBox().getHeight() - 60);
                contentStream.showText("O. C.");
                contentStream.endText();
                
                 //linea  3
                contentStream.moveTo(120,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(120,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
                contentStream.newLineAtOffset(130, page.getMediaBox().getHeight() - 60);
                contentStream.showText("COMPONENTE");
                contentStream.endText();
                
                 //linea  4
                contentStream.moveTo(195,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(195,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 60);
                contentStream.showText("C/R");
                contentStream.endText();
                
                 //linea  5
                contentStream.moveTo(270,  page.getMediaBox().getHeight() - 50);
                contentStream.lineTo(270,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 60);
                contentStream.showText("PZ. ENT.");
                contentStream.endText();
                
                 //linea  6
                contentStream.moveTo(350,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(350,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
   
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(370, page.getMediaBox().getHeight() - 60);
                contentStream.showText("NO. CUÑETES");
                contentStream.endText();
                
                 //linea  7
                contentStream.moveTo(450,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(450,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 60);
                contentStream.showText("NO. SIM");
                contentStream.endText();
                
                 //linea  8
                contentStream.moveTo(550,  page.getMediaBox().getHeight() - 40);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();
                
                //Lineas Horizontal
                //linea 
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 62);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 62);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 70);
                contentStream.showText("1");
                contentStream.endText();
                
                String orden1 = null;
                String orden2 = null;
                String orden3 = null;
                String orden4 = null;
                String orden5 = null;
                String orden6 = null;
                String orden7 = null;
                String orden8 = null;
                String orden9 = null;
                String orden10 = null;
                String orden11 = null;
                String orden12 = null;
                String orden13 = null;
                String orden14 = null;
                String orden15 = null;
                String orden16 = null;
                String orden17 = null;
                String orden18 = null;
                String orden19 = null;
                String orden20 = null;
                String orden21 = null;
                String orden22 = null;
                String orden23 = null;
                String orden24 = null;
                String orden25 = null;
                String orden26 = null;
                String orden27 = null;
                String orden28 = null;
                String orden29 = null;
                String orden30 = null;
                String orden31 = null;
                String orden32 = null;
                String orden33 = null;
                String orden34 = null;
                String orden35 = null;
                String orden36 = null;
                String orden37 = null;
                String orden38 = null;
                String orden39 = null;
                String orden40 = null;
                String orden41 = null;
                String orden42 = null;
                String orden43 = null;
                String orden44 = null;
                String orden45 = null;
                String orden46 = null;
                String orden47 = null;
                String orden48 = null;
                String orden49 = null;
                String orden50 = null;
                String orden51 = null;
                String orden52 = null;

                ResultSet registro = comando.executeQuery("SELECT orden FROM embarque WHERE facturado='1' ");
                if (registro.next()==true) {
                        orden1 = registro.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 70);
                        contentStream.showText(orden1);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden1+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                                            
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden1+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden1+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 70);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }

                //linea 2
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 74);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 74);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 82);
                contentStream.showText("2");
                contentStream.endText();
                
                ResultSet registro2 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND facturado='1'");
                if (registro2.next()==true) {
                        orden2 = registro2.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 82);
                        contentStream.showText(orden2);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden2+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden2+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden2+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 82);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 3
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 86);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 86);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 94);
                contentStream.showText("3");
                contentStream.endText();
                
                ResultSet registro3 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND facturado='1'");
                if (registro3.next()==true) {
                        orden3 = registro3.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 94);
                        contentStream.showText(orden3);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden3+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden3+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden3+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 94);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        } 
                }
                
                //linea 4
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 98);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 98);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 106);
                contentStream.showText("4");
                contentStream.endText();
                
                ResultSet registro4 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND facturado='1'");
                if (registro4.next()==true) {
                        orden4 = registro4.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 106);
                        contentStream.showText(orden4);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden4+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden4+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden4+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 106);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        } 
                }
                
                //linea 5
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 110);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 110);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 118);
                contentStream.showText("5");
                contentStream.endText();
                
                ResultSet registro5 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND facturado='1'");
                if (registro5.next()==true) {
                        orden5 = registro5.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 118);
                        contentStream.showText(orden5);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden5+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden5+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden5+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 118);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }   
                }
                
                //linea 6
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 122);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 122);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 130);
                contentStream.showText("6");
                contentStream.endText();
                
                ResultSet registro6 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND facturado='1'");
                if (registro6.next()==true) {
                        orden6 = registro6.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(orden6);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden6+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden6+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden6+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 7
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 134);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 134);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 142);
                contentStream.showText("7");
                contentStream.endText();
                
                ResultSet registro7 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND facturado='1'");
                if (registro7.next()==true) {
                        orden7 = registro7.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 142);
                        contentStream.showText(orden7);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden7+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden7+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden7+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 142);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 8
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 146);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 146);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 154);
                contentStream.showText("8");
                contentStream.endText();
                
                ResultSet registro8 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND facturado='1'");
                if (registro8.next()==true) {
                        orden8 = registro8.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 154);
                        contentStream.showText(orden8);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden8+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden8+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden8+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 154);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 9
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 158);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 158);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 166);
                contentStream.showText("9");
                contentStream.endText();
                
                ResultSet registro9 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"'AND orden !='"+orden8+"' AND facturado='1'");
                if (registro9.next()==true) {
                        orden9 = registro9.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 166);
                        contentStream.showText(orden9);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden9+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                         
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden9+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden9+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 166);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }    
                }
                
                 //linea 10
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 170);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 170);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 178);
                contentStream.showText("10");
                contentStream.endText();
                
                 ResultSet registro10 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND facturado='1'");
                if (registro10.next()==true) {
                        orden10 = registro10.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 178);
                        contentStream.showText(orden10);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden10+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden10+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden10+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 178);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }  
                }
                
                //linea 11
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 182);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 182);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 190);
                contentStream.showText("11");
                contentStream.endText();
                
                ResultSet registro11 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND facturado='1'");
                if (registro11.next()==true) {
                        orden11 = registro11.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 190);
                        contentStream.showText(orden11);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden11+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden11+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden11+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 190);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 12
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 194);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 194);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 202);
                contentStream.showText("12");
                contentStream.endText();
                
                ResultSet registro12 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND facturado='1'");
                if (registro12.next()==true) {
                        orden12 = registro12.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 202);
                        contentStream.showText(orden12);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden12+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden12+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden12+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 202);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 13
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 206);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 206);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 214);
                contentStream.showText("13");
                contentStream.endText();
                
                ResultSet registro13 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND facturado='1'");
                if (registro13.next()==true) {
                        orden13 = registro13.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 214);
                        contentStream.showText(orden13);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden13+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden13+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden13+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 214);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }  
                }
                
                //linea 14
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 218);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 218);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 226);
                contentStream.showText("14");
                contentStream.endText();
                
                ResultSet registro14 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND facturado='1'");
                if (registro14.next()==true) {
                        orden14 = registro14.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 226);
                        contentStream.showText(orden14);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden14+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
    
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden14+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden14+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 226);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 15
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 230);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 230);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 238);
                contentStream.showText("15");
                contentStream.endText();
                
                ResultSet registro15 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND facturado='1'");
                if (registro15.next()==true) {
                        orden15 = registro15.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 238);
                        contentStream.showText(orden15);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden15+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden15+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden15+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 238);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 16
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 242);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 242);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 250);
                contentStream.showText("16");
                contentStream.endText();
                
                ResultSet registro16 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND facturado='1'");
                if (registro16.next()==true) {
                        orden16 = registro16.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 250);
                        contentStream.showText(orden16);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden16+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                       
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden16+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden16+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 250);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 17
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 254);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 254);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 262);
                contentStream.showText("17");
                contentStream.endText();
                
                ResultSet registro17 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND facturado='1'");
                if (registro17.next()==true) {
                        orden17 = registro17.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 262);
                        contentStream.showText(orden17);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden17+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden17+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden17+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 262);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 18
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 266);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 266);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 274);
                contentStream.showText("18");
                contentStream.endText();
                
                ResultSet registro18 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND facturado='1'");
                if (registro18.next()==true) {
                        orden18 = registro18.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 274);
                        contentStream.showText(orden18);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden18+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden18+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden18+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 274);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 19
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 278);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 278);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 286);
                contentStream.showText("19");
                contentStream.endText();
                
                ResultSet registro19 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND facturado='1'");
                if (registro19.next()==true) {
                        orden19 = registro19.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 286);
                        contentStream.showText(orden19);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden19+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
 
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden19+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden19+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 286);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 20
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 290);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 290);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 298);
                contentStream.showText("20");
                contentStream.endText();
                
                ResultSet registro20 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND facturado='1'");
                if (registro20.next()==true) {
                        orden20 = registro20.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 298);
                        contentStream.showText(orden20);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden20+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                          
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden20+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden20+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 298);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 21
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 302);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 302);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 310);
                contentStream.showText("21");
                contentStream.endText();
                
                ResultSet registro21 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND facturado='1'");
                if (registro21.next()==true) {
                        orden21 = registro21.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 310);
                        contentStream.showText(orden21);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden21+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
            
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden21+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden21+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 310);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                 //linea 22
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 314);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 314);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 322);
                contentStream.showText("22");
                contentStream.endText();
                
                ResultSet registro22 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND facturado='1'");
                if (registro22.next()==true) {
                        orden22 = registro22.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 322);
                        contentStream.showText(orden22);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroCR.next()==true){
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden22+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden22+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden22+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 322);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 23
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 326);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 326);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 334);
                contentStream.showText("23");
                contentStream.endText();
                
                ResultSet registro23 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND facturado='1'");
                if (registro23.next()==true) {
                        orden23 = registro23.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 334);
                        contentStream.showText(orden23);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden23+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden23+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden23+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 334);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                 //linea 24
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 338);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 338);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 346);
                contentStream.showText("24");
                contentStream.endText();
                
                ResultSet registro24 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND facturado='1'");
                if (registro24.next()==true) {
                        orden24 = registro24.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 346);
                        contentStream.showText(orden24);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden24+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden24+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden24+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 346);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                 //linea 25
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 350);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 350);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 358);
                contentStream.showText("25");
                contentStream.endText();
                
                ResultSet registro25 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND facturado='1'");
                if (registro25.next()==true) {
                        orden25 = registro25.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 358);
                        contentStream.showText(orden25);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden25+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden25+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden25+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 358);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 26
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 362);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 362);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 370);
                contentStream.showText("26");
                contentStream.endText();
                
                ResultSet registro26 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND facturado='1'");
                if (registro26.next()==true) {
                        orden26 = registro26.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 370);
                        contentStream.showText(orden26);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden26+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden26+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden26+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 370);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 27
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 374);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 374);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 382);
                contentStream.showText("27");
                contentStream.endText();
                
                ResultSet registro27 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND facturado='1'");
                if (registro27.next()==true) {
                        orden27 = registro27.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 382);
                        contentStream.showText(orden27);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden27+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden27+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden27+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 382);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 28
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 386);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 386);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 394);
                contentStream.showText("28");
                contentStream.endText();
                
                ResultSet registro28 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND facturado='1'");
                if (registro28.next()==true) {
                        orden28 = registro28.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 394);
                        contentStream.showText(orden28);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden28+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
           
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden28+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden28+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 394);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 29
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 398);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 398);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 406);
                contentStream.showText("29");
                contentStream.endText();
                
                ResultSet registro29 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND facturado='1'");
                if (registro29.next()==true) {
                        orden29 = registro29.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 406);
                        contentStream.showText(orden29);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden29+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden29+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden29+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 406);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 30
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 410);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 410);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 418);
                contentStream.showText("30");
                contentStream.endText();
                
                ResultSet registro30 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND facturado='1'");
                if (registro30.next()==true) {
                        orden30 = registro30.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 418);
                        contentStream.showText(orden30);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden30+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden30+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden30+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 418);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 31
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 422);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 422);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 430);
                contentStream.showText("31");
                contentStream.endText();
                
                ResultSet registro31 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND facturado='1'");
                if (registro31.next()==true) {
                        orden31 = registro31.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 430);
                        contentStream.showText(orden31);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden31+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden31+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden31+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 430);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                 //linea 32
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 434);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 434);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 442);
                contentStream.showText("32");
                contentStream.endText();
                
                ResultSet registro32 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND facturado='1'");
                if (registro32.next()==true) {
                        orden32 = registro32.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 442);
                        contentStream.showText(orden32);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden32+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden32+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden32+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 442);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 33
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 446);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 446);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 454);
                contentStream.showText("33");
                contentStream.endText();
                
                ResultSet registro33 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND facturado='1'");
                if (registro33.next()==true) {
                        orden33 = registro33.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 454);
                        contentStream.showText(orden33);
                        contentStream.endText();
 
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden33+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
           
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden33+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden33+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 454);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 34
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 458);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 458);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 466);
                contentStream.showText("34");
                contentStream.endText();
                
                ResultSet registro34 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND facturado='1'");
                if (registro34.next()==true) {
                        orden34 = registro34.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 466);
                        contentStream.showText(orden34);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden34+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden34+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden34+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 466);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 35
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 470);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 470);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 478);
                contentStream.showText("35");
                contentStream.endText();
                
                ResultSet registro35 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND facturado='1'");
                if (registro35.next()==true) {
                        orden35 = registro35.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 478);
                        contentStream.showText(orden35);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden35+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                 
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden35+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden35+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 478);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 36
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 482);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 482);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 490);
                contentStream.showText("36");
                contentStream.endText();
                
                ResultSet registro36 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND facturado='1'");
                if (registro36.next()==true) {
                        orden36 = registro36.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 490);
                        contentStream.showText(orden36);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden36+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                      
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden36+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden36+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 490);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 37
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 494);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 494);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 502);
                contentStream.showText("37");
                contentStream.endText();
                
                ResultSet registro37 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND facturado='1'");
                if (registro37.next()==true) {
                        orden37 = registro37.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 502);
                        contentStream.showText(orden37);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden37+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden37+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden37+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 502);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 38
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 506);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 506);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 514);
                contentStream.showText("38");
                contentStream.endText();
                
                ResultSet registro38 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND facturado='1'");
                if (registro38.next()==true) {
                        orden38 = registro38.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 514);
                        contentStream.showText(orden38);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden38+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden38+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden38+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 514);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 39
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 518);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 518);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 526);
                contentStream.showText("39");
                contentStream.endText();
                
                ResultSet registro39 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND facturado='1'");
                if (registro39.next()==true) {
                        orden39 = registro39.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 526);
                        contentStream.showText(orden39);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden39+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden39+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden39+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 526);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 40
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 530);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 530);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 538);
                contentStream.showText("40");
                contentStream.endText();
                
                ResultSet registro40 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND facturado='1'");
                if (registro40.next()==true) {
                        orden40 = registro40.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 538);
                        contentStream.showText(orden40);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden40+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden40+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden40+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 538);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }  
                }
                
                //linea 41
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 542);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 542);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 550);
                contentStream.showText("41");
                contentStream.endText();
                
                ResultSet registro41 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND facturado='1'");
                if (registro41.next()==true) {
                        orden41 = registro41.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 550);
                        contentStream.showText(orden41);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden41+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden41+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden41+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 550);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 42
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 554);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 554);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 562);
                contentStream.showText("42");
                contentStream.endText();
                
                ResultSet registro42 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND facturado='1'");
                if (registro42.next()==true) {
                        orden42 = registro42.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 562);
                        contentStream.showText(orden42);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden42+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden42+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden42+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 562);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 43
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 566);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 566);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 574);
                contentStream.showText("43");
                contentStream.endText();
                
                ResultSet registro43 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND facturado='1'");
                if (registro43.next()==true) {
                        orden43 = registro43.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 574);
                        contentStream.showText(orden43);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden43+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden43+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden43+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 574);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 44
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 578);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 578);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 586);
                contentStream.showText("44");
                contentStream.endText();
                
                ResultSet registro44 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND facturado='1'");
                if (registro44.next()==true) {
                        orden44 = registro44.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 586);
                        contentStream.showText(orden44);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden44+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden44+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden44+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 586);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 45
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 590);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 590);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 598);
                contentStream.showText("45");
                contentStream.endText();
                
                ResultSet registro45 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND facturado='1'");
                if (registro45.next()==true) {
                        orden45 = registro45.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 598);
                        contentStream.showText(orden45);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden45+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden45+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden45+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 598);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 46
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 602);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 602);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 610);
                contentStream.showText("46");
                contentStream.endText();
                
                ResultSet registro46 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND facturado='1'");
                if (registro46.next()==true) {
                        orden46 = registro46.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 610);
                        contentStream.showText(orden46);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden46+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }
                  
                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden46+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden46+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 610);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 47
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 614);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 614);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 622);
                contentStream.showText("47");
                contentStream.endText();
                
                ResultSet registro47 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"' AND facturado='1'");
                if (registro47.next()==true) {
                        orden47 = registro47.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 622);
                        contentStream.showText(orden47);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden47+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden47+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden47+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 622);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 48
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 626);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 626);
                contentStream.stroke();
                
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 634);
                contentStream.showText("48");
                contentStream.endText();
                
                ResultSet registro48 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"'AND orden !='"+orden47+"' AND facturado='1'");
                if (registro48.next()==true) {
                        orden48 = registro48.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 634);
                        contentStream.showText(orden48);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden48+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden48+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden48+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 634);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }
                }
                
                //linea 49
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 638);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 638);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 646);
                contentStream.showText("49");
                contentStream.endText();
                
                ResultSet registro49 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"'AND orden !='"+orden47+"'AND orden !='"+orden48+"' AND facturado='1'");
                if (registro49.next()==true) {
                        orden49 = registro49.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 646);
                        contentStream.showText(orden49);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden49+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden49+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden49+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 646);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 50
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 650);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 650);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 658);
                contentStream.showText("50");
                contentStream.endText();
                
                ResultSet registro50 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"'AND orden !='"+orden47+"'AND orden !='"+orden48+"' AND orden !='"+orden49+"' AND facturado='1'");
                if (registro50.next()==true) {
                        orden50 = registro49.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 658);
                        contentStream.showText(orden50);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden50+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden50+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden50+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 658);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }

                //linea 51
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 662);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 662);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 670);
                contentStream.showText("51");
                contentStream.endText();
                
                ResultSet registro51 = comando.executeQuery("SELECT orden FROM embarque WHERE orden !='"+orden1+"' AND orden !='"+orden2+"' AND orden !='"+orden3+"' AND orden !='"+orden4+"' AND orden !='"+orden5+"' AND orden !='"+orden6+"' AND orden !='"+orden7+"' AND orden !='"+orden8+"' AND orden !='"+orden9+"' AND orden !='"+orden10+"' AND orden !='"+orden11+"' AND orden !='"+orden12+"' AND orden !='"+orden13+"' AND orden !='"+orden14+"' AND orden !='"+orden15+"' AND orden !='"+orden16+"' AND orden !='"+orden17+"' AND orden !='"+orden18+"' AND orden !='"+orden19+"' AND orden !='"+orden20+"' AND orden !='"+orden21+"' AND orden !='"+orden22+"' AND orden !='"+orden23+"' AND orden !='"+orden24+"' AND orden !='"+orden25+"' AND orden !='"+orden26+"' AND orden !='"+orden27+"' AND orden !='"+orden28+"' AND orden !='"+orden29+"' AND orden !='"+orden30+"' AND orden !='"+orden31+"' AND orden !='"+orden32+"' AND orden !='"+orden33+"' AND orden !='"+orden34+"' AND orden !='"+orden35+"' AND orden !='"+orden36+"' AND orden !='"+orden37+"' AND orden !='"+orden38+"' AND orden !='"+orden39+"' AND orden !='"+orden40+"' AND orden !='"+orden41+"' AND orden !='"+orden42+"' AND orden !='"+orden43+"' AND orden !='"+orden44+"' AND orden !='"+orden45+"' AND orden !='"+orden46+"'AND orden !='"+orden47+"'AND orden !='"+orden48+"' AND orden !='"+orden49+"' AND orden !='"+orden50+"' AND facturado='1'");
                if (registro51.next()==true) {
                        orden51 = registro51.getString("orden");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                        contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 670);
                        contentStream.showText(orden51);
                        contentStream.endText();
                        
                        String componente = null;
                        ResultSet registroC = comando.executeQuery("SELECT componente FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroC.next()==true) {
                                componente = registroC.getString("componente");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(componente);
                                contentStream.endText();
                        }
                        
                        ResultSet registroCR = comando.executeQuery("SELECT cr FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroCR.next()==true) {
                                String cr = registroCR.getString("cr");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(cr);
                                contentStream.endText();
                        }
                        
                        ResultSet registroP = comando.executeQuery("SELECT cantidad FROM embarque WHERE orden= '"+orden51+"'");
                        if (registroP.next()==true) {
                                String pizsEn = registroP.getString("cantidad");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(290, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(pizsEn);
                                contentStream.endText();
                        }

                        ResultSet registroCu = comando.executeQuery("SELECT noCuñetes FROM embarque WHERE orden='"+orden51+"'");
                        if (registroCu.next()==true) {
                                String cuñetes = registroCu.getString("noCuñetes");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(375, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(cuñetes);
                                contentStream.endText();
                        }
                        
                        ResultSet registroSim = comando.executeQuery("SELECT NoSim FROM embarque WHERE orden='"+orden51+"'");
                        if (registroSim.next()==true) {
                                String SIM = registroSim.getString("NoSim");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                                contentStream.newLineAtOffset(480, page.getMediaBox().getHeight() - 670);
                                contentStream.showText(SIM);
                                contentStream.endText();
                        }     
                }
                
                //linea 52
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 674);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 674);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 682);
                contentStream.showText("52");
                contentStream.endText();

                //linea 53
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 686);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 686);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 694);
                contentStream.showText("53");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 694);
                contentStream.showText("ELABORÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 694);
                contentStream.showText("Saúl");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 694);
                contentStream.showText("Arzate");
                contentStream.endText();
                
                //linea 54
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 698);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 698);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 706);
                contentStream.showText("54");
                contentStream.endText();
                
                //linea 55
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 710);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 710);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 718);
                contentStream.showText("55");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 718);
                contentStream.showText("REVISÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 718);
                contentStream.showText("Silvia");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 718);
                contentStream.showText("Monroy");
                contentStream.endText();
                
                //linea 56
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 722);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 722);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 730);
                contentStream.showText("56");
                contentStream.endText();
                
                 //linea 57
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 734);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 734);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(43, page.getMediaBox().getHeight() - 742);
                contentStream.showText("57");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 742);
                contentStream.showText("APROBÓ");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(140, page.getMediaBox().getHeight() - 742);
                contentStream.showText("Arturo");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 742);
                contentStream.showText("Kehoe");
                contentStream.endText();
                
                 //linea 58
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - 746);
                contentStream.lineTo(550,  page.getMediaBox().getHeight() - 746);
                contentStream.stroke();

            }
            doc.save("Lista de Embarque.pdf");
        }
    }
     

    /**to
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/limpiar.png"))); // NOI18N
        jButton7.setText("LIMPIAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 0, 120, 30));

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("EMBARQUE");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 480, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 120, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 40));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 130, 30));

        jtxtfiltro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxtfiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtfiltroActionPerformed(evt);
            }
        });
        jtxtfiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtfiltroKeyTyped(evt);
            }
        });
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 120, 240, 30));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton4.setText("MODIFICAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ORDEN", "COMPONENTE", "C/R", "CANTIDAD", "NO. CUÑETES", "NO. SIM", "PESO NETO", "FACTURA", "ENVIADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 1050, 360));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/lista.png"))); // NOI18N
        jButton5.setText("GENERAR LISTA DE EMBARQUE");
        jButton5.setActionCommand("Generar Lista de Embarque");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 310, 30));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Factura.png"))); // NOI18N
        jButton6.setText("ENVIAR A FACTURAR");
        jButton6.setActionCommand("Generar Lista de Embarque");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 220, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        jButton1.setText("ELIMINAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 150, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Statement comando=cn.createStatement();

            int fila_seleccionada = jTable1.getSelectedRow();
            if(fila_seleccionada >= 0){
                String orden = (String) jTable1.getValueAt(fila_seleccionada, 0);
                if(mod.getId_tipo()== 1 || mod.getId_tipo()== 2){
                    ResultSet registroP = comando.executeQuery("SELECT factura FROM embarque WHERE orden= '"+orden+"' AND facturado=TRUE");
                    if (registroP.next()==true) {
                        String val = registroP.getString("factura");
                        if(val== null){
                            String clave=JOptionPane.showInputDialog("INGRESAR CONTRASEÑA: \n\n FAVOR DE AVISAR A FACTURACIÓN DE DICHA MODIFICACIÓN \n");
                            if(clave.equals("JCTroqueles")){
                                EmbarqueGUI.this.dispose();
                                ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                                modificar.setVisible(true);
                                modificar.setLocationRelativeTo(null);
                            }else{
                                JOptionPane.showMessageDialog(this, "CONTRASEÑA INCORRECTA.");       
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "NO PUEDE SER MODIFICADA, ORDEN FACTURADA.");
                        }
                    }else{
                        EmbarqueGUI.this.dispose();
                        ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                        modificar.setVisible(true);
                        modificar.setLocationRelativeTo(null);
                    }
                }else{
                    ResultSet registroP = comando.executeQuery("SELECT orden FROM embarque WHERE orden= '"+orden+"' AND facturado=TRUE");
                    if (registroP.next()==true) {
                        JOptionPane.showMessageDialog(this, "NO PUEDE SER MODIFICADA, ORDEN ENVIADA A FACTURACIÓN.");
                    }else{
                        EmbarqueGUI.this.dispose();
                        ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                        modificar.setVisible(true);
                        modificar.setLocationRelativeTo(null);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

           
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
        jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final java.awt.event.KeyEvent ke){
                jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
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

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       try {
            Statement comando=cn.createStatement();
            int fila_seleccionada = trs.convertRowIndexToModel(jTable1.getSelectedRow());
            int fila_seleccionada2 = jTable1.getSelectedRow();
            if(fila_seleccionada >= 0){
                String orden = (String) jTable1.getValueAt(fila_seleccionada2, 0);
                if(mod.getId_tipo()== 1 || mod.getId_tipo()== 2){
                    ResultSet registroP = comando.executeQuery("SELECT factura FROM embarque WHERE orden= '"+orden+"' AND facturado=TRUE");
                    if (registroP.next()==true) {
                        String val = registroP.getString("factura");
                        if(val== null){
                            String clave=JOptionPane.showInputDialog("INGRESAR CONTRASEÑA: \n\n FAVOR DE AVISAR A FACTURACIÓN DE DICHA MODIFICACIÓN \n");
                            if(clave.equals("JCTroqueles")){
                                EmbarqueGUI.this.dispose();
                                ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                                modificar.setVisible(true);
                                modificar.setLocationRelativeTo(null);
                            }else{
                                JOptionPane.showMessageDialog(this, "CONTRASEÑA INCORRECTA.");       
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "NO PUEDE SER MODIFICADA, ORDEN FACTURADA.");
                        }
                    }else{
                        EmbarqueGUI.this.dispose();
                        ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                        modificar.setVisible(true);
                        modificar.setLocationRelativeTo(null);
                    }
                }else{
                    ResultSet registroP = comando.executeQuery("SELECT orden FROM embarque WHERE orden= '"+orden+"' AND facturado=TRUE");
                    if (registroP.next()==true) {
                        JOptionPane.showMessageDialog(this, "NO PUEDE SER MODIFICADA, ORDEN ENVIADA A FACTURACIÓN.");
                    }else{
                        EmbarqueGUI.this.dispose();
                        ModificarEmbarque modificar = new ModificarEmbarque(this.embarque.get(fila_seleccionada),mod);
                        modificar.setVisible(true);
                        modificar.setLocationRelativeTo(null);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            //this.ListaEmbarquePDF();
            this.ListaEmbarqueClientePDF();
            JOptionPane.showMessageDialog(this, "Lista de Embarque Generada");
        } catch (IOException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            Statement comando=cn.createStatement();
            int fila_seleccionada = jTable1.getSelectedRow();
       
            if(fila_seleccionada >= 0 ){
                String componente = (String) jTable1.getValueAt(fila_seleccionada, 1);
                String orden = (String) jTable1.getValueAt(fila_seleccionada, 0);
                ResultSet registro = comando.executeQuery("SELECT * FROM embarque WHERE orden= '"+orden+"' AND señalEmb=TRUE AND NoSim IS NOT NULL AND facturado=FALSE ");
                if (registro.next()==true) {
                    ResultSet registro2 = comando.executeQuery("SELECT * FROM preciocomponente WHERE componente= '"+componente+"' AND precioU !=0");
                    if (registro2.next()==true) {
                        ResultSet registro3 = comando.executeQuery("SELECT * FROM crs WHERE componente= '"+componente+"' AND cr !='0'");
                        if (registro3.next()==true) {
                            if(this.embarque_servicio.existeComponente(orden)== 0)
                            {
                                EmbarqueGUI.this.dispose();

                                Date date= new Date();
                                DateFormat fechaHoraEnvi= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                                String fecha = fechaHoraEnvi.format(date).toString();
                                //Ordenes
                                PreparedStatement pst1 = cn.prepareStatement("INSERT INTO facturacion (orden,componente,cr,cantidad, noCuñetes,NoSim,pesoNeto,fechaHoraEnvi) SELECT embarque.orden, embarque.componente, embarque.cr,embarque.cantidad,embarque.noCuñetes,embarque.NoSim,pesoNeto,'"+fecha+"' FROM embarque WHERE embarque.orden='"+orden+"'");
                                pst1.executeUpdate();

                                //Precios
                                PreparedStatement pst2 = cn.prepareStatement("UPDATE facturacion JOIN preciocomponente ON preciocomponente.componente = facturacion.componente SET facturacion.PU = preciocomponente.precioU");
                                pst2.executeUpdate();

                                //Precios
                                PreparedStatement pst3 = cn.prepareStatement("update facturacion set facturacion.total = (cantidad * PU)");
                                pst3.executeUpdate();

                                //datos enviados a facturar
                                PreparedStatement pst5 = cn.prepareStatement("UPDATE embarque JOIN facturacion ON embarque.orden=facturacion.orden SET embarque.facturado = IF(embarque.orden= '"+orden+"', embarque.facturado = false,embarque.facturado = true)");
                                pst5.executeUpdate();

                                //Peso neto
                                PreparedStatement pst4 = cn.prepareStatement("UPDATE facturacion JOIN embarque ON embarque.orden = facturacion.orden SET facturacion.pesoNeto = embarque.pesoNeto");
                                pst4.executeUpdate();

                                JOptionPane.showMessageDialog(this, "ORDEN ENVIADA A FACTURACIÓN");
                                EmbarqueGUI embarque= new EmbarqueGUI(mod);
                                embarque.setVisible(true);
                            }else{
                                JOptionPane.showMessageDialog(this, "ESTA ORDEN YA FUE ENVIADA A FACTURACIÓN.");
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "FAVOR DE REVISAR EL C/R DEL COMPONENTE: "+componente);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "FAVOR DE REVISAR EL PRECIO DEL COMPONENTE: "+componente);
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "NO SE PUEDE ENVIAR A FACTURACIÓN");
                }
            }else{
                JOptionPane.showMessageDialog(this, "POR FAVOR SELECCIONE UNA FILA.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
         try {
            
            int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
            if(resp == JOptionPane.YES_NO_OPTION)
            {
                EmbarqueGUI.this.dispose();
                //Eliminar
                PreparedStatement pst4 = cn.prepareStatement("DELETE FROM embarque WHERE facturado='1'");
                pst4.executeUpdate();

                JOptionPane.showMessageDialog(this, "Datos Eliminados.");

                EmbarqueGUI embarque= new EmbarqueGUI(mod);
                embarque.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            int fila_seleccionada = jTable1.getSelectedRow();
            if(fila_seleccionada != -1){
                String orden = (String) jTable1.getValueAt(fila_seleccionada, 0);
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    this.embarque_servicio.eliminar(orden);
                    
                    EmbarqueGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    EmbarqueGUI mp = new EmbarqueGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error.");
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
            java.util.logging.Logger.getLogger(EmbarqueGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmbarqueGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmbarqueGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmbarqueGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EmbarqueGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(EmbarqueGUI.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables

}