/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenesSolicitadas;

import Modelos.CambiosM;
import Servicios.Cambios_servicio;
import Servicios.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author JC
 */
public class toPDF {
    Conexion cc = new Conexion();
    Connection cn;
    private final Cambios_servicio cambios_servicio = new Cambios_servicio();
    private List<CambiosM> cambios;
    
    public void Cambios() throws IOException, SQLException{
    try (PDDocument document = new PDDocument()) {

        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page);
        
        PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.OVERWRITE, false);
        
        PDPage page2 = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page2);
        
        PDPageContentStream contentStream2 = new PDPageContentStream(document, page2, AppendMode.OVERWRITE, false);
        
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
        contentStream.newLineAtOffset(300, page.getMediaBox().getHeight() - 30);
        contentStream.showText("C A M B I O S");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 24);
        contentStream2.newLineAtOffset(300, page2.getMediaBox().getHeight() - 30);
        contentStream2.showText("C A M B I O S");
        contentStream2.endText();


        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(700, page.getMediaBox().getHeight() - 20);
        contentStream.showText("F-PC 01 REV 01");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA, 10);
        contentStream2.newLineAtOffset(700, page2.getMediaBox().getHeight() - 20);
        contentStream2.showText("F-PC 01 REV 01");
        contentStream2.endText();

        //VERTICAL
        
        //LIENA 1
        //Hoja 1
        contentStream.moveTo(40,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(40,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(40,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(40,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();
        
        //HOJA 1
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(41, page.getMediaBox().getHeight() - 50);
        contentStream.showText("PT");
        contentStream.endText();

        contentStream.moveTo(60,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(60,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(41, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("PT");
        contentStream2.endText();

        contentStream2.moveTo(60,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(60,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();
        
        //HOJA 1
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(65, page.getMediaBox().getHeight() - 50);
        contentStream.showText("ORDEN");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(65, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("ORDEN");
        contentStream2.endText();

        //LINEA 2
        //Hoja 1
        contentStream.moveTo(130,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(130,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(130,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(130,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();


        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(135, page.getMediaBox().getHeight() - 50);
        contentStream.showText("FECHA V.");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(135, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("FECHA V.");
        contentStream2.endText();

        //LINEA 3
        //Hoja 1
        contentStream.moveTo(200,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(200,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(200,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(200,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();


        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(215, page.getMediaBox().getHeight() - 50);
        contentStream.showText("COMP.");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(215, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("COMP.");
        contentStream2.endText();

         //LINEA 4
         //Hoja 1
        contentStream.moveTo(265,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(265,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(265,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(265,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();

//
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(275, page.getMediaBox().getHeight() - 50);
        contentStream.showText("C/R");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(275, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("C/R");
        contentStream2.endText();

        //LINEA 5
        //Hoja 1
        contentStream.moveTo(340,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(340,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(340,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(340,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(350, page.getMediaBox().getHeight() - 50);
        contentStream.showText("CANTIDAD");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(350, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("CANTIDAD");
        contentStream2.endText();
        
        //LINEA 6
        //Hoja 1
        contentStream.moveTo(420,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(420,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(420,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(420,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();
        
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(570, page.getMediaBox().getHeight() - 50);
        contentStream.showText("COMENTARIO");
        contentStream.endText();
        
        //HOJA 2
        contentStream2.beginText();
        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream2.newLineAtOffset(570, page2.getMediaBox().getHeight() - 50);
        contentStream2.showText("COMENTARIO");
        contentStream2.endText();
        
         //LINEA 6
        //Hoja 1
        contentStream.moveTo(820,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(820,  page.getMediaBox().getHeight() - 580);
        contentStream.stroke();
        
        //Hoja 2
        contentStream2.moveTo(820,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(820,  page2.getMediaBox().getHeight() - 580);
        contentStream2.stroke();
        
        
        //HORIZONTAL
        //LINEA 1
        contentStream.moveTo(40,  page.getMediaBox().getHeight() - 40);
        contentStream.lineTo(820,  page.getMediaBox().getHeight() - 40);
        contentStream.stroke();
        
        //HOJA 2
        contentStream2.moveTo(40,  page2.getMediaBox().getHeight() - 40);
        contentStream2.lineTo(820,  page2.getMediaBox().getHeight() - 40);
        contentStream2.stroke();

        //LINEA 2
        contentStream.moveTo(40,  page.getMediaBox().getHeight() - 55);
        contentStream.lineTo(820,  page.getMediaBox().getHeight() - 55);
        contentStream.stroke();
        
        //HOJA 2
        contentStream2.moveTo(40,  page2.getMediaBox().getHeight() - 55);
        contentStream2.lineTo(820,  page2.getMediaBox().getHeight() - 55);
        contentStream2.stroke();

        //Hoja 1
        for(int y=55; y<=580; y= y+15){
                contentStream.moveTo(40,  page.getMediaBox().getHeight() - y);
                contentStream.lineTo(820,  page.getMediaBox().getHeight() - y);
                contentStream.stroke();
        } 
        //Hoja 2
        for(int y=55; y<=580; y= y+15){
                contentStream2.moveTo(40,  page2.getMediaBox().getHeight() - y);
                contentStream2.lineTo(820,  page2.getMediaBox().getHeight() - y);
                contentStream2.stroke();
        } 

        this.cambios = this.cambios_servicio.recuperarTodas(Conexion.obtener());
        //Se agregan los numeros para llevar un conteo de cuantas ordenes son
        for(int x=1, nuy=67; x<200 && nuy<1614; x++, nuy=nuy+15)
        {
            String num= String.valueOf(x);
            if(nuy<580){
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(40, page.getMediaBox().getHeight() - nuy);
                contentStream.showText(num);
                contentStream.endText();
            }
         }
       
        for(int x=36, nuy=67; x<200 && nuy<1614; x++, nuy=nuy+15)
        {
            String num= String.valueOf(x);
            if(nuy<580){
                contentStream2.beginText();
                contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream2.newLineAtOffset(40, page2.getMediaBox().getHeight() - nuy);
                contentStream2.showText(num);
                contentStream2.endText();
            }
         }

        //Aqui llena datos
        //Hoja 1
        for(int i = 0, my=67; i < this.cambios.size() && my<=1614; i++, my=my+15)
        {
            if(my<580)
            {
               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 12);
               contentStream.newLineAtOffset(62, page.getMediaBox().getHeight() - my);
               contentStream.showText(this.cambios.get(i).getOrden());
               contentStream.endText();

               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 12);
               contentStream.newLineAtOffset(132, page.getMediaBox().getHeight() - my);
               contentStream.showText(this.cambios.get(i).getFechaV());
               contentStream.endText();

               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 12);
               contentStream.newLineAtOffset(202, page.getMediaBox().getHeight() - my);
               contentStream.showText(this.cambios.get(i).getComponente());
               contentStream.endText();
               
               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 12);
               contentStream.newLineAtOffset(267, page.getMediaBox().getHeight() - my);
               contentStream.showText(this.cambios.get(i).getCr());
               contentStream.endText();


               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 12);
               contentStream.newLineAtOffset(342, page.getMediaBox().getHeight() - my);
               contentStream.showText(String.valueOf(this.cambios.get(i).getCantidad()));
               contentStream.endText();
               
               contentStream.beginText();
               contentStream.setFont(PDType1Font.HELVETICA, 8);
               contentStream.newLineAtOffset(422, page.getMediaBox().getHeight() - my);
               contentStream.showText(this.cambios.get(i).getComentario());
               contentStream.endText();
            }
          
        }
        //Hoja 2
        for(int i = 35, my=67; i < this.cambios.size() && my<=1614; i++, my=my+15)
        {
            if(my<580)
            {
               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 12);
               contentStream2.newLineAtOffset(62, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(this.cambios.get(i).getOrden());
               contentStream2.endText();

               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 12);
               contentStream2.newLineAtOffset(132, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(this.cambios.get(i).getFechaV());
               contentStream2.endText();

               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 12);
               contentStream2.newLineAtOffset(202, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(this.cambios.get(i).getComponente());
               contentStream2.endText();
               
               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 12);
               contentStream2.newLineAtOffset(267, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(this.cambios.get(i).getCr());
               contentStream2.endText();


               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 12);
               contentStream2.newLineAtOffset(342, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(String.valueOf(this.cambios.get(i).getCantidad()));
               contentStream2.endText();
               
               contentStream2.beginText();
               contentStream2.setFont(PDType1Font.HELVETICA, 8);
               contentStream2.newLineAtOffset(422, page2.getMediaBox().getHeight() - my);
               contentStream2.showText(this.cambios.get(i).getComentario());
               contentStream2.endText();
            }
        }
        
        contentStream.close();
        contentStream2.close();

        document.save("CAMBIOS.pdf");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(toPDF.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
