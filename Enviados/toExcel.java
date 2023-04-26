/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enviados;

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
  //Entregados
  File file = new File("Entregados.xls");
  
  
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
    public void WriteEntregados()
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
            workbook.createSheet( "Entregados", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT fechaRecibida , fechaVencida , orden , cantidad_reque, cantidadEntregada,piezasEntregar,consignatario,item_cliente,comentario,fechaEmbarque,factura,embarques FROM entregados ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
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
