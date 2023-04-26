 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenesSolicitadas;

import ComponentesRep.ComponentesRepGUI;
import Enviados.EntregadosGUI;
import Modelos.AnalisisAtrasosM;
import Modelos.TiempoEstandarM;
import Modelos.Usuarios;
import Servicios.AnalisisAtrasos_servicio;
import Servicios.Conexion;
import Servicios.TiempoEstandar_servicio;
import TiempoAtrasos.TiempoAtrasosGUI;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class OrdenesSolicitadasGUI extends javax.swing.JFrame {
    private final AnalisisAtrasos_servicio analisisAtrasos_servicio = new AnalisisAtrasos_servicio();
    private List<AnalisisAtrasosM> analisisatrasos;
    private final TiempoEstandar_servicio tiempoEstandar_servicio = new TiempoEstandar_servicio();
    private List<TiempoEstandarM> tiempoEstandar;
    private final toExcel excel= new toExcel(); //Generar Excel
    
    private final toPDF pdf= new toPDF(); //Generar PDF

    TableRowSorter trs;
    TableRowSorter trs2;
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
  
    public OrdenesSolicitadasGUI() throws SQLException, ClassNotFoundException {
        
        initComponents();
        
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.cn = Conexion.obtener();
        this.analisisAtrasos();
        this.tiempoestandar();
        this.suma();
        
        jButton7.setVisible(false);  
        jButton16.setVisible(false);
        
        //Boton Exportar Analisis de Atrasos
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);
        
        //Boton Exportar Tiempo Estandar
        jButton10.setFocusPainted(false);
        jButton10.setBorderPainted(false);
        //jButton10.setContentAreaFilled(false);
        
    }
    
    public OrdenesSolicitadasGUI(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        
        initComponents();
        
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.analisisAtrasos();
        this.tiempoestandar();
        this.suma();      
        
        jButton7.setVisible(false); 
        jButton16.setVisible(false);
        
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        //jButton1.setContentAreaFilled(false);
        
        //Boton Exportar Tiempo Estandar
        jButton10.setFocusPainted(false);
        jButton10.setBorderPainted(false);
        //jButton10.setContentAreaFilled(false);
        
        if(mod.getId_tipo()== 4){
            jButton2.setEnabled(false);
            jButton4.setEnabled(false);
            jButton6.setEnabled(false);
            jButton5.setEnabled(false);
            jButton7.setEnabled(false);
        }
        
        PreparedStatement pst = cn.prepareStatement("INSERT INTO orden_compra (orden_compra.fechaV,orden_compra.orden,orden_compra.componente,orden_compra.cantidad) SELECT analisisatrasos.fechaVencida,analisisatrasos.orden, analisisatrasos.item_cliente,analisisatrasos.piezasEntregar FROM analisisatrasos WHERE NOT EXISTS ( SELECT orden_compra.orden FROM orden_compra WHERE orden_compra.orden = analisisatrasos.orden)");
        pst.executeUpdate();
        
        PreparedStatement pst2 = cn.prepareStatement("UPDATE orden_compra JOIN estructura ON estructura.componente = orden_compra.componente SET orden_compra.CR = estructura.CR");
        pst2.executeUpdate();
        
        PreparedStatement pst3 = cn.prepareStatement("UPDATE orden_compra JOIN estructura ON estructura.componente = orden_compra.componente SET orden_compra.calibre= estructura.calibre");
        pst3.executeUpdate();
        
        PreparedStatement pst4 = cn.prepareStatement("UPDATE orden_compra JOIN estructura ON estructura.componente = orden_compra.componente SET orden_compra.consumo_kg= (estructura.peso_estamp*orden_compra.cantidad)");
        pst4.executeUpdate();
        
        PreparedStatement pst5 = cn.prepareStatement("UPDATE orden_compra JOIN estructura ON estructura.componente = orden_compra.componente SET orden_compra.no_oper= estructura.OP");
        pst5.executeUpdate();
        
        PreparedStatement pst6 = cn.prepareStatement("UPDATE orden_compra SET no_golpes=(cantidad*no_oper) WHERE orden_compra.orden=orden_compra.orden");
        pst6.executeUpdate();

        //Modificar cantidad a entregar en analisis produccion               
        PreparedStatement pst7 = cn.prepareStatement("UPDATE atrasosproduccion JOIN analisisatrasos ON analisisatrasos.orden = atrasosproduccion.orden SET atrasosproduccion.cantidad = analisisatrasos.piezasEntregar");
        pst7.executeUpdate();
        
        //MODIFICAR FECHA  ORDEN DE COMPRA             
        PreparedStatement pst8 = cn.prepareStatement("UPDATE orden_compra JOIN analisisatrasos ON analisisatrasos.orden = orden_compra.orden SET orden_compra.fechaV = analisisatrasos.fechaVencida");
        pst8.executeUpdate();
        
        //MODIFICAR CANTIDAD ORDEN DE COMPRA
        PreparedStatement pstCANT = cn.prepareStatement("UPDATE orden_compra JOIN analisisatrasos ON analisisatrasos.orden = orden_compra.orden SET orden_compra.cantidad = analisisatrasos.cantidad_reque");
        pstCANT.executeUpdate();
        
        //MODIFICAR FECHA ATRASOS PRODUCCION
        PreparedStatement pst9 = cn.prepareStatement("UPDATE atrasosproduccion JOIN analisisatrasos ON analisisatrasos.orden = atrasosproduccion.orden SET atrasosproduccion.fechaV = analisisatrasos.fechaVencida");
        pst9.executeUpdate();

        //MODIFICAR FECHA ORDEN CALIDAD
        PreparedStatement pst10 = cn.prepareStatement("UPDATE ordenescalidad JOIN analisisatrasos ON analisisatrasos.orden = ordenescalidad.orden SET ordenescalidad.fechaV = analisisatrasos.fechaVencida");
        pst10.executeUpdate();
        
        //MODIFICAR CANTIDAD ORDEN CALIDAD
        PreparedStatement pst10CANT = cn.prepareStatement("UPDATE ordenescalidad JOIN analisisatrasos ON analisisatrasos.orden = ordenescalidad.orden SET ordenescalidad.cantidad = analisisatrasos.piezasEntregar");
        pst10CANT.executeUpdate();
        
        PreparedStatement pstP = cn.prepareStatement("UPDATE analisisatrasos SET prioridad='0' WHERE prioridad IS NULL");
        pstP.executeUpdate();
         
    }
     
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }
    
    private void analisisAtrasos() {
        try{
            //Tabla analisis de atrasos
            this.analisisatrasos = this.analisisAtrasos_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm = (DefaultTableModel) jTableA.getModel();
            dtm.setRowCount(0);
            for(int i = 0; i < this.analisisatrasos.size(); i++){
                
                
                dtm.addRow(new Object[]{
                    
                    this.analisisatrasos.get(i).isPrioridad(),
                    this.analisisatrasos.get(i).getFechaRecibida(),
                    this.analisisatrasos.get(i).getFechaVencida(),
                    this.analisisatrasos.get(i).getOrden(),
                    this.analisisatrasos.get(i).getCantidad_reque(),
                    this.analisisatrasos.get(i).getCantidadEntregada(),
                    this.analisisatrasos.get(i).getPiezasEntregar(),
                    this.analisisatrasos.get(i).getConsignatario(),
                    this.analisisatrasos.get(i).getItem_cliente(),
                    this.analisisatrasos.get(i).getComentario(),
                    this.analisisatrasos.get(i).getFechaEmbarque(),
                    this.analisisatrasos.get(i).getFactura(),
                    this.analisisatrasos.get(i).getEmbarques()
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
    
    private void tiempoestandar() {
        try{
            //Tabla tiempo estandar
            this.tiempoEstandar = this.tiempoEstandar_servicio.recuperarTodas(Conexion.obtener());
            DefaultTableModel dtm2 = (DefaultTableModel) jTable2.getModel();
            dtm2.setRowCount(0);
            for(int i = 0; i < this.tiempoEstandar.size(); i++){
                dtm2.addRow(new Object[]{
                    this.tiempoEstandar.get(i).getOrden(),
                    this.tiempoEstandar.get(i).getComponente(),
                    this.tiempoEstandar.get(i).getInst_desm(),
                    this.tiempoEstandar.get(i).getTiempo_troquelado(),
                    this.tiempoEstandar.get(i).getTiempo_total()
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
    
    public void Filtro(int num0,int num1, int num2, int num3){
        trs.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(),num1,num2, num3));
    }
    
    public void Filtro2(){
       int ColumnaTabla = 0;
      trs2.setRowFilter(RowFilter.regexFilter(jtxtfiltro.getText(), ColumnaTabla,1));
   }
    
    public void prioridad(){
        int fila_seleccionada = jTableA.getSelectedRow();          
        String orden = (String) jTableA.getValueAt(fila_seleccionada, 3);
 
        Object check = (Object) jTableA.getValueAt(fila_seleccionada, 0);
        if(check == null){
            System.out.print("No se guarda");
        }else{
            try {
                PreparedStatement pstPrio = cn.prepareStatement("UPDATE analisisatrasos SET Prioridad = (SELECT IF(Prioridad= 1, 0, 1) WHERE analisisatrasos.orden= '"+orden+"') WHERE analisisatrasos.orden= '"+orden+"'");
                pstPrio.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    //Metodo para crear PDF produccion
    public void produccionPDF(String fecha) throws IOException, SQLException{
    try (PDDocument document = new PDDocument()) {
          
            Statement comando=cn.createStatement();
            
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            //PAGINA CORTADOR
            PDPage page2 = new PDPage(PDRectangle.A6);
            document.addPage(page2);
            
            PDPageContentStream contentStream2 = new PDPageContentStream(document, page2);
            
            int fila_seleccionada = jTableA.getSelectedRow();
            
            String componente_CA = (String) jTableA.getValueAt(fila_seleccionada, 8);
            String orden = (String) jTableA.getValueAt(fila_seleccionada, 3);

                
            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 20);
            contentStream.showText("ORDEN DE PRODUCCIÓN");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset( 220, page.getMediaBox().getHeight() - 30);
            contentStream.showText("'JC TALLER MECANICO'");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 40);
            contentStream.showText("Estatus P.P.A.P:");
            contentStream.endText();
            
            //Agergar ESTATUS 
                ResultSet registro = comando.executeQuery("SELECT PpapStatus FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden = '" + orden+"'");
                if (registro.next()==true) {
                        String val1 = registro.getString("PpapStatus");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 40);
                        contentStream.showText(val1);
                        contentStream.endText();
                       
                } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 40);
                        contentStream.showText("#N/D");
                        contentStream.endText();
                }
                
            //Letrero de No. de orden
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 50);
            contentStream.showText("No. de orden:");
            contentStream.endText();
            
            //Agergar orden
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 50);
            contentStream.showText(orden);
            contentStream.endText();
                      
            //Letrero de componente
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 56);
            contentStream.showText("Componente:");
            contentStream.endText();
            
            //Agregar componente
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 56);
            contentStream.showText(componente_CA);
            contentStream.endText();
            
            //Letrero de C/R
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 62);
            contentStream.showText("C/R:");
            contentStream.endText();
            
            //Agregar C/R
            ResultSet registroCR = comando.executeQuery("SELECT cr FROM crs, analisisatrasos WHERE componente="+"'"+componente_CA+"' AND analisisatrasos.orden = '" + orden+"'");
            if (registroCR.next()==true) {
                    String val1 = registroCR.getString("cr");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 62);
                    contentStream.showText(val1);
                    contentStream.endText();

            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 62);
                    contentStream.showText("#N/D");
                    contentStream.endText();
            }
            
            //Letrero de cantidad requerida
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 68);
            contentStream.showText("Cantidad requerida:");
            contentStream.endText();
            
            //Agregar cantidad requeridas
            String cantidadReque = null;
            ResultSet registroCant = comando.executeQuery("SELECT cantidad_reque FROM analisisatrasos WHERE  analisisatrasos.orden = '" + orden+"'");
            if (registroCant.next()==true) {
                    cantidadReque = registroCant.getString("cantidad_reque");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 68);
                    contentStream.showText(cantidadReque);
                    contentStream.endText();
            }

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(90, page.getMediaBox().getHeight() - 68);
            contentStream.showText("Pzs");
            contentStream.endText();
            
            //Letrero lamina calibre
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 74);
            contentStream.showText("Lamina Calibre:");
            contentStream.endText();
            
            //Agergar lamina
            ResultSet registro2 = comando.executeQuery("SELECT desMatePrima FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden = '" + orden+"'");
            if (registro2.next()==true) {
                    String val1 = registro2.getString("desMatePrima");
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 74);
                    contentStream.showText(val1 = val1.replace("\n", "").replace("\r", ""));
                    contentStream.endText();

            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 74);
                    contentStream.showText("#N/D");
                    contentStream.endText();
            }
            
            //Letrero Requerimiento en kilogramos
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 80);
            contentStream.showText("Requerimiento(kgs):");
            contentStream.endText();
            
            //Agregar requerimiento
            ResultSet registro3 = comando.executeQuery("SELECT ROUND((analisisatrasos.cantidad_reque *consumoyantecedentes.consumo_uni),2) AS resultado FROM analisisatrasos, consumoyantecedentes WHERE item_cliente="+"'"+componente_CA+"'"+" AND componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                String requerimiento = null;
                if (registro3.next()==true) {
                        requerimiento = registro3.getString("resultado");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 80);
                        contentStream.showText(requerimiento);
                        contentStream.endText();
                       
                } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 74);
                        contentStream.showText("#N/D");
                        contentStream.endText();
                }
            
            //Letrero de Ancho de Tira
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 86);
            contentStream.showText("Ancho de Tira:");
            contentStream.endText();
            
            //Agregar ancho de tira
            ResultSet registro4 = comando.executeQuery("SELECT anchoTira FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro4.next()==true) {
                        String val1 = registro4.getString("anchoTira");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 86);
                        contentStream.showText(val1);
                        contentStream.endText();
                       
                } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 74);
                        contentStream.showText("#N/D");
                        contentStream.endText();
                }
                
            //Letrero familia
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 92);
            contentStream.showText("FAMILIA:");
            contentStream.endText();
            
            //Agregar familia
            ResultSet registro5 = comando.executeQuery("SELECT familia FROM antecedentesfamilia, analisisatrasos WHERE componente="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro5.next()==true) {
                        String val1 = registro5.getString("familia");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 92);
                        contentStream.showText(val1= val1.replace("\n", "").replace("\r", ""));
                        contentStream.endText();      
                } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 92);
                        contentStream.showText("#N/D");
                        contentStream.endText();
                }
                
            //Letrero Antecedentes
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 99);
            contentStream.showText("ANTECEDENTES:");
            contentStream.endText();
            
            //Agregar antecedentes
            ResultSet registro6 = comando.executeQuery("SELECT comentario_CA FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro6.next()==true) {
                        String val1 = registro6.getString("comentario_CA");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 3);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 99);
                        contentStream.showText(val1 = val1.replace("\n", "").replace("\r", ""));
                        contentStream.endText();
                } else {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 99);
                        contentStream.showText("#N/D");
                        contentStream.endText();
                }
                
            //Letrero vencimiento
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 40);
            contentStream.showText("Vencimiento:");
            contentStream.endText();
            
            //Agregar fecha vencimiento
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 40);
            contentStream.showText(fecha);
            contentStream.endText();
            
            //Letrero consignatario
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 46);
            contentStream.showText("Consignatario:");
            contentStream.endText(); 
            
             //Agregar consignatario
            ResultSet registroCon = comando.executeQuery("SELECT consignatario FROM analisisatrasos WHERE item_cliente="+"'"+componente_CA+"' AND orden='" + orden+"'");
            if (registroCon.next()==true) {
                    String val1 = registroCon.getString("consignatario");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 46);
                    contentStream.showText(val1);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 46);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
             //Tabla pequeña
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 55);
            contentStream.showText("No. Hojas");
            contentStream.endText(); 
            
             //Agregar no. hojas
            ResultSet registroNoHojas = comando.executeQuery("SELECT TRUNCATE((analisisatrasos.cantidad_reque / calculosteoricos.pzas_hojasEntero),0) AS resultado FROM analisisatrasos, calculosteoricos, datoslistaprecios, inventario WHERE analisisatrasos.item_cliente='"+componente_CA+"'"+" AND calculosteoricos.componente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"' AND datoslistaprecios.componente='"+componente_CA+"' AND datoslistaprecios.calibre=inventario.calibre AND inventario.unidad='Hoja'");
            String valResNoHojas = null;
            if (registroNoHojas.next()==true) {
                    valResNoHojas = registroNoHojas.getString("resultado");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 55);
                    contentStream.showText(valResNoHojas);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 55);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 63);
            contentStream.showText("No. Tiras");
            contentStream.endText(); 
            
            ResultSet registroNoTiras = comando.executeQuery("SELECT ("+valResNoHojas+" * calculosteoricos.tiras_hojasEntero) AS resultado FROM calculosteoricos, analisisatrasos , datoslistaprecios, inventario WHERE analisisatrasos.item_cliente='"+componente_CA+"'"+" AND calculosteoricos.componente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"' AND datoslistaprecios.componente='"+componente_CA+"' AND datoslistaprecios.calibre=inventario.calibre AND inventario.unidad='Hoja'");
            String noTiras = null;
            if (registroNoTiras.next()==true) {
                    noTiras = registroNoTiras.getString("resultado");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 63);
                    contentStream.showText(noTiras);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 63);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 71);
            contentStream.showText("Kg Lamina");
            contentStream.endText(); 
            
            //Calcular kg lamina
            String kgLamina= null;
            ResultSet registroPeso = comando.executeQuery("SELECT ROUND((analisisatrasos.cantidad_reque * calculosteoricos.peso_pzasCal),2) AS resultado FROM analisisatrasos, calculosteoricos WHERE analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND calculosteoricos.componente="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
            if (registroPeso.next()==true) {
                    kgLamina = registroPeso.getString("resultado");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 71);
                    contentStream.showText(kgLamina);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 71);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 79);
            contentStream.showText("Pzs. Tira");
            contentStream.endText();
            
            ResultSet registroDiferencia = comando.executeQuery("SELECT pzas_tiraEntero FROM  calculosteoricos WHERE componente="+"'"+componente_CA+"'");
            String pizTira= null;
            if (registroDiferencia.next()==true) {
                    pizTira = registroDiferencia.getString("pzas_tiraEntero");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.setNonStrokingColor(0.921f, 0.078f, 0.078f);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 79);
                    contentStream.showText(pizTira);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.setNonStrokingColor(0.921f, 0.078f, 0.078f);
                    contentStream.newLineAtOffset(252, page.getMediaBox().getHeight() - 79);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
            contentStream.setNonStrokingColor(0f,0f,0f);
            //Diferencia requerimiento(kg)-kg lamina
            ResultSet registroCal = comando.executeQuery("SELECT ROUND(('"+kgLamina +"'-'"+requerimiento+"'),2) AS resultado FROM  analisisatrasos WHERE analisisatrasos.item_cliente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
            String diferencia= null;
            if (registroCal.next()==true) {
                    diferencia = registroCal.getString("resultado");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.newLineAtOffset(244, page.getMediaBox().getHeight() - 90);
                    contentStream.showText(diferencia);
                    contentStream.endText();
            } else {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream.newLineAtOffset(244, page.getMediaBox().getHeight() - 90);
                    contentStream.showText("#N/D");
                    contentStream.endText();
                }
            
            //Linea izquierda
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 53);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 57);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 61);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 65);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 69);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 73);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 77);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 81);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 85);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 89);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 93);
            contentStream.showText("|");
            contentStream.endText();
            
            //Linea central
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 53);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 57);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 61);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 65);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 69);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 73);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 77);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(246, page.getMediaBox().getHeight() - 81);
            contentStream.showText("|");
            contentStream.endText();
            
            //Linea derecha
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 53);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 57);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 61);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 65);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 69);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 73);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 77);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 81);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 85);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 89);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(279, page.getMediaBox().getHeight() - 93);
            contentStream.showText("|");
            contentStream.endText();
            
            //Linea superios
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 50);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 58);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 66);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 74);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 83);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
            
            //Linea inferios
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(218, page.getMediaBox().getHeight() - 95);
            contentStream.showText("-------------------------------------");
            contentStream.endText();
             
            //aqui termina tabla pequeña
            
            //Tabla de troqueles
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 110);
            contentStream.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(4, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(6, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Operación");
            contentStream.endText();
            
            //Agregar operaciones
            ResultSet registroOp = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp.next()==true) {
                        String val1 = registroOp.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 125);
                        contentStream.showText(val1+"°");
                        contentStream.endText();         
                } 
                
            ResultSet registroOp2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp2.next()==true) {
                    String val2 = registroOp2.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(val2+"°");
                        contentStream.endText();
                }
                
            ResultSet registroOp3 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp3.next()==true) {
                        String val3 = registroOp3.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 135);
                        contentStream.showText(val3+"°");
                        contentStream.endText();
                }
                
            ResultSet registroOp4 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp4.next()==true) {
                        String val4 = registroOp4.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 140);
                        contentStream.showText(val4+"°");
                        contentStream.endText();
                }
                
            ResultSet registroOp5 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroOp5.next()==true) {
                        String val5 = registroOp5.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(val5+"°");
                        contentStream.endText();
                }
            
            ResultSet registroOp6 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroOp6.next()==true) {
                        String val6 = registroOp6.getString("numOperaciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(15, page.getMediaBox().getHeight() - 150);
                        contentStream.showText(val6+"°");
                        contentStream.endText();
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(32, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Troquel");
            contentStream.endText();
            
            //Agregar troquel
            ResultSet registroTro = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro.next()==true) {
                        String val1 = registroTro.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 125);
                        contentStream.showText(val1);
                        contentStream.endText();
                        
                        //Agregar ubicacion de troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val1+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 125);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                } 
                
            //Agregar Troquel
            ResultSet registroTro2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro2.next()==true) {
                    String val2 = registroTro2.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(val2);
                        contentStream.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val2+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 130);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                }
                
            //Agregar Troquel    
            ResultSet registroTro3 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro3.next()==true) {
                        String val3 = registroTro3.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 135);
                        contentStream.showText(val3);
                        contentStream.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val3+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 135);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                }
                
            //Agregar Troquel    
            ResultSet registroTro4 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro4.next()==true) {
                        String val4 = registroTro4.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 140);
                        contentStream.showText(val4);
                        contentStream.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val4+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 140);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                }
                
            //Agregar Troquel
            ResultSet registroTro5 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroTro5.next()==true) {
                        String val5 = registroTro5.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(val5);
                        contentStream.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val5+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 145);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                }
             
            //Agregar Troquel
            ResultSet registroTro6 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroTro6.next()==true) {
                        String val6 = registroTro6.getString("troquel");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(34, page.getMediaBox().getHeight() - 150);
                        contentStream.showText(val6);
                        contentStream.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub = comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val6+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream.beginText();
                                contentStream.setFont(PDType1Font.HELVETICA, 5);
                                contentStream.newLineAtOffset(185, page.getMediaBox().getHeight() - 150);
                                contentStream.showText(val1troquel);
                                contentStream.endText();
                        }
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(55, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(60, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Caja");
            contentStream.endText();
            
            //Agregar caja
            ResultSet registroCaja = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroCaja.next()==true) {
                        String val1 = registroCaja.getString("caja");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 125);
                        contentStream.showText(val1);
                        contentStream.endText();   
                }
                
            ResultSet registroCaja2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja2.next()==true) {
                    String val2 = registroCaja2.getString("caja");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(val2);
                        contentStream.endText();
                }
                
             
            ResultSet registroCaja3 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja3.next()==true) {
                        String val3 = registroCaja3.getString("caja");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 135);
                        contentStream.showText(val3);
                        contentStream.endText();
                }
                
            ResultSet registroCaja4 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja4.next()==true) {
                        String val4 = registroCaja4.getString("caja");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 140);
                        contentStream.showText(val4);
                        contentStream.endText();
                }
                
            ResultSet registroCaja5 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja5.next()==true) {
                        String val5 = registroCaja5.getString("caja");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 5);
                        contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(val5);
                        contentStream.endText();
                }
                
            ResultSet registroCaja6 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
               if (registroCaja6.next()==true) {
                       String val6 = registroCaja6.getString("caja");
                       contentStream.beginText();
                       contentStream.setFont(PDType1Font.HELVETICA, 5);
                       contentStream.newLineAtOffset(58, page.getMediaBox().getHeight() - 150);
                       contentStream.showText(val6);
                       contentStream.endText();
               }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(80, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(85, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Aplicación del Troquel");
            contentStream.endText();
            
            //Agregar aplicacion del troquel
            ResultSet registroApp = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroApp.next()==true) {
                        String val1 = registroApp.getString("opciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 125);
                        contentStream.showText(val1);
                        contentStream.endText();
                }
                
            ResultSet registroApp2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroApp2.next()==true) {
                    String val2 = registroApp2.getString("opciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(val2);
                        contentStream.endText();
                }
                
            ResultSet registroApp3 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+ "'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp3.next()==true) {
                        String val3 = registroApp3.getString("opciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 135);
                        contentStream.showText(val3);
                        contentStream.endText();
                }
                
            ResultSet registroApp4 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+ "'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp4.next()==true) {
                        String val4 = registroApp4.getString("opciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 140);
                        contentStream.showText(val4);
                        contentStream.endText();
                }
                
            ResultSet registroApp5 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp5.next()==true) {
                        String val5 = registroApp5.getString("opciones");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(val5);
                        contentStream.endText();
                }
                
            ResultSet registroApp6 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
               if (registroApp6.next()==true) {
                       String val6 = registroApp6.getString("opciones");
                       contentStream.beginText();
                       contentStream.setFont(PDType1Font.HELVETICA, 4);
                       contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 150);
                       contentStream.showText(val6);
                       contentStream.endText();
               }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(180, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //Letrero de Ubicacion de troquel
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(190, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Ubicación");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //Letrero Maquinas a utilizar
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(235, page.getMediaBox().getHeight() - 115);
            contentStream.showText("Maquina a Utilizar");
            contentStream.endText();
            
            //Agregar Maquina a utilizar primera operacion
            ResultSet registroMaq = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones=1 AND componente='"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroMaq.next()==true) {
                        String val1 = registroMaq.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 125);
                        contentStream.showText(val1);
                        contentStream.endText();
                }
                
            //Agregar Maquina a utilizar segunda opercion    
            ResultSet registroMaq2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente='"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq2.next()==true) {
                    String val2 = registroMaq2.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 130);
                        contentStream.showText(val2);
                        contentStream.endText();
                }
                
            //Agregar Maquina a utilizar tercera operacion    
            ResultSet registroMaq3 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq3.next()==true) {
                        String val3 = registroMaq3.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 135);
                        contentStream.showText(val3);
                        contentStream.endText();
                }
                
            //Agregar Maquina a utilizar cuarta operacion    
            ResultSet registroMaq4 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq4.next()==true) {
                        String val4 = registroMaq4.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 140);
                        contentStream.showText(val4);
                        contentStream.endText();
                }
                
            //Agregar Maquina a utilizar quinta operacion    
            ResultSet registroMaq5 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq5.next()==true) {
                        String val5 = registroMaq5.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 145);
                        contentStream.showText(val5);
                        contentStream.endText();
                }
                
            //Agregar Maquina a utilizar SEXTA operacion    
            ResultSet registroMaq6 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq6.next()==true) {
                        String val6 = registroMaq6.getString("maquinaOp");
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 4);
                        contentStream.newLineAtOffset(224, page.getMediaBox().getHeight() - 150);
                        contentStream.showText(val6);
                        contentStream.endText();
                }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 120);
            contentStream.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 112);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 116);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 120);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 124);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 128);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 132);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 136);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 140);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 144);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 148);
            contentStream.showText("|");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(296, page.getMediaBox().getHeight() - 152);
            contentStream.showText("|");
            contentStream.endText();
            
            //
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream.newLineAtOffset(5, page.getMediaBox().getHeight() - 154);
            contentStream.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 5);
            contentStream.newLineAtOffset(22, page.getMediaBox().getHeight() - 165);
            contentStream.showText("TIEMPO TROQUELADO:");
            contentStream.endText();
            
            //Agregar Tiempo por troquel primera operacion
            ResultSet registroTiempoTroquel = comando.executeQuery("SELECT IF(tiemposyrecursos.pzsMin!='0', TRUNCATE(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60),0) , '0') AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='1' AND tiemposyrecursos.numeroOper='1' AND analisisatrasos.orden="+"'"+orden+"'" );
            String valtiempoTroquel =null;
            String op1 = "1° ";
            if (registroTiempoTroquel.next()==true) {
                    valtiempoTroquel = registroTiempoTroquel.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 165);
                    contentStream.showText(op1+CalcularTiempo(Integer.parseInt(valtiempoTroquel)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo por troquel segunda operacion
            ResultSet registroTiempoTroquel2 = comando.executeQuery("SELECT IF(tiemposyrecursos.pzsMin!='0', TRUNCATE(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60),0) , '0') AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='2' AND tiemposyrecursos.numeroOper='2' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoTroquel2 = null;
            String op2 = "2° ";
            if (registroTiempoTroquel2.next()==true) {
                    valtiempoTroquel2 = registroTiempoTroquel2.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 171);
                    contentStream.showText(op2 +CalcularTiempo(Integer.parseInt(valtiempoTroquel2)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo por troquel Tercera operacion 
            ResultSet registroTiempoTroquel3 = comando.executeQuery("SELECT IF(tiemposyrecursos.pzsMin!='0', TRUNCATE(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60),0) , '0') AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='3' AND tiemposyrecursos.numeroOper='3'AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoTroquel3 = null;
            String op3 = "3° ";
            if (registroTiempoTroquel3.next()==true) {
                    valtiempoTroquel3 = registroTiempoTroquel3.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 183);
                    contentStream.showText(op3+CalcularTiempo(Integer.parseInt(valtiempoTroquel3)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo por troquel cuarta operacion 
            ResultSet registroTiempoTroquel4 = comando.executeQuery("SELECT IF(tiemposyrecursos.pzsMin!='0', TRUNCATE(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60),0) , '0') AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='4' AND tiemposyrecursos.numeroOper='4' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoTroquel4 = null;
            String op4 = "4° ";
            if (registroTiempoTroquel4.next()==true) {
                    valtiempoTroquel4 = registroTiempoTroquel4.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 189);
                    contentStream.showText(op4+CalcularTiempo(Integer.parseInt(valtiempoTroquel4)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo por troquel quinta operacion 
            ResultSet registroTiempoTroquel5 = comando.executeQuery("SELECT IF(tiemposyrecursos.pzsMin!='0', TRUNCATE(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60),0) , '0') AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='5' AND tiemposyrecursos.numeroOper='5' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoTroquel5 = null;
            String op5 = "5° ";
            if (registroTiempoTroquel5.next()==true) {
                    valtiempoTroquel5 = registroTiempoTroquel5.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(82, page.getMediaBox().getHeight() - 195);
                    contentStream.showText(op5+CalcularTiempo(Integer.parseInt(valtiempoTroquel5)));
                    contentStream.endText();
            }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 5);
            contentStream.newLineAtOffset(22, page.getMediaBox().getHeight() - 200);
            contentStream.showText("TIEMPO TOTAL TROQUELADO:");
            contentStream.endText();
            
            //Agregar tiempo total de troquelado
            ResultSet registroTiemposT = comando.executeQuery("SELECT tiempo_troquelado FROM tiempoestandar,analisisatrasos WHERE tiempoestandar.orden = '"+orden+"' AND analisisatrasos.orden = '"+orden+"' AND tiempoestandar.componente='"+componente_CA+"'AND analisisatrasos.item_cliente='"+componente_CA+"'");
            String valtiemposT;
            if (registroTiemposT.next()==true) {
                    valtiemposT = registroTiemposT.getString("tiempo_troquelado");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(100, page.getMediaBox().getHeight() - 200);
                    contentStream.showText(valtiemposT);
                    contentStream.endText();
            }
            
            //Instalar y desmotar
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 5);
            contentStream.newLineAtOffset(150, page.getMediaBox().getHeight() - 165);
            contentStream.showText("INSTALAR + DESMONTAR:");
            contentStream.endText();
            
            //Agregar Tiempo ins+desm primera operacion
            ResultSet registroTiempoID = comando.executeQuery("SELECT ((inst_desm)*60) AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='1' AND tiemposyrecursos.numeroOper='1' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoID = null;
            String opID1 = "1° ";
            if (registroTiempoID.next()==true) {
                    valtiempoID = registroTiempoID.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 165);
                    contentStream.showText(opID1+CalcularTiempo(Integer.parseInt(valtiempoID)));
                    contentStream.endText();
            }
            
             //Agregar Tiempo ins+desm segunda operacion
            ResultSet registroTiempoID2 = comando.executeQuery("SELECT ((inst_desm)*60) AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='2' AND tiemposyrecursos.numeroOper='2' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoID2 = null;
            String opID2 = "2° ";
            if (registroTiempoID2.next()==true) {
                    valtiempoID2 = registroTiempoID2.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 171);
                    contentStream.showText(opID2 +CalcularTiempo(Integer.parseInt(valtiempoID2)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo ins+desm Tercera operacion 
            ResultSet registroTiempoID3 = comando.executeQuery("SELECT ((inst_desm)*60) AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='3' AND tiemposyrecursos.numeroOper='3' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoID3 = null;
            String opID3 = "3° ";
            if (registroTiempoID3.next()==true) {
                    valtiempoID3 = registroTiempoID3.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 183);
                    contentStream.showText(opID3+CalcularTiempo(Integer.parseInt(valtiempoID3)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo ins+desm cuarta operacion 
            ResultSet registroTiempoID4 = comando.executeQuery("SELECT ((inst_desm)*60) AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='4' AND tiemposyrecursos.numeroOper='4' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoID4 = null;
            String opID4 = "4° ";
            if (registroTiempoID4.next()==true) {
                    valtiempoID4 = registroTiempoID4.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 189);
                    contentStream.showText(opID4+CalcularTiempo(Integer.parseInt(valtiempoID4)));
                    contentStream.endText();
            }
            
            //Agregar Tiempo ins+desm quinta operacion 
            ResultSet registroTiempoID5 = comando.executeQuery("SELECT ((inst_desm)*60) AS resu FROM tiemposyrecursos, analisisatrasos, troqueles WHERE tiemposyrecursos.componente="+"'"+componente_CA+"'"+" AND troqueles.componente="+"'"+componente_CA+"'"+" AND analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND troqueles.numOperaciones='5' AND tiemposyrecursos.numeroOper='5' AND analisisatrasos.orden="+"'"+orden+"'");
            String valtiempoID5 = null;
            String opID5 = "5° ";
            if (registroTiempoID5.next()==true){
                    valtiempoID5 = registroTiempoID5.getString("resu"); 
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 195);
                    contentStream.showText(opID5+CalcularTiempo(Integer.parseInt(valtiempoID5)));
                    contentStream.endText();
            }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 5);
            contentStream.newLineAtOffset(150, page.getMediaBox().getHeight() - 200);
            contentStream.showText("TIEMPO TOTAL INST+DESM:");
            contentStream.endText();
            
            //Agregar tiempo total de Inst+ desm
            ResultSet registroTiemposTID = comando.executeQuery("SELECT inst_desm AS res FROM tiempoestandar WHERE orden ="+ "'"+orden+"'");
            String valtiemposTID = null;
            if (registroTiemposTID.next()==true) {
                    valtiemposTID = registroTiemposTID.getString("res");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(220, page.getMediaBox().getHeight() - 200);
                    contentStream.showText(valtiemposTID);
                    contentStream.endText();
            }
            
            //Total de tiempo
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 5);
            contentStream.newLineAtOffset(100, page.getMediaBox().getHeight() - 220);
            contentStream.showText("TIEMPO TOTAL:");
            contentStream.endText();
            
            //Agregar tiempo total de produccion
            ResultSet registroTiempos = comando.executeQuery("SELECT tiempo_total FROM tiempoestandar WHERE orden ="+ "'"+orden+"'");
            String valtiempos = null;
            if (registroTiempos.next()==true) {
                    valtiempos = registroTiempos.getString("tiempo_total");   
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream.newLineAtOffset(142, page.getMediaBox().getHeight() - 220);
                    contentStream.showText(valtiempos);
                    contentStream.endText();
            }
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(22, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Operación");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(70, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Fecha");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(120, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Cantidad Parcial");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(190, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Acumulado");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 5);
            contentStream.newLineAtOffset(250, page.getMediaBox().getHeight() - 240);
            contentStream.showText("Operador");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 5);
            contentStream.newLineAtOffset(240, page.getMediaBox().getHeight() - 350);
            contentStream.showText("F-PP 01 REV 03");
            contentStream.endText();
            
            //ORDEN DE CORTE
            
             // Text
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream2.newLineAtOffset(70, page2.getMediaBox().getHeight() - 20);
            contentStream2.showText("ORDEN DE CORTE");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 30);
            contentStream2.showText("'JC TALLER MECANICO'");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 40);
            contentStream2.showText("Estatus P.P.A.P:");
            contentStream2.endText();
            
            //Agergar ESTATUS 
            ResultSet registro22= comando.executeQuery("SELECT PpapStatus FROM consumoyantecedentes, analisisatrasos WHERE componente_CA='"+componente_CA+"' AND analisisatrasos.orden = '" + orden+"'");
            if (registro22.next()==true) {
                    String val1 = registro22.getString("PpapStatus");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 40);
                    contentStream2.showText(val1);
                    contentStream2.endText();   
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 40);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 50);
            contentStream2.showText("No. de orden:");
            contentStream2.endText();
            
            //Agergar orden
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 50);
            contentStream2.showText(orden);
            contentStream2.endText();

            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 56);
            contentStream2.showText("Componente:");
            contentStream2.endText();
            
            //Agregar componente
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 56);
            contentStream2.showText(componente_CA);
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 62);
            contentStream2.showText("C/R:");
            contentStream2.endText();
            
            //Agregar C/R
            ResultSet registroCR2 = comando.executeQuery("SELECT cr FROM crs, analisisatrasos WHERE componente="+"'"+componente_CA+"' AND analisisatrasos.orden = '" + orden+"'");
            if (registroCR2.next()==true) {
                    String val1 = registroCR2.getString("cr");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 62);
                    contentStream2.showText(val1);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 62);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
            }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 68);
            contentStream2.showText("Cantidad requerida:");
            contentStream2.endText();
            
            //Agregar cantidad requeridas
            String cantidadReque2 = null;
            ResultSet registroCant2 = comando.executeQuery("SELECT cantidad_reque FROM analisisatrasos WHERE  analisisatrasos.orden = '" + orden+"'");
            if (registroCant2.next()==true) {
                    cantidadReque2 = registroCant2.getString("cantidad_reque");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 68);
                    contentStream2.showText(cantidadReque2);
                    contentStream2.endText();
            }

            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(90, page2.getMediaBox().getHeight() - 68);
            contentStream2.showText("Pzs");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 74);
            contentStream2.showText("Lamina Calibre:");
            contentStream2.endText();
            
            //Agergar lamina
                ResultSet registro2_2 = comando.executeQuery("SELECT desMatePrima FROM consumoyantecedentes, analisisatrasos WHERE componente_CA='"+componente_CA+"' AND analisisatrasos.orden ='"+orden+"'");
                if (registro2_2.next()==true) {
                    String val1 = registro2_2.getString("desMatePrima");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 74);
                    contentStream2.showText(val1 = val1.replace("\n", "").replace("\r", ""));
                    contentStream2.endText();      
                } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 74);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }

            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 80);
            contentStream2.showText("Requerimiento(kgs):");
            contentStream2.endText();
            
            //Agregar requerimiento
            ResultSet registro2_22 = comando.executeQuery("SELECT ROUND((analisisatrasos.cantidad_reque *consumoyantecedentes.consumo_uni),2) AS resultado FROM analisisatrasos, consumoyantecedentes WHERE item_cliente="+"'"+componente_CA+"'"+" AND componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
            String requerimiento2 = null;
            if (registro2_22.next()==true) {
                requerimiento2 = registro2_22.getString("resultado");
                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 80);
                contentStream2.showText(requerimiento2);
                contentStream2.endText();
            } else {
                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 74);
                contentStream2.showText("#N/D");
                contentStream2.endText();
            }
                    
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 86);
            contentStream2.showText("Ancho de Tira:");
            contentStream2.endText();
            
            //Agregar ancho de tira
            ResultSet registro4_2 = comando.executeQuery("SELECT anchoTira FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro4_2.next()==true) {
                    String val1 = registro4_2.getString("anchoTira");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 86);
                    contentStream2.showText(val1);
                    contentStream2.endText();
                } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 74);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 92);
            contentStream2.showText("FAMILIA:");
            contentStream2.endText();

           ResultSet registro5_2= comando.executeQuery("SELECT familia FROM antecedentesfamilia, analisisatrasos WHERE componente="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro5_2.next()==true) {
                    String val1 = registro5_2.getString("familia");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 92);
                    contentStream2.showText(val1= val1.replace("\n", "").replace("\r", ""));
                    contentStream2.endText();
                } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 92);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 99);
            contentStream2.showText("ANTECEDENTES:");
            contentStream2.endText();
            
            //Agregar antecedentes
            ResultSet registro6_2 = comando.executeQuery("SELECT comentario_CA FROM consumoyantecedentes, analisisatrasos WHERE componente_CA="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
                if (registro6_2.next()==true) {
                    String val1 = registro6_2.getString("comentario_CA");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 3);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 99);
                    contentStream2.showText(val1 = val1.replace("\n", "").replace("\r", ""));
                    contentStream2.endText();
                } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 99);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
                
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 40);
            contentStream2.showText("Vencimiento:");
            contentStream2.endText();
            
            //Agregar fecha vencimiento
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 40);
            contentStream2.showText(fecha);
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 46);
            contentStream2.showText("Consignatario:");
            contentStream2.endText(); 
            
            //Agregar consignatario
            ResultSet registroCon2 = comando.executeQuery("SELECT consignatario FROM analisisatrasos WHERE item_cliente="+"'"+componente_CA+"' AND orden='" + orden+"'");
            if (registroCon2.next()==true) {
                    String val1 = registroCon2.getString("consignatario");   
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 46);
                    contentStream2.showText(val1);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 46);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
             //Tabla pequeña
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 55);
            contentStream2.showText("No. Hojas");
            contentStream2.endText(); 
            
           ResultSet registroNoHojas2 = comando.executeQuery("SELECT TRUNCATE((analisisatrasos.cantidad_reque / calculosteoricos.pzas_hojasEntero),0) AS resultado FROM analisisatrasos, calculosteoricos, datoslistaprecios, inventario WHERE analisisatrasos.item_cliente='"+componente_CA+"'"+" AND calculosteoricos.componente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"' AND datoslistaprecios.componente='"+componente_CA+"' AND datoslistaprecios.calibre=inventario.calibre AND inventario.unidad='Hoja'");
            String valResNoHojas2 = null;
            if (registroNoHojas2.next()==true) {
                    valResNoHojas2 = registroNoHojas2.getString("resultado");
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 55);
                    contentStream2.showText(valResNoHojas2);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 55);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 63);
            contentStream2.showText("No. Tiras");
            contentStream2.endText(); 
            
            ResultSet registroNoTiras2 = comando.executeQuery("SELECT ("+valResNoHojas+" * calculosteoricos.tiras_hojasEntero) AS resultado FROM calculosteoricos, analisisatrasos , datoslistaprecios, inventario WHERE analisisatrasos.item_cliente='"+componente_CA+"'"+" AND calculosteoricos.componente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"' AND datoslistaprecios.componente='"+componente_CA+"' AND datoslistaprecios.calibre=inventario.calibre AND inventario.unidad='Hoja'");
            String noTiras2 = null;
            if (registroNoTiras2.next()==true) {
                    noTiras2 = registroNoTiras2.getString("resultado");   
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 63);
                    contentStream2.showText(noTiras);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 63);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 71);
            contentStream2.showText("Kg Lamina");
            contentStream2.endText(); 
            
            //Calcular kg lamina
            //String kgLamina= null;
            ResultSet registroPeso2 = comando.executeQuery("SELECT ROUND((analisisatrasos.cantidad_reque * calculosteoricos.peso_pzasCal),2) AS resultado FROM analisisatrasos, calculosteoricos WHERE analisisatrasos.item_cliente="+"'"+componente_CA+"'"+" AND calculosteoricos.componente="+"'"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
            if (registroPeso2.next()==true) {
                    kgLamina = registroPeso2.getString("resultado");   
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 71);
                    contentStream2.showText(kgLamina);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 71);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 79);
            contentStream2.showText("Pzs. Tira");
            contentStream2.endText();
            
            ResultSet registroDiferencia2 = comando.executeQuery("SELECT pzas_tiraEntero FROM  calculosteoricos WHERE componente="+"'"+componente_CA+"'");
            String pizTira2= null;
            if (registroDiferencia2.next()==true) {
                    pizTira2 = registroDiferencia2.getString("pzas_tiraEntero");   
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.setNonStrokingColor(0.921f, 0.078f, 0.078f);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 79);
                    contentStream2.showText(pizTira2);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
                    contentStream2.setNonStrokingColor(0.921f, 0.078f, 0.078f);
                    contentStream2.newLineAtOffset(252, page2.getMediaBox().getHeight() - 79);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            contentStream2.setNonStrokingColor(0f,0f,0f);
            //Diferencia requerimiento(kg)-kg lamina
            ResultSet registroCal2 = comando.executeQuery("SELECT ROUND(('"+kgLamina +"'-'"+requerimiento+"'),2) AS resultado FROM  analisisatrasos WHERE analisisatrasos.item_cliente='"+componente_CA+"' AND analisisatrasos.orden='" + orden+"'");
            String diferencia2= null;
            if (registroCal2.next()==true) {
                    diferencia2 = registroCal2.getString("resultado");   
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream2.newLineAtOffset(244, page2.getMediaBox().getHeight() - 90);
                    contentStream2.showText(diferencia2);
                    contentStream2.endText();
            } else {
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 6);
                    contentStream2.newLineAtOffset(244, page2.getMediaBox().getHeight() - 90);
                    contentStream2.showText("#N/D");
                    contentStream2.endText();
                }
            
            //Linea izquierda
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 53);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 57);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 61);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 65);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 69);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 73);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 77);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 81);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 85);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 89);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 93);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //Linea central
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 53);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 57);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 61);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 65);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 69);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 73);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 77);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(246, page2.getMediaBox().getHeight() - 81);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //Linea derecha
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 53);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 57);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 61);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 65);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 69);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 73);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 77);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 81);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 85);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 89);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(279, page2.getMediaBox().getHeight() - 93);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //Liena superios
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 50);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 58);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 66);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 74);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 83);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
            
            //Linea inferior
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(218, page2.getMediaBox().getHeight() - 95);
            contentStream2.showText("-------------------------------------");
            contentStream2.endText();
             
            //aquí termina tabla pequeña
            
            //Tabla de troqueles
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 110);
            contentStream2.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(4, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(6, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Operación");
            contentStream2.endText();
            
            //Agregar operaciones
            ResultSet registroOp1_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp1_2.next()==true) {
                        String val1 = registroOp1_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 125);
                        contentStream2.showText(val1+"°");
                        contentStream2.endText();         
                }
                
            ResultSet registroOp2_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp2_2.next()==true) {
                    String val2 = registroOp2_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 130);
                        contentStream2.showText(val2+"°");
                        contentStream2.endText();
                }
                
            ResultSet registroOp3_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp3_2.next()==true) {
                        String val3 = registroOp3_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 135);
                        contentStream2.showText(val3+"°");
                        contentStream2.endText();
                }
                
            ResultSet registroOp4_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroOp4_2.next()==true) {       
                        String val4 = registroOp4_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 140);
                        contentStream2.showText(val4+"°");
                        contentStream2.endText();
                }
                
            ResultSet registroOp5_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroOp5_2.next()==true) {
                        String val5 = registroOp5_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 145);
                        contentStream2.showText(val5+"°");
                        contentStream2.endText();
                }
            
            ResultSet registroOp6_2 = comando.executeQuery("SELECT numOperaciones FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroOp6_2.next()==true) {
                        String val6 = registroOp6_2.getString("numOperaciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(15, page2.getMediaBox().getHeight() - 150);
                        contentStream2.showText(val6+"°");
                        contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(30, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(32, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Troquel");
            contentStream2.endText();
            
            //Agregar troquel
            ResultSet registroTro1_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro1_2.next()==true) {
                        String val1 = registroTro1_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 125);
                        contentStream2.showText(val1);
                        contentStream2.endText();
                        
                        //Agregar ubicacion de troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val1+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 125);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                } 
                
            //Agregar Troquel
            ResultSet registroTro2_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro2_2.next()==true) {
                    String val2 = registroTro2_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 130);
                        contentStream2.showText(val2);
                        contentStream2.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val2+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 130);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                }
                
            //Agregar Troquel    
            ResultSet registroTro3_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro3_2.next()==true) {
                        String val3 = registroTro3_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 135);
                        contentStream2.showText(val3);
                        contentStream2.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val3+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 135);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                }
                
            //Agregar Troquel    
            ResultSet registroTro4_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroTro4_2.next()==true) {
                        String val4 = registroTro4_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 140);
                        contentStream2.showText(val4);
                        contentStream2.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val4+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 140);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                }
                
            //Agregar Troquel
            ResultSet registroTro5_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroTro5_2.next()==true) {
                        String val5 = registroTro5_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 145);
                        contentStream2.showText(val5);
                        contentStream2.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val5+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 145);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                }
             
            //Agregar Troquel
             ResultSet registroTro6_2 = comando.executeQuery("SELECT troquel FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroTro6_2.next()==true) {
                        String val6 = registroTro6_2.getString("troquel");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(34, page2.getMediaBox().getHeight() - 150);
                        contentStream2.showText(val6);
                        contentStream2.endText();
                        
                        //Agregar ubicacion troquel
                        ResultSet registroHub= comando.executeQuery("SELECT ubicacion FROM ubicaciontroquel, analisisatrasos WHERE troquel="+ "'"+val6+"'  AND analisisatrasos.orden='" + orden+"'");
                        if (registroHub.next()==true) {
                                String val1troquel = registroHub.getString("ubicacion");
                                contentStream2.beginText();
                                contentStream2.setFont(PDType1Font.HELVETICA, 5);
                                contentStream2.newLineAtOffset(185, page2.getMediaBox().getHeight() - 150);
                                contentStream2.showText(val1troquel);
                                contentStream2.endText();
                        }
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(55, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(60, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Caja");
            contentStream2.endText();
            
            //Agregar caja
            ResultSet registroCaja1_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroCaja1_2.next()==true) {
                        String val1 = registroCaja1_2.getString("caja");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 125);
                        contentStream2.showText(val1);
                        contentStream2.endText();   
                }
                
            ResultSet registroCaja2_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja2_2.next()==true) {
                    String val2 = registroCaja2_2.getString("caja");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 130);
                        contentStream2.showText(val2);
                        contentStream2.endText();
                }
                
             
            ResultSet registroCaja3_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja3_2.next()==true) {
                        String val3 = registroCaja3_2.getString("caja");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 135);
                        contentStream2.showText(val3);
                        contentStream2.endText();
                }
                
            ResultSet registroCaja4_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA +"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja4_2.next()==true) {
                        String val4 = registroCaja4_2.getString("caja");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 140);
                        contentStream2.showText(val4);
                        contentStream2.endText();
                }
                
            ResultSet registroCaja5_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroCaja5_2.next()==true) {
                        String val5 = registroCaja5_2.getString("caja");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 5);
                        contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 145);
                        contentStream2.showText(val5);
                        contentStream2.endText();
                }
                
            ResultSet registroCaja6_2 = comando.executeQuery("SELECT caja FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
               if (registroCaja6_2.next()==true) {
                       String val6 = registroCaja6_2.getString("caja");
                       contentStream2.beginText();
                       contentStream2.setFont(PDType1Font.HELVETICA, 5);
                       contentStream2.newLineAtOffset(58, page2.getMediaBox().getHeight() - 150);
                       contentStream2.showText(val6);
                       contentStream2.endText();
               }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(80, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(85, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Aplicación del Troquel");
            contentStream2.endText();
            
            //Agregar aplicacion del troquel
            ResultSet registroApp1_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='1' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroApp1_2.next()==true) {
                        String val1 = registroApp1_2.getString("opciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 125);
                        contentStream2.showText(val1);
                        contentStream2.endText();
                }
                
            ResultSet registroApp2_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroApp2_2.next()==true) {
                    String val2 = registroApp2_2.getString("opciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 130);
                        contentStream2.showText(val2);
                        contentStream2.endText();
                }
                
            ResultSet registroApp3_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+ "'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp3_2.next()==true) {
                        String val3 = registroApp3_2.getString("opciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 135);
                        contentStream2.showText(val3);
                        contentStream2.endText();
                }
                
            ResultSet registroApp4_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+ "'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp4_2.next()==true) {     
                        String val4 = registroApp4_2.getString("opciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 140);
                        contentStream2.showText(val4);
                        contentStream2.endText();
                }
                
            ResultSet registroApp5_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroApp5_2.next()==true) {
                        String val5 = registroApp5_2.getString("opciones");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 145);
                        contentStream2.showText(val5);
                        contentStream2.endText();
                }
                
            ResultSet registroApp6_2 = comando.executeQuery("SELECT opciones FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
               if (registroApp6_2.next()==true) {
                       String val6 = registroApp6_2.getString("opciones");
                       contentStream2.beginText();
                       contentStream2.setFont(PDType1Font.HELVETICA, 4);
                       contentStream2.newLineAtOffset(82, page2.getMediaBox().getHeight() - 150);
                       contentStream2.showText(val6);
                       contentStream2.endText();
               }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //Letrero de Ubicacion de troquel
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(190, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Ubicación");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(220, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //Letrero Maquinas a utilizar
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(235, page2.getMediaBox().getHeight() - 115);
            contentStream2.showText("Maquina a Utilizar");
            contentStream2.endText();
            
            //Agregar Maquina a utilizar primera operacion
            ResultSet registroMaq1_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones=1 AND componente='"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'" );
                if (registroMaq1_2.next()==true) {
                        String val1 = registroMaq1_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 125);
                        contentStream2.showText(val1);
                        contentStream2.endText();
                }
                
            //Agregar Maquina a utilizar segunda opercion    
            ResultSet registroMaq2_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='2' AND componente='"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq2_2.next()==true) {
                    String val2 = registroMaq2_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 130);
                        contentStream2.showText(val2);
                        contentStream2.endText();
                }
                
            //Agregar Maquina a utilizar tercera operacion    
            ResultSet registroMaq3_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='3' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq3_2.next()==true) {
                        String val3 = registroMaq3_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 135);
                        contentStream2.showText(val3);
                        contentStream2.endText();
                }
                
            //Agregar Maquina a utilizar cuarta operacion    
            ResultSet registroMaq4_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='4' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq4_2.next()==true) {
                        String val4 = registroMaq4_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 140);
                        contentStream2.showText(val4);
                        contentStream2.endText();
                }
                
            //Agregar Maquina a utilizar quinta operacion    
            ResultSet registroMaq5_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='5' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq5_2.next()==true) {
                        String val5 = registroMaq5_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 145);
                        contentStream2.showText(val5);
                        contentStream2.endText();
                }
                
            //Agregar Maquina a utilizar SEXTA operacion    
            ResultSet registroMaq6_2 = comando.executeQuery("SELECT maquinaOp FROM troqueles, analisisatrasos WHERE numOperaciones='6' AND componente="+"'"+componente_CA+"'  AND analisisatrasos.orden='" + orden+"'");
                if (registroMaq6_2.next()==true) {
                        String val6 = registroMaq6_2.getString("maquinaOp");
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA, 4);
                        contentStream2.newLineAtOffset(224, page2.getMediaBox().getHeight() - 150);
                        contentStream2.showText(val6);
                        contentStream2.endText();
                }
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 112);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 116);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 120);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 124);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 128);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 132);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 136);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 140);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 144);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 148);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(296, page2.getMediaBox().getHeight() - 152);
            contentStream2.showText("|");
            contentStream2.endText();
            
            //
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(5, page2.getMediaBox().getHeight() - 154);
            contentStream2.showText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            contentStream2.endText();
            
            //Letrero de almacen de materia prima
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(90, page2.getMediaBox().getHeight() - 165);
            contentStream2.showText("ALMACEN DE MATERIA PRIMA");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 180);
            contentStream2.showText("SOLICITANTE:___________________________________________________________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 195);
            contentStream2.showText("TIPO DE MATERIAL:");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(90, page2.getMediaBox().getHeight() - 195);
            contentStream2.showText("ROLLO");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(110, page2.getMediaBox().getHeight() - 192);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(110, page2.getMediaBox().getHeight() - 196);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(111, page2.getMediaBox().getHeight() - 190);
            contentStream2.showText("------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 192);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 196);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(111, page2.getMediaBox().getHeight() - 198);
            contentStream2.showText("------------------------");
            contentStream2.endText();
            
            //HOJA
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA, 5);
            contentStream2.newLineAtOffset(160, page2.getMediaBox().getHeight() - 195);
            contentStream2.showText("HOJA");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 192);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 196);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 190);
            contentStream2.showText("------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(219, page2.getMediaBox().getHeight() - 192);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(219, page2.getMediaBox().getHeight() - 196);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(180, page2.getMediaBox().getHeight() - 198);
            contentStream2.showText("------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 210);
            contentStream2.showText("ANCHO DE TIRA:_______________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 210);
            contentStream2.showText("NO. ROLLO:_______________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 221);
            contentStream2.showText("NO. HOJAS:        _______________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 221);
            contentStream2.showText("NO. TIRAS:  _______________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 232);
            contentStream2.showText("PESO (CAL):       _______________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 250);
            contentStream2.showText("FIRMA DEL SOLICITANTE:_________________________________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 270);
            contentStream2.showText("AUTORIZADO POR:__________________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 270);
            contentStream2.showText("PUESTO:__________________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 281);
            contentStream2.showText("------------------------------------------------------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(23, page2.getMediaBox().getHeight() - 285);
            contentStream2.showText("OBSERVACIONES:");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 284);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 288);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 292);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 296);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 300);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(20, page2.getMediaBox().getHeight() - 304);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(21, page2.getMediaBox().getHeight() - 306);
            contentStream2.showText("------------------------------------------------------------------------");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 284);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 288);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 292);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 296);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 300);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(140, page2.getMediaBox().getHeight() - 304);
            contentStream2.showText("|");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 5);
            contentStream2.newLineAtOffset(150, page2.getMediaBox().getHeight() - 295);
            contentStream2.showText("FIRMA:   __________________________");
            contentStream2.endText();
            
            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 5);
            contentStream2.newLineAtOffset(240, page2.getMediaBox().getHeight() - 350);
            contentStream2.showText("F-PP 02 REV 03");
            contentStream2.endText();
            
            contentStream.close();
            
            contentStream2.close();

            document.save("ORDEN.pdf");
        }
    }
   
    private static String CalcularTiempo(int totalSecs)
    {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
    
    public void suma(){
         
        try {
            jLabel9.setText("");
            jLabel11.setText("");
            jButton14.setText("");

            Statement comando=cn.createStatement();
            
            //TOTAL DE ATRASOS
            ResultSet registroA = comando.executeQuery("SELECT COUNT(fechaVencida) AS total FROM analisisatrasos WHERE CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) <= CONVERT(DATE_FORMAT(NOW(), '%Y%m%d'), CHAR)");
            if(registroA.next()==true) {
                jButton14.setText(registroA.getString("total")); 
            }
           
            //TOTAL DE LINEAS
            ResultSet registro = comando.executeQuery("SELECT COUNT(*) AS suma FROM analisisatrasos WHERE 1");
            if(registro.next()==true) {
                jLabel9.setText(registro.getString("suma"));                
            }
            
            //SUMA DE PIEZAS SOLICITADAS
            ResultSet registro2 = comando.executeQuery("SELECT SUM(cantidad_reque) AS sumPzs FROM analisisatrasos WHERE 1");
            if(registro2.next()==true) {
                jLabel11.setText(registro2.getString("sumPzs"));                
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jtxtfiltro = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableA = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ORDENES SOLICITADAS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 610, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/buscar.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 80, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton3.setText("CERRAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 110, 40));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Entregado.png"))); // NOI18N
        jButton4.setText("ENTREGADO");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 160, 30));

        jLabel5.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("TIEMPO ESTANDAR");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 400, 480, 50));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null}
            },
            new String [] {
                "ORDEN", "COMPONENTE", "INST + DESM ", "TIEMPO TROQUELADO ", "TIEMPO TOTAL "
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 800, 220));

        jLabel10.setText("TOTAL PZS SOLICITADAS:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 360, -1, 20));

        jLabel11.setText("jLabel11");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 360, 130, 20));

        jLabel13.setText("TOTAL DE ATRASOS:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, -1, 20));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/calculo.png"))); // NOI18N
        jButton5.setText("CALCULAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 510, 150, 40));

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
        getContentPane().add(jtxtfiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 80, 240, 30));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/PDF.png"))); // NOI18N
        jButton6.setText("GENERAR ORDEN");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 370, 190, 40));

        jLabel8.setText("TOTAL DE LINEAS:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, -1, 20));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Tiempo.png"))); // NOI18N
        jButton8.setText("TIEMPOS ATRASOS");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 620, 210, 40));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        jButton7.setText("MODIFICAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        jLabel9.setText("jLabel9");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 360, -1, 20));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 500, 60, 60));

        jLabel7.setText("EXPORTAR");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 560, -1, -1));

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Ordenes.png"))); // NOI18N
        jButton9.setText("<HTML><center>REGISTRO DE<br> NUEVAS ORDENES</HTML>");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 0, -1, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 60, 60));

        jLabel6.setText("EXPORTAR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        jButton12.setText("AGREGAR");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 120, -1));

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton11.setText("<html><center>ORDEN<br> CANCELADA</center></html>");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 120, -1));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/477439.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 80, 60));

        jTableA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PRIORIDAD", "<html><center>FECHA<br> RECIBIDA</center></html>", "<html><center>FECHA<br> VENCIDA</center></html>", "ORDEN", "<html><center>CANTIDAD<br> SOLICITADA</center></html>", "<html><center>CANTIDAD<br> ENTREGADA</center></html>", "<html><center>PIEZAS A<br> ENTREGAR</center></html>", "CONSIGNATARIO", "COMPONENTE", "COMENTARIO", "<html><center>FECHA<br> EMBARQUE</center></html>", "FACTURA", "EMBARQUES"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableA.setName(""); // NOI18N
        jScrollPane2.setViewportView(jTableA);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 1150, 250));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton14.setText("jButton14");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, -1, -1));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Pronostico.png"))); // NOI18N
        jButton15.setText("VER PARCIAL");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 160, 30));

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Pronostico.png"))); // NOI18N
        jButton16.setText("VER PARCIAL");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 160, 30));

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        jButton17.setText("ELIMINAR");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, 30));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 660));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/PDF.png"))); // NOI18N
        jMenu1.setText("CAMBIOS");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excelC.png"))); // NOI18N
        jMenu2.setText("PROGRAMA DE MATRICERIA");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila_seleccionada = jTableA.getSelectedRow();
       
        if(fila_seleccionada >= 0 ){
            try {
                OrdenesSolicitadasGUI.this.dispose();
                ModificarOrdenesSol modificar = new ModificarOrdenesSol(this.analisisatrasos.get(fila_seleccionada), mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed
   
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            int fila_seleccionada = jTableA.getSelectedRow();
            if(fila_seleccionada != -1){
                int resp = JOptionPane.showConfirmDialog(null, "¿LA ORDEN ES PARCIAL?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                String ordenEnv = (String) jTableA.getValueAt(fila_seleccionada, 3);
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    
                    PreparedStatement pst = cn.prepareStatement("INSERT INTO entregados (entregados.fechaRecibida,entregados.fechaVencida, entregados.orden, entregados.cantidad_reque, entregados.cantidadEntregada, entregados.piezasEntregar, entregados.consignatario, entregados.item_cliente,entregados.comentario,entregados.fechaEmbarque,entregados.factura) SELECT analisisatrasos.fechaRecibida,analisisatrasos.fechaVencida, analisisatrasos.orden, analisisatrasos.cantidad_reque, analisisatrasos.CantidadEntregada, analisisatrasos.piezasEntregar, analisisatrasos.consignatario, analisisatrasos.item_cliente, analisisatrasos.comentario, analisisatrasos.fechaEmbarque, analisisatrasos.factura FROM analisisatrasos WHERE analisisatrasos.orden='"+ordenEnv+"'");
                    pst.executeUpdate();
                    
                    OrdenesSolicitadasGUI.this.dispose();
                    EntregadosGUI entregados = new EntregadosGUI(mod);
                    entregados.setVisible(true);
                    entregados.setLocationRelativeTo(null);
                }else if(resp == JOptionPane.NO_OPTION){

                    //copiar datos de la fila seleccionada analisis de atrasos a entregados
                    PreparedStatement pst = cn.prepareStatement("INSERT INTO entregados (entregados.fechaRecibida,entregados.fechaVencida, entregados.orden, entregados.cantidad_reque, entregados.cantidadEntregada, entregados.piezasEntregar, entregados.consignatario, entregados.item_cliente,entregados.comentario,entregados.fechaEmbarque,entregados.factura) SELECT analisisatrasos.fechaRecibida,analisisatrasos.fechaVencida, analisisatrasos.orden, analisisatrasos.cantidad_reque, analisisatrasos.CantidadEntregada, analisisatrasos.piezasEntregar, analisisatrasos.consignatario, analisisatrasos.item_cliente, analisisatrasos.comentario, analisisatrasos.fechaEmbarque, analisisatrasos.factura FROM analisisatrasos WHERE analisisatrasos.orden='"+ordenEnv+"'");
                    pst.executeUpdate();

                    //Eliminar el dato tiempo Atrasos
                    PreparedStatement pst4 = cn.prepareStatement("DELETE FROM tiempoatrasos WHERE orden='"+ordenEnv+"'");
                    pst4.executeUpdate();

                    //Eliminar el dato tiempoestandar
                    PreparedStatement pst2 = cn.prepareStatement("DELETE FROM tiempoestandar WHERE orden='"+ordenEnv+"'");
                    pst2.executeUpdate();

                    //Eliminar el dato analisisatrasos
                    PreparedStatement pst3 = cn.prepareStatement("DELETE FROM analisisatrasos WHERE orden='"+ordenEnv+"'");
                    pst3.executeUpdate();

                    //Eliminar el dato atrasos Produccion
                    PreparedStatement pst5 = cn.prepareStatement("DELETE FROM atrasosproduccion WHERE orden='"+ordenEnv+"'");
                    pst5.executeUpdate();

                    //Eliminar orden de compra
                    PreparedStatement pst6 = cn.prepareStatement("DELETE FROM orden_compra WHERE orden='"+ordenEnv+"'");
                    pst6.executeUpdate();
                    
                    OrdenesSolicitadasGUI.this.dispose();
                    EntregadosGUI entregados = new EntregadosGUI(mod);
                    entregados.setVisible(true);
                    entregados.setLocationRelativeTo(null);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error."); 
        }     
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try{
            //Insertar el componente, la orden y la fecha de vencimiento a la tabla tiempoestandar
            PreparedStatement pst = cn.prepareStatement("INSERT INTO tiempoestandar (tiempoestandar.fechaVencida,tiempoestandar.orden,tiempoestandar.componente) SELECT analisisatrasos.fechaVencida, analisisatrasos.orden, analisisatrasos.item_cliente FROM analisisatrasos WHERE NOT EXISTS ( SELECT tiempoestandar.orden FROM tiempoestandar WHERE tiempoestandar.orden = analisisatrasos.orden AND tiempoestandar.componente= analisisatrasos.item_cliente)");
            pst.executeUpdate();
            
            //calcular los tiempos
            //Tiempo instalar + desempacar
            PreparedStatement pst2 = cn.prepareStatement("UPDATE tiempoestandar SET tiempoestandar.inst_desm=(SELECT sec_to_time(CAST(SUM(((inst_desm)*60)) AS SIGNED )) FROM tiemposyrecursos, analisisatrasos WHERE tiemposyrecursos.componente=tiemposyrecursos.componente AND tiemposyrecursos.componente= tiempoestandar.componente AND tiempoestandar.componente=analisisatrasos.item_cliente AND tiempoestandar.orden= analisisatrasos.orden)");
            pst2.executeUpdate();
            
            //tiempo de troquelado
            PreparedStatement pst3 = cn.prepareStatement("UPDATE tiempoestandar SET tiempo_troquelado=(SELECT IF(tiemposyrecursos.pzsMin!='0', sec_to_time(CAST(SUM((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*60) AS SIGNED )),'00:00:00') FROM tiemposyrecursos, analisisatrasos WHERE tiemposyrecursos.componente=tiemposyrecursos.componente AND tiemposyrecursos.componente= tiempoestandar.componente AND tiempoestandar.componente= analisisatrasos.item_cliente AND tiempoestandar.orden= analisisatrasos.orden AND tiempoestandar.orden= analisisatrasos.orden)");
            pst3.executeUpdate();
            
            //tiempo total
            PreparedStatement pst4 = cn.prepareStatement("UPDATE tiempoestandar SET tiempo_total=(SELECT IF(tiempoestandar.tiempo_troquelado!='0', concat(ADDTIME(inst_desm ,tiempo_troquelado)), '00:00:00') WHERE orden= orden AND componente=componente)");
            pst4.executeUpdate();

            //visualizar tabla 
            this.analisisAtrasos();
            this.tiempoestandar();
            
            JOptionPane.showMessageDialog(this, "¡Tiempos Calculados!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error."); 
            ComponenteFaltante compofalt = new ComponenteFaltante(mod);
            compofalt.setVisible(true);
            compofalt.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jtxtfiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtfiltroActionPerformed
    }//GEN-LAST:event_jtxtfiltroActionPerformed

    private void jtxtfiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtfiltroKeyTyped
         jtxtfiltro.addKeyListener(new KeyAdapter(){
            //@Override
            public void keyReleased(final KeyEvent ke){
                jtxtfiltro.setText (jtxtfiltro.getText().toUpperCase());
                String cadena= (jtxtfiltro.getText());
                jtxtfiltro.setText(cadena); 
                
                Filtro(1,3,8,9);
                               
            }
        });
        trs = new TableRowSorter(jTableA.getModel());
        jTableA.setRowSorter(trs);
        
         jtxtfiltro.addKeyListener(new KeyAdapter(){
           //@Override
           public void keyReleased(final KeyEvent ke){
               String cadena= (jtxtfiltro.getText());
               jtxtfiltro.setText(cadena);
               Filtro2(); 
           }
        });

        trs2 = new TableRowSorter(jTable2.getModel());
        jTable2.setRowSorter(trs2);
        
        jButton7.setVisible(true);
        jButton2.setVisible(false);
        
        jButton16.setVisible(true);
        jButton15.setVisible(false);
    }//GEN-LAST:event_jtxtfiltroKeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            int fila_seleccionada = jTableA.getSelectedRow();
           
            if(fila_seleccionada >= 0){
                String componente = (String) jTableA.getValueAt(fila_seleccionada, 8);
                String orden = (String) jTableA.getValueAt(fila_seleccionada, 3);
                
                //Ordenes
                PreparedStatement pst4 = cn.prepareStatement("INSERT INTO cambio (componente,troquel,numOperaciones) SELECT troqueles.componente,troqueles.troquel, troqueles.numOperaciones FROM troqueles WHERE troqueles.componente = '"+componente+"' AND NOT EXISTS ( SELECT cambio.componente FROM cambio WHERE cambio.componente = troqueles.componente)");
                pst4.executeUpdate();
                
                PreparedStatement pst = cn.prepareStatement("INSERT INTO atrasosproduccion (orden,fechaV,cantidad,componente,comentario) SELECT analisisatrasos.orden,analisisatrasos.fechaVencida, analisisatrasos.piezasEntregar,analisisatrasos.item_cliente,analisisatrasos.comentario  FROM analisisatrasos WHERE analisisatrasos.orden = '"+orden+"' AND NOT EXISTS ( SELECT atrasosproduccion.orden FROM atrasosproduccion WHERE atrasosproduccion.orden = analisisatrasos.orden)");
                pst.executeUpdate();
                
                PreparedStatement pst3 = cn.prepareStatement("UPDATE atrasosproduccion JOIN estructura ON estructura.componente = atrasosproduccion.componente SET atrasosproduccion.calibre = estructura.calibre");
                pst3.executeUpdate();
                
                PreparedStatement pst5 = cn.prepareStatement("UPDATE atrasosproduccion JOIN inventario ON inventario.calibre = atrasosproduccion.calibre SET atrasosproduccion.noCal = inventario.num_calibre");
                pst5.executeUpdate();
                
                PreparedStatement pst2 = cn.prepareStatement("INSERT INTO almacenmp (orden,componente) SELECT analisisatrasos.orden, analisisatrasos.item_cliente FROM analisisatrasos WHERE analisisatrasos.orden = '"+orden+"' AND NOT EXISTS ( SELECT almacenmp.orden FROM almacenmp WHERE almacenmp.orden = analisisatrasos.orden)");
                pst2.executeUpdate();

               String fechaVencimiento = (String) jTableA.getValueAt(fila_seleccionada, 2);
               String fecha = JOptionPane.showInputDialog("GENERAR ORDEN \n\n FECHA DE VENCIMIENTO ACTUAL: "+ fechaVencimiento+ "\n \n FECHA:" );
            
               this.produccionPDF(fecha);
               
               JOptionPane.showMessageDialog(this, "ORDEN GENERADA");
              
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            } 
        } catch (IOException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int fila_seleccionada2 = trs.convertRowIndexToModel(jTableA.getSelectedRow());
        if(fila_seleccionada2 >= 0){
            try {
                OrdenesSolicitadasGUI.this.dispose();
                ModificarOrdenesSol modificar = new ModificarOrdenesSol(this.analisisatrasos.get(fila_seleccionada2),mod);
                modificar.setVisible(true);
                modificar.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }  
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            //Tabla Tiempo Atrasos
            //Insertar orden, cantidad, componente y fechaVencimiento de analisis de atrasos
            PreparedStatement pst5 = cn.prepareStatement("INSERT INTO tiempoatrasos (tiempoatrasos.orden, tiempoatrasos.cantidad_reque, tiempoatrasos.componente, tiempoatrasos.numOperaciones, tiempoatrasos.troquel, tiempoatrasos.maquina, tiempoatrasos.inst_desm, tiempoatrasos.tiempo_troquelado, tiempoatrasos.tiempo_total, tiempoatrasos.fechaVencida, tiempoatrasos.componente_TR, tiempoatrasos.componente_T, tiempoatrasos.componente_A) SELECT analisisatrasos.orden, analisisatrasos.cantidad_reque, analisisatrasos.item_cliente, troqueles.numOperaciones, troqueles.troquel, troqueles.maquinaOp,sec_to_time(CAST((tiemposyrecursos.inst_desm*'60') AS SIGNED)),sec_to_time(CAST(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*'60') AS SIGNED)), sec_to_time(CAST(((tiemposyrecursos.inst_desm*'60') + ((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*'60')) AS SIGNED)), analisisatrasos.fechaVencida, tiemposyrecursos.componente, troqueles.componente, analisisatrasos.item_cliente  FROM analisisatrasos, troqueles, tiemposyrecursos WHERE troqueles.componente= analisisatrasos.item_cliente AND tiemposyrecursos.componente= analisisatrasos.item_cliente AND tiemposyrecursos.numeroOper= troqueles.numOperaciones AND NOT EXISTS ( SELECT tiempoatrasos.orden FROM tiempoatrasos WHERE tiempoatrasos.orden = analisisatrasos.orden)");
            pst5.executeUpdate();
            
            //Modificar
            //inst_desm
            PreparedStatement pst6 = cn.prepareStatement("UPDATE tiempoatrasos SET inst_desm=(SELECT sec_to_time(CAST((tiemposyrecursos.inst_desm*'60') AS SIGNED)) FROM tiemposyrecursos WHERE tiemposyrecursos.componente=tiempoatrasos.componente AND tiemposyrecursos.numeroOper= tiempoatrasos.numOperaciones)");
            pst6.executeUpdate();
            
            //tiempo_troquelado
            PreparedStatement pst7 = cn.prepareStatement("UPDATE tiempoatrasos SET tiempo_troquelado=(SELECT IF( tiemposyrecursos.pzsMin!='0', sec_to_time(CAST(((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*'60')AS SIGNED)),'00:00:00')FROM tiemposyrecursos, analisisatrasos WHERE tiemposyrecursos.componente=tiempoatrasos.componente AND tiemposyrecursos.numeroOper= tiempoatrasos.numOperaciones AND analisisatrasos.orden= tiempoatrasos.orden)");
            pst7.executeUpdate();
            
            //tiempo_total
            PreparedStatement pst8 = cn.prepareStatement("UPDATE tiempoatrasos SET tiempo_total=(SELECT IF(tiempoatrasos.tiempo_troquelado!='00:00:00', sec_to_time(CAST(((tiemposyrecursos.inst_desm*'60') + ((analisisatrasos.cantidad_reque/tiemposyrecursos.pzsMin)*'60'))AS SIGNED)), '00:00:00')FROM tiemposyrecursos,analisisatrasos WHERE tiemposyrecursos.componente=tiempoatrasos.componente AND tiemposyrecursos.numeroOper= tiempoatrasos.numOperaciones AND analisisatrasos.orden= tiempoatrasos.orden)");
            pst8.executeUpdate();
            
            //Modificar ordengenerada              
            PreparedStatement pst9 = cn.prepareStatement("UPDATE nuevasordenes SET ordengenerada = '0'");
            pst9.executeUpdate();
            
            OrdenesSolicitadasGUI.this.dispose();
            TiempoAtrasosGUI tiempoAtrasos = new TiempoAtrasosGUI(mod);
            tiempoAtrasos.setVisible(true);
            tiempoAtrasos.setLocationRelativeTo(null);
            
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try {
            ComponentesRepGUI ordenes = new ComponentesRepGUI(mod);
            ordenes.setVisible(true);
            ordenes.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       this.excel.WriteExcelAnalisis();
        JOptionPane.showMessageDialog(this, "Datos Exportados");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       this.excel.WriteExcelTiempoEstandar();
        JOptionPane.showMessageDialog(this, "Datos Exportados");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         try{
            int fila_seleccionada = jTableA.getSelectedRow();
            if(fila_seleccionada != -1){
                String ordenCancelada = (String) jTableA.getValueAt(fila_seleccionada, 3);
                
                //Modificar comentario de orden cancelada
                PreparedStatement pst9 = cn.prepareStatement("UPDATE analisisatrasos SET comentario='ORDEN CANCELADA' WHERE analisisatrasos.orden='"+ordenCancelada+"'");
                pst9.executeUpdate();
                
                //copiar datos de la fila seleccionada analisis de atrasos a entregados
                PreparedStatement pst = cn.prepareStatement("INSERT INTO entregados (entregados.fechaRecibida,entregados.fechaVencida, entregados.orden, entregados.cantidad_reque, entregados.cantidadEntregada, entregados.piezasEntregar, entregados.consignatario, entregados.item_cliente,entregados.comentario,entregados.fechaEmbarque,entregados.factura, entregados.embarques) SELECT analisisatrasos.fechaRecibida,analisisatrasos.fechaVencida, analisisatrasos.orden, analisisatrasos.cantidad_reque, analisisatrasos.CantidadEntregada, analisisatrasos.piezasEntregar, analisisatrasos.consignatario, analisisatrasos.item_cliente, analisisatrasos.comentario, analisisatrasos.fechaEmbarque, analisisatrasos.factura, analisisatrasos.embarques FROM analisisatrasos WHERE analisisatrasos.orden='"+ordenCancelada+"'");
                pst.executeUpdate();
                
                //Eliminar el dato tiempo Atrasos
                PreparedStatement pst4 = cn.prepareStatement("DELETE FROM tiempoatrasos WHERE orden='"+ordenCancelada+"'");
                pst4.executeUpdate();
                
                //Eliminar el dato tiempoestandar
                PreparedStatement pst2 = cn.prepareStatement("DELETE FROM tiempoestandar WHERE orden='"+ordenCancelada+"'");
                pst2.executeUpdate();
                
                //Eliminar el dato analisisatrasos
                PreparedStatement pst3 = cn.prepareStatement("DELETE FROM analisisatrasos WHERE orden='"+ordenCancelada+"'");
                pst3.executeUpdate();
                
                //Eliminar el dato atrasos Produccion
                PreparedStatement pst5 = cn.prepareStatement("DELETE FROM atrasosproduccion WHERE orden='"+ordenCancelada+"'");
                pst5.executeUpdate();
                
                //Eliminar el dato orden calidad
                PreparedStatement pst6 = cn.prepareStatement("DELETE FROM ordenescalidad WHERE orden='"+ordenCancelada+"'");
                pst6.executeUpdate();
                
                //Eliminar orden de compra
                PreparedStatement pst7 = cn.prepareStatement("DELETE FROM orden_compra WHERE orden='"+ordenCancelada+"'");
                pst7.executeUpdate();
                
                //Eliminar embarque
                PreparedStatement pst8 = cn.prepareStatement("DELETE FROM embarque WHERE orden='"+ordenCancelada+"'");
                pst8.executeUpdate();
                
                OrdenesSolicitadasGUI.this.dispose();
                JOptionPane.showMessageDialog(this, "ORDEN CANCELADA."); 
                OrdenesSolicitadasGUI analisis= new OrdenesSolicitadasGUI(mod);
                analisis.setVisible(true);
        
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error."); 
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        try {
            OrdenesSolicitadasGUI.this.dispose();
            AgregarOrdenesSol agregar = new AgregarOrdenesSol(mod);
            agregar.setVisible(true);
            agregar.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
       excel.WriteTallerMecanico();
       JOptionPane.showMessageDialog(this, "LISTA TALLER MECANICO DESCARGADA.");
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        try {
            pdf.Cambios();
            JOptionPane.showMessageDialog(this, "LISTA CAMBIOS DESCARGADA.");
        } catch (IOException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
       try {
            int fila_seleccionada = jTableA.getSelectedRow();       
            if(fila_seleccionada >= 0 ){
                String orden = (String) jTableA.getValueAt(fila_seleccionada, 3);
                PreparedStatement pstPrio = cn.prepareStatement("UPDATE analisisatrasos SET Prioridad = (SELECT IF(Prioridad= 1, 0, 1) WHERE analisisatrasos.orden= '"+orden+"') WHERE analisisatrasos.orden= '"+orden+"'");
                pstPrio.executeUpdate();
                
                OrdenesSolicitadasGUI.this.dispose();
                JOptionPane.showMessageDialog(this, "PRIORIDAD MODIFICADA");

                OrdenesSolicitadasGUI mp = new OrdenesSolicitadasGUI(mod);
                mp.setVisible(true);
                mp.setLocationRelativeTo(null);
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        } catch (SQLException ex) {
               Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        try {
            AtrasosGUI ordenes = new AtrasosGUI(mod);
            ordenes.setVisible(true);
            ordenes.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int fila_seleccionada = jTableA.getSelectedRow();
       
        if(fila_seleccionada >= 0){
            OrdenesSolicitadasGUI.this.dispose();
            VerParciales modificar = new VerParciales(this.analisisatrasos.get(fila_seleccionada), mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        int fila_seleccionada2 = trs.convertRowIndexToModel(jTableA.getSelectedRow());
        if(fila_seleccionada2 >= 0){
            OrdenesSolicitadasGUI.this.dispose();
            VerParciales modificar = new VerParciales(this.analisisatrasos.get(fila_seleccionada2),mod);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        }else{
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }  
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try{
            int fila_seleccionada = jTableA.getSelectedRow();
            if(fila_seleccionada != -1){
                String orden = (String) jTableA.getValueAt(fila_seleccionada, 3);
                int resp = JOptionPane.showConfirmDialog(null, "¿ESTAS SEGURO QUE DESEAS ELIMINAR?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if(resp == JOptionPane.YES_NO_OPTION)
                {
                    this.analisisAtrasos_servicio.eliminar(orden);
                    
                    OrdenesSolicitadasGUI.this.dispose();
                    JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS");

                    OrdenesSolicitadasGUI mp = new OrdenesSolicitadasGUI(mod);
                    mp.setVisible(true);
                    mp.setLocationRelativeTo(null);
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }//GEN-LAST:event_jButton17ActionPerformed

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
            java.util.logging.Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new OrdenesSolicitadasGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(OrdenesSolicitadasGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableA;
    private javax.swing.JTextField jtxtfiltro;
    // End of variables declaration//GEN-END:variables

}