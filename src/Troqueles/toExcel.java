/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Troqueles;

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
  
  File file = new File("Troqueles.xls");

  
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
    public void WriteExcelTroquel()
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
            workbook.createSheet( "Troqueles", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT componente, numOperaciones, caja, troquel,opciones, maquinaOp FROM troqueles ORDER BY componente ASC";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label comp  = new Label( 0 , 0, "COMPONENTE" , cf );                  
                  Label numop= new Label( 1 , 0, "NUM. OP" , cf );  
                  Label caj= new Label( 2 , 0, "CAJA" , cf ); 
                  Label tro= new Label( 3 , 0, "TROQUEL" , cf );
                  Label op= new Label( 4 , 0, "OPCIONES" , cf );
                  Label mop= new Label( 5 , 0, "MAQUINA OP." , cf );
                                    
                  Label componente  = new Label( 0 , row, res.getString( "componente" ) , cf );                  
                  Label numeroOp= new Label( 1 , row, res.getString( "numOperaciones" ) , cf );  
                  Label caja= new Label( 2 , row, res.getString( "caja" ) , cf ); 
                  Label troquel= new Label( 3 , row, res.getString( "troquel" ) , cf ); 
                  Label opciones= new Label( 4 , row, res.getString( "opciones" ) , cf ); 
                  Label maquinaOP= new Label( 5 , row, res.getString( "maquinaOp" ) , cf ); 

                  row ++;                  
                 try {
                     excelSheet.addCell( comp );
                     excelSheet.addCell( numop );
                     excelSheet.addCell( caj );
                     excelSheet.addCell( tro );
                     excelSheet.addCell( op );
                     excelSheet.addCell( mop );
                     
                     excelSheet.addCell( componente );
                     excelSheet.addCell( numeroOp );
                     excelSheet.addCell( caja );
                     excelSheet.addCell( troquel );
                     excelSheet.addCell( opciones );
                     excelSheet.addCell( maquinaOP );

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
