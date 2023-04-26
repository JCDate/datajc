/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proveedores;

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
  
  File file = new File("Proveedores.xls");

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
    public void WriteExcelProveedores()
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
            workbook.createSheet( "Proveedores", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT nombre, colonia, parque, codigoPostal,ciudad, estado,pais,calibre FROM proveedores ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label nom  = new Label( 0 , 0, "NOMBRE" , cf );                  
                  Label col= new Label( 1 , 0, "COLONIA" , cf );                  
                  Label par= new Label( 2 , 0, "PARQUE" , cf );
                  Label cp= new Label( 3 , 0, "C.P." , cf ); 
                  Label ciu= new Label( 4 , 0, "CIUDAD" , cf ); 
                  Label est= new Label( 5 , 0, "ESTADO" , cf ); 
                  Label pa= new Label( 6 , 0, "PAIS" , cf );
                  Label cal= new Label( 7 , 0, "CALIBRE" , cf );
                                    
                  Label nombre  = new Label( 0 , row, res.getString( "nombre" ) , cf );                  
                  Label colonia= new Label( 1 , row, res.getString( "colonia" ) , cf ); 
                  Label parque= new Label( 2 , row, res.getString( "parque" ) , cf );
                  Label CP= new Label( 3 , row, res.getString( "codigoPostal" ) , cf );
                  Label ciudad= new Label( 4 , row, res.getString( "ciudad" ) , cf );
                  Label estado= new Label( 5 , row, res.getString( "estado" ) , cf );
                  Label pais= new Label( 6 , row, res.getString( "pais" ) , cf );
                  Label calibre= new Label( 7 , row, res.getString( "calibre" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( nom );
                     excelSheet.addCell( col );
                     excelSheet.addCell( par);
                     excelSheet.addCell( cp );
                     excelSheet.addCell( ciu );
                     excelSheet.addCell( est );
                     excelSheet.addCell( pa );
                     excelSheet.addCell( cal );
                     
                     excelSheet.addCell( nombre );
                     excelSheet.addCell( colonia );
                     excelSheet.addCell( parque );
                     excelSheet.addCell( CP );
                     excelSheet.addCell( ciudad );
                     excelSheet.addCell( estado );
                     excelSheet.addCell( pais );
                     excelSheet.addCell( calibre );

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
