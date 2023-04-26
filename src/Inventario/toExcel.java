/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;


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
  
  File file = new File("Inventario.xls");
  
  
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
    public void WriteExcelInventario()
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
            workbook.createSheet( "Inventario", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT calibre, descripcion, num_calibre,unidad,milesimas,existencia,kgs,tolerancia,peso_hoja,observaciones FROM inventario ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label cal  = new Label( 0 , 0, "CALIBRE" , cf );                  
                  Label desc= new Label( 1 , 0, "DESCRIPCION" , cf );                  
                  Label nuCal= new Label( 2 , 0, "NUM. CALIBRE" , cf );
                  Label uni= new Label( 3 , 0, "UNIDAD" , cf ); 
                  Label mil= new Label( 4 , 0, "MILESIMAS" , cf ); 
                  Label exist= new Label( 5 , 0, "EXISTENCIA" , cf );
                  Label kgs= new Label( 6 , 0, "KGS" , cf ); 
                  Label tol= new Label( 7 , 0, "TOLERANCIA" , cf ); 
                  Label pesoH= new Label( 8 , 0, "PESO/HOJA" , cf ); 
                  Label obs= new Label( 9 , 0, "OBSERVACIONES" , cf ); 
                                    
                  Label calibre = new Label( 0 , row, res.getString( "calibre" ) , cf );                  
                  Label descripcion= new Label( 1 , row, res.getString( "descripcion" ) , cf ); 
                  Label numCalibre= new Label( 2 , row, res.getString( "num_calibre" ) , cf );
                  Label unidad= new Label( 3 , row, res.getString( "unidad" ) , cf );
                  Label milesima= new Label( 4 , row, res.getString( "milesimas" ) , cf );
                  Label existencia= new Label( 5 , row, res.getString( "existencia" ) , cf );
                  Label Kgs= new Label( 6 , row, res.getString( "kgs" ) , cf );
                  Label tolerancia= new Label( 7 , row, res.getString( "tolerancia" ) , cf );
                  Label pesoHoja= new Label( 8 , row, res.getString( "peso_hoja" ) , cf );
                  Label observaciones= new Label( 9 , row, res.getString( "observaciones" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( cal );
                     excelSheet.addCell( desc );
                     excelSheet.addCell( nuCal);
                     excelSheet.addCell( uni );
                     excelSheet.addCell( mil );
                     excelSheet.addCell( exist );
                     excelSheet.addCell( kgs );
                     excelSheet.addCell( tol );
                     excelSheet.addCell( pesoH );
                     excelSheet.addCell( obs );
                     

                     excelSheet.addCell( calibre );
                     excelSheet.addCell( descripcion );
                     excelSheet.addCell( numCalibre );
                     excelSheet.addCell( unidad );
                     excelSheet.addCell( milesima );
                     excelSheet.addCell( existencia );
                     excelSheet.addCell( Kgs );
                     excelSheet.addCell( tolerancia );
                     excelSheet.addCell( pesoHoja );
                     excelSheet.addCell( observaciones );

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
