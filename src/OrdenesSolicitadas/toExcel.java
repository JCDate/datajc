/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenesSolicitadas;

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
    //Ordenes solicitadas
    File file = new File("ORDENES SOLICITADAS.xls");

    //Tiempo Estandar
    File file1 = new File("TiempoEstandar.xls");

    //TALLER MECANICO
    File file2 = new File("TALLER MECANICO.xls");

    //Analisis de Atrasos
    File file3 = new File("Analisis de Atrasos.xls");

    Conexion cc = new Conexion();
    Connection cn;

    public toExcel()
    {
       try{
        this.cn = Conexion.obtener();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
    }

    /**
 * Metodo para obtener los registros de la base de datos y crear el archivo excel
 */
    public void WriteExcelAnalisis()
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
            workbook = Workbook.createWorkbook( file, wbSettings);
            //hoja con nombre de la tabla
            workbook.createSheet( "OrdenesSolicitadas", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo" );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage());
        }

        //Consulta SQL 
        String sql = "SELECT fechaRecibida , fechaVencida , orden , cantidad_reque, cantidadEntregada,piezasEntregar,consignatario,item_cliente, cr,comentario,fechaEmbarque,factura,embarques FROM analisisatrasos, crs WHERE item_cliente= componente ORDER BY prioridad DESC, CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida , '/', 1)) ASC";
         try{
            PreparedStatement pstm = cn.prepareStatement( sql );
            ResultSet res = pstm.executeQuery();
            System.out.println(  "obteniendo registros.....Listo" );

            while(res.next())
            {
                Label fr  = new Label( 0 , 0, "FECHA RECIBIDA" , cf );
                Label fv= new Label( 1 , 0, "FECHA VENCIDA" , cf );
                Label or= new Label( 2 , 0, "ORDEN", cf );
                Label canR= new Label( 3 , 0, "CANTIDAD REQUERIDA" , cf );
                Label cantE= new Label( 4 , 0, "CANTIDAD ENTREGADA" , cf );
                Label pE= new Label( 5 , 0, "PIEZAS ENTEGAR", cf );
                Label cons= new Label( 6 , 0, "CONSIGNATARIO" , cf );
                Label componente= new Label( 7 , 0, "COMPONENTE" , cf );
                Label cr= new Label( 8 , 0, "C/R" , cf ); 
                Label coment= new Label( 9 , 0, "COMENTARIO" , cf ); 
                Label fE= new Label( 10 , 0, "FECHA DE EMBARQUE" , cf ); 
                Label fac= new Label( 11 , 0, "FACTURA" , cf ); 
                Label emb= new Label( 12 , 0, "EMBARQUES" , cf ); 

                Label fechaRecibida  = new Label( 0 , row, res.getString( "fechaRecibida" ) , cf );                  
                Label fechaVencida= new Label( 1 , row, res.getString( "fechaVencida" ) , cf );                  
                Label orden= new Label( 2 , row, res.getString( "orden" ) , cf );
                Number cantidad_reque= new Number( 3 , row, res.getLong( "cantidad_reque" ) , cf );   
                Number cantidadEntregada= new Number( 4 , row, res.getLong( "cantidadEntregada" ) , cf ); 
                Number piezasEntregar= new Number( 5 , row, res.getLong( "piezasEntregar" ) , cf ); 
                Label consignatario= new Label( 6 , row, res.getString( "consignatario" ) , cf ); 
                Label item_cliente= new Label( 7 , row, res.getString( "item_cliente" ) , cf ); 
                Label CR= new Label( 8 , row, res.getString( "cr" ) , cf ); 
                Label comentario= new Label( 9 , row, res.getString( "comentario" ) , cf ); 
                Label fechaEmbarque= new Label( 10 , row, res.getString( "fechaEmbarque" ) , cf ); 
                Label factura= new Label( 11 , row, res.getString( "factura" ) , cf ); 
                Label embarques= new Label( 12 , row, res.getString( "embarques" ) , cf ); 

                row ++;
                try {
                    excelSheet.addCell( fr );
                    excelSheet.addCell( fv );
                    excelSheet.addCell( or );
                    excelSheet.addCell( canR );
                    excelSheet.addCell( cantE );
                    excelSheet.addCell( pE );
                    excelSheet.addCell( cons );
                    excelSheet.addCell( componente );
                    excelSheet.addCell( cr );
                    excelSheet.addCell( coment );
                    excelSheet.addCell( fE );
                    excelSheet.addCell( fac );
                    excelSheet.addCell( emb );

                    excelSheet.addCell( fechaRecibida );
                    excelSheet.addCell( fechaVencida );
                    excelSheet.addCell( orden );
                    excelSheet.addCell( cantidad_reque );
                    excelSheet.addCell( cantidadEntregada );
                    excelSheet.addCell( piezasEntregar );
                    excelSheet.addCell( consignatario );
                    excelSheet.addCell( item_cliente );
                    excelSheet.addCell( CR );
                    excelSheet.addCell( comentario );
                    excelSheet.addCell( fechaEmbarque );
                    excelSheet.addCell( factura );
                    excelSheet.addCell( embarques );

                } catch (WriteException ex) {
                    System.err.println(  ex.getMessage() );
                }
            }
            res.close();         
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }

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
    
    public void WriteExcelTiempoEstandar()
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
            workbook = Workbook.createWorkbook( file1, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "Tiempo Estandar", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT fechaVencida , orden , componente , inst_desm, tiempo_troquelado,tiempo_total FROM tiempoestandar ";
         try{
            PreparedStatement pstm = cn.prepareStatement( sql );
            ResultSet res = pstm.executeQuery();
            System.out.println(  "obteniendo registros.....Listo"  );
 
            while(res.next())
            {
                Label fv  = new Label( 0 , 0, "FECHA VENCIDA" , cf );
                Label or= new Label( 1 , 0, "ORDEN" , cf );                  
                Label compo= new Label( 2 , 0, "COMPONENTE", cf );
                Label instD= new Label( 3 , 0, "INST-DESM" , cf );   
                Label tiempoT= new Label( 4 , 0, "TIEMPO TROQUELADO" , cf ); 
                Label TT= new Label( 5 , 0, "TIEMPO TOTAL", cf ); 

                Label fechaVencida  = new Label( 0 , row, res.getString( "fechaVencida" ) , cf );                  
                Label orden= new Label( 1 , row, res.getString( "orden" ) , cf );                  
                Label componente= new Label( 2 , row, res.getString( "componente" ) , cf );
                Number inst_desm= new Number( 3 , row, res.getLong( "inst_desm" ) , cf );   
                Number tiempo_troquelado= new Number( 4 , row, res.getLong( "tiempo_troquelado" ) , cf ); 
                Number tiempo_total= new Number( 5 , row, res.getLong( "tiempo_total" ) , cf ); 
                  
                row ++;                  
                try {
                    excelSheet.addCell( fv );
                    excelSheet.addCell( or );
                    excelSheet.addCell( compo );
                    excelSheet.addCell( instD );
                    excelSheet.addCell( tiempoT );
                    excelSheet.addCell( TT );

                    excelSheet.addCell( fechaVencida );
                    excelSheet.addCell( orden );
                    excelSheet.addCell( componente );
                    excelSheet.addCell( inst_desm );
                    excelSheet.addCell( tiempo_troquelado );
                    excelSheet.addCell( tiempo_total );                     
                     
                } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                }
            }
             res.close();         
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }

        try {
            workbook.write();
            workbook.close();
            System.out.println(  "Escribiendo en disco....Listo"  );         
        } catch (IOException ex) {
            System.err.println(  ex.getMessage() );
        }catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }

        System.out.println(  "Proceso completado...."  );
    }
    
    public void WriteTallerMecanico()
    {
        int row=4;
        int linea=0;
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf); 
        
        WritableFont wf2 = new WritableFont( WritableFont.ARIAL, 22, WritableFont.NO_BOLD );
        WritableCellFormat cf2 = new WritableCellFormat(wf2); 

        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        try {
            workbook = Workbook.createWorkbook( file2, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "Taller Mecanico", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT orden, fechaV, atrasosproduccion.componente, crs.cr, cantidad, troquel, fechaEnt, reparacion, comentario FROM atrasosproduccion,crs WHERE atrasosproduccion.componente=crs.componente AND comentario LIKE '%TALLER MECANICO%' ORDER BY CONCAT(SUBSTRING_INDEX(fechaV , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaV , '/', 2), '/', -1),SUBSTRING_INDEX(fechaV, '/', 1)) ASC";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
   
              while(res.next())
              {
                  Label letrero= new Label( 3 , 0, "TALLER MECANICO" , cf2 );
                  
                  Label codigo= new Label( 8 , 2, "F-6H 05 REV 1.0" , cf );
                  Label lineas= new Label( 0 , 4, "PT" , cf );
                  Label or= new Label( 1 , 4, "ORDEN" , cf );
                  Label fv  = new Label( 2 , 4, "FECHA VENCIDA" , cf );                                
                  Label compo= new Label( 3 , 4, "COMPONENTE", cf );
                  Label cr= new Label( 4 , 4, "CR" , cf ); 
                  Label cant= new Label( 5 , 4, "CANTIDAD" , cf );
                  Label tro= new Label( 6 , 4, "TROQUEL" , cf ); 
                  Label fechE= new Label( 7 , 4, "FECHA ENTRADA" , cf );
                  Label rep= new Label( 8 , 4, "REPARACION" , cf );
                  Label com= new Label( 9 , 4, "COMENTARIO" , cf ); 
 
                  Number li= new Number( 0 , row, linea , cf );
                  Label orden= new Label( 1 , row, res.getString( "orden" ) , cf ); 
                  Label fechaVencida  = new Label( 2 , row, res.getString( "fechaV" ) , cf );                                 
                  Label componente= new Label( 3 , row, res.getString( "componente" ) , cf );
                  Label CR= new Label( 4 , row, res.getString( "cr" ) , cf );   
                  Number cantidad= new Number( 5 , row, res.getLong( "cantidad" ) , cf ); 
                  Label troquel= new Label( 6 , row, res.getString( "troquel" ) , cf );
                  Label fechaEnt= new Label( 7 , row, res.getString( "fechaEnt" ) , cf );
                  Label reparacion= new Label( 8 , row, res.getString( "reparacion" ) , cf );
                  Label comentario= new Label( 9 , row, res.getString( "comentario" ) , cf ); 

                  row ++;   
                  linea ++;
                 try {
                     excelSheet.addCell( letrero );
                     
                     excelSheet.addCell( codigo );
                     excelSheet.addCell( lineas );
                     excelSheet.addCell( or );
                     excelSheet.addCell( fv );
                     excelSheet.addCell( compo );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( cant );
                     excelSheet.addCell( tro );
                     excelSheet.addCell( fechE );
                     excelSheet.addCell( rep );                     
                     excelSheet.addCell( com );
                     
                     excelSheet.addCell( li );
                     excelSheet.addCell( orden );
                     excelSheet.addCell( fechaVencida );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( CR );
                     excelSheet.addCell( cantidad );
                     excelSheet.addCell( troquel );
                     excelSheet.addCell( fechaEnt );
                     excelSheet.addCell( reparacion );
                     excelSheet.addCell( comentario );                    
                     
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
        }catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }
        System.out.println(  "Proceso completado...."  );
    }
    
    public void WriteExcelAtrasos()
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
            workbook = Workbook.createWorkbook( file3, wbSettings);
            //hoja con nombre de la tabla
            workbook.createSheet( "atrasos", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo" );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT prioridad,fechaRecibida,fechaVencida,orden,cantidad_reque ,cantidadEntregada, piezasEntregar,  consignatario, item_cliente, comentario, fechaEmbarque, factura, embarques FROM analisisatrasos WHERE CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) <= CONVERT(DATE_FORMAT(NOW(), '%Y%m%d'), CHAR) ORDER BY CONCAT(SUBSTRING_INDEX(fechaVencida , '/', -1),SUBSTRING_INDEX(SUBSTRING_INDEX(fechaVencida , '/', 2), '/', -1),SUBSTRING_INDEX(fechaVencida, '/', 1)) ASC ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo" );

              while(res.next())
              {
                  Label fr  = new Label( 0 , 0, "FECHA RECIBIDA" , cf );                  
                  Label fv= new Label( 1 , 0, "FECHA VENCIDA" , cf );                  
                  Label or= new Label( 2 , 0, "ORDEN", cf );
                  Label canR= new Label( 3 , 0, "CANTIDAD REQUERIDA" , cf );   
                  Label cantE= new Label( 4 , 0, "CANTIDAD ENTREGADA" , cf ); 
                  Label pE= new Label( 5 , 0, "PIEZAS ENTEGAR", cf ); 
                  Label cons= new Label( 6 , 0, "CONSIGNATARIO" , cf ); 
                  Label componente= new Label( 7 , 0, "COMPONENTE" , cf ); 
                  Label coment= new Label( 8 , 0, "COMENTARIO" , cf ); 
                  Label fE= new Label( 9 , 0, "FECHA DE EMBARQUE" , cf ); 
                  Label fac= new Label( 10 , 0, "FACTURA" , cf ); 
                  Label emb= new Label( 11 , 0, "EMBARQUES" , cf ); 
                  
                  Label fechaRecibida  = new Label( 0 , row, res.getString( "fechaRecibida" ) , cf );                  
                  Label fechaVencida= new Label( 1 , row, res.getString( "fechaVencida" ) , cf );                  
                  Label orden= new Label( 2 , row, res.getString( "orden" ) , cf );
                  Number cantidad_reque= new Number( 3 , row, res.getLong( "cantidad_reque" ) , cf );   
                  Number cantidadEntregada= new Number( 4 , row, res.getLong( "cantidadEntregada" ) , cf ); 
                  Number piezasEntregar= new Number( 5 , row, res.getLong( "piezasEntregar" ) , cf ); 
                  Label consignatario= new Label( 6 , row, res.getString( "consignatario" ) , cf ); 
                  Label item_cliente= new Label( 7 , row, res.getString( "item_cliente" ) , cf ); 
                  Label comentario= new Label( 8 , row, res.getString( "comentario" ) , cf ); 
                  Label fechaEmbarque= new Label( 9 , row, res.getString( "fechaEmbarque" ) , cf ); 
                  Label factura= new Label( 10 , row, res.getString( "factura" ) , cf ); 
                  Label embarques= new Label( 11 , row, res.getString( "embarques" ) , cf ); 
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( fr );
                     excelSheet.addCell( fv );
                     excelSheet.addCell( or );
                     excelSheet.addCell( canR );
                     excelSheet.addCell( cantE );
                     excelSheet.addCell( pE );
                     excelSheet.addCell( cons );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( coment );
                     excelSheet.addCell( fE );
                     excelSheet.addCell( fac );
                     excelSheet.addCell( emb );
                     
                     excelSheet.addCell( fechaRecibida );
                     excelSheet.addCell( fechaVencida );
                     excelSheet.addCell( orden );
                     excelSheet.addCell( cantidad_reque );
                     excelSheet.addCell( cantidadEntregada );
                     excelSheet.addCell( piezasEntregar );
                     excelSheet.addCell( consignatario );
                     excelSheet.addCell( item_cliente );
                     excelSheet.addCell( comentario );
                     excelSheet.addCell( fechaEmbarque );
                     excelSheet.addCell( factura );
                     excelSheet.addCell( embarques );

                 } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                 } 
              }
             res.close();         
         }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
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
