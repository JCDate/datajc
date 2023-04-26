/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepDiarioProd;

import Servicios.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JOptionPane;
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
 
  File file = new File("Reporte Diario Produccion.xls");

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
    public void WriteExcelRep()
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
            workbook.createSheet( "ReporteDiarioProduccion", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        String mes=JOptionPane.showInputDialog("INGRESAR FECHA (MM/YYYY): \n 01 ENERO \n 02 FEBRERO \n 03 MARZO \n 04 ABRIL \n 05 MAYO \n 06 JUNIO \n 07 JULIO \n 08 AGOSTO \n 09 SEPTIEMBRE \n 10 OCTUBRE \n 11 NOVIEMBRE \n 12 DICIEMBRE\n\n");
        //Consulta SQL 
        String sql = "SELECT orden,componente,cr,contS,fecha,operador,noMaquina,actividad,operacion,tOp,inicio,fin,cantProd,comentario FROM repdiprod WHERE SUBSTRING(fecha, 4, 7) ='"+mes+"'";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  
                  Label or= new Label( 0 , 0, "ORDEN" , cf );                  
                  Label comp= new Label( 1 , 0, "COMPONENTE", cf );
                  Label c= new Label( 2 , 0, "C/R" , cf );   
                  Label cantS= new Label( 3 , 0, "CANT. SOLICITADA" , cf ); 
                  Label fe= new Label( 4 , 0, "FECHA", cf ); 
                  Label op= new Label( 5 , 0, "OPERADOR" , cf ); 
                  Label noM= new Label( 6 , 0, "NO. MAQUINA" , cf ); 
                  Label act= new Label( 7 , 0, "ACTIVIDAD" , cf ); 
                  Label opera= new Label( 8 , 0, "OPERACION" , cf ); 
                  Label Topera= new Label( 9 , 0, "TOTAL OP." , cf );
                  Label ini= new Label( 10 , 0, "INICIO" , cf ); 
                  Label fi= new Label( 11 , 0, "FIN" , cf ); 
                  Label cantP= new Label( 12 , 0, "CANT. PRODUCIDA" , cf ); 
                  Label com= new Label( 13 , 0, "COMENTARIO" , cf ); 
                  
                     
                  Label orden= new Label( 0 , row, res.getString( "orden" ) , cf );                  
                  Label componente= new Label( 1 , row, res.getString( "componente" ) , cf );
                  Label cr= new Label( 2 , row, res.getString( "cr" ) , cf );   
                  Number contS= new Number( 3 , row, res.getLong( "contS" ) , cf ); 
                  Label fecha= new Label( 4 , row, res.getString( "fecha" ) , cf ); 
                  Label operador= new Label( 5 , row, res.getString( "operador" ) , cf ); 
                  Label noMaquina= new Label( 6 , row, res.getString( "noMaquina" ) , cf ); 
                  Label actividad= new Label( 7 , row, res.getString( "actividad" ) , cf ); 
                  Label operacion= new Label( 8 , row, res.getString( "operacion" ) , cf ); 
                  Label tOp= new Label( 9 , row, res.getString( "tOp" ) , cf ); 
                  Label inicio= new Label( 10 , row, res.getString( "inicio" ) , cf ); 
                  Label fin= new Label( 11 , row, res.getString( "fin" ) , cf ); 
                  Number cantProd= new Number( 12 , row, res.getLong( "cantProd" ) , cf );
                  Label comentario= new Label( 13 , row, res.getString( "comentario" ) , cf );
                  
                  row ++;                  
                 try {
                    
                     excelSheet.addCell( or );
                     excelSheet.addCell( comp );
                     excelSheet.addCell( c );
                     excelSheet.addCell( cantS );
                     excelSheet.addCell( fe );
                     excelSheet.addCell( op );
                     excelSheet.addCell( noM );
                     excelSheet.addCell( act );
                     excelSheet.addCell( opera );
                     excelSheet.addCell( Topera );
                     excelSheet.addCell( ini );
                     excelSheet.addCell( fi );
                     excelSheet.addCell( cantP );
                     excelSheet.addCell( com );
                     
                    
                     excelSheet.addCell( orden );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( contS );
                     excelSheet.addCell( fecha );
                     excelSheet.addCell( operador );
                     excelSheet.addCell( noMaquina );
                     excelSheet.addCell( actividad );
                     excelSheet.addCell( operacion );
                     excelSheet.addCell( tOp );
                     excelSheet.addCell( inicio );
                     excelSheet.addCell( fin );
                     excelSheet.addCell( cantProd );
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
        }
        catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }

        System.out.println(  "Proceso completado...."  );
    }
 
}
