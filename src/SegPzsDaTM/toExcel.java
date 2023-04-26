/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SegPzsDaTM;

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
  
  //Direccion para guardar el archivo, con su nombre
  File file = new File("SEGUIMIENTO PIEZAS DAÑADAS DEL TALLER MECANICO.xls");
 
  
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
    public void WriteExcel()
    {
        int row=6;
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf);  
        
        WritableFont wf2 = new WritableFont( WritableFont.ARIAL, 18, WritableFont.BOLD );
        WritableCellFormat cf2 = new WritableCellFormat(wf2); 
        
        WritableFont wf3 = new WritableFont( WritableFont.ARIAL, 14, WritableFont.BOLD );
        WritableCellFormat cf3 = new WritableCellFormat(wf3);
        
        WritableFont wf4 = new WritableFont( WritableFont.ARIAL, 12, WritableFont.BOLD );
        WritableCellFormat cf4 = new WritableCellFormat(wf4);  

        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        try {
            workbook = Workbook.createWorkbook( file, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "Seg. pzs. dañadas", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT cotizaciontmpd.cotizacion, cotizaciontmpd.componente, cr, troquel, pzaDañada, solicitudtmpd.fechaSol, solicitudtmpd.envioCot, solicitudtmpd.precioC, solicitudtmpd.enviadoSKF, solicitudtmpd.aprobadoSKF, solicitudtmpd.solicitudM, solicitudtmpd.costoM, solicitudtmpd.fechaMaq, solicitudtmpd.temple, solicitudtmpd.ajuste, solicitudtmpd.produccion, solicitudtmpd.pzFacturadaSKF FROM cotizaciontmpd, solicitudtmpd WHERE cotizaciontmpd.cotizacion= solicitudtmpd.cotizacion AND cotizaciontmpd.componente = solicitudtmpd.componente";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label letrero  = new Label( 4 , 3, "SEGUIMIENTO PIEZAS DAÑADAS DEL TALLER MECANICO" , cf2 );
                  Label COTIZACIÓN  = new Label( 0 , 5, "COTIZACIÓN" , cf3 );                  
                  Label CR= new Label( 1 , 5, "CR" , cf3 );
                  Label COMP= new Label( 2 , 5, "COMPONENTE" , cf3 );
                  Label TROQUELES= new Label( 3 , 5, "TROQUELES" , cf3 );
                  Label PZSDAÑADAS= new Label( 4 , 5, "PZS. DAÑADAS" , cf3 );
                  Label FECHASOLICITUD= new Label( 5 , 5, "FECHA DE SOLICITUD" , cf3 );
                  Label ENVCOT= new Label( 6 , 5, "ENVIO DE COTIZACIÓN (J.C)" , cf3 );
                  Label PRECOT= new Label( 7 , 5, "PRECIO DE COTIZACIÓN" , cf3 );
                  Label ENVSKF= new Label( 8 , 5, "ENVIADO A SKF" , cf3 );
                  Label APROSKF= new Label( 9 , 5, "APROBADO POR SKF" , cf3 );
                  Label SOLM= new Label( 10 , 5, "SOLICITUD DE MATERIAL" , cf3 );
                  Label COSM= new Label( 11 , 5, "COSTO DE MATERIAL" , cf3 );
                  Label FEMA= new Label( 12 , 5, "FECHA DE MAQUINADO" , cf3 );
                  Label TEMPLE= new Label( 13 , 5, "TEMPLE " , cf3 );
                  Label AJ= new Label( 14 , 5, "AJUSTE" , cf3 );
                  Label PRODUCCIÓN = new Label( 15 , 5, "PRODUCCIÓN " , cf3 );
                  Label PZFSKF= new Label( 16 , 5, "PIEZA FACTURADA A SKF" , cf3);
                  
                  Label cotizacion  = new Label( 0 , row, res.getString( "cotizacion" ) , cf4 );                  
                  Label componente  = new Label( 1 , row, res.getString( "componente" ) , cf );                  
                  Label cr= new Label( 2 , row, res.getString( "cr" ) , cf );  
                  Label troquel= new Label( 3 , row, res.getString( "troquel" ) , cf );  
                  Label pzaDañada= new Label( 4 , row, res.getString( "pzaDañada" ) , cf );  
                  Label fechaSol= new Label( 5 , row, res.getString( "fechaSol" ) , cf );  
                  Label envioCot= new Label( 6 , row, res.getString( "envioCot" ) , cf );  
                  Label precioC= new Label( 7 , row, res.getString( "precioC" ) , cf );  
                  Label enviadoSKF= new Label( 8 , row, res.getString( "enviadoSKF" ) , cf );  
                  Label aprobadoSKF= new Label( 9 , row, res.getString( "aprobadoSKF" ) , cf );  
                  Label solicitudM= new Label( 10 , row, res.getString( "solicitudM" ) , cf );  
                  Label costoM= new Label( 11 , row, res.getString( "costoM" ) , cf );  
                  Label fechaMaq= new Label( 12 , row, res.getString( "fechaMaq" ) , cf );  
                  Label temple= new Label( 13 , row, res.getString( "temple" ) , cf );  
                  Label ajuste= new Label( 14 , row, res.getString( "ajuste" ) , cf );  
                  Label produccion= new Label( 15 , row, res.getString( "produccion" ) , cf );  
                  Label pzFacturadaSKF= new Label( 16 , row, res.getString( "pzFacturadaSKF" ) , cf );  
                  

                  row ++;                  
                 try {
                     excelSheet.addCell( letrero );
                     excelSheet.addCell( COTIZACIÓN );
                     excelSheet.addCell( CR );
                     excelSheet.addCell( COMP );
                     excelSheet.addCell( TROQUELES );
                     excelSheet.addCell( PZSDAÑADAS );
                     excelSheet.addCell( FECHASOLICITUD );
                     excelSheet.addCell( ENVCOT );
                     excelSheet.addCell( PRECOT );
                     excelSheet.addCell( ENVSKF );
                     excelSheet.addCell( APROSKF );
                     excelSheet.addCell( SOLM );
                     excelSheet.addCell( COSM );
                     excelSheet.addCell( FEMA );
                     excelSheet.addCell( TEMPLE );
                     excelSheet.addCell( AJ );
                     excelSheet.addCell( PRODUCCIÓN );
                     excelSheet.addCell( PZFSKF );
                     
                     excelSheet.addCell( cotizacion );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( troquel );
                     excelSheet.addCell( pzaDañada );
                     excelSheet.addCell( fechaSol );
                     excelSheet.addCell( envioCot );
                     excelSheet.addCell( precioC );
                     excelSheet.addCell( enviadoSKF );
                     excelSheet.addCell( aprobadoSKF );
                     excelSheet.addCell( solicitudM );
                     excelSheet.addCell( costoM );
                     excelSheet.addCell( fechaMaq );
                     excelSheet.addCell( temple );
                     excelSheet.addCell( ajuste );
                     excelSheet.addCell( produccion );
                     excelSheet.addCell( pzFacturadaSKF );
                     

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
