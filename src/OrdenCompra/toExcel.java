/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenCompra;

import Servicios.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author JC
 */
public class toExcel {
     /* DATOS PARA LA CONEXION */
  
  File file = new File("OrdenCompra.xls");

  
  Conexion cc = new Conexion();
  Connection cn;
  

    /**
 * Constructor de clase
 */
    public toExcel()
    {
     
       try{
         
        this.cn = Conexion.obtener();
         //obtenemos la conexión
        
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
    }

    /**
 * Metodo para obtener los registros de la base de datos y crear el archivo excel
 */
    public void WriteExcelOrdenCompra()
    {
        int row=1;
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf);    

        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        try {
            workbook = Workbook.createWorkbook( file, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "OrdenCompra", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT fechaV, orden, componente, CR, cantidad, calibre,consumo_kg,no_oper,no_golpes FROM orden_compra ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label fecV  = new Label( 0 , 0, "FECHA V." , cf );                  
                  Label ord= new Label( 1 , 0, "ORDEN" , cf );                  
                  Label comp= new Label( 2 , 0, "COMPONENTE" , cf );
                  Label cr= new Label( 3 , 0, "CR" , cf ); 
                  Label cant= new Label( 4 , 0, "CANTIDAD" , cf ); 
                  Label cal= new Label( 5 , 0, "CALIBRE" , cf ); 
                  Label cons= new Label( 6 , 0, "CONSUMO KG" , cf );
                  Label noO= new Label( 7 , 0, "NO. OPERACION" , cf );
                  Label noG= new Label( 8 , 0, "NO.GOLPES" , cf );
                                    
                  Label fechaV  = new Label( 0 , row, res.getString( "fechaV" ) , cf );                  
                  Label orden= new Label( 1 , row, res.getString( "orden" ) , cf ); 
                  Label componente= new Label( 2 , row, res.getString( "componente" ) , cf );
                  Label CR= new Label( 3 , row, res.getString( "CR" ) , cf );
                  Label cantidad= new Label( 4 , row, res.getString( "cantidad" ) , cf );
                  Label calibre= new Label( 5 , row, res.getString( "calibre" ) , cf );
                  Label consumoKG= new Label( 6 , row, res.getString( "consumo_kg" ) , cf );
                  Label noOper= new Label( 7 , row, res.getString( "no_oper" ) , cf );
                  Label noGolpes= new Label( 8 , row, res.getString( "no_golpes" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( fecV );
                     excelSheet.addCell( ord );
                     excelSheet.addCell( comp);
                     excelSheet.addCell( cr );
                     excelSheet.addCell( cant );
                     excelSheet.addCell( cal );
                     excelSheet.addCell( cons );
                     excelSheet.addCell( noO );
                     excelSheet.addCell( noG );
                     
                     excelSheet.addCell( fechaV );
                     excelSheet.addCell( orden );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( CR );
                     excelSheet.addCell( cantidad );
                     excelSheet.addCell( calibre );
                     excelSheet.addCell( consumoKG );
                     excelSheet.addCell( noOper );
                     excelSheet.addCell( noGolpes );

                 } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                 } 
              }
             res.close();         
         }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }

        //Escribe el archivo excel en disco
        try {
            workbook.write();
            workbook.close();
            System.out.println(  "Escribiendo en disco....Listo"  );         
        } catch (IOException ex) {
            System.err.println(  ex.getMessage() );
        }
        catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }

        System.out.println(  "Proceso completado...."  );
    }
    
}
