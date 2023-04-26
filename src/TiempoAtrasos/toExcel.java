/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TiempoAtrasos;

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
 
  //Tiempo Atrasos
  File file = new File("TiempoAtrasos.xls");

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
    
    public void WriteExcelTiempoAtrasos()
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
            workbook.createSheet( "Tiempo Atrasos", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT orden ,cantidad_reque, componente,numOperaciones,troquel,maquina , inst_desm, tiempo_troquelado,tiempo_total,fechaVencida FROM tiempoatrasos ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label or  = new Label( 0 , 0, "ORDEN" , cf );                  
                  Label cantR= new Label( 1 , 0, "CANTIDAD REQUERIDA" , cf );                  
                  Label compo= new Label( 2 , 0, "COMPONENTE", cf );
                  Label nOP= new Label( 3 , 0, "Num. OP.", cf );
                  Label tro= new Label( 4 , 0, "TROQUEL", cf );
                  Label maq= new Label( 5 , 0, "TMAQUINA", cf );
                  Label instDes= new Label( 6 , 0, "INST-DESM" , cf );   
                  Label tiempoT= new Label( 7 , 0, "TIEMPO TROQUELADO" , cf ); 
                  Label TT= new Label( 8 , 0, "TIEMPO TOTAL", cf ); 
                  Label fechaV= new Label( 9 , 0, "FECHA VENCIDA", cf ); 
                  
                  Label orden  = new Label( 0 , row, res.getString( "orden" ) , cf );                  
                  Label cantidad_reque= new Label( 1 , row, res.getString( "cantidad_reque" ) , cf );                  
                  Label componente= new Label( 2 , row, res.getString( "componente" ) , cf );
                  Label numOperaciones= new Label( 3 , row, res.getString( "numOperaciones" ) , cf );
                  Label troquel= new Label( 4 , row, res.getString( "troquel" ) , cf );
                  Label maquina= new Label( 5 , row, res.getString( "maquina" ) , cf );
                  Label inst_desm= new Label( 6 , row, res.getString( "inst_desm" ) , cf );
                  Label tiempo_troquelado= new Label( 7 , row, res.getString( "tiempo_troquelado" ) , cf );
                  Label tiempo_total= new Label( 8 , row, res.getString( "tiempo_total" ) , cf );
                  Label fechaVencida= new Label( 9 , row, res.getString( "fechaVencida" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( or );
                     excelSheet.addCell( cantR );
                     excelSheet.addCell( compo );
                     excelSheet.addCell( nOP );
                     excelSheet.addCell( tro );
                     excelSheet.addCell( maq );
                     excelSheet.addCell( instDes );
                     excelSheet.addCell( tiempoT );
                     excelSheet.addCell( TT );
                     excelSheet.addCell( fechaV );
                     
                     excelSheet.addCell( orden );
                     excelSheet.addCell( cantidad_reque );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( numOperaciones );
                     excelSheet.addCell( troquel );
                     excelSheet.addCell( maquina );  
                     excelSheet.addCell( inst_desm ); 
                     excelSheet.addCell( tiempo_troquelado ); 
                     excelSheet.addCell( tiempo_total ); 
                     excelSheet.addCell( fechaVencida ); 
                     
                     
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
