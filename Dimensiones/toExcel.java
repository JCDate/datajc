/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dimensiones;

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
  
  File file = new File("Dimensiones.xls");

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
    public void WriteExcelDimensiones()
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
            workbook.createSheet( "Dimensiones", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT componente, cr, calibre, diaEx, diaInt, altura FROM dim ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label comp  = new Label( 0 , 0, "COMPONENTE" , cf );                  
                  Label cr= new Label( 1 , 0, "CR" , cf );                  
                  Label cal= new Label( 2 , 0, "CALIBRE" , cf );
                  Label diaEx= new Label( 3 , 0, "DIAM. EXT." , cf ); 
                  Label diaIn= new Label( 4 , 0, "DIAM. INT." , cf ); 
                  Label al= new Label( 5 , 0, "ALTURA" , cf ); 
                                    
                  Label componente  = new Label( 0 , row, res.getString( "componente" ) , cf );                  
                  Label CR= new Label( 1 , row, res.getString( "cr" ) , cf ); 
                  Label calibre= new Label( 2 , row, res.getString( "calibre" ) , cf );
                  Label diaExt= new Label( 3 , row, res.getString( "diaEx" ) , cf );
                  Label diaInt= new Label( 4 , row, res.getString( "diaInt" ) , cf );
                  Label altura= new Label( 5 , row, res.getString( "altura" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( comp );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( cal);
                     excelSheet.addCell( diaEx );
                     excelSheet.addCell( diaIn );
                     excelSheet.addCell( al );
                     
                     excelSheet.addCell( componente );
                     excelSheet.addCell( CR );
                     excelSheet.addCell( calibre );
                     excelSheet.addCell( diaExt );
                     excelSheet.addCell( diaInt );
                     excelSheet.addCell( altura );

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
