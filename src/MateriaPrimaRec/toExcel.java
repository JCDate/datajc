/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MateriaPrimaRec;

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
  
  File file = new File("MateriaPrimaRecibida.xls");
  
  
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
    public void WriteExcelMateriaPrimaRecibida()
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
            workbook.createSheet( "MateriaPrimaRecibida", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT ordenCompra,calibre,proveedor,kgSolicitados,kgRecibidos,factura,fechaRecibida FROM mprecibida ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label ordenC  = new Label( 0 , 0, "ORDEN COMPRA" , cf );                  
                  Label cal= new Label( 1 , 0, "CALIBRE" , cf );                  
                  Label prov= new Label( 2 , 0, "PROVEEDOR" , cf );
                  Label kgs= new Label( 3 , 0, "KG SOLICITADOS" , cf ); 
                  Label kgr= new Label( 4 , 0, "KG RECIBIDOS" , cf ); 
                  Label fac= new Label( 5 , 0, "FACTURA" , cf );
                  Label fecR= new Label( 6 , 0, "FECHA RECIBIDA" , cf ); 
                                    
                  Label ordenCompra = new Label( 0 , row, res.getString( "ordenCompra" ) , cf );                  
                  Label calibre= new Label( 1 , row, res.getString( "calibre" ) , cf ); 
                  Label proveedor= new Label( 2 , row, res.getString( "proveedor" ) , cf );
                  Label kgSol= new Label( 3 , row, res.getString( "kgSolicitados" ) , cf );
                  Label kgRec= new Label( 4 , row, res.getString( "kgRecibidos" ) , cf );
                  Label factura= new Label( 5 , row, res.getString( "factura" ) , cf );
                  Label fechaRec= new Label( 6 , row, res.getString( "fechaRecibida" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( ordenC );
                     excelSheet.addCell( cal );
                     excelSheet.addCell( prov);
                     excelSheet.addCell( kgs );
                     excelSheet.addCell( kgr );
                     excelSheet.addCell( fac );
                     excelSheet.addCell( fecR );                     

                     excelSheet.addCell( ordenCompra );
                     excelSheet.addCell( calibre );
                     excelSheet.addCell( proveedor );
                     excelSheet.addCell( kgSol );
                     excelSheet.addCell( kgRec );
                     excelSheet.addCell( factura );
                     excelSheet.addCell( fechaRec );

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
