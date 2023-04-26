/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TiemposYRecursos;

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
import jxl.write.Number;
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
  
  //Tiempo Y Recursos
  File file = new File("TiemposYRecursos.xls");
  
  
  
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
   
    public void WriteExcelTiemposYRecursos()
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
            workbook.createSheet( "Tiempos Y Recursos", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT componente,numeroOper,inst,pzsMin,desm,inst_desm FROM tiemposyrecursos ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label compo  = new Label( 0 , 0, "COMPONENTE" , cf );                  
                  Label nOp= new Label( 1 , 0, "NUM. OPERACIONES" , cf );                  
                  Label ins= new Label( 2 , 0, "INST", cf );
                  Label pzsM= new Label( 3 , 0, "PZS MIN" , cf );   
                  Label des= new Label( 4 , 0, "DESM" , cf ); 
                  Label inDes= new Label( 5 , 0, "INST+DESM", cf ); 
 
                  
                  Label componente  = new Label( 0 , row, res.getString( "componente" ) , cf );                  
                  Label numeroOper= new Label( 1 , row, res.getString( "numeroOper" ) , cf );                  
                  Label inst= new Label( 2 , row, res.getString( "inst" ) , cf );
                  Number pzsMin= new Number( 3 , row, res.getFloat( "pzsMin" ) , cf );   
                  Number desm= new Number( 4 , row, res.getLong( "desm" ) , cf ); 
                  Number inst_desm= new Number( 5 , row, res.getLong( "inst_desm" ) , cf ); 
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( compo );
                     excelSheet.addCell( nOp );
                     excelSheet.addCell( ins );
                     excelSheet.addCell( pzsM );
                     excelSheet.addCell( des );
                     excelSheet.addCell( inDes );
                     
                     excelSheet.addCell( componente );
                     excelSheet.addCell( numeroOper );
                     excelSheet.addCell( inst );
                     excelSheet.addCell( pzsMin );
                     excelSheet.addCell( desm );
                     excelSheet.addCell( inst_desm );                     
                     
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
